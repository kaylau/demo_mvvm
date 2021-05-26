package com.kay.demo.mvvm.view.act

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import com.gyf.barlibrary.ImmersionBar
import com.kay.demo.R
import com.kay.demo.base.BaseAct
import com.kay.demo.databinding.ActWebviewBinding
import com.kay.demo.mvvm.vm.WebViewVm
import com.youth.banner.util.LogUtils
import java.net.URISyntaxException


/**
 * @Author: Administrator
 * @CreateDate: 2021/1/26 16:43
 * @Description:
 */
class WebViewAct : BaseAct<WebViewVm, ActWebviewBinding>() {

    private val pkgNameGoogleMarket = "com.android.vending"
    private lateinit var title: String
    private lateinit var url: String
    private val appList: MutableList<String> = mutableListOf()

    override fun getLayoutId(): Int = R.layout.act_webview

    override fun bindVM() {
        dataBinding.vm = viewModel
    }

    override fun initImmersionBar() {
        ImmersionBar.with(this)
            .fitsSystemWindows(true)
            .statusBarColor(R.color.color_80000000)
            .navigationBarColor(R.color.black)
            .statusBarDarkFont(false, 0.2f)
            .init()
    }

    override fun initData() {
        val bundleExtra = intent?.getBundleExtra("bundle")
        title = bundleExtra?.getString("title").toString()
        url = bundleExtra?.getString("url").toString()
//        dataBinding.rlTitle.centerTitle.text = title
        viewModel.title.value = title
        initAppList()
        LogUtils.e("url ${this.javaClass.simpleName}: $url")
        dataBinding.webView.loadUrl(url)
        initWebViewSetting()
    }


    @SuppressLint("QueryPermissionsNeeded")
    private fun initAppList() {
        appList.clear()
        val packageManager = this.packageManager
        val packageInfoList = packageManager.getInstalledPackages(0)
        for (packageInfo in packageInfoList) {
            appList.add(packageInfo.packageName)
        }
    }

    private fun initWebViewSetting() {
        val setting = dataBinding.webView.settings
        // 支持缩放
        // 支持缩放
        setting.setSupportZoom(true)
        // 设置支持缩放 + -
        // 设置支持缩放 + -
        setting.builtInZoomControls = true
        // 关闭 webView 中缓存
        /**/
        // 关闭 webView 中缓存
        /**/setting.cacheMode = WebSettings.LOAD_NO_CACHE
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        // 设置WebView属性，能够执行Javascript脚本
        // 设置WebView属性，能够执行Javascript脚本
        setting.javaScriptEnabled = true
        setting.savePassword = false
        setting.domStorageEnabled = true
        setting.defaultTextEncodingName = "utf-8"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setting.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        dataBinding.webView.webViewClient = client
        dataBinding.webView.webChromeClient = MyWebChromeClient()

        dataBinding.webView.addJavascriptInterface(MyJavascriptInterface(this), "android")

        dataBinding.webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
            if (!TextUtils.isEmpty(url)) {
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                val contentUrl = Uri.parse(url)
                intent.data = contentUrl
                startActivity(intent)
                finish()
            }
        }
    }


    private val client: WebViewClient = object : WebViewClient() {
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            LogUtils.e("onPageFinished ${this.javaClass.simpleName}: $url")
            val titleStr = view.title
            if (TextUtils.isEmpty(title)) {
                titleStr?.let {
                    viewModel.title.value = it
                }
            }
            LogUtils.e("onPageFinished ${this.javaClass.simpleName}: $titleStr")
        }

        /**
         * 防止加载网页时调起系统浏览器
         */
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            LogUtils.e("shouldOverrideUrlLoading ${this.javaClass.simpleName}: $url")
            if (TextUtils.isEmpty(url) || url.startsWith("http://") || url.startsWith("https://")) return false
            if (url.startsWith("tel:")) {
                val intent = Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse(url)
                )
                startActivity(intent)
            } else if (url.startsWith("intent://")) {
                try {
                    val context = view.context
                    val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    if (intent != null) {
                        view.stopLoading()
                        val packageManager = context.packageManager
                        val info = packageManager.resolveActivity(
                            intent,
                            PackageManager.MATCH_DEFAULT_ONLY
                        )
                        if (info != null) {
                            context.startActivity(intent)
                        } else {
                            val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl))
                            context.startActivity(browserIntent)
                        }
                        finish()
                    }
                } catch (e: URISyntaxException) {
                }
                return false
            }
            return try {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                if (appList.contains(pkgNameGoogleMarket)) {
                    intent.setPackage(pkgNameGoogleMarket)
                }
                startActivity(intent)
                finish()
                true
            } catch (e: Exception) {
                true
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            super.onReceivedSslError(view, handler, error)
            val mHandler: SslErrorHandler = handler
            val builder = AlertDialog.Builder(this@WebViewAct)
            builder.setMessage(this@WebViewAct.getString(R.string.ssl_error))
            builder.setPositiveButton(this@WebViewAct.getString(R.string.continueStr),
                DialogInterface.OnClickListener { dialog, which -> mHandler.proceed() })
            builder.setNegativeButton(this@WebViewAct.getString(R.string.cancel),
                DialogInterface.OnClickListener { dialog, which -> mHandler.cancel() })
            builder.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, event ->
                if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    mHandler.cancel()
                    dialog.dismiss()
                    return@OnKeyListener true
                }
                false
            })
            val dialog = builder.create()
            dialog.show()
        }
    }


    inner class MyWebChromeClient : WebChromeClient() {
        override fun onReceivedTitle(view: WebView, titleStr: String) {
            super.onReceivedTitle(view, titleStr)

            if (TextUtils.isEmpty(title)) {
                viewModel.title.value = titleStr
            }
            LogUtils.e("onReceivedTitle ${this.javaClass.simpleName}: $titleStr")
        }

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress >= 100) {
                dataBinding.progressBar.visibility = View.GONE
            } else {
                if (dataBinding.progressBar.visibility == View.GONE) {
                    dataBinding.progressBar.visibility = View.VISIBLE
                }
                dataBinding.progressBar.progress = newProgress
            }
        }

        // JS的提示框
        override fun onJsAlert(
            view: WebView,
            url: String,
            message: String,
            result: JsResult
        ): Boolean {
            // 构建一个Builder来显示网页中的alert对话框
            val builder = AlertDialog.Builder(view.context)
            builder.setMessage(message)
            builder.setPositiveButton(
                android.R.string.ok
            ) { dialog, which -> result.confirm() }
            builder.setCancelable(false)
            builder.create()
            builder.show()
            return true
        }

        // JS调用和反调用
        override fun onJsPrompt(
            view: WebView,
            url: String,
            message: String,
            defaultValue: String,
            result: JsPromptResult
        ): Boolean {
            if (message == "1") {
                // 解析参数defaultValue
                // 调用java方法并得到结果
            }
            // 返回结果
            result.confirm("result")
            return true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        val parent = dataBinding.webView.parent as ViewGroup
        parent.removeView(dataBinding.webView)
        (window.decorView as ViewGroup).removeAllViews()
        dataBinding.webView.visibility = View.GONE
        dataBinding.webView.stopLoading()
        dataBinding.webView.settings.javaScriptEnabled = false
        dataBinding.webView.tag = null
        dataBinding.webView.clearHistory()
        dataBinding.webView.clearCache(true)
        dataBinding.webView.clearView()
        dataBinding.webView.removeAllViews()
        dataBinding.webView.destroy()
    }

    class MyJavascriptInterface(val context: Context) {

        /**
         * 前端代码嵌入js：
         * faqItemClick 名应和js函数方法名一致
         */
        @JavascriptInterface
        fun faqItemClick(position: Int) {

        }

        @JavascriptInterface
        fun openTutorials() {

        }
    }

}
