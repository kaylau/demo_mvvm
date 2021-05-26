package com.kay.demo.net.model.respone

data class ListRsp(val dataList: List<ListBean>)

data class ListBean(
    val name: String? = "",
    val type: Int? = 0,
    val id: String? = "",
    val url: String? = "",
)