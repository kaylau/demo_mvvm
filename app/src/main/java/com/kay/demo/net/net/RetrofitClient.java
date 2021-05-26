package com.kay.demo.net.net;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Log;


import com.kay.demo.BuildConfig;
import com.kay.demo.GlobalApp;
import com.kay.demo.net.intercepter.CommonParamsInterceptor;
import com.kay.demo.net.intercepter.HeaderInterceptor;
import com.kay.demo.net.util.NetCfg;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 18:07
 * @Description:
 */
public class RetrofitClient {

    public static final String TAG = RetrofitClient.class.getSimpleName();

    public static final long CONN_TIMEOUT = 30;
    public static final long WRITER_TIMEOUT = 30;
    public static final long READ_TIMEOUT = 30;

    private static volatile RetrofitClient mHttpHelper = null;
    private static Map<Integer, String> interceptUrlMap;// 保存拦截BaseUrl
    private Retrofit mRetrofit;

    private RetrofitClient() {
    }

    /**
     * 获取实例
     *
     * @return
     */
    public static RetrofitClient getInstance() {
        if (null == mHttpHelper) {
            synchronized (RetrofitClient.class) {
                if (null == mHttpHelper) {
                    mHttpHelper = new RetrofitClient();
                }
            }
        }
        return mHttpHelper;
    }

    /**
     * 初始化
     */
    public static void init() {
        new Builder().initOkHttp().initRetrofit().build();
    }

    public static class Builder {
        private OkHttpClient mOkHttpClient;
        private OkHttpClient.Builder mOkHttpClientBuilder;
        private Retrofit mRetrofit;

        private Builder() {
        }

        /**
         * 初始化OkHttp
         *
         * @return
         */
        public Builder initOkHttp() {
            if (null == mOkHttpClientBuilder) {
                synchronized (RetrofitClient.class) {
                    if (null == mOkHttpClientBuilder) {
                        mOkHttpClientBuilder = new OkHttpClient.Builder()
                                .addInterceptor(new CommonParamsInterceptor())
                                .addNetworkInterceptor(new HeaderInterceptor())
                                .connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS)
                                .writeTimeout(WRITER_TIMEOUT, TimeUnit.SECONDS)
                                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS);
                        if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                                @Override
                                public void log(String message) {
                                    if (TextUtils.isEmpty(message)) {
                                        return;
                                    }
                                    Log.e(TAG, message);
                                }
                            });
                            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

                            mOkHttpClientBuilder.addInterceptor(baseUrlInterceptor()).addNetworkInterceptor(logging);
                        }
                    }
                }
            }
            return this;
        }

        /**
         * 初始化Retrofit
         *
         * @return
         */
        public Builder initRetrofit() {

            Retrofit.Builder builder = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(NetCfg.BASE_URL);
            this.mOkHttpClient = mOkHttpClientBuilder.build();
            this.mRetrofit = builder.client(mOkHttpClient).build();
            return this;
        }

        public void build() {
            RetrofitClient.getInstance().build(this);
        }
    }

    //拦截器-切换BaseUrl
    private static Interceptor baseUrlInterceptor() {
        return chain -> {
            //获取原始的originalRequest
            Request originalRequest = chain.request();
            //获取老的url
            HttpUrl url = originalRequest.url();
            //如果是第三方的url 就不用baseUrl
            String s = url.toString();
            // 追加拦截URL
            if (interceptUrlMap == null)
                interceptUrlMap = new ArrayMap<>();
            interceptUrlMap.put(NetCfg.BASE_URL.hashCode(), NetCfg.BASE_URL);
            boolean hasBaseUrl = false;
            for (int key : interceptUrlMap.keySet()) {
                if (s.contains(interceptUrlMap.get(key))) {
                    hasBaseUrl = true;
                    break;
                }
            }
            //拦截其他url
            if (!hasBaseUrl) return chain.proceed(originalRequest);
            //获取originalRequest的创建者builder
            Request.Builder builder = originalRequest.newBuilder();
            HttpUrl baseURL = HttpUrl.parse(NetCfg.BASE_URL);
            //重建新的HttpUrl，需要重新设置的url部分
            HttpUrl newHttpUrl = url.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build();
            //获取处理后的新newRequest
            Request newRequest = builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        };
    }


    /**
     * 全局创建
     *
     * @param builder
     */
    private void build(Builder builder) {
        if (null == builder || null == builder.mOkHttpClientBuilder
                || null == builder.mOkHttpClient || null == builder.mRetrofit) {
            return;
        }
//        OkHttpClient.Builder mOkHttpClientBuilder = builder.mOkHttpClientBuilder;
//        OkHttpClient mOkHttpClient = builder.mOkHttpClient;
        this.mRetrofit = builder.mRetrofit;
    }

    /**
     * Create Retrofit Instance
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> tClass) {
        if (null == tClass || null == mRetrofit) return null;
        return mRetrofit.create(tClass);
    }
}
