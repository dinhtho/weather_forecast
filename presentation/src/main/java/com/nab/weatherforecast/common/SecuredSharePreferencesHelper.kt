package com.nab.weatherforecast.common

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.nab.weatherforecast.di.MainApp
import java.util.*

/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */
object SecuredSharePreferencesHelper {
    private const val TIME_REQUEST = "TIME_REQUEST"

    private fun createOrGetMasterKeys(context: Context): MasterKey {
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "local_store",
            createOrGetMasterKeys(context),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun saveRequestDate(time: Long) {
        getSharedPreferences(MainApp.instance).edit().putLong(TIME_REQUEST, time).apply()
    }

    fun checkRequestTimeOverDate(): Boolean {
        val currentBeginDate = Calendar.getInstance().apply {
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val preDate = getSharedPreferences(MainApp.instance).getLong(TIME_REQUEST, 0)
        val isOver = currentBeginDate > preDate
        saveRequestDate(currentBeginDate)
        return isOver
    }

}