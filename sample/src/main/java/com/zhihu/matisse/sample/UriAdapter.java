package com.zhihu.matisse.sample;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UriAdapter extends RecyclerView.Adapter<UriAdapter.UriViewHolder> {

    private List<Uri> mUris;
    private List<String> mPaths;
    private Context mContext;

    void setData(List<Uri> uris, List<String> paths) {
        mUris = uris;
        mPaths = paths;
        notifyDataSetChanged();
    }

    @Override
    public UriAdapter.UriViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new UriAdapter.UriViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.uri_item, parent, false));
    }

    @Override
    public void onBindViewHolder(UriAdapter.UriViewHolder holder, int position) {
        holder.mUri.setText(mUris.get(position).toString());
        holder.mPath.setText(mPaths.get(position));

        holder.mUri.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);
        holder.mPath.setAlpha(position % 2 == 0 ? 1.0f : 0.54f);

        Glide.with(mContext).load(mUris.get(position)).into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mUris == null ? 0 : mUris.size();
    }

    static class UriViewHolder extends RecyclerView.ViewHolder {

        private final TextView mUri;
        private final TextView mPath;
        private final ImageView mImageView;

        UriViewHolder(View contentView) {
            super(contentView);
            mUri = contentView.findViewById(R.id.uri);
            mPath = contentView.findViewById(R.id.path);
            mImageView = contentView.findViewById(R.id.image);
        }
    }
}
