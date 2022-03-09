package com.jdw.nftcreator;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class C2971a extends BaseAdapter {
    private static LayoutInflater f14170b = null;
    private final String[] f14171a;

    C2971a(Activity activity, String[] strArr) {
        this.f14171a = strArr;
        f14170b = (LayoutInflater) activity.getSystemService("layout_inflater");
    }

    public int getCount() {
        return this.f14171a.length;
    }

    public Object getItem(int i) {
        return Integer.valueOf(i);
    }

    public long getItemId(int i) {
        return i;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        C2970a c2970a;
        if (view == null) {
            view = f14170b.inflate(R.layout.item_row, viewGroup, false);
            C2970a c2970a2 = new C2970a();
            c2970a2.f14168a = view.findViewById(R.id.mywork_row_main_layout);
            c2970a2.f14169b = view.findViewById(R.id.mywork_row_image);
            view.setTag(c2970a2);
            c2970a = c2970a2;
        } else {
            c2970a = (C2970a) view.getTag();
        }
        c2970a.f14169b.setImageBitmap(BitmapFactory.decodeFile(this.f14171a[i]));
        return view;
    }

    static class C2970a {
        RelativeLayout f14168a;
        ImageView f14169b;

        C2970a() {
        }
    }
}
