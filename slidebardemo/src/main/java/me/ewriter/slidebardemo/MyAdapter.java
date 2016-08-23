package me.ewriter.slidebardemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zubin on 2016/8/23.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

    private Context mContext;
    private List<String> mList = new ArrayList<>();
    private Map<String, Integer> map = new HashMap<>();

    public MyAdapter(Context mContext) {
        this.mContext = mContext;
        String[] array = mContext.getResources().getStringArray(R.array.list);
        for (int i = 0; i < array.length; i++) {
            String name = array[i];
            String head = array[i].substring(0, 1).toUpperCase();

            if (!Character.isLetter(head.charAt(0))) {
                head = "#";
            }
            if (!map.containsKey(head)) {
                map.put(head, i);
            }

            mList.add(name);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        TextView tv = (TextView) holder.itemView;
        tv.setText(mList.get(position));
    }

    /**返回第一个字母所在位置*/
    public int getPositionFromLetter(String letter) {
        if (map.containsKey(letter))
            return map.get(letter);
        else
            return -1;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        public MyHolder(View itemView) {
            super(itemView);
        }
    }
}
