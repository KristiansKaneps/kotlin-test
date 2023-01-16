package helpers

import api.API
import kotlinx.coroutines.promise
import types.Holiday
import kotlin.js.Date
import kotlin.js.Promise

class CalendarInstance(
    var date: Date = Date(),
) {
    fun getDaysInMonth(): Int {
        val year = date.getFullYear().toInt()
        val month = date.getMonth().toInt()
        return Date(year, month + 1, 0).getDate().toInt()
    }
    fun getDaysInPreviousMonth(): Int = Date(getPreviousMonthYear(), getPreviousMonth() + 1, 0).getDate().toInt()
    fun getDaysInNextMonth(): Int = Date(getNextMonthYear(), getNextMonth() + 1, 0).getDate().toInt()

    fun getMonth(): Int = date.getMonth()
    fun getPreviousMonth(): Int = when {
        getMonth() == 0 -> 11
        else -> getMonth() - 1
    }
    fun getNextMonth(): Int = when {
        getMonth() == 11 -> 0
        else -> getMonth() + 1
    }

    fun getYear(): Int = date.getFullYear()
    fun getPreviousMonthYear(): Int = when {
        getMonth() == 0 -> getYear() - 1
        else -> getYear()
    }
    fun getNextMonthYear(): Int = when {
        getMonth() == 11 -> getYear() + 1
        else -> getYear()
    }

    fun getDateOffset(): Int {
        val offset = Date(getPreviousMonthYear(), getPreviousMonth() + 1, 0).getDay().toInt()
        return if (offset == 7) 0 else offset - 1
    }

    fun getDateOverflow(): Int {
        val offset = Date(getNextMonthYear(), getNextMonth() + 1, 0).getDay().toInt()
        return if (offset == 7) 0 else 7 - offset
    }

    fun fetchHolidays(): Promise<List<Holiday>> = API.scope.promise {
        API.fetchHolidays(getYear(), "LV")
    }

    fun isSameDay(date1: Date?, date2: Date?): Boolean {
//        return date1?.getDate() == date2?.getDate()
        return date1?.getFullYear() == date2?.getFullYear() &&
                date1?.getMonth() == date2?.getMonth() &&
                date1?.getDate() == date2?.getDate()
    }
}
