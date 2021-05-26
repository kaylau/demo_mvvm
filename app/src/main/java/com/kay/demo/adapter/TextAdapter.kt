package com.kay.demo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kay.demo.R
import com.kay.demo.databinding.ItemTextBinding
import com.kay.demo.net.model.respone.ItemData


/**
 * @Author: Administrator
 * @CreateDate: 2021/5/25 16:34
 * @Description:
 */
class TextAdapter() : RecyclerView.Adapter<TextAdapter.ViewHolder>() {

    private lateinit var listData: List<ItemData>
    private lateinit var listener: OnItemClick

    constructor(listData: List<ItemData>, listener: OnItemClick) : this() {
        this.listData = listData
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var bind: ItemTextBinding? = DataBindingUtil.bind(itemView)

        fun bindData(item: ItemData, listener: OnItemClick) {
            bind?.item = item
            bind?.listener = listener
        }
    }

    public interface OnItemClick {
        fun itemClick(item: ItemData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_text, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listData[position], listener)
    }

    override fun getItemCount(): Int = listData.size
}