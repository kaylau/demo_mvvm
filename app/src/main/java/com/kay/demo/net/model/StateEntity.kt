package com.kay.demo.net.model

import com.kay.demo.net.util.StateConstants


/**
 * 统一处理状态码（自定义）
 */
data class StateEntity(@StateConstants.Types val state: Int, val message: String = "")