package com.jdw.nftcreator;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import jp.wasabeef.picasso.transformations.BuildConfig;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import okhttp3.Request;

public class geturl {
    OkHttpClient client = new Builder().connectTimeout(45, TimeUnit.SECONDS).writeTimeout(45, TimeUnit.SECONDS).readTimeout(45, TimeUnit.SECONDS).build();

    public JSONObject makeHttpRequestpost(String url, ArrayList<param> a1) {
        String json = BuildConfig.FLAVOR;
        int i = 0;
        while (i < a1.size()) {
            try {
                ((param) a1.get(i)).setValue(((param) a1.get(i)).getValue().replace("\"", "\\\"").replace("'", "\\'"));
                i++;
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }
        }


        try {
            json = new geturl().run(url, a1);


            Log.e("json",""+json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            Log.e("json",""+new JSONObject(json));
            return new JSONObject(json);
        } catch (JSONException e2) {
            Log.e("JSON Parser", "Error parsing data " + e2.toString());
            return null;
        }
    }

    public JSONObject makeHttpRequestget(String url) {
        String json = BuildConfig.FLAVOR;
        try {
            json = new geturl().run_Get(url);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        try {
            return new JSONObject(json);
        } catch (JSONException e2) {
            Log.e("JSON Parser", "Error parsing data " + e2.toString());
            return null;
        }
    }

    public String run(String url) throws IOException {
        return this.client.newCall(new Request.Builder().url(url).build()).execute().body().string();
    }

    public String run(String url, ArrayList<param> a1) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        for (int i = 0; i < a1.size(); i++) {
            formBody.add(((param) a1.get(i)).name, ((param) a1.get(i)).value);
        }
        return this.client.newCall(new Request.Builder().url(url).post(formBody.build()).build()).execute().body().string();
    }

    public String run_Get(String url) throws IOException {
        return this.client.newCall(new Request.Builder().url(url).get().build()).execute().body().string();
    }
}
