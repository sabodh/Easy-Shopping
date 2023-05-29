package com.online.shoppinglist.presentation.ui

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import com.online.shoppinglist.utils.ScreenUtils
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject


class ScreenOrientationTest {


    @Test
    fun `test getGridCount returns Correct TabPortrait`() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        val screenUtils = ScreenUtils(context)

        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_XLARGE
            orientation = Configuration.ORIENTATION_PORTRAIT
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(3, gridCount)
    }
    @Test
    fun `test get GridCount returns TabLandscape`() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        val screenUtils = ScreenUtils(context)

        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_XLARGE
            orientation = Configuration.ORIENTATION_LANDSCAPE
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(5, gridCount)
    }
    @Test
    fun `test get GridCount returns Phone Landscape`() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        val screenUtils = ScreenUtils(context)

        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL
            orientation = Configuration.ORIENTATION_LANDSCAPE
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(4, gridCount)
    }
    @Test
    fun `test get GridCount returns Phone Portrait`() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        val screenUtils = ScreenUtils(context)

        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_NORMAL
            orientation = Configuration.ORIENTATION_PORTRAIT
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(2, gridCount)
    }
    @Test
    fun `test get GridCount returns Small Tab Portrait`() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        val screenUtils = ScreenUtils(context)

        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE
            orientation = Configuration.ORIENTATION_PORTRAIT
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(3, gridCount)
    }
    @Test
    fun `test get GridCount returns Small Tab Landscape`() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        val screenUtils = ScreenUtils(context)

        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE
            orientation = Configuration.ORIENTATION_LANDSCAPE
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(5, gridCount)
    }
}