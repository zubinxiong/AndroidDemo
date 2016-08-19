package me.ewriter.rxgank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.ewriter.rxgank.api.entity.GankItem;

/**
 * Created by Zubin on 2016/8/16.
 */
public class SimpeFragmentAdapter extends RecyclerView.Adapter<SimpeFragmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<GankItem> mTitleList;

    public SimpeFragmentAdapter(Context mContext, List<GankItem> title) {
        this.mContext = mContext;
        this.mTitleList = title;
    }

    @Override
    public SimpeFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpeFragmentAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(mTitleList.get(position).getDesc());
    }

    @Override
    public int getItemCount() {
        return mTitleList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView;
        }
    }
}
