import ir.ah.igapweather.other.util.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

class FakeNetworkConnectionInterceptor : Interceptor {

    var isInternetAvailable = true

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable)
            throw NoInternetException()
        return chain.proceed(chain.request())
    }

}