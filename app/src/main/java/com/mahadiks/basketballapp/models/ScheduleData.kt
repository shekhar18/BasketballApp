package com.mahadiks.basketballapp.models

import com.google.gson.annotations.SerializedName


data class ScheduleData(
    @SerializedName("schedules")
    val listSchedule:List<Schedule>
)
