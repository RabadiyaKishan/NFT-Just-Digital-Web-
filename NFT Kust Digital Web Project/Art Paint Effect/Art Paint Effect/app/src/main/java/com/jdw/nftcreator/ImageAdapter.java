package com.jdw.nftcreator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageAdapter extends BaseAdapter {
    private final Integer[] mThumbIds;
    ViewHolderItem viewHolder;
    private final Context context;

    public ImageAdapter(Context context, Integer[] mThumbIds) {
        this.context = context;
        this.mThumbIds = mThumbIds;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) this.context.getSystemService("layout_inflater");
        View gridView = new View(this.context);
        this.viewHolder = new ViewHolderItem();
        gridView = inflater.inflate(R.layout.edit_grid_item, null);
        this.viewHolder.imageView = gridView.findViewById(R.id.img_theme);
        gridView.setTag(this.viewHolder);
        Picasso.with(this.context).load(this.mThumbIds[position].intValue()).placeholder(R.drawable.round_placeholder).into(this.viewHolder.imageView);
        return gridView;
    }

    public int getCount() {
        return this.mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    static class ViewHolderItem {
        ImageView imageView;

        ViewHolderItem() {
        }
    }
}
