package com.kay.demo.adapter

import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kay.demo.R
import com.kay.demo.adapter.TipsBannerAdapter.BannerViewHolder
import com.kay.demo.utils.getResColor
import com.youth.banner.adapter.BannerAdapter

class TipsBannerAdapter(data: List<String>) : BannerAdapter<String?, BannerViewHolder>(data) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val t = TextView(parent.context)
        t.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        t.setTextColor(getResColor(parent.context, R.color.white))
        t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
        t.maxLines = 1
        t.ellipsize = TextUtils.TruncateAt.END
        t.includeFontPadding = false
        t.gravity = Gravity.CENTER_VERTICAL
        return BannerViewHolder(t)
    }


    class BannerViewHolder(var textView: TextView) : RecyclerView.ViewHolder(
        textView
    )

    override fun onBindView(holder: BannerViewHolder?, data: String?, position: Int, size: Int) {
        holder?.textView?.text = data
    }
}