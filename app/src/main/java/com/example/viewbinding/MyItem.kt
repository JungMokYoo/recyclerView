package com.example.viewbinding
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
data class MyItem(
    val aIcon:Int ,
    val aName:Int ,
    val aAddr:Int ,
    val price:Int ,
    val reply: Int,
    val comment:String ,
    val heart: Int,
    val like:String,
    val seller:Int,
    val words:Int
):Parcelable
