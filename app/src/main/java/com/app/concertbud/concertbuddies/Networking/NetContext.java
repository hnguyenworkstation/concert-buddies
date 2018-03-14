package com.app.concertbud.concertbuddies.Networking;

import android.util.Log;

import com.app.concertbud.concertbuddies.AppControllers.BaseApplication;
import com.app.concertbud.concertbuddies.AppControllers.BasePreferenceManager;
import com.app.concertbud.concertbuddies.R;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by huongnguyen on 3/3/18.
 */

public class NetContext {
    private static final String TAG = NetContext.class.getSimpleName();
    public static NetContext instance = new NetContext();

    private Retrofit retrofit;
    private OkHttpClient client;

    private NetContext() {
        client = new OkHttpClient
                .Builder().connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggerInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BaseApplication.getInstance().getResources().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            String token = BasePreferenceManager.getDefault().getUserToken();
            Request request;
            if (token != null) {
                request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", String.format("JWT %s", token))
                        .build();
                Log.e(TAG, String.format("intercept: %s", request.headers().toString()));
            }
            else {
                request = chain.request().newBuilder().build();
            }
            return chain.proceed(request);

        }
    }
    private class LoggerInterceptor implements Interceptor {
        private static final String TAG = "LoggerInterceptor";

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Log.d(TAG, String.format("url: %s", request.toString()));

            RequestBody body = request.body();
            if (body != null) Log.d(TAG, String.format("body: %s", body.toString()));

            Headers headers = request.headers();
            if (headers != null) Log.d(TAG, String.format("headers: %s", headers.toString()));

            Response response = chain.proceed(request);
            Log.d(TAG, String.format("response: %s", response.toString()));
            Log.d(TAG, String.format("response body: %s", responseBody(response)));
            return response;
        }
    }

    private String responseBody(Response response) {
        ResponseBody responseBody = response.body();
        BufferedSource source = responseBody.source();
        try {
            source.request(Long.MAX_VALUE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Buffer buffer = source.buffer();
        return buffer.clone().readString(Charset.forName("UTF-8"));
    }

    public <T> T create(Class<T> classz) {
        return retrofit.create(classz);
    }
}
