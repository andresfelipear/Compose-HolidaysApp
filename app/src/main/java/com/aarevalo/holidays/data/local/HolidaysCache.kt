package com.aarevalo.holidays.data.local

import com.aarevalo.holidays.domain.model.Holiday
import java.util.concurrent.locks.ReentrantLock
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.withLock

@Singleton
class HolidaysCache @Inject constructor() {

    private val lock = ReentrantLock()

    private val holidays = mutableListOf<Holiday>()

    fun refreshHolidays(newHolidays: List<Holiday>) {
        lock.withLock {
            holidays.clear()
            holidays.addAll(newHolidays)
        }
    }

    fun clearHolidays() {
        lock.withLock {
            holidays.clear()
        }
    }

    fun getHolidays(): List<Holiday>? {
        lock.withLock {
            if(holidays.toList().isEmpty()){
                return null
            }
            return holidays.toList()
        }
    }
}