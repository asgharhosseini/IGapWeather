package ir.ah.igapweather.util

import FakeNetworkConnectionInterceptor
import com.google.common.truth.Truth.assertThat
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ir.ah.igapweather.di.BigDecimalAdapter
import ir.ah.igapweather.di.SerializeNulls
import ir.ah.igapweather.other.util.safeApiCall
import ir.ah.igapweather.other.wrapper.ApiCallFailure
import ir.ah.igapweather.other.wrapper.Resource
import kotlinx.coroutines.runBlocking
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import okhttp3.OkHttpClient
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import java.util.*

class SafeApiCallTest {
    interface TestRemoteApi {
        @GET("/api/data")
        suspend fun getTestPost(): Response<TestPost>
    }

    data class TestPost(
        val id: Int
    )

    private val moshi = Moshi.Builder()
        .add(Calendar::class.java, Rfc3339DateJsonAdapter().nullSafe())
        .add(SerializeNulls.JSON_ADAPTER_FACTORY)
        .add(KotlinJsonAdapterFactory())
        .add(BigDecimalAdapter)
        .build()
    private val jsonAdapter = moshi.adapter(TestPost::class.java)

    private val testPost = TestPost(1)
    private val jsonTestPost = jsonAdapter.toJson(testPost)

    private lateinit var mockWebServer: MockWebServer
    private lateinit var fakeNetworkConnectionInterceptor: FakeNetworkConnectionInterceptor
    private lateinit var api: TestRemoteApi

    @Before
    fun before() {
        mockWebServer = MockWebServer()
        fakeNetworkConnectionInterceptor = FakeNetworkConnectionInterceptor()
        val client =
            OkHttpClient.Builder().addInterceptor(fakeNetworkConnectionInterceptor).build()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TestRemoteApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    /**
     * when api call returned 200(success), should return [Resource.Success]
     * */
    @Test
    fun `test1`() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody(jsonTestPost))
        val response = safeApiCall { api.getTestPost() }
        assertThat(response is Resource.Success).isTrue()
        assertThat(response.success).isEqualTo(testPost)
    }


    /**
     * when api call returned 401(unauthorized), should return [ApiCallFailure.Unauthorized]
     * */
    @Test
    fun test2() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(401).setBody("unauthorized"))
        val response = safeApiCall { api.getTestPost() }.failure as ApiCallFailure
        assertThat((response as ApiCallFailure.Unauthorized).errorMessage).isEqualTo("unauthorized")
    }


    /**
     * when api call return unhandled error code, should return [ApiCallFailure.OtherError]
     * */
    @Test
    fun test3() = runBlocking {
        mockWebServer.enqueue(MockResponse().setResponseCode(429).setBody("Too many requests"))
        val response = safeApiCall { api.getTestPost() }.failure as ApiCallFailure
        assertThat(response is ApiCallFailure.OtherError).isTrue()
        assertThat(response.errorMessage).isEqualTo("Too many requests")
    }


    /**
     * when api call without network, should return [ApiCallFailure.NoInternet]
     * */
    @Test
    fun test4() = runBlocking {
        //simulate not network
        fakeNetworkConnectionInterceptor.isInternetAvailable = false

        val response = safeApiCall { api.getTestPost() }.failure as ApiCallFailure
        assertThat(response is ApiCallFailure.NoInternet).isTrue()
    }




}