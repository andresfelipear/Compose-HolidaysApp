package com.aarevalo.holidays.navigation

sealed class Route(val routeName: String){
    data object MainTab : Route("mainTab")
    data object HolidaysTab : Route("holidaysTab")
    data object MonthTab : Route("monthTab")
    data object WeekTab : Route("weekTab")
    data object YearTab : Route("yearTab")
    data object Settings : Route("settings")
    data object About : Route("about")

    open val navCommand = routeName

}