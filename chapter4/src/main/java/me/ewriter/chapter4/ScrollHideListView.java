package me.ewriter.chapter4;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ScrollHideListView extends AppCompatActivity implements View.OnTouchListener, AbsListView.OnScrollListener {

    private Toolbar mToolbar;
    private ListView mListView;
    private String[] mStr = new String[20];
    private int mTouchSlop;
    private float mFirstY;
    private float mCurrentY;
    private int direction;
    private ObjectAnimator mAnimator;
    private boolean mShow = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_hide_list_view);

        // 获得系统认为的最低滚动的距离
        mTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mListView = (ListView) findViewById(R.id.listview);

        for (int i = 0; i < mStr.length; i++) {
            mStr[i] = "Item " + i;
        }

        View header = new View(this);
        header.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                (int) getResources().getDimension( R.dimen.abc_action_bar_default_height_material)));

        mListView.addHeaderView(header);
        mListView.setAdapter(new ArrayAdapter<String>(
                ScrollHideListView.this,
                android.R.layout.simple_expandable_list_item_1,
                mStr));

        mListView.setOnTouchListener(this);
        // ListView的滑动监听还有OnScrollListener
        mListView.setOnScrollListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mFirstY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                mCurrentY = event.getY();
//                if (mCurrentY - mFirstY > mTouchSlop) {
//                    direction = 0; // down
//                } else if (mFirstY - mCurrentY > mTouchSlop) {
//                    direction = 1; // up
//                }
//
//                if (direction == 1) {
//                    if (mShow) {
//                        toolbarAnim(1); // 显示toolbar
//                        mShow = !mShow;
//                    }
//                } else if (direction == 0) {
//                    if (!mShow) {
//                        toolbarAnim(0); // 隐藏toolbar
//                        mShow = !mShow;
//                    }
//                }


                // 这部分代码和上面书中的代码效果一样，只是以自己理解的方式重新写了一遍
                if (mCurrentY - mFirstY > mTouchSlop) {
                    // 手指向下，即逐渐显示item9,8,7 之类的。显示toolbar
                    if (!mShow) {
                        toolbarAnim(0);
                        mShow = !mShow;
                    }
                } else if (mFirstY - mCurrentY > mTouchSlop) {
                    // 手指向上，即逐渐显示item8,9,10这类的。隐藏toolbar
                    if (mShow) {
                        toolbarAnim(1);
                        mShow = !mShow;
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
                break;
        }
        return false;
    }

    /**
     *  toolbar 显示动画
     * @param flag flag = 0 时显示，flag = 1 时隐藏
     */
    private void toolbarAnim(int flag) {
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        // 坐标参考：http://leeeyou.xyz/Android-Android%E5%9D%90%E6%A0%87%E4%BD%93%E7%B3%BB
        if (flag == 0) {
            // 属性动画，View， 平移动画，后面的章节还会具体的介绍，这里不过多解释
            // 显示toolbar
            mAnimator = ObjectAnimator.ofFloat(mToolbar,
                    "translationY", mToolbar.getTranslationY(), 0);
        } else {
            // 隐藏toolbar，getTranslationY() 为0 ，需要让toolbar 移动负向的距离
            mAnimator = ObjectAnimator.ofFloat(mToolbar,
                    "translationY", mToolbar.getTranslationY(),
                    -mToolbar.getHeight());
        }
        mAnimator.start();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                // 手指抛动时，惯性滑动的状态
                break;
            case SCROLL_STATE_IDLE:
                // 停止滚动
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                // 正在滚动
                break;
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // view， 当前能看见的第一个item id， 当前能看见的item 总数，整个listview 的item 总数
    }
}
