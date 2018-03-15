package com.app.concertbud.concertbuddies.Networking;

import android.content.res.Resources;
import android.util.Log;

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
 * Created by csadmin on 3/15/2018.
 */

public class FacebookContext {
    private static final String TAG = FacebookContext.class.getSimpleName();
    public static FacebookContext instance = new FacebookContext();

    private Retrofit retrofit;
    private OkHttpClient client;

    private FacebookContext () {
        client = new OkHttpClient
                .Builder().connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LoggerInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(Resources.getSystem().getString(R.string.facebook_graph_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // TODO: Double check if we need JWT
            Request request = chain.request().newBuilder().build();
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
