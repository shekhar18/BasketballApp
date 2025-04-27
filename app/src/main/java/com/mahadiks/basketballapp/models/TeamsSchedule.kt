package com.mahadiks.basketballapp.models

data class TeamsSchedule(
    val homeTeam: HomeTeam?,
    val visitedTeam: VisitedTeam?,
    val gameVenue: String,
    val gameTime: String,
    val gameDate: String,
    val gameYearAndMonth:String,
    val buyTicket:String,
    val gameStatus: Int, //future game = 1, Live Game = 2, Past Game = 3
)