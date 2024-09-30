package com.example.nba.utils

import android.content.Context
import com.example.nba.R

 fun generateYearList(startYear: Int, endYear: Int): List<String> {
    return (startYear..endYear).map { it.toString() }.reversed()
}

 fun getTeamLogoUrl(teamName: String, context: Context): String {
    return when (teamName) {
        context.getString(R.string.Atlanta) -> context.getString(R.string.ATL_URL)
        context.getString(R.string.Boston) -> context.getString(R.string.BOS_URL)
        context.getString(R.string.Brooklyn) -> context.getString(R.string.BKN_URL)
        context.getString(R.string.Charlotte) -> context.getString(R.string.CHA_URL)
        context.getString(R.string.Chicago) -> context.getString(R.string.CHI_URL)
        context.getString(R.string.Cleveland) -> context.getString(R.string.CLE_URL)
        context.getString(R.string.Dallas) -> context.getString(R.string.DAL_URL)
        context.getString(R.string.Denver) -> context.getString(R.string.DEN_URL)
        context.getString(R.string.Detroit) -> context.getString(R.string.DET_URL)
        context.getString(R.string.GoldenState) -> context.getString(R.string.GSW_URL)
        context.getString(R.string.Houston) -> context.getString(R.string.HOU_URL)
        context.getString(R.string.Indiana) -> context.getString(R.string.IND_URL)
        context.getString(R.string.LAClippers) -> context.getString(R.string.LAC_URL)
        context.getString(R.string.LALakers) -> context.getString(R.string.LAL_URL)
        context.getString(R.string.Memphis) -> context.getString(R.string.MEM_URL)
        context.getString(R.string.Miami) -> context.getString(R.string.MIA_URL)
        context.getString(R.string.Milwaukee) -> context.getString(R.string.MIL_URL)
        context.getString(R.string.Minnesota) -> context.getString(R.string.MIN_URL)
        context.getString(R.string.NewOrleans) -> context.getString(R.string.NOP_URL)
        context.getString(R.string.NewYork) -> context.getString(R.string.NYC_URL)
        context.getString(R.string.OklahomaCity) -> context.getString(R.string.OKC_URL)
        context.getString(R.string.Orlando) -> context.getString(R.string.ORL_URL)
        context.getString(R.string.Philadelphia) -> context.getString(R.string.PHI_URL)
        context.getString(R.string.Phoenix) -> context.getString(R.string.PHX_URL)
        context.getString(R.string.Portland) -> context.getString(R.string.POR_URL)
        context.getString(R.string.Sacramento) -> context.getString(R.string.SAC_URL)
        context.getString(R.string.SanAntonio) -> context.getString(R.string.SAS_URL)
        context.getString(R.string.Toronto) -> context.getString(R.string.TOR_URL)
        context.getString(R.string.Utah) -> context.getString(R.string.UTA_URL)
        context.getString(R.string.Washington) -> context.getString(R.string.WAS_URL)
        else -> context.getString(R.string.default_logo)
    }
}

fun getTeamAbbreviationList(context: Context): List<String> {
    return listOf(
        context.getString(R.string.Atlanta),
        context.getString(R.string.Boston),
        context.getString(R.string.Brooklyn),
    context.getString(R.string.Charlotte),
    context.getString(R.string.Chicago),
    context.getString(R.string.Cleveland),
    context.getString(R.string.Dallas),
    context.getString(R.string.Denver),
    context.getString(R.string.Detroit),
    context.getString(R.string.GoldenState),
    context.getString(R.string.Houston),
    context.getString(R.string.Indiana),
    context.getString(R.string.LAClippers),
    context.getString(R.string.LALakers),
    context.getString(R.string.Memphis),
    context.getString(R.string.Miami) ,
    context.getString(R.string.Milwaukee),
    context.getString(R.string.Minnesota),
        context.getString(R.string.NewOrleans),
    context.getString(R.string.NewYork) ,
    context.getString(R.string.OklahomaCity),
    context.getString(R.string.Orlando) ,
    context.getString(R.string.Philadelphia),
    context.getString(R.string.Phoenix) ,
    context.getString(R.string.Portland) ,
    context.getString(R.string.Sacramento) ,
    context.getString(R.string.SanAntonio) ,
    context.getString(R.string.Toronto),
    context.getString(R.string.Utah) ,
    context.getString(R.string.Washington)
    )
}
