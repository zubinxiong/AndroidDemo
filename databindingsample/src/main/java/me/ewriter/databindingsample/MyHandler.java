package me.ewriter.databindingsample;

import android.view.View;
import android.widget.Toast;

/**
 * Created by Zubin on 2016/8/22.
 * 点击事件的handler :Method References
 */
public class MyHandler {

    public void onClickUserName(View view){
        Toast.makeText(view.getContext(), "onClickUserName", Toast.LENGTH_SHORT).show();
    }

    public void onClickPassWord(View view) {
        Toast.makeText(view.getContext(), "onClickPassWord", Toast.LENGTH_SHORT).show();
    }
}
