package me.ewriter.slidebardemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import me.ewriter.slidebardemo.view.OnTouchLetterListener;
import me.ewriter.slidebardemo.view.SlideBar;

/**
 * 自定义 SlideBar 并不难，主要是需要把list的item转换成相应业务逻辑的排序
 * 这个只是测试用，离实际使用还是有许多地方需要完善，可以参考下面在 github 上找的 start 较多的项目
 * https://github.com/kongnanlive/SideBar
 * https://github.com/CaMnter/EasyRecyclerViewSidebar
 * */
public class MainActivity extends AppCompatActivity implements OnTouchLetterListener {

    SlideBar mSlideBar;
    RecyclerView mRecyclerView;
    MyAdapter adapter;
    // FloatView 应该是在 slidebar 中定义的，不应该在这单独处理
    TextView mFloatView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFloatView = (TextView) findViewById(R.id.float_view);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new MyAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);

        mSlideBar = (SlideBar) findViewById(R.id.slide_bar);
        mSlideBar.setOnTouchLetterListener(this);
    }

    @Override
    public void onTouchLetterChange(String s) {
        mFloatView.setText(s);
        mFloatView.setVisibility(View.VISIBLE);
        mFloatView.postDelayed(new Runnable() {
            @Override
            public void run() {
               mFloatView.setVisibility(View.INVISIBLE);
            }
        }, 1000);
        int index = adapter.getPositionFromLetter(s);
        if (index >= 0) {
            LinearLayoutManager manager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            manager.scrollToPositionWithOffset(index, 0);
        }

    }
}
