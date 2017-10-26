package info.androidhive.retrofit.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    public static final String BASE_URL = "https://api.myjson.com/bins/";
    private static Retrofit retrofit = null;
    static final int cacheSize = 10 * 1024 * 1024;


    public static Retrofit getClient(File cacheFile, final Context context) {
        if (retrofit==null) {
            Cache cache = new Cache(cacheFile, cacheSize);

            //looks like that there are thousands of ways of writing this things.
            OkHttpClient okHttpClient = new OkHttpClient.Builder().cache(cache).addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    if (NetworkUtils.isNetworkAvailable(context)) {
                        request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                    } else {
                        request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                    }
                    return chain.proceed(request);
                }
            }).build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}
