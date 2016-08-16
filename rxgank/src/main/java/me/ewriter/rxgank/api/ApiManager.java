package me.ewriter.rxgank.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zubin on 2016/8/16.
 */
public class ApiManager {

    public static final String API_BASE_URL = "http://gank.io/api/";
    public static final int DEFAULT_TIMEOUT = 10;

    public static final String CATEGORY_ANDROID = "Android";
    public static final String CATEGORY_WELFARE = "福利";
    public static final String CATEGORY_IOS = "iOS";
    public static final String CATEGORY_ALL = "all";

    private static GankApi sGankApi;

    protected static final Object monitor = new Object();

    public static GankApi getsGankApi() {
        synchronized (monitor) {
            if (sGankApi == null) {
                initGankApi();
            }
            return sGankApi;
        }
    }

    private static void initGankApi() {
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = builder.client(client).build();

        sGankApi = retrofit.create(GankApi.class);
    }
}
