package com.nab.data.store

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.*
import javax.inject.Inject

/**
 * Created by tho nguyen on 6/16/2021.
 * dinhthohcmus@gmail.com
 */
class SecuredLocalStore @Inject constructor(private val context: Context) {
    companion object{
        private const val TIME_REQUEST = "TIME_REQUEST"
    }

    private fun createOrGetMasterKeys(): MasterKey {
        return MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private fun getSharedPreferences(): SharedPreferences {
        return EncryptedSharedPreferences.create(
            context,
            "local_store",
            createOrGetMasterKeys(),
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    private fun saveRequestDate(time: Long) {
        getSharedPreferences().edit().putLong(TIME_REQUEST, time).apply()
    }

    fun checkRequestTimeOverDate(): Boolean {
        val currentBeginDate = Calendar.getInstance().apply {
            set(Calendar.HOUR, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis

        val preDate = getSharedPreferences().getLong(TIME_REQUEST, 0)
        val isOver = currentBeginDate > preDate
        saveRequestDate(currentBeginDate)
        return isOver
    }

}