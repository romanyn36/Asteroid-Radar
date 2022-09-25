package com.udacity.asteroidradar

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
}
     object Dates{
        fun convertDateStringToFormattedString(date: Date, format: String, locale: Locale = Locale.getDefault()) : String {
            val formatter = SimpleDateFormat(format, locale)
            return formatter.format(date)
        }

        fun addDaysToDate(date: Date, daysToAdd: Int) : Date {
            val c = Calendar.getInstance()
            c.time = date
            c.add(Calendar.DATE, daysToAdd)
            return c.time
        }
    }

enum class AsteroidFilter(val value: String) {
    SHOW_SAVED("saved"), SHOW_TODAY("today"), SHOW_WEEK("week") }