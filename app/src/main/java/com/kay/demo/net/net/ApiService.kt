package com.kay.demo.net.net

import com.kay.demo.net.model.request.FeedbackReq
import com.kay.demo.net.model.BaseResponse
import com.kay.demo.net.model.request.LoginReq
import com.kay.demo.net.model.request.RegisterReq
import com.kay.demo.net.model.respone.ListRsp
import com.kay.demo.net.model.respone.LoginSignUpRsp
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 18:00
 * @Description:
 */
interface ApiService {

    @POST("api/user/register")
    suspend fun register(@Body req: RegisterReq): BaseResponse<LoginSignUpRsp>

    @POST("api/user/login")
    suspend fun login(@Body req: LoginReq): BaseResponse<LoginSignUpRsp>

    @POST("api/user/feedback")
    suspend fun feedback(@Body req: FeedbackReq): BaseResponse<Nothing>

    @GET("api/user/list")
    suspend fun userList(
        @Query("pageIndex") pageIndex: Int,
        @Query("pageSize") pageSize: Int,
    ): BaseResponse<ListRsp>

}