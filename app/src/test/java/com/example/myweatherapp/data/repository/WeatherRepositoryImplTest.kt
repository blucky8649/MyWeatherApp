package com.example.myweatherapp.data.repository

import com.example.data.data.source.local.LocalDataSource
import com.example.data.data.source.mapper.toWeather
import com.example.data.data.source.remote.RemoteDataSource
import com.example.data.data.source.remote.dto.ConsolidatedWeatherDto
import com.example.data.data.source.remote.dto.WeatherDetailDto
import com.example.data.data.source.remote.dto.WeatherDto
import com.example.data.data.source.repository.WeatherRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.myweatherapp.model.entity.WeatherEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import org.mockito.*
import org.mockito.ArgumentMatchers.*
import retrofit2.Response

class WeatherRepositoryImplTest {
    val mockQuery = ""

    lateinit var localDbResult: ArrayList<WeatherEntity>
    lateinit var repository: com.example.domain.repository.WeatherRepository
    lateinit var weatherInfoList: ArrayList<WeatherEntity>
    lateinit var weatherDetailList: ArrayList<WeatherDetailDto>
    lateinit var weatherInfoListFromRemote: ArrayList<WeatherEntity>
    lateinit var weatherLocalList: ArrayList<WeatherDto>

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        weatherInfoList = arrayListOf(
            WeatherEntity(1, "seoul", listOf()),
            WeatherEntity(2, "tokyo", listOf()),
            WeatherEntity(3, "undefined", listOf()),
            WeatherEntity(4, "gggggg", listOf())
        )
        weatherInfoListFromRemote = arrayListOf(
            WeatherEntity(5, "seoul", listOf()),
            WeatherEntity(6, "tokyo", listOf()),
            WeatherEntity(7, "undefined", listOf()),
            WeatherEntity(8, "gggggg", listOf())
        )

        weatherDetailList = arrayListOf(
            WeatherDetailDto.emptyWeatherDetail.copy(woeid = 5),
            WeatherDetailDto.emptyWeatherDetail.copy(woeid = 6),
            WeatherDetailDto.emptyWeatherDetail.copy(woeid = 7),
            WeatherDetailDto.emptyWeatherDetail.copy(woeid = 8)
        )

        weatherLocalList = arrayListOf(
            WeatherDto("seoul", 1),
            WeatherDto("tokyo", 2),
            WeatherDto("undefined", 3),
            WeatherDto("gggggg", 4)
        )

        repository = WeatherRepositoryImpl(localDataSource, remoteDataSource)
        localDbResult = weatherInfoList.toMutableList() as ArrayList<WeatherEntity>
        runBlocking {
            Mockito.`when`(localDataSource.insert(anyList()))
                .then {
                    localDbResult.addAll(weatherInfoListFromRemote)
                }
            Mockito.`when`(remoteDataSource.getWeatherDetail(anyInt()))
                .thenReturn(
                    WeatherDetailDto.emptyWeatherDetail.copy(
                        consolidated_weather = listOf(
                            ConsolidatedWeatherDto.emptyConsolidatedWeather
                        )
                    )
                )
            Mockito.`when`(localDataSource.getWeather())
                .thenReturn(localDbResult.toList().map { it.toWeather() })
            Mockito.`when`(remoteDataSource.getWeatherLocal(mockQuery))
                .thenReturn(Response.success(weatherLocalList))
            Mockito.`when`(localDataSource.clear()).then {
                localDbResult.clear()
            }
        }
    }

    @Test
    fun notEmptyLocalData_And_DisableFetchFromRemoteTest() = runBlocking {
        // 로컬데이터가 비어있지 않은 상태에서 fetchFromRemote를 false로 하면 로컬에서 데이터를 불러온다.
        val fakeFlow = repository.observeWeatherListings(mockQuery, false)
        Truth.assertThat(weatherInfoList).isEqualTo(fakeFlow.last().data)
    }

    @Test
    fun emptyLocalData_And_DisableFetchFromRemoteTest() = runBlocking {
        // 로컬데이터가 비어있는 상태에서 fetchFromRemote를 false로 하면 리모트에서 데이터를 불러온다.
        localDbResult.clear()
        val fakeFlow = repository.observeWeatherListings(mockQuery, false)
        Truth.assertThat(weatherInfoListFromRemote).isEqualTo(fakeFlow.last().data)
    }

    @Test
    fun emptyLocalData_And_EnableFetchFromRemoteTest() = runBlocking {
        // 로컬데이터가 비어있는 상태에서 fetchFromRemote를 true로 하면 리모트에서 데이터를 불러온다.
        localDbResult.clear()
        val fakeFlow = repository.observeWeatherListings(mockQuery, true)
        Truth.assertThat(weatherInfoListFromRemote).isEqualTo(fakeFlow.last().data)
    }

    @Test
    fun notEmptyLocalData_And_EnableFetchFromRemoteTest() = runBlocking {
        // 로컬데이터가 비어있지 않은 상태에서 fetchFromRemote를 true로 하면 리모트에서 데이터를 불러온다.
        val fakeFlow = repository.observeWeatherListings(mockQuery, true)
        Truth.assertThat(weatherInfoListFromRemote).isEqualTo(fakeFlow.last().data)

    }
}
