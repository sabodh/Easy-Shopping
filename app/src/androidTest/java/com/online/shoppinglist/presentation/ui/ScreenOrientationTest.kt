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


@HiltAndroidTest
@RunWith(MockitoJUnitRunner::class)
class ScreenOrientationTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var screenUtils: ScreenUtils

    @Before
    fun setUp() {
        hiltRule.inject()
    }
    @Test
    fun test_getGridCount_returnsCorrectTabPortrait() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
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
    fun test_getGridCount_returnsTabLandscape() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
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
    fun test_getGridCount_returnsPhoneLandscape() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
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
    fun test_getGridCount_returnsPhonePortrait() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
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
    fun test_getGridCount_returnsSmallTabPortrait() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
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
    fun test_getGridCount_returnsSmallTabLandscape() {
        val context = Mockito.mock(Context::class.java)
        val resources = Mockito.mock(Resources::class.java)
        Mockito.`when`(context.resources).thenReturn(resources)
        val configuration = Configuration().apply {
            screenLayout = Configuration.SCREENLAYOUT_SIZE_LARGE
            orientation = Configuration.ORIENTATION_LANDSCAPE
        }
        Mockito.`when`(resources.configuration).thenReturn(configuration)
        val gridCount = screenUtils.getGridCount()
        assertEquals(4, gridCount)
    }
}