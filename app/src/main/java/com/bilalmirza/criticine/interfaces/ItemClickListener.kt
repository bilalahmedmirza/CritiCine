package com.bilalmirza.criticine.interfaces

interface ItemClickListener<T> {
    fun onItemClick(item: T, pos: Int, type: Int)
}