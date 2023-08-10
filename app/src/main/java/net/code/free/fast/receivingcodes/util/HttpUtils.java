package net.code.free.fast.receivingcodes.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpUtils {

    private static final String TAG = "HttpUtils";

    public static void sendGetRequest(String urlString, final HttpCallback<String>  callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Request failed: " + e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    String responseString = response.body().string();
                    Log.d(TAG, "Response: " + responseString);

                    JSONObject jsonObject = new JSONObject(responseString);
                    JSONArray result = jsonObject.getJSONArray("result");


                    callback.onSuccess(result.toString());

                } catch (Exception e) {
                    Log.e(TAG, "JSON parse failed: " + e.getMessage());
                    callback.onFailure(e);

                    //请求失败，重新发起请求
                    sendGetRequest(urlString,callback);

                }
            }
        });
    }

    public static void sendPostRequest(String urlString, String requestBody, final HttpCallback<String> callback) {


        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(requestBody, mediaType);
        Request request = new Request.Builder()
                .url(urlString)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Request failed: " + e);
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response!=null) {
                    try {
                        String responseString = response.body().string();
                        Log.d(TAG, "Response: " + responseString);
                        callback.onSuccess(responseString);
                    } catch (Exception e) {
                        Log.e(TAG, "JSON parse failed: " + e.getMessage());
                        callback.onFailure(e);
                    }
                }
            }
        });
    }


    public static void getBitmapFromURL(String urlString, final HttpCallback<Bitmap> callback) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(urlString)
                .get()
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Request failed: " + e.getMessage());
                callback.onFailure(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    InputStream input = response.body().byteStream();
                    Bitmap bitmap = BitmapFactory.decodeStream(input);
                    input.close();


                    callback.onSuccess(bitmap);

                } catch (Exception e) {
                    Log.e(TAG, "JSON parse failed: " + e.getMessage());
                    callback.onFailure(e);
                }
            }
        });
    }

    public interface HttpCallback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
