package com.jdw.nftcreator;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.facebook.ads.AdSize;
import com.facebook.ads.AudienceNetworkAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.kinda.alert.KAlertDialog;
import com.yalantis.ucrop.UCrop;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    public static Uri fileUri;
    int shim_gallry = 11;
    TextView pp;
    int iddd;
    LinearLayout pip;
    ImageView rateus, share, moreapp;
    MainActivity f14111a;
    private AdView mAdView;
    private View adContainer;

    private void BannerAd() {
        MobileAds.initialize(MainActivity.this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        adContainer = findViewById(R.id.bannerAd);
        AdView mAdView = new AdView(MainActivity.this);
        mAdView.setAdSize(com.google.android.gms.ads.AdSize.SMART_BANNER);
        mAdView.setAdUnitId(getString(R.string.google_banner));
        ((RelativeLayout) adContainer).addView(mAdView);
        com.google.android.gms.ads.AdRequest adRequest = new com.google.android.gms.ads.AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                FacebookBannerAD();
            }
        });
    }

    private void FacebookBannerAD() {
        adContainer.setVisibility(View.GONE);
        AudienceNetworkAds.initialize(MainActivity.this);
        com.facebook.ads.AdView FacebookAdView = new com.facebook.ads.AdView(MainActivity.this, getString(R.string.facebook_banner), AdSize.BANNER_HEIGHT_90);
        LinearLayout adContainer = findViewById(R.id.banner_container);
        adContainer.setVisibility(View.VISIBLE);
        adContainer.addView(FacebookAdView);
        FacebookAdView.loadAd();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BannerAd();
        LinearLayout f14137d = findViewById(R.id.btn_albume);
        f14137d.setOnClickListener(new C29582(MainActivity.this));
        pip = findViewById(R.id.btn_gallary);
        rateus = findViewById(R.id.btn_rate);
        moreapp = findViewById(R.id.img_moreapps);

        pip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iddd = 15;
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent1, shim_gallry);
            }
        });

        rateus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rate = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName()));
                try {
                    startActivity(rate);
                } catch (ActivityNotFoundException back5) {
                    Toast.makeText(
                            MainActivity.this, "You don't have Google Play installed or Internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
        moreapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SEND");
                intent.setType("text/plain");
                intent.putExtra("android.intent.extra.TEXT", MainActivity.this.getResources().getString(R.string.app_name) + " Create By : https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                MainActivity.this.startActivity(Intent.createChooser(intent, "Share App"));
            }
        });
        init();

    }

    void init() {
        if (isOnline()) {
        } else {
            KAlertDialog a = new KAlertDialog(this, KAlertDialog.WARNING_TYPE);
            a.setTitleText("No Internet or WIFI?");
            a.setContentText("Please Check Your Internet Connection!");
            a.setCancelText("Exit");
            a.setConfirmText("Try Again");
            a.showCancelButton(true);
            a.setConfirmClickListener(new KAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(KAlertDialog kAlertDialog) {
                    init();
                    kAlertDialog.dismissWithAnimation();
                }
            });
            a.setCancelClickListener(new KAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(KAlertDialog sDialog) {
                    sDialog.cancel();
                    finish();
                }
            });
            a.setCancelable(false);
            a.show();
            Toast.makeText(this, "Please turn on Internet or Wifi", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
            } else {
                requestPermission();
            }
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == shim_gallry && resultCode == -1) {
            if (data != null) {
                fileUri = data.getData();
                startCropActivity(data.getData());
            } else {
                Toast.makeText(this, "error", Toast.LENGTH_LONG).show();
                finish();
            }
        } else if (UCrop.REQUEST_CROP == requestCode) {
            Log.e("crop", "if");
            if (resultCode == RESULT_OK) {
                Log.e("crop1", "if");
                handleCropResult(data);
            } else {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        } else if (UCrop.RESULT_ERROR == requestCode) {
            handleCropError(data);
        }
    }

    private void startCropActivity(Uri data) {
        String destinationFileName = "crop";
        destinationFileName += ".jpg";
        UCrop uCrop = UCrop.of(data, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        UCrop.Options options = new UCrop.Options();
        options.setToolbarColor(getResources().getColor(R.color.backgroundcolor));
        options.setToolbarWidgetColor(getResources().getColor(R.color.whiteOrg1));
        options.setStatusBarColor(getResources().getColor(R.color.backgroundcolor));
        options.setActiveWidgetColor(getResources().getColor(R.color.whiteOrg));
        options.setToolbarTitle("Crop");
        uCrop.withOptions(options);
        uCrop.start(MainActivity.this);
    }

    private void handleCropResult(@NonNull Intent result) {
        Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            Log.e("", "");
            Intent i = new Intent(this, EditActivity.class);
            i.putExtra("image", "" + resultUri);
            startActivity(i);
        } else {
            Toast.makeText(MainActivity.this, "Failed To Crop", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Toast.makeText(MainActivity.this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "unexpected_error", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    class C29582 implements View.OnClickListener {
        C29582(MainActivity homeActivity) {
            f14111a = homeActivity;
        }

        public void onClick(View view) {
            f14111a.startActivity(new Intent(f14111a, AlbumeActivity.class));
        }
    }
}
