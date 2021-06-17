package com.nab.weatherforecast.forecast.adapter.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseHolder<V, B : ViewBinding>(protected val binding: B) : RecyclerView.ViewHolder(binding.root) {
    var data: V? = null
    open fun bind(data: V, position: Int){}
    open fun bind(data: V, position: Int, payloads: MutableList<Any>){}
}
