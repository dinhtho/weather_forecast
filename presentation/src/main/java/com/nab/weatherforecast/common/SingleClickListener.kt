package com.nab.weatherforecast.common

import android.os.SystemClock
import android.view.View


/**
 * Created by tho nguyen on 6/17/2021.
 * dinhthohcmus@gmail.com
 */
abstract class SingleClickListener : View.OnClickListener {
    private var lastClickMillis: Long = 0
    override fun onClick(v: View?) {
        val now = SystemClock.elapsedRealtime()
        if (now - lastClickMillis > THRESHOLD_MILLIS) {
            onClicked(v)
        }
        lastClickMillis = now
    }

    abstract fun onClicked(v: View?)

    companion object {
        private const val THRESHOLD_MILLIS = 700L
    }
}