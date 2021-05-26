package com.kay.demo.net.intercepter;

import android.text.TextUtils;


import com.kay.demo.utils.SystemUtils;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 17:57
 * @Description:
 */
public class CommonParamsInterceptor implements Interceptor {

    @NotNull
    @Override
    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
        //添加公共参数
        Map<String, String> params = new HashMap<>();
        params.put("deviceId", SystemUtils.getIMEI());

        Request request = chain.request();
        Request newRequest = parseRequest(request, params);
        return chain.proceed(newRequest);
    }

    private Request parseRequest(Request request, Map<String, String> params) {
        Request newRequest;
        String method = request.method().toLowerCase(Locale.getDefault());
        switch (method) {
            case "post":
                newRequest = addPostParams(request, params);
                break;
            default:
                newRequest = addGetParams(request, params);
        }
        return newRequest;
    }

    private Request addGetParams(Request request, Map<String, String> params) {
        HttpUrl httpUrl = urlAppendParams(request, params);
        return request.newBuilder()
                .url(httpUrl)
                .build();
    }

    private Request addPostParams(Request request, Map<String, String> params) {
        RequestBody body = request.body();
        if (body == null) {
            return request;
        }
        if (body instanceof FormBody) {
            FormBody.Builder builder = new FormBody.Builder();
            FormBody formBody = (FormBody) body;
            int size = formBody.size();
            //添加原来已有的参数
            for (int i = 0; i < size; i++) {
                builder.add(formBody.name(i), formBody.value(i));
            }
            //添加额外的参数
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            return request.newBuilder()
                    .post(builder.build())
                    .build();
        } else if (body instanceof MultipartBody) {
            return request;
        } else {
            //Body提交
            Buffer buffer = new Buffer();
            try {
                body.writeTo(buffer);
                String json = buffer.readUtf8();
                if (TextUtils.isEmpty(json)) {
                    return request;
                }
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(json);
                    for (Map.Entry<String, String> entry : params.entrySet()) {
                        jsonObject.put(entry.getKey(), entry.getValue());
                    }
                    String content = jsonObject.toString();
                    return request.newBuilder()
                            .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), content))
                            .build();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public HttpUrl urlAppendParams(Request request, Map<String, String> params) {
        HttpUrl.Builder builder = request.url().newBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String mapKey = entry.getKey();
            String mapValue = entry.getValue();
            builder.addQueryParameter(mapKey, mapValue);
        }
        return builder.build();
    }
}
