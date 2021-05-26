package com.kay.demo.net

import com.kay.demo.net.model.request.FeedbackReq
import com.kay.demo.net.model.request.LoginReq
import com.kay.demo.net.model.request.RegisterReq
import com.kay.demo.net.net.BaseRepository
import com.kay.demo.net.net.ApiService
import com.kay.demo.net.net.RetrofitClient


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/25 17:51
 * @Description:
 */
object ApiNetWork : BaseRepository() {

    private val apiService = RetrofitClient.getInstance().create(ApiService::class.java)

    suspend fun register(req: RegisterReq) =
        callRequest(call = { handleResponse(apiService.register(req)) })

    suspend fun login(req: LoginReq) =
        callRequest(call = { handleResponse(apiService.login(req)) })


    suspend fun feedback(req: FeedbackReq) =
        callRequest(call = { handleResponse(apiService.feedback(req)) })


}