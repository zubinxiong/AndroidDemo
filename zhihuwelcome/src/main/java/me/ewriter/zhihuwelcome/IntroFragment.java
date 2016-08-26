package me.ewriter.zhihuwelcome;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Zubin on 2016/8/25.
 */
public class IntroFragment extends Fragment {

    TextView mText;
    ImageView mImage;
    private String text;
    private int index;
    int[] res = {R.drawable.intro_1, R.drawable.intro_2, R.drawable.intro_3};

    public static IntroFragment newInstance(String text, int index) {

        Bundle args = new Bundle();
        args.putString("text", text);
        args.putInt("index", index);
        IntroFragment fragment = new IntroFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            text = getArguments().getString("text");
            index = getArguments().getInt("index");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.view_fragment, container, false);
        mText = (TextView) view.findViewById(R.id.intro_text);
        mImage = (ImageView) view.findViewById(R.id.intro_image);
        mText.setText(text);
//        mImage.setImageResource(res[index]);
        mImage.setBackgroundResource(res[index]);
        return view;
    }

    private TextView getIntroTextView() {
        return mText;
    }


}
