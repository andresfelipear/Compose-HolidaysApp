package com.aarevalo.holidays.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aarevalo.holidays.domain.model.Holiday
import java.time.LocalDate

@Entity(tableName = "holidays")
data class HolidayEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val name: String,
)
{
    fun toHoliday(): Holiday {
        return Holiday(name = name, date = LocalDate.parse(date))
    }

    companion object{
        fun fromHoliday(holiday: Holiday): HolidayEntity {
            return HolidayEntity(
                date = holiday.date.toString(),
                name = holiday.name
            )
        }
    }
}
