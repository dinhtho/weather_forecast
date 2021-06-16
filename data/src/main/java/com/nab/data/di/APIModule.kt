package com.nab.data.di

import android.app.Application
import android.content.Context
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.nab.data.BuildConfig
import com.nab.data.R
import com.nab.data.api.WeatherForecastService
import com.nab.data.entities.SSLCertificates
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object APIModule {
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideSSLCertificates(context: Context): SSLCertificates {
        return SSLCertificates(
            domainPattern = context.getString(R.string.ssl_domain),
            cert1 = context.getString(R.string.ssl_pinning1),
            cert2 = context.getString(R.string.ssl_pinning2),
            cert3 = context.getString(R.string.ssl_pinning3)
        )
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        sslCertificates: SSLCertificates
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .certificatePinner(
                CertificatePinner.Builder()
                    .add(sslCertificates.domainPattern, sslCertificates.cert1)
                    .add(sslCertificates.domainPattern, sslCertificates.cert2)
                    .add(sslCertificates.domainPattern, sslCertificates.cert3)
                    .build()
            )
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
    }

    @Provides
    @Singleton
    fun provideWeatherForecastService(retrofit: Retrofit): WeatherForecastService {
        return retrofit.create(WeatherForecastService::class.java)
    }

}