package com.mahadiks.basketballapp.models

import com.google.gson.annotations.SerializedName

data class Teams(
    @SerializedName("tid")
    val teamId:String,
    @SerializedName("logo")
    val teamLogoUrl:String,
    @SerializedName("ta")
    val teamShortName:String,
    @SerializedName("tc")
    val teamFullName:String,
    @SerializedName("color")
    val teamColor:String,
)
