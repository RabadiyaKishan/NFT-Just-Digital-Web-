package com.jdw.nftcreator;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;

import mehdi.sakout.fancybuttons.FancyButton;

public class AlbumeActivity extends AppCompatActivity {
    @SuppressLint({"StaticFieldLeak"})
    static LinearLayout f13988c;
    GridView f13989a;
    C2971a f13990b;
    File f13991d;
    Animation f13992e;
    boolean f13993f = true;
    Handler f13994g = new Handler();
    Dialog f13996i;
    Dialog f13997j;
    private String[] f13998k;
    private File[] f13999l;

    ProgressDialog dialog;

    class C29231 implements Comparator {
        final AlbumeActivity f13972a;

        C29231(AlbumeActivity albumeActivity) {
            this.f13972a = albumeActivity;
        }

        public int compare(Object obj, Object obj2) {
            if (((File) obj).lastModified() > ((File) obj2).lastModified()) {
                return -1;
            }
            return ((File) obj).lastModified() < ((File) obj2).lastModified() ? 1 : 0;
        }
    }

    class C29242 implements OnItemClickListener {
        final AlbumeActivity f13973a;

        C29242(AlbumeActivity albumeActivity) {
            this.f13973a = albumeActivity;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
            this.f13973a.m17194a(i);
        }
    }

    class C29274 implements Comparator {
        final AlbumeActivity f13976a;

        C29274(AlbumeActivity albumeActivity) {
            this.f13976a = albumeActivity;
        }

        public int compare(Object obj, Object obj2) {
            if (((File) obj).lastModified() > ((File) obj2).lastModified()) {
                return -1;
            }
            return ((File) obj).lastModified() < ((File) obj2).lastModified() ? 1 : 0;
        }
    }

    @SuppressLint("ResourceType")
    private void m17194a(final int i) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(1);
            dialog.getWindow().setBackgroundDrawableResource(17170445);
            dialog.setContentView(R.layout.zoom);
            ((TouchImageView) dialog.findViewById(R.id.imageView1)).setImageBitmap(m17208a(new File(this.f13998k[i])));
            ((FancyButton) dialog.findViewById(R.id.share)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("image/jpeg");
                        intent.putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(AlbumeActivity.this, "com.androidlab.artpainteffect.provider", new File(AlbumeActivity.this.f13998k[i])));
                        AlbumeActivity.this.startActivity(Intent.createChooser(intent, "Share image using"));
                    } catch (Exception e) {
                    }
                }
            });
            ((FancyButton) dialog.findViewById(R.id.delete)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    view.startAnimation(AlbumeActivity.this.f13992e);
                    AlbumeActivity.this.m17199b(i);
                }
            });
            ((FancyButton) dialog.findViewById(R.id.home)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    view.startAnimation(AlbumeActivity.this.f13992e);
                    dialog.dismiss();
                    AlbumeActivity.this.m17207f();
                }
            });
            dialog.show();
            dialog.getWindow().setLayout(-1, -1);
        } catch (Exception e) {
        }
    }

    public static void m17198b() {
        f13988c.setVisibility(View.VISIBLE);
    }

    @SuppressLint("ResourceType")
    private void m17199b(final int i) {
        try {
            final Dialog dialog = new Dialog(this);
            dialog.getWindow().setBackgroundDrawableResource(17170445);
            dialog.requestWindowFeature(1);
            dialog.setContentView(R.layout.dialog2);
            ((TextView) dialog.findViewById(R.id.txtmsg)).setText("Do you want to delete image?");
            ((Button) dialog.findViewById(R.id.btyesss)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    view.startAnimation(AlbumeActivity.this.f13992e);
                    dialog.dismiss();
                    new File(AlbumeActivity.this.f13998k[i]).delete();
                    AlbumeActivity.this.f13990b.notifyDataSetChanged();
                    AlbumeActivity.this.f13989a.setAdapter(AlbumeActivity.this.f13990b);
                    Intent intent = AlbumeActivity.this.getIntent();
                    AlbumeActivity.this.finish();
                    AlbumeActivity.this.startActivity(intent);
                }
            });
            ((Button) dialog.findViewById(R.id.btnooo)).setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    view.startAnimation(AlbumeActivity.this.f13992e);
                    dialog.dismiss();
                }
            });
            dialog.show();
        } catch (Exception e) {
        }
    }

    private void m17201c() {
        this.f13998k = null;
        if (this.f13991d.isDirectory()) {
            this.f13999l = this.f13991d.listFiles();
            Arrays.sort(this.f13999l, new C29274(this));
            this.f13998k = new String[this.f13999l.length];
            for (int i = 0; i < this.f13999l.length; i++) {
                this.f13998k[i] = this.f13999l[i].getAbsolutePath();
            }
        }
        if (this.f13999l.length == 0) {
            m17198b();
        } else if (this.f13993f) {
            this.f13993f = false;
        }
    }

    private void m17205e() {
        this.f13997j.cancel();
    }

    private void m17207f() {
        m17209a();
    }

    public Bitmap m17208a(File file) {
        int i = 1;
        Bitmap bitmap = null;
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(file), null, options);
            while ((options.outWidth / i) / 2 >= 390 && (options.outHeight / i) / 2 >= 390) {
                i *= 2;
            }
            options = new Options();
            options.inSampleSize = i;
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file), null, options);
        } catch (FileNotFoundException e) {
        }
        return bitmap;
    }

    protected void m17209a() {
        finish();
    }

    public void onBackPressed() {
        m17207f();
        super.onBackPressed();
    }

    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            setContentView(R.layout.activity_albume);


            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Albume");
            toolbar.setTitleTextColor(this.getResources().getColor(R.color.white));
            this.f13992e = AnimationUtils.loadAnimation(this, R.anim.viewpush);
            f13988c = (LinearLayout) findViewById(R.id.ll_nofav);
            if (Environment.getExternalStorageState().equals("mounted")) {
                this.f13991d = new File(APPUtility.getAppDir() + "/" + getString(R.string.app_name) + "/");
                this.f13991d.mkdirs();
            } else {
                Toast.makeText(this, "Error! No SDCARD Found!", Toast.LENGTH_LONG).show();
            }

            Log.e("File", "=" + f13991d);


            if (this.f13991d.isDirectory()) {
                this.f13999l = this.f13991d.listFiles();
                Arrays.sort(this.f13999l, new C29231(this));
                this.f13998k = new String[this.f13999l.length];
                for (int i = 0; i < this.f13999l.length; i++) {
                    this.f13998k[i] = this.f13999l[i].getAbsolutePath();
                }
            }
            m17201c();
            if (this.f13999l.length == 0) {
                m17198b();

                Log.e("xxxx", "xxxx");
            }
            this.f13989a = (GridView) findViewById(R.id.gridviewimage);
            this.f13990b = new C2971a(this, this.f13998k);
            this.f13989a.setAdapter(this.f13990b);
            this.f13989a.setOnItemClickListener(new C29242(this));
        } catch (Exception e) {
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 16908332:
                finish();
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return true;
    }


}
