package com.example.zifzafnews.data

import java.util.Calendar
import javax.inject.Inject

class TimeProvider @Inject constructor() {
    fun getMinutesInCurrentHour(): Int {
        return Calendar.getInstance().get(Calendar.MINUTE)
    }
}