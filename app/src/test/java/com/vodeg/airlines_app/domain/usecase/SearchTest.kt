package com.vodeg.airlines_app.domain.usecase

import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SearchTest {
    private lateinit var search: Search
    private lateinit var airlinesRepo: AirlinesRepo
    private val airlines = mutableListOf(
        Airline(
            name = "essam",
            country = "Egypt",
            slogan = "we Travel To Gather",
            headQuaters = "19st shoubra - cairo ",
            established = "",
            website = "",
            logo = "",
        ),
        Airline(
            name = "mohamed",
            country = "Oman",
            slogan = "we live for Travelling",
            headQuaters = "19st El obuar  - Oman ",
            established = "",
            website = "",
            logo = "",
        ),
        Airline(
            name = "ali",
            country = "Quatar",
            slogan = "we Travel To Gather",
            headQuaters = "19st shoubra - Quatar ",
            established = "",
            website = "",
            logo = "",
        )

    )

    @Before
    fun init() {
        airlinesRepo = Mockito.mock(AirlinesRepo::class.java)
        search = Search(airlinesRepo)
    }

    @Test
    fun testSearchForAirlineAndFind() {
        runBlocking {
            Mockito.`when`(airlinesRepo.getCachedAirLines()).thenReturn(airlines)
            val actualResult = search.run("essam")
            Assert.assertEquals(1, actualResult.size)


        }
    }
    @Test
    fun testSearchForAirlineAndNotFind() {
        runBlocking {
            Mockito.`when`(airlinesRepo.getCachedAirLines()).thenReturn(airlines)
            val actualResult = search.run("osama")
            Assert.assertEquals(0, actualResult.size)


        }
    }
}