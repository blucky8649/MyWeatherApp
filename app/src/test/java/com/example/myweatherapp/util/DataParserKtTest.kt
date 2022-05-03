package com.example.myweatherapp.util

import org.junit.Assert.*
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import java.util.*

class DataParserKtTest {

    @Test
    fun `오늘 날짜 출력 테스트`() {
        assertThat(getToday()).isEqualTo("2022-05-03")
    }

    @Test
    fun `내일 날짜 출력 테스트`() {
        assertTrue(getTomorrow() == "2022-05-04")
    }
}