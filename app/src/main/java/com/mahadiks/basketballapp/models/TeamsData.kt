package com.mahadiks.basketballapp.models

import com.google.gson.annotations.SerializedName

data class TeamsData (
    @SerializedName("teams")
    val teamsList:List<Teams>
)