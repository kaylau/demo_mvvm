package com.kay.demo.utils

import com.tencent.mmkv.MMKV


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 16:32
 * @Description:
 */
object MMKVUtils {

    private val kv: MMKV = MMKV.defaultMMKV()

    fun put(key: String?, value: Any?): Boolean {
        var commit = false
        if (value == null) return false
        when (value) {
            is String -> {
                commit = kv.encode(key, value as String?)
            }
            is Boolean -> {
                commit = kv.encode(key, (value as Boolean?) ?: false)
            }
            is Float -> {
                commit = kv.encode(key, (value as Float?) ?: 0.0F)
            }
            is Int -> {
                commit = kv.encode(key, (value as Int?) ?: 0)
            }
            is Long -> {
                commit = kv.encode(key, (value as Long?) ?: 0L)
            }
            is Double -> {
                commit = kv.encode(key, (value as Double?) ?: 0.00)
            }
        }
        return commit
    }

    fun getString(key: String?): String? {
        return getString(key, "")
    }

    fun getString(key: String?, defaultValue: String?): String? {
        return kv.decodeString(key, defaultValue)
    }

    fun getInt(key: String?, defaultValue: Int): Int {
        return kv.decodeInt(key, defaultValue)
    }

    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return kv.decodeBool(key, defaultValue)
    }

    fun getDouble(key: String?, defaultValue: Double?): Double {
        return kv.decodeDouble(key, defaultValue ?: 0.00)
    }

    fun getLong(key: String?, defaultValue: Long?): Long {
        return kv.decodeLong(key, defaultValue ?: 0)
    }

    fun remove(key: String?) {
        kv.remove(key)
    }

}