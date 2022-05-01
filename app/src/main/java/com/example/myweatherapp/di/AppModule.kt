package com.example.myweatherapp.di

import android.app.Application
import androidx.room.Room
import com.example.myweatherapp.data.source.remote.WeatherApi
import com.example.myweatherapp.data.source.local.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
        return Retrofit.Builder()
            .baseUrl("https://www.metaweather.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(WeatherApi::class.java)
    }
    @Provides
    @Singleton
    fun provideWeatherDatabase(app: Application): WeatherDatabase =
        Room.databaseBuilder(
            app,
            WeatherDatabase::class.java,
            "weather_db.db"
        ).build()
}