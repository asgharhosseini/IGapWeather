package ir.ah.igapweather.ui.splash


import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import ir.ah.igapweather.R
import ir.ah.igapweather.launchFragmentInHiltContainer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.cancel
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@HiltAndroidTest
class SplashFragmentTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()




    @Inject
    lateinit var mainCoroutineDispatcher: CoroutineDispatcher


    lateinit var splashViewModel: SplashViewModel



    @Before
    fun setup() {
        hiltRule.inject()

        splashViewModel = SplashViewModel(mainCoroutineDispatcher)

    }

    @After
    fun tearDown() {
        mainCoroutineDispatcher.cancel()

    }



    @Test
    fun testDisplayedAppName() {
        launchFragmentInHiltContainer<SplashFragment>()
        onView(withId(R.id.appNameView)).check(matches(isDisplayed()))

    }
    @Test
    fun testDisplayedAnimationView() {
        launchFragmentInHiltContainer<SplashFragment>()
        onView(withId(R.id.animationView)).check(matches(isDisplayed()))
    }
    @Test
    fun testDisplayedDescriptionView() {
        launchFragmentInHiltContainer<SplashFragment>()
        onView(withId(R.id.appDescriptionView)).check(matches(isDisplayed()))
    }
    @Test
    fun testDisplayedSiteView() {
        launchFragmentInHiltContainer<SplashFragment>()
        onView(withId(R.id.appSiteView)).check(matches(isDisplayed()))
    }


    @Test
    fun navigateBetweenSplashFragmentAndCurrentWeatherAfter2000s () {
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<SplashFragment>()
        {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(isRoot()).perform(waitFor(2000))

        verify(navController).navigate(
            SplashFragmentDirections.actionSplashFragmentToCurrentWeatherFragment()
        )

    }



    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

}