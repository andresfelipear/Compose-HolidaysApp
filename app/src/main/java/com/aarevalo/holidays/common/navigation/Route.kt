package com.aarevalo.holidays.common.navigation

sealed class Route(val routeName: String){
    data object MainTab : Route("mainTab")
    data object HolidaysTab : Route("holidaysTab")
    data object MonthTab : Route("monthTab")
    data object YearTab : Route("yearTab")

    open val navCommand = routeName

}