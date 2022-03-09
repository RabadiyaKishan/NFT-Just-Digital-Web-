package com.jdw.nftcreator;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.facebook.ads.Ad;
import com.facebook.ads.AudienceNetworkAds;
import com.facebook.ads.InterstitialAdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.File;

public class SaveActivity extends AppCompatActivity {

    ImageView mImageView;
    Bitmap mBitmap;
    Uri myUri;
    Button fb, insta, what, share, save, back, download_image;
    RelativeLayout fbll, install, whatll, sharell, savell, backll, download;
    String path;
    private InterstitialAd mInterstitialAd;
    private com.facebook.ads.InterstitialAd interstitialAd;

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_activity);

        fb = findViewById(R.id.facebook);
        insta = findViewById(R.id.insta);
        what = findViewById(R.id.whatsup);
        share = findViewById(R.id.share);
        save = findViewById(R.id.save);
        back = findViewById(R.id.back);
        download_image = findViewById(R.id.download_image);

        savell = findViewById(R.id.savell);
        backll = findViewById(R.id.backll);
        fbll = findViewById(R.id.facebookll);
        install = findViewById(R.id.install);
        whatll = findViewById(R.id.whatsupll);
        sharell = findViewById(R.id.sharell);
        mImageView = findViewById(R.id.mainImageView);
        download = findViewById(R.id.download);

        Intent in = getIntent();
        path = in.getStringExtra("path");
        Log.e("path-", path);

        mImageView.setImageURI(Uri.parse("file://" + path));
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + path)));
        myUri = Uri.parse(path);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AudienceNetworkAds.initialize(SaveActivity.this);

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, getString(R.string.google_interstitial), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                SaveActivity.this.mInterstitialAd = interstitialAd;
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(SaveActivity.this);
                } else {
                }
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
                FacebookInterstitialAd();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageBtnClicked();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backk();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook();
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instagram();
            }
        });
        what.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsup();
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
        download_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImageBtnClicked();
            }
        });

    }

    public void saveImageBtnClicked() {
        Toast.makeText(SaveActivity.this, "NFT Save Successfully !", Toast.LENGTH_SHORT).show();
    }

    public void backk() {
        File fdelete = new File(myUri.getPath());
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + myUri.getPath());
            } else {
                System.out.println("file not Deleted :" + myUri.getPath());
            }
        }
        finish();
        startActivity(new Intent(SaveActivity.this, MainActivity.class));
    }

    public void facebook() {
        final Uri data = FileProvider.getUriForFile(SaveActivity.this, getPackageName() + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.setPackage("com.facebook.katana");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));
    }

    public void instagram() {
        final Uri data = FileProvider.getUriForFile(SaveActivity.this, getPackageName() + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.setPackage("com.instagram.android");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));
    }

    public void whatsup() {
        final Uri data = FileProvider.getUriForFile(SaveActivity.this, getPackageName() + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.setPackage("com.whatsapp");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        String shareMessage = getString(R.string.app_name) + "\n\nLet me recommend you this application\n\n";
        shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
        sendIntent.putExtra("jid", 91 + "@s.whatsapp.net");
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));
    }

    public void share() {
        final Uri data = FileProvider.getUriForFile(SaveActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", new File(path));
        SaveActivity.this.grantUriPermission(SaveActivity.this.getPackageName(), data, Intent.
                FLAG_GRANT_READ_URI_PERMISSION);
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("image/*");
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Image");
        sendIntent.putExtra(Intent.EXTRA_STREAM, data);
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        sendIntent.putExtra("android.intent.extra.TEXT", getResources().getString(R.string.app_name));
        SaveActivity.this.startActivity(Intent.createChooser(sendIntent, "Share Image:"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SaveActivity.this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }

    protected void FacebookInterstitialAd() {
        interstitialAd = new com.facebook.ads.InterstitialAd(SaveActivity.this, getString(R.string.facebook_interstitial));
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
            }

            @Override
            public void onError(Ad ad, com.facebook.ads.AdError adError) {
                // Ad error callback
                Log.d("Error: ", adError.toString());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
            }
        };
        interstitialAd.loadAd(interstitialAd.buildLoadAdConfig().withAdListener(interstitialAdListener).build());
    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }
}
