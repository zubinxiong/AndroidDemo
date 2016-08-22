package me.ewriter.databindingsample;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.databindingsample.databinding.ListItemBinding;

/**
 * Created by Zubin on 2016/8/22.
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.MyHolder> {

    private Context mContext;
    private List<User> mList;

    public TestAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            User user = new User("item" + i, "pass" + i);
            mList.add(user);
        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new MyHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        User user = mList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        // 根据item 的 xml 名称
        private ListItemBinding binding;

        public MyHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        public void bind(User user) {
            binding.setUser(user);
        }

    }
}
