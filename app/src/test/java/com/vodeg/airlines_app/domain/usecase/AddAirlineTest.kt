package com.vodeg.airlines_app.domain.usecase

import com.vodeg.airlines_app.data.model.Airline
import com.vodeg.airlines_app.domain.repository.AirlinesRepo
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class AddAirlineTest {
    private lateinit var addAirLine: AddNewAirline
    private lateinit var airlinesRepo: AirlinesRepo


    @Before
    fun init() {
        airlinesRepo = Mockito.mock(AirlinesRepo::class.java)
        addAirLine = AddNewAirline(airlinesRepo)
    }

    @Test
    fun testAddAirLinesSuccess() {
        runBlocking {
            var newAirline = Airline(
                name = "osama",
                country = "England",
                slogan = "we Travel To Gather",
                headQuaters = "19st shoubra - England ",
                established = "",
                website = "",
                logo = "",
            )
            Mockito.`when`(airlinesRepo.addNewAirLine(newAirline)).thenReturn(true)

            val actualResult = addAirLine.run(newAirline)
            Assert.assertEquals(true, actualResult)
        }


    }

    @Test
    fun testAddAirLinesFailed() {
        runBlocking {
            var newAirline = Airline(
                name = "osama",
                country = "England",
                slogan = "we Travel To Gather",
                headQuaters = "19st shoubra - England ",
                established = "",
                website = "",
                logo = "",
            )
            Mockito.`when`(airlinesRepo.addNewAirLine(newAirline)).thenReturn(false)

            val actualResult = addAirLine.run(newAirline)
            Assert.assertEquals(false, actualResult)
        }


    }
}