package com.mahadiks.basketballapp.models

import com.google.gson.annotations.SerializedName


data class Schedule(
    @SerializedName("year") val year: Int,
    @SerializedName("h") val homeTeam: HomeTeamSchedule,
    @SerializedName("v") val visitedTeam: VisitedTeamSchedule,
    @SerializedName("st") val gameStatus: Int,
    @SerializedName("stt") val matchTime: String,
    @SerializedName("gametime") val gameTimeAndDate: String,
    @SerializedName("arena_city") val arenaCityName: String,
    @SerializedName("buy_ticket") val buyTicket: String
)

