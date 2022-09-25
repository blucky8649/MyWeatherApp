package com.example.data.data.module

import android.app.Application
import androidx.room.Room
import com.example.data.data.source.repository.WeatherRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.data.data.source.local.LocalDataSource
import com.example.data.data.source.local.WeatherDatabase
import com.example.data.data.source.local.WeatherLocalDataSource
import com.example.data.data.source.remote.RemoteDataSource
import com.example.data.data.source.remote.WeatherApi
import com.example.data.data.source.remote.WeatherRemoteDataSource
import com.example.domain.usecases.GetWeatherListUseCase
import dagger.Binds
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
object DataModule {

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
            "weatherInfo_db.db"
        ).build()

    @Provides
    @Singleton
    fun provideRemoteDataSource(api: WeatherApi): RemoteDataSource = WeatherRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideLocalDataSource(db: WeatherDatabase): LocalDataSource = WeatherLocalDataSource(db)


    @Provides
    @Singleton
    fun repository(localDataSource: LocalDataSource, remoteDataSource: RemoteDataSource): WeatherRepository = WeatherRepositoryImpl(localDataSource, remoteDataSource)
}
