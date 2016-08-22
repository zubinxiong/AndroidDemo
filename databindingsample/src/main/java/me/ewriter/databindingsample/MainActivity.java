package me.ewriter.databindingsample;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import me.ewriter.databindingsample.BR;
import me.ewriter.databindingsample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // 这个实例代码仅仅是对文档中部分内容的例子 https://developer.android.com/topic/libraries/data-binding/index.html
    // 更多高级的用法参考这篇文章 https://realm.io/cn/news/data-binding-android-boyar-mount/
    // http://blog.zhaiyifan.cn/2016/07/06/android-new-project-from-0-p8/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 绑定数据 -方法

        // 文档上还写的是根据 xml 生成的反向文件(MainActivityBinding) 是错误的，实际上是正向
        // 使用 下面的方法发生错误，参考下面的链接 reset 并重启解决
        // http://stackoverflow.com/questions/32170597/databindingutil-setcontentview-type-parameter-t-has-incompatible-upper-bounds
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        User user = new User("Test", "abcpassword");
//        binding.setUser(user); 这个和下面的效果一致
        binding.setVariable(BR.user, user);

        MyHandler handler = new MyHandler();
        binding.setHandlers(handler);

        // 这里的 recyclerview 是根据 id 自动生成的
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerview.setAdapter(new TestAdapter(this));

    }
}
