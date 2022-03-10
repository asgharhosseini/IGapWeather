package ir.ah.igapweather.di
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationHeaderInterceptor @Inject constructor(

) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request().newBuilder()

        val newRequest = originalRequest.build()
        return chain.proceed(newRequest)
    }
}