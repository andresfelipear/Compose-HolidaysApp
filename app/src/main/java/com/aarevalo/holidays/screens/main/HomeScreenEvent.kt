package com.aarevalo.holidays.screens.main

sealed class HomeScreenEvent
{
    data object UpdatedYear : HomeScreenEvent()
}