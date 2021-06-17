package com.nab.weatherforecast.forecast.adapter.base

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<V, VH : BaseHolder<V, *>> : RecyclerView.Adapter<VH>() {

    var dataSource: ArrayList<V> = ArrayList()
    abstract fun getDiffItem(): SameItemCallBack<V>

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.apply {
            data = getItem(position)
            bind(data!!, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).hashCode().toLong()
    }


    open fun getItem(position: Int): V {
        return dataSource[position]

    }

    override fun getItemCount() = dataSource.size

    open fun setDataSource(dataSource: List<V>) {
        val newList = ArrayList(dataSource)
        submit(newList)
    }

    fun refreshData() {
        val newList = ArrayList(dataSource)
        submit(newList)
    }

    fun clearAll() {
        submit(arrayListOf())
    }

    fun getDataSource(): List<V> {
        return dataSource
    }

    fun appendItem(item: V) {

        this.dataSource.add(item)
        notifyItemInserted(itemCount)
    }

    fun appendItemPosition(item: V, position: Int) {
        if (this.dataSource.isEmpty() || position < 0 || position > this.dataSource.size) {
            return
        }
        this.dataSource.add(position, item)
        notifyItemInserted(position)
    }

    fun updateItem(position: Int, item: V) {
        if (this.dataSource.size > position) {
            dataSource[position] = item
            notifyItemChanged(position)
        }
    }


    fun removeAtPosition(position: Int) {
        if (position >= 0 && this.dataSource.size > position) {
            dataSource.removeAt(position)
            notifyItemRangeRemoved(position, 1)
        }
    }

    fun removeItem(item: V) {
        val position = dataSource.indexOf(item)
        if (position > -1) {
            dataSource.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    open fun appendItems(items: List<V>) {
        if (dataSource.isEmpty()) {
            setDataSource(items)
        } else {
            dataSource.addAll(items)
            notifyItemRangeInserted(itemCount, items.size)
        }
    }

    fun insertItems(position: Int, items: List<V>) {
        if (dataSource.isEmpty()) {
            setDataSource(items)
        } else {
            dataSource.addAll(position, items)
            notifyItemRangeInserted(position, items.size)
        }
    }


    protected fun submit(newList: ArrayList<V>) {
        autoNotify(this.dataSource, newList)
    }


    fun autoNotify(old: List<V>, new: List<V>) {

        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = old[oldItemPosition]
                val newItem = new[newItemPosition]
                return getDiffItem().areItemsTheSame(oldItem, newItem)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = old[oldItemPosition]
                val newItem = new[newItemPosition]
                return getDiffItem().areContentsTheSame(oldItem, newItem)
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                return super.getChangePayload(oldItemPosition, newItemPosition)
            }

            override fun getOldListSize() = old.size

            override fun getNewListSize() = new.size
        })

        this@BaseAdapter.dataSource.clear()
        this@BaseAdapter.dataSource.addAll(new)


        diff.dispatchUpdatesTo(this)
    }

    interface SameItemCallBack<V> {
        fun areItemsTheSame(oldItem: V, newItem: V): Boolean
        fun areContentsTheSame(oldItem: V, newItem: V): Boolean
    }

}
