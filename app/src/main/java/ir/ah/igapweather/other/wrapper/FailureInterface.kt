package ir.ah.igapweather.other.wrapper


sealed interface FailureInterface

/**
 * Used to implement a variety of errors
 */

sealed class ApiCallFailure(
    open val errorMessage: String? = null,
    open val error: Exception? = null
) : FailureInterface {

    class Unauthorized(override val errorMessage: String?) :
        ApiCallFailure(errorMessage = errorMessage)

    class OtherError(override val errorMessage: String?, override val error: Exception? = null) :
        ApiCallFailure(errorMessage = errorMessage, error)
    class WeatherError(override val errorMessage: String?, override val error: Exception? = null) :
        ApiCallFailure(errorMessage = errorMessage, error)

    object NoInternet : ApiCallFailure()
}