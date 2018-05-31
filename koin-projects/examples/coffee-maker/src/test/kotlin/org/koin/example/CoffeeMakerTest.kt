package org.koin.example

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times

class CoffeeMakerTest : KoinTest {

    val heater: Heater by inject()
    val coffeeMaker: CoffeeMaker by inject()

    val mockHeaterModule = module {
        single { mock(Heater::class.java) }
    }

    @Before
    fun before() {
        startKoin(listOf(coffeeMakerModule, mockHeaterModule))
    }

    @After
    fun after() {
        closeKoin()
    }

    @Test
    fun testHeaterIsTurnedOnAndThenOff() {
        given(heater.isHot()).will { true }
        coffeeMaker.brew()
        Mockito.verify(heater, times(1)).on()
        Mockito.verify(heater, times(1)).off()
    }

}