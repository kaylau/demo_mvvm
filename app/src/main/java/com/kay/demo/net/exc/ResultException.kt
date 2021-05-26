package com.kay.demo.net.exc


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 15:48
 * @Description:
 */
class ResultException(var errCode: String?, var msg: String?) : Exception(msg)