package me.ewriter.eventbusdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by Zubin on 2016/9/5.
 */
public class BottomFragment extends Fragment {

    TextView textView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.bottom, container, false);
        textView = (TextView) layout.findViewById(R.id.demo_tap_txt);
        return layout;
    }

    @Subscribe
    public void receiveMessage(TestEvent event) {
        textView.setVisibility(View.VISIBLE);
        textView.setAlpha(1f);
        ViewCompat.animate(textView).alphaBy(-1f).setDuration(400);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
