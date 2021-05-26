package com.kay.demo.net.model.request

/**
 * @Author: Administrator
 * @CreateDate: 2021/2/2 17:16
 * @Description:
 */
data class RegisterReq(
    val loginName: String?,
    val password: String?,
    val deviceId: String?="",
    val phoneNumber: String?="",
    val email: String? = ""
)
