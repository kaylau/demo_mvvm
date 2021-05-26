package com.kay.demo.net.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 18:03
 * @Description:
 */
public class BaseResponse<T> implements Serializable {


    /**
     * 页大小
     */
    @SerializedName("pageSize")
    private int pageSize;

    /**
     * 当前页数
     */
    @SerializedName("page")
    private int page;

    /**
     * 总页数
     */
    @SerializedName("pageCount")
    private int pageCount;

    /**
     * 返回码
     */
    @SerializedName("returnCode")
    private int returnCode;

    /**
     * 消息响应的主体
     */
    @SerializedName("data")
    private T data;

    /**
     * 提示信息
     */
    @SerializedName("message")
    private String message;

    /**
     * 异常信息
     */
    @SerializedName("ex")
    private String ex;

    /**
     * 请求流水号
     */
    @SerializedName("serialNo")
    private String serialNo;


    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEx() {
        return ex;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
