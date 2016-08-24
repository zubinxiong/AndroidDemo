package me.ewriter.dynamiclistview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Zubin on 2016/8/24.
 * ListView 的drag 效果
 * https://www.youtube.com/watch?v=_BZIvjMgH-Q
 */
public class DynamicListView extends ListView {

    private static final String TAG = DynamicListView.class.getSimpleName();

    public ArrayList<String> mCheeseList;

    private final int SMOOTH_SCROLL_AMOUNT_AT_EDGE = 15;
    private final int MOVE_DURATION = 150;
    // 细线的宽度
    private final int LINE_THICKNESS = 15;

    private int mLastEventY = -1;

    // down 是的 y 坐标
    private int mDownY = -1;
    // down 时的 x 坐标
    private int mDownX = -1;

    // view移动后的 top
    private int mTotalOffset = 0;

    // 是否长按住了 item
    private boolean mCellIsMobile = false;
    // 是否在滚动
    private boolean mIsMobileScrolling = false;
    // 到达顶部时 listview 要移动的距离
    private int mSmoothScrollAmountAtEdge = 0;

    private final int INVALID_ID = -1;
    // 当前长按 上面一个 item 的 id
    private long mAboveItemId = INVALID_ID;
    // 当前长按的 itemID
    private long mMobileItemId = INVALID_ID;
    // 当前长按 下面一个 item 的 id
    private long mBelowItemId = INVALID_ID;

    // 长按后悬浮的 BitmapDrawable ，增加了黑色的描边
    private BitmapDrawable mHoverCell;
    // 长按悬浮的 cell 当前尺寸
    private Rect mHoverCellCurrentBounds;
    // 长按悬浮的 cell 原始尺寸
    private Rect mHoverCellOriginalBounds;

    private final int INVALID_POINTER_ID = -1;
    private int mActivePointerId = INVALID_POINTER_ID;

    private boolean mIsWaitingForScrollFinish = false;
    private int mScrollState = OnScrollListener.SCROLL_STATE_IDLE;

    public DynamicListView(Context context) {
        super(context);
        init(context);
    }

    public DynamicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DynamicListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOnItemLongClickListener(mOnItemLongClickListener);
        setOnScrollListener(mScrollListener);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        mSmoothScrollAmountAtEdge = (int)(SMOOTH_SCROLL_AMOUNT_AT_EDGE / metrics.density);
    }

    /**
     * Listens for long clicks on any items in the listview. When a cell has
     * been selected, the hover cell is created and set up.
     */
    private OnItemLongClickListener mOnItemLongClickListener = new OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            mTotalOffset = 0;

            // downPostion 就是 Listview 的 item position ，比如 1-100
            int downPosition = pointToPosition(mDownX, mDownY);
            // itemNum 是当前可见 item 的位置，比如当前实际是10， 但确实屏幕上可见的第4 个
            int itemNum = downPosition - getFirstVisiblePosition();

            // 获取当前可见的这个 View ,也就是手指长按的View
            View selectedView = getChildAt(itemNum);

            mMobileItemId = getAdapter().getItemId(downPosition);
            mHoverCell = getAndAddHoverView(selectedView);
            selectedView.setVisibility(INVISIBLE);

            mCellIsMobile = true;

            updateNeighborViewsForID(mMobileItemId);

            Log.d(TAG, "downPosition = " + downPosition + "; itemNum = " + itemNum);

            return true;
        }
    };


    public void setCheeseList(ArrayList<String> cheeseList) {
        mCheeseList = cheeseList;
    }

    /**
     * Creates the hover cell with the appropriate bitmap and of appropriate
     * size. The hover cell's BitmapDrawable is drawn on top of the bitmap every
     * single time an invalidate call is made.
     */
    private BitmapDrawable getAndAddHoverView(View v) {
        int w = v.getWidth();
        int h = v.getHeight();
        int top = v.getTop();
        int left = v.getLeft();

        Bitmap b = getBitmapWithBorder(v);

        BitmapDrawable drawable = new BitmapDrawable(getResources(), b);

        // 这里后面两个参数实际上就是后面这个,只是这里手动计算的 int right = v.getRight(); int bottom = v.getBottom();
        mHoverCellOriginalBounds = new Rect(left, top, left + w, top + h);
        mHoverCellCurrentBounds = new Rect(mHoverCellOriginalBounds);

        drawable.setBounds(mHoverCellCurrentBounds);

        return drawable;
    }

    /** Draws a black border over the screenshot of the view passed in. */
    private Bitmap getBitmapWithBorder(View v) {
        Bitmap bitmap = getBitmapFromView(v);
        Canvas can = new Canvas(bitmap);

        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(LINE_THICKNESS);
        paint.setColor(Color.BLACK);

        can.drawBitmap(bitmap, 0, 0, null);
        can.drawRect(rect, paint);

        return bitmap;
    }

    /** Returns a bitmap showing a screenshot of the view passed in. */
    private Bitmap getBitmapFromView(View v) {
        Bitmap bitmap = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        // 用Bitmap 承载这个 画布 Canvas
        Canvas canvas = new Canvas(bitmap);
        v.draw(canvas);
        return bitmap;
    }

    /**
     * Stores a reference to the views above and below the item currently
     * corresponding to the hover cell. It is important to note that if this
     * item is either at the top or bottom of the list, mAboveItemId or mBelowItemId
     * may be invalid.
     * 更新当前长按的item 上下的id (同时最多只会影响相邻的两个 item)，但要注意第一个和最后一个
     */
    private void updateNeighborViewsForID(long itemID) {
        int position = getPositionForID(itemID);
        StableArrayAdapter adapter = ((StableArrayAdapter)getAdapter());
        // 记录下当前长按 item 的上一个 和 下一个 的 itemid
        mAboveItemId = adapter.getItemId(position - 1);
        mBelowItemId = adapter.getItemId(position + 1);
    }

    /** Retrieves the position in the list corresponding to itemID */
    public int getPositionForID(long itemID) {
        View view = getViewForID(itemID);
        if (view == null) {
            return -1;
        } else {
            return getPositionForView(view);
        }
    }

    /** Retrieves the view in the list corresponding to itemID */
    public View getViewForID(long itemID) {
        int firstVisiblePosition = getFirstVisiblePosition();
        StableArrayAdapter adapter = (StableArrayAdapter) getAdapter();
        // 注意这里用的是 getChildCount 也是指可见总数， 全部总数是 getCount
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            int postion = firstVisiblePosition + i;
            // 但我们这里要通过adapter 去获取实际上的View ，所以要加上 firstVisible
            long id = adapter.getItemId(postion);
            if (id == itemID) {
                return v;
            }
        }
        return null;
    }

    /**
     *  dispatchDraw gets invoked when all the child views are about to be drawn.
     *  By overriding this method, the hover cell (BitmapDrawable) can be drawn
     *  over the listview's items whenever the listview is redrawn.
     * 绘制 子 View ，onDraw 是 View 本身
     */
    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mHoverCell != null) {
            mHoverCell.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // ACTION_MASK 是用来处理多点触摸的事件
        // http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2013/0226/911.html
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mDownX = (int) ev.getX();
                mDownY = (int) ev.getY();

                // 第一根手指down 是的信息
                mActivePointerId = ev.getPointerId(0);

                Log.d(TAG, "mDownX = " + mDownX + "; mActivePointerId = " + mActivePointerId);
                break;

            case MotionEvent.ACTION_MOVE:
                if (mActivePointerId == INVALID_POINTER_ID) {
                    break;
                }

                // 第一根手指的 Given a pointer identifier, find the index of its data in the event.
                int pointerIndex = ev.findPointerIndex(mActivePointerId);

                mLastEventY = (int) ev.getY(pointerIndex);
                int deltaY = mLastEventY - mDownY;

                if (mCellIsMobile) {
                    // 当前 rect 的 left 和 top 位置改变，
                    // 这里是上下滑动，所以 left 不变，right + delta + ??
                    mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left,
                            mHoverCellOriginalBounds.top + deltaY + mTotalOffset);
                    // 重新设置 RectF cell 的位置(bounds)
                    mHoverCell.setBounds(mHoverCellCurrentBounds);
                    invalidate();

                    handleCellSwitch();

                    // 上面已经处理完了长按移动的效果，下面是处理在底部或者顶部 listview 滚动的
                    mIsMobileScrolling = false;
                    handleMobileCellScroll();

                    return false;
                }

                break;

            case MotionEvent.ACTION_UP:
                touchEventsEnded();
                break;
            case MotionEvent.ACTION_CANCEL:
                touchEventsCancelled();
                break;
            case MotionEvent.ACTION_POINTER_UP:
                /* If a multitouch event took place and the original touch dictating
                 * the movement of the hover cell has ended, then the dragging event
                 * ends and the hover cell is animated to its corresponding position
                 * in the listview. */
                pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >>
                        MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mActivePointerId) {
                    touchEventsEnded();
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * This method determines whether the hover cell has been shifted far enough
     * to invoke a cell swap. If so, then the respective cell swap candidate is
     * determined and the data set is changed. Upon posting a notification of the
     * data set change, a layout is invoked to place the cells in the right place.
     * Using a ViewTreeObserver and a corresponding OnPreDrawListener, we can
     * offset the cell being swapped to where it previously was and then animate it to
     * its new position.
     * 当移动的距离能够交换的时候，发出通知交换，使用 ViewTreeObserver 在 preDraw 时候处理交换时的动画
     */
    private void handleCellSwitch() {
        // 手指滑动的距离
        final int deltaY = mLastEventY - mDownY;
        // 滑动后View 的 top, 实际上就是 mHoverCellCurrentBounds.top？
        int deltaYTotal = mHoverCellOriginalBounds.top + mTotalOffset + deltaY;

        View belowView = getViewForID(mBelowItemId);
        View mobileView = getViewForID(mMobileItemId);
        View aboveView = getViewForID(mAboveItemId);

        // 地下有 view ，并且悬浮 view 的top 大于原来的top
        boolean isBelow = (belowView != null) && (deltaYTotal > belowView.getTop());
        boolean isAbove = (aboveView != null) && (deltaYTotal < aboveView.getTop());

        // 达到上移或下移的条件
        if (isAbove || isBelow) {
            // 获取要交换位置item 的itemID 和 itemView
            final long switchItemID = isBelow ? mBelowItemId : mAboveItemId;
            View switchView = isBelow ? belowView : aboveView;
            // 当前长按View 的 postion
            final int originalItem = getPositionForView(mobileView);

            if (switchView == null) {
                // 最顶或最低
                updateNeighborViewsForID(mMobileItemId);
                return;
            }

            swapElements(mCheeseList, originalItem, getPositionForView(switchView));

//            ((BaseAdapter) getAdapter()).notifyDataSetChanged();
//
//            // 把移动结束的y赋值给 down
//            mDownY = mLastEventY;
//
//            // 获取要交换位置的 view 的top
//            final int switchViewStartTop = switchView.getTop();
//
//            mobileView.setVisibility(View.VISIBLE);
//            switchView.setVisibility(View.INVISIBLE);
//
//            updateNeighborViewsForID(mMobileItemId);

            mobileView.setVisibility(VISIBLE);
            ((BaseAdapter) getAdapter()).notifyDataSetChanged();
            mDownY = mLastEventY;
            final int switchViewStartTop = switchView.getTop();
            updateNeighborViewsForID(mMobileItemId);

            // 在视图被绘制前调用
            final ViewTreeObserver observer = getViewTreeObserver();
            observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    observer.removeOnPreDrawListener(this);

                    View mobileView = getViewForID(mMobileItemId);
                    if (mobileView != null)
                        mobileView.setVisibility(INVISIBLE);

                    // 获取交换位置后的 View
                    final View switchView = getViewForID(switchItemID);

                    mTotalOffset += deltaY;

                    int switchViewNewTop = switchView.getTop();
                    int delta = switchViewStartTop - switchViewNewTop;

                    switchView.setTranslationY(delta);

                    ObjectAnimator animator = ObjectAnimator.ofFloat(switchView,
                            View.TRANSLATION_Y, 0);
                    animator.setDuration(MOVE_DURATION);
                    animator.start();

                    return true;
                }
            });
        }
    }

    /**
     * 交换位置,传入的参数为list， 悬浮view 的position和 要交换View 的postion
     * */
    private void swapElements(ArrayList arrayList, int indexOne, int indexTwo) {
        Object temp = arrayList.get(indexOne);
        arrayList.set(indexOne, arrayList.get(indexTwo));
        arrayList.set(indexTwo, temp);
    }

    /**
     * This scroll listener is added to the listview in order to handle cell swapping
     * when the cell is either at the top or bottom edge of the listview. If the hover
     * cell is at either edge of the listview, the listview will begin scrolling. As
     * scrolling takes place, the listview continuously checks if new cells became visible
     * and determines whether they are potential candidates for a cell swap.
     *
     * 在边界时处理
     */
    private OnScrollListener mScrollListener = new OnScrollListener () {

        private int mPreviousFirstVisibleItem = -1;
        private int mPreviousVisibleItemCount = -1;
        private int mCurrentFirstVisibleItem;
        private int mCurrentVisibleItemCount;
        private int mCurrentScrollState;

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            mCurrentScrollState = scrollState;
            mScrollState = scrollState;
            isScrollCompleted();
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            mCurrentFirstVisibleItem = firstVisibleItem;
            mCurrentVisibleItemCount = visibleItemCount;

            Log.d(TAG, "firstVisibleItem" + firstVisibleItem + "; visibleItemCount = " + visibleItemCount);

            // 如果第一个 item 是 -1 说明已经是顶部
            mPreviousFirstVisibleItem = (mPreviousFirstVisibleItem == -1) ? mCurrentFirstVisibleItem
                    : mPreviousFirstVisibleItem;
            mPreviousVisibleItemCount = (mPreviousVisibleItemCount == -1) ? mCurrentVisibleItemCount
                    : mPreviousVisibleItemCount;

            checkAndHandleFirstVisibleCellChange();
            checkAndHandleLastVisibleCellChange();

            mPreviousFirstVisibleItem = mCurrentFirstVisibleItem;
            mPreviousVisibleItemCount = mCurrentVisibleItemCount;
        }

        /**
         * This method is in charge of invoking 1 of 2 actions. Firstly, if the listview
         * is in a state of scrolling invoked by the hover cell being outside the bounds
         * of the listview, then this scrolling event is continued. Secondly, if the hover
         * cell has already been released, this invokes the animation for the hover cell
         * to return to its correct position after the listview has entered an idle scroll
         * state.
         */
        private void isScrollCompleted() {
            if (mCurrentVisibleItemCount > 0 && mCurrentScrollState == SCROLL_STATE_IDLE) {
                if (mCellIsMobile && mIsMobileScrolling) {
                    handleMobileCellScroll();
                } else if (mIsWaitingForScrollFinish) {
                    touchEventsEnded();
                }
            }
        }

        /**
         * Determines if the listview scrolled up enough to reveal a new cell at the
         * top of the list. If so, then the appropriate parameters are updated.
         */
        public void checkAndHandleFirstVisibleCellChange() {
            if (mCurrentFirstVisibleItem != mPreviousFirstVisibleItem) {
                if (mCellIsMobile && mMobileItemId != INVALID_ID) {
                    updateNeighborViewsForID(mMobileItemId);
                    handleCellSwitch();
                }
            }
        }

        /**
         * Determines if the listview scrolled down enough to reveal a new cell at the
         * bottom of the list. If so, then the appropriate parameters are updated.
         */
        public void checkAndHandleLastVisibleCellChange() {
            int currentLastVisibleItem = mCurrentFirstVisibleItem + mCurrentVisibleItemCount;
            int previousLastVisibleItem = mPreviousFirstVisibleItem + mPreviousVisibleItemCount;
            if (currentLastVisibleItem != previousLastVisibleItem) {
                if (mCellIsMobile && mMobileItemId != INVALID_ID) {
                    updateNeighborViewsForID(mMobileItemId);
                    handleCellSwitch();
                }
            }
        }

    };


    /**
     *  Determines whether this listview is in a scrolling state invoked
     *  by the fact that the hover cell is out of the bounds of the listview;
     */
    private void handleMobileCellScroll() {
        mIsMobileScrolling = handleMobileCellScroll(mHoverCellCurrentBounds);
    }

    /**
     * This method is in charge of determining if the hover cell is above
     * or below the bounds of the listview. If so, the listview does an appropriate
     * upward or downward smooth scroll so as to reveal new items.
     */
    public boolean handleMobileCellScroll(Rect r) {
        int offset = computeVerticalScrollOffset();
        int height = getHeight();
        int extent = computeVerticalScrollExtent();
        int range = computeVerticalScrollRange();
        int hoverViewTop = r.top;
        int hoverHeight = r.height();

        if (hoverViewTop <= 0 && offset > 0) {
            smoothScrollBy(-mSmoothScrollAmountAtEdge, 0);
            return true;
        }

        if (hoverViewTop + hoverHeight >= height && (offset + extent) < range) {
            smoothScrollBy(mSmoothScrollAmountAtEdge, 0);
            return true;
        }

        return false;
    }

    /**
     * Resets all the appropriate fields to a default state while also animating
     * the hover cell back to its correct location.
     */
    private void touchEventsEnded () {
        final View mobileView = getViewForID(mMobileItemId);
        if (mCellIsMobile|| mIsWaitingForScrollFinish) {
            mCellIsMobile = false;
            mIsWaitingForScrollFinish = false;
            mIsMobileScrolling = false;
            mActivePointerId = INVALID_POINTER_ID;

            // If the autoscroller has not completed scrolling, we need to wait for it to
            // finish in order to determine the final location of where the hover cell
            // should be animated to.
            if (mScrollState != OnScrollListener.SCROLL_STATE_IDLE) {
                mIsWaitingForScrollFinish = true;
                return;
            }

            mHoverCellCurrentBounds.offsetTo(mHoverCellOriginalBounds.left, mobileView.getTop());

            ObjectAnimator hoverViewAnimator = ObjectAnimator.ofObject(mHoverCell, "bounds",
                    sBoundEvaluator, mHoverCellCurrentBounds);
            hoverViewAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    invalidate();
                }
            });
            hoverViewAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    setEnabled(false);
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mAboveItemId = INVALID_ID;
                    mMobileItemId = INVALID_ID;
                    mBelowItemId = INVALID_ID;
                    mobileView.setVisibility(VISIBLE);
                    mHoverCell = null;
                    setEnabled(true);
                    invalidate();
                }
            });
            hoverViewAnimator.start();
        } else {
            touchEventsCancelled();
        }
    }

    /**
     * This TypeEvaluator is used to animate the BitmapDrawable back to its
     * final location when the user lifts his finger by modifying the
     * BitmapDrawable's bounds.
     *  释放后 浮动的view 回到它位置的动画
     */
    private final static TypeEvaluator<Rect> sBoundEvaluator = new TypeEvaluator<Rect>() {
        public Rect evaluate(float fraction, Rect startValue, Rect endValue) {
            return new Rect(interpolate(startValue.left, endValue.left, fraction),
                    interpolate(startValue.top, endValue.top, fraction),
                    interpolate(startValue.right, endValue.right, fraction),
                    interpolate(startValue.bottom, endValue.bottom, fraction));
        }

        public int interpolate(int start, int end, float fraction) {
            return (int)(start + fraction * (end - start));
        }
    };


    /**
     * Resets all the appropriate fields to a default state.
     */
    private void touchEventsCancelled () {
        View mobileView = getViewForID(mMobileItemId);
        if (mCellIsMobile) {
            mAboveItemId = INVALID_ID;
            mMobileItemId = INVALID_ID;
            mBelowItemId = INVALID_ID;
            mobileView.setVisibility(VISIBLE);
            mHoverCell = null;
            invalidate();
        }
        mCellIsMobile = false;
        mIsMobileScrolling = false;
        mActivePointerId = INVALID_POINTER_ID;
    }
}
