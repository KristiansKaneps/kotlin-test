package api

import types.Holiday

object API {
    val scope = mainScope

    suspend fun fetchHolidays(year: Int, countryCode: String): List<Holiday> {
        return json(get("https://date.nager.at/api/v3/publicholidays/$year/$countryCode"))
    }
}
