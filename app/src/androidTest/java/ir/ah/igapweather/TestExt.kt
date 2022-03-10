package ir.ah.igapweather


import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher


fun getElementFromMatchAtPosition(
    matcher: Matcher<View>,
    position: Int
): Matcher<View?> {
    return object : BaseMatcher<View?>() {
        var counter = 0
        override fun matches(item: Any): Boolean {
            if (matcher.matches(item)) {
                if (counter == position) {
                    counter++
                    return true
                }
                counter++
            }
            return false
        }

        override fun describeTo(description: Description) {
            description.appendText("Element at hierarchy position $position")
        }
    }
}

fun isSoftKeyboardShown(): Boolean {
    val imm: InputMethodManager = InstrumentationRegistry.getInstrumentation().context
        .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    return imm.isAcceptingText
}