package com.mahadiks.basketballapp.models

import com.google.gson.annotations.SerializedName

data class HomeTeamSchedule(
    @SerializedName("tid")
    val teamId :String,
    @SerializedName("ta")
    val teamShortName:String,
    @SerializedName("tc")
    val teamFullName:String,
    @SerializedName("s")
    val teamScore:String
)
