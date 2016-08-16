package me.ewriter.rxgank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.ewriter.rxgank.PictureFragment;
import me.ewriter.rxgank.R;

/**
 * Created by Zubin on 2016/8/16.
 */
public class PictureFragmentAdapter extends RecyclerView.Adapter<PictureFragmentAdapter.MyViewHolder> {

    private Context mContext;
    private List<String> mUrlList;

    public PictureFragmentAdapter(Context mContext, List<String> mUrlList) {
        this.mContext = mContext;
        this.mUrlList = mUrlList;
    }

    @Override
    public PictureFragmentAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(PictureFragmentAdapter.MyViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(mUrlList.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUrlList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
        }
    }
}
