package ir.ah.igapweather.ui.splash


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.ah.igapweather.R
import ir.ah.igapweather.launchFragmentInHiltContainer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SplashFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()




    lateinit var mainCoroutineDispatcher: CoroutineDispatcher


    lateinit var splashViewModel: SplashViewModel



    @Before
    fun setup() {
        hiltRule.inject()
        mainCoroutineDispatcher
        splashViewModel = SplashViewModel(mainCoroutineDispatcher)

    }

    @After
    fun tearDown() {
        mainCoroutineDispatcher.cancel()

    }



    @Test
    fun test1() {
        launchFragmentInHiltContainer<SplashFragment>()
        onView(withId(R.id.textView)).check(matches(isDisplayed()))

    }








}