package com.kay.demo

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory
import com.bumptech.glide.module.AppGlideModule

/**
 * GlideApp 必须实现
 * Created by zst on 2018/1/23.
 */
@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        super.registerComponents(context, glide, registry)
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        /**
         * 优先外部存储作为磁盘缓存目录，防止内部存储文件过大
         * 外部存储目录默认地址为：/sdcard/Android/data//cache/image_manager_disk_cache
         */
        builder.setDiskCache(ExternalCacheDiskCacheFactory(context))
    }
}