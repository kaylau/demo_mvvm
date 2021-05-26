package com.kay.demo.net.intercepter;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.kay.demo.utils.MMKVUtils;
import com.kay.demo.utils.SystemUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.TreeMap;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 17:59
 * @Description:
 */
public class HeaderInterceptor implements Interceptor {


    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
//        TreeMap<String, Object> treeMap = getRequestBody(request);
        Request newRequest = request.newBuilder()
                .addHeader("versionCode", "1.0.0")
                .addHeader("deviceId", SystemUtils.getIMEI())
                .addHeader("token", MMKVUtils.INSTANCE.getString("token"))
                .addHeader("Accept", "application/json,text/plain,*/*")
                .build();
        return chain.proceed(newRequest);
    }

    private TreeMap<String, Object> getRequestBody(Request request) {
        if (request != null) {
            RequestBody body = request.body();
            String httpUrl = request.url().toString();
            if (TextUtils.isEmpty(httpUrl)) {
                return null;
            }
            if (TextUtils.equals(request.method(), "GET") || body instanceof MultipartBody) {
                return buildTreeMap(httpUrl);
            } else {
                if (body instanceof FormBody) {
                    TreeMap<String, Object> treeMap = new TreeMap<>();
                    FormBody formBody = (FormBody) body;
                    for (int i = 0; i < formBody.size(); i++) {
                        String key = formBody.name(i);
                        String value = formBody.value(i);
                        treeMap.put(key, value);
                    }
                    return treeMap;
                } else {
                    try {
                        Buffer bufferedSink = new Buffer();
                        body.writeTo(bufferedSink);
                        String result = bufferedSink.readUtf8();
                        return new Gson().<TreeMap<String, Object>>fromJson(result, TreeMap.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    private TreeMap<String, Object> buildTreeMap(String httpUrl) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        if (httpUrl.contains("?")) {
            String[] list = httpUrl.split("\\?");
            if (list[1].contains("&")) {
                String[] requestList = list[1].split("&");
                for (String s : requestList) {
                    try {
                        String[] split = s.split("=");
                        String key = URLDecoder.decode(split[0], "UTF-8");
                        String value = "";
                        if (split.length == 2) {
                            value = URLDecoder.decode(split[1], "UTF-8");
                        }
                        treeMap.put(key, value);
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                try {
                    String key = URLDecoder.decode(list[1].split("=")[0], "UTF-8");
                    String value = URLDecoder.decode(list[1].split("=")[1], "UTF-8");
                    treeMap.put(key, value);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return treeMap;
    }
}
