package com.jdw.nftcreator;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Matrix.ScaleToFit;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore.Images.Media;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.jess.ui.TwoWayAdapterView;
import com.jess.ui.TwoWayAdapterView.OnItemClickListener;
import com.jess.ui.TwoWayGridView;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import jp.co.cyberagent.android.gpuimage.GPUImage.ScaleType;
import jp.co.cyberagent.android.gpuimage.GPUImageView;
import jp.co.cyberagent.android.gpuimage.Rotation;


public class EditActivity extends AppCompatActivity {

    public ArrayList<String> mSelectedPack = new ArrayList();
    ImageView BackgroundBlurLayer;
    Uri ImageUri;
    Integer Module = Integer.valueOf(0);
    Bitmap bitmap;
    String encoded = "";
    GPUImageView img;
    InputStream is = null;
    Bitmap finalbitmap;
    DiscreteSeekBar seek;
    Toolbar toolbar;
    int h, w;
    Bitmap finaleffect;
    Bitmap normalSeleceted;
    String Path;
    Bitmap bitmap1;
    ProgressDialog progress3;
    int opecity = 255;
    private AlertDialog dialog;
    private boolean exit = false;
    private TwoWayGridView mPackGrid;

    public static int getCameraPhotoOrientation(String imageFilePath) {
        try {
            ExifInterface exif = new ExifInterface(imageFilePath);
            Log.d("exifOrientation", exif.getAttribute("Orientation"));
            int orientation = exif.getAttributeInt("Orientation", 1);
            Log.d("orientation :", orientation + "");
            switch (orientation) {
                case 3:
                    return SubsamplingScaleImageView.ORIENTATION_180;
                case 6:
                    return 90;
                case 8:
                    return SubsamplingScaleImageView.ORIENTATION_270;
                default:
                    return 0;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static Bitmap scaleBitmapAndKeepRation(Bitmap TargetBmp, int reqHeightInPixels, int reqWidthInPixels) {
        Matrix m = new Matrix();
        m.setRectToRect(new RectF(0.0f, 0.0f, (float) TargetBmp.getWidth(), (float) TargetBmp.getHeight()), new RectF(0.0f, 0.0f, (float) reqWidthInPixels, (float) reqHeightInPixels), ScaleToFit.CENTER);
        return Bitmap.createBitmap(TargetBmp, 0, 0, TargetBmp.getWidth(), TargetBmp.getHeight(), m, true);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) EditActivity.this
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    private Bitmap download_Image(String url) {

        Bitmap bmp = null;
        try {
            URL ulrn = new URL(url);
            HttpURLConnection con = (HttpURLConnection) ulrn.openConnection();
            InputStream is = con.getInputStream();
            bmp = BitmapFactory.decodeStream(is);
            if (null != bmp)
                return bmp;

        } catch (Exception e) {
        }
        return bmp;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact);

        progress3 = new ProgressDialog(EditActivity.this);
        progress3.setTitle("Please Wait");
        progress3.setMessage("Image Processing...");
        progress3.setCancelable(false);
        progress3.show();


        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progress3.dismiss();
            }
        }, 3000);


        initilizeVariables();
        if (getIntent().hasExtra("image")) {
            Path = getIntent().getStringExtra("image");
            this.ImageUri = Uri.parse(Path);
            this.img.setImage(this.ImageUri);
            AsyncTask.execute(new Runnable() {
                public void run() {
                    try {
                        bitmap1 = EditActivity.this.getScaledBitMapBaseOnScreenSize(Media.getBitmap(EditActivity.this.getContentResolver(), EditActivity.this.ImageUri));
                        EditActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                EditActivity.this.compressimage(bitmap1);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            this.ImageUri = Uri.parse(getIntent().getStringExtra("ImageUri"));
            this.img.setImage(this.ImageUri);
            AsyncTask.execute(new Runnable() {
                public void run() {
                    try {
                        EditActivity.this.compressimage(EditActivity.this.getScaledBitMapBaseOnScreenSize(Media.getBitmap(EditActivity.this.getContentResolver(), EditActivity.this.ImageUri)));
                        EditActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        if (getIntent().hasExtra("module")) {
            this.Module = Integer.valueOf(getIntent().getIntExtra("module", 1));
        }

        this.BackgroundBlurLayer = findViewById(R.id.BackgroundBlurLayer);
        this.BackgroundBlurLayer.setImageResource(AllStaticData.BackgroundImage.intValue());

        initGrid();
    }

    void compressimage(Bitmap imageview_bitmap) {

        normalSeleceted = imageview_bitmap;
        ProgressDialog progress2;
        progress2 = new ProgressDialog(EditActivity.this);
        progress2.setTitle("Loading");
        progress2.setMessage(" Please Wait Art Working...");
        progress2.setCancelable(false);
        progress2.show();

        this.bitmap = imageview_bitmap;
        finalbitmap = imageview_bitmap;
        int aaa = bitmap.getHeight();
        double ggg = bitmap.getHeight() * 0.2;
        int he = 0;
        Bitmap bitmap1 = Bitmap.createBitmap(this.bitmap.getWidth(), this.bitmap.getHeight() + he, Bitmap.Config.RGB_565);
        Bitmap bmOverlay = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), bitmap1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);
        canvas.drawBitmap(this.bitmap, new Matrix(), null);
        canvas.drawBitmap(this.bitmap, 0, 0, null);
        this.bitmap = bmOverlay;

        h = bitmap.getHeight() * 5 / 100;
        w = bitmap.getWidth() * 5 / 100;

        int h1 = bitmap.getHeight() + (h * 2);
        int w1 = bitmap.getWidth() + (w * 2);

        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap bmp = Bitmap.createBitmap(w1, h1, conf);
        Canvas canvas1 = new Canvas(bmp);

        bitmap = putOverlay(bmp, bitmap, w, h);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaleBitmapAndKeepRation(this.bitmap, 1024, 1024).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        this.encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);

        byte[] bytes = encoded.getBytes();
        int e = bytes.length;

        if (e > 740000) {

            byteArrayOutputStream = new ByteArrayOutputStream();
            scaleBitmapAndKeepRation(this.bitmap, 900, 900).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            this.encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            bytes = encoded.getBytes();
            e = bytes.length;

        }

        if (e > 740000) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            scaleBitmapAndKeepRation(this.bitmap, 720, 720).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            this.encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            bytes = encoded.getBytes();
            e = bytes.length;
        }


        if (e > 740000) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            scaleBitmapAndKeepRation(this.bitmap, 620, 620).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            this.encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            bytes = encoded.getBytes();
            e = bytes.length;
        }

        if (e > 740000) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            scaleBitmapAndKeepRation(this.bitmap, 520, 520).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            this.encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            bytes = encoded.getBytes();
            e = bytes.length;
        }


        if (e > 740000) {
            byteArrayOutputStream = new ByteArrayOutputStream();
            scaleBitmapAndKeepRation(this.bitmap, 400, 400).compress(CompressFormat.PNG, 100, byteArrayOutputStream);
            this.encoded = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
            bytes = encoded.getBytes();
            e = bytes.length;
        }
        progress2.dismiss();
        return;
    }

    public Bitmap putOverlay(Bitmap bitmap, Bitmap overlay, int ww, int hh) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(overlay, ww, hh, paint);
        return bitmap;
    }

    private void initilizeVariables() {
        this.img = findViewById(R.id.img);
        this.img.setScaleType(ScaleType.CENTER_INSIDE);
        seek = findViewById(R.id.seek);
        seek.setProgress(255);
        seek.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar seekBar, int value, boolean fromUser) {
                opecity = value;
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar seekBar) {
                if (finaleffect != null) {

                    Bitmap aa = makeTransparent(finaleffect, opecity);
                    img.getGPUImage().deleteImage();
                    img.setImage(createSingleImageFromMultipleImages(normalSeleceted, aa));
                }
            }
        });
        this.mPackGrid = findViewById(R.id.gridview1);
        this.BackgroundBlurLayer = findViewById(R.id.BackgroundBlurLayer);
        this.mSelectedPack.add("filte-57-hd");
        this.mSelectedPack.add("filte-58-hd");
        this.mSelectedPack.add("filte-59-hd");
        this.mSelectedPack.add("filte-60-hd");
        this.mSelectedPack.add("filte-61-hd");
        this.mSelectedPack.add("filte-62-hd");
        this.mSelectedPack.add("filte-63-hd");
        this.mSelectedPack.add("filte-64-hd");
        this.mSelectedPack.add("filte-65-hd");
        this.mSelectedPack.add("filte-66-hd");
        this.mSelectedPack.add("filte-67-hd");
        this.mSelectedPack.add("filte-68-hd");
        this.mSelectedPack.add("filte-69-hd");
        this.mSelectedPack.add("filte-70-hd");
        this.mSelectedPack.add("filte-71-hd");
        this.mSelectedPack.add("filte-72-hd");
        this.mSelectedPack.add("filte-73-hd");
        this.mSelectedPack.add("filte-74-hd");
        this.mSelectedPack.add("filte-75-hd");
        this.mSelectedPack.add("filte-76-hd");
        this.mSelectedPack.add("filte-77-hd");
        this.mSelectedPack.add("filte-78-hd");
        this.mSelectedPack.add("filte-79-hd");
        this.mSelectedPack.add("filte-80-hd");
        this.mSelectedPack.add("filte-81-hd");
        this.mSelectedPack.add("filte-82-hd");
        this.mSelectedPack.add("filte-83-hd");
        this.mSelectedPack.add("filte-84-hd");
        this.mSelectedPack.add("filter-3-th-hd");
        this.mSelectedPack.add("filter-4-th-hd");
        this.mSelectedPack.add("filter-5-th-hd");
        this.mSelectedPack.add("filter-6-th-hd");
        this.mSelectedPack.add("filter-7-th-hd");
        this.mSelectedPack.add("filter-8-th-hd");
        this.mSelectedPack.add("filter-9-th-hd");
        this.mSelectedPack.add("filter-10-th-hd");
        this.mSelectedPack.add("filter-11-th-hd");
        this.mSelectedPack.add("filter-12-th-hd");
        this.mSelectedPack.add("filter-13-th-hd");
        this.mSelectedPack.add("filter-14-th-hd");
        this.mSelectedPack.add("filter-15-th-hd");
        this.mSelectedPack.add("filter-16-th-hd");
        this.mSelectedPack.add("filter-17-th-hd");
        this.mSelectedPack.add("filter-18-th-hd");
        this.mSelectedPack.add("filter-19-th-hd");
        this.mSelectedPack.add("filter-20-th-hd");
        this.mSelectedPack.add("filter-21-th-hd");
        this.mSelectedPack.add("filter-22-th-hd");
        this.mSelectedPack.add("filter-23-th-hd");
        this.mSelectedPack.add("filter-24-th-hd");
        this.mSelectedPack.add("filter-25-th-hd");
        this.mSelectedPack.add("filter-26-th-hd");
        this.mSelectedPack.add("filter-27-th-hd");
        this.mSelectedPack.add("filter-28-th-hd");
        this.mSelectedPack.add("filter-29-th-hd");
        this.mSelectedPack.add("filter-30-th-hd");
        this.mSelectedPack.add("filter-33-th-hd");
        this.mSelectedPack.add("filter-34-th-hd");
        this.mSelectedPack.add("filter-35-th-hd");
        this.mSelectedPack.add("filter-36-th-hd");
        this.mSelectedPack.add("filter-37-th-hd");
        this.mSelectedPack.add("filter-38-th-hd");
        this.mSelectedPack.add("filter-39-th-hd");
        this.mSelectedPack.add("filter-40-th-hd");
        this.mSelectedPack.add("filter-41-hd");
        this.mSelectedPack.add("filter-42-hd");
        this.mSelectedPack.add("filter-43-hd");
        this.mSelectedPack.add("filter-44-hd");
        this.mSelectedPack.add("filter-45-hd");
        this.mSelectedPack.add("filter-46-hd");
        this.mSelectedPack.add("filter-47-hd");
        this.mSelectedPack.add("filter-48-hd");
        this.mSelectedPack.add("filter-49-hd");
        this.mSelectedPack.add("filter-50-hd");
        this.mSelectedPack.add("filter-51-hd");
        this.mSelectedPack.add("filter-52-hd");
        this.mSelectedPack.add("filter-53-hd");
        this.mSelectedPack.add("filter-54-hd");
        this.mSelectedPack.add("filter-55-hd");
        this.mSelectedPack.add("filter-56-hd");
        this.mSelectedPack.add("filter-57-hd");
        this.mSelectedPack.add("filter-58-hd");
        this.mSelectedPack.add("filter-59-hd");
        this.mSelectedPack.add("filter-60-hd");
        this.mSelectedPack.add("filter-61-hd");
        this.mSelectedPack.add("filter-62-hd");
        this.mSelectedPack.add("flame3-th-hd");
        this.mSelectedPack.add("molnia2-th-hd");
        this.mSelectedPack.add("plast7-hd");
        this.mSelectedPack.add("z18-hd");
    }

    private Bitmap createSingleImageFromMultipleImages(Bitmap firstImage, Bitmap secondImage) {

        Bitmap result = Bitmap.createBitmap(firstImage.getWidth(), firstImage.getHeight(), firstImage.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(firstImage, new Matrix(), null);
        secondImage = Bitmap.createScaledBitmap(secondImage, firstImage.getWidth(), firstImage.getHeight(), false);
        canvas.drawBitmap(secondImage, 0, 0, null);
        return result;
    }

    public Bitmap makeTransparent(Bitmap src, int value) {
        int width = src.getWidth();
        int height = src.getHeight();
        Bitmap transBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(transBitmap);
        canvas.drawARGB(0, 0, 0, 0);
        final Paint paint = new Paint();
        paint.setAlpha(value);
        canvas.drawBitmap(src, 0, 0, paint);
        return transBitmap;
    }

    private void initGrid() {
        this.mPackGrid.setAdapter(new ImageAdapter(this, AllStaticData.artifactsIds));
        this.mPackGrid.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(TwoWayAdapterView parent, View v, int position, long id) {
                try {
                    if (EditActivity.this.encoded.equals("")) {
                        EditActivity.this.compressimage(EditActivity.this.img.getGPUImage().getBitmapWithFilterApplied());
                    }
                    new Callapi(EditActivity.this.encoded, EditActivity.this.img, EditActivity.this.mSelectedPack.get(position)).execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private Bitmap getScaledBitMapBaseOnScreenSize(Bitmap bitmapOriginal) {
        Bitmap scaledBitmap = null;
        try {
            int orientation = getCameraPhotoOrientation(this.ImageUri.getPath());
            Matrix matrix = new Matrix();
            matrix.postRotate((float) orientation);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmapOriginal, 0, 0, bitmapOriginal.getWidth(), bitmapOriginal.getHeight(), matrix, true);
            int targetWidth = this.img.getWidth();
            return Bitmap.createScaledBitmap(rotatedBitmap, targetWidth, (int) (((double) targetWidth) * (((double) rotatedBitmap.getHeight()) / ((double) rotatedBitmap.getWidth()))), false);
        } catch (Exception e) {
            e.printStackTrace();
            return scaledBitmap;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        String path;
        if (id == R.id.action_done) {
            Bitmap finalimagesave = img.getGPUImage().getBitmapWithFilterApplied();
            path = storeImage(finalimagesave);
            Intent i = new Intent(getApplicationContext(), SaveActivity.class);
            i.putExtra("path", path);
            startActivity(i);
        } else {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            return null;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pictureFile.toString();
    }

    private File getOutputMediaFile() {
        File mediaStorageDir = new File(APPUtility.getAppDir().toString()
                + "/"
                + this.getResources().getString(R.string.app_name));
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());
        File mediaFile;
        String mImageName = "Paint_Art-" + getRandomNumber(100, 1000) + "_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    public void onBackPressed() {
        if (this.exit) {
            finish();
            return;
        }
        Toast.makeText(this, "Press Back again to Exit.", Toast.LENGTH_SHORT).show();
        this.exit = true;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                EditActivity.this.exit = false;
            }
        }, 3000);
    }

    private class Callapi extends AsyncTask<Object, Object, Bitmap> {
        String encoded;
        String file_link;
        GPUImageView imageView;
        String type;
        ProgressDialog progress1;


        public Callapi(String encoded, GPUImageView imageView, String type) {
            this.encoded = encoded;
            this.imageView = imageView;
            this.type = type;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            progress1 = new ProgressDialog(EditActivity.this);
            progress1.setTitle("Please Wait");
            progress1.setMessage("Applying Effect...");
            progress1.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress1.setCancelable(false);
            progress1.show();
        }

        protected Bitmap doInBackground(Object... voids) {
            ArrayList<param> a1 = new ArrayList();
            a1.add(new param("fileToUpload", this.encoded));
            Bitmap xxx;


            String finalResult;
            String HttpUrl = "https://butterflycreativestudio.com/CartoonEffect/arteffect.php";
            finalResult = postText(HttpUrl, type);
            Log.e("finalResult", "" + finalResult);

            try {
                this.file_link = new geturl().makeHttpRequestpost("http://color.photofuneditor.com/" + finalResult, a1).optString("file_link");
                xxx = download_Image("http://color.photofuneditor.com/output/" + file_link);
                int w0, w1, h0, h1;
                h0 = xxx.getHeight() * 5 / 100;
                w0 = xxx.getWidth() * 5 / 100;
                w1 = xxx.getWidth() - (w0 * 2);
                h1 = xxx.getHeight() - (h0 * 2);
                float scaleWidth = ((float) w1) / xxx.getWidth();
                float scaleHeight = ((float) h1) / xxx.getHeight();
                Matrix matrix = new Matrix();
                matrix.postScale(scaleWidth, scaleHeight);
                xxx = Bitmap.createBitmap(xxx, w0, h0, w1, h1, matrix, true);
                finalbitmap = xxx;
                return xxx;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap res) {
            super.onPostExecute(res);

            if (res == null) {
                progress1.dismiss();
                if (!isNetworkAvailable()) {
                    Toast.makeText(EditActivity.this, "Please Check Internet Conncetion", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(EditActivity.this, "Please After Some Time To apply effect Because App Is Under Processing", Toast.LENGTH_LONG).show();
                }
                return;
            }
            finaleffect = res;
            progress1.dismiss();
            this.imageView.setRotation(Rotation.NORMAL);
            Callapi.this.imageView.getGPUImage().deleteImage();
            Callapi.this.imageView.setImage(res);
            seek.setProgress(255);
        }


        String postText(String url, String id) {
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("filter", id));
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httpPost);
                HttpEntity resEntity = response.getEntity();

                if (resEntity != null) {
                    String responseStr = EntityUtils.toString(resEntity).trim();
                    Log.e("error1", "Response: " + responseStr);
                    return responseStr;
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.e("error", "Response: no1");

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("error", "Response: no1");
            }
            return "aaa";
        }
    }
}
