package com.pgillis.paper.core.network.retrofit

import androidx.core.os.trace
import com.pgillis.paper.core.network.PaperNetworkDataSource
import com.pgillis.paper.core.network.model.NetworkItem
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/"
private const val HIRING_ENDPOINT = "hiring.json"

private interface RetrofitPaperNetworkApi {
    @GET(HIRING_ENDPOINT)
    suspend fun getItems(): List<NetworkItem>
}

/**
 * [Retrofit] backed [PaperNetworkDataSource]
 */
@Singleton
internal class RetrofitPaperNetwork @Inject constructor(
    networkJson: Json,
    okhttpCallFactory: dagger.Lazy<Call.Factory>,
) : PaperNetworkDataSource {

    private val networkApi = trace("RetrofitPaperNetwork") {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            // We use callFactory lambda here with dagger.Lazy<Call.Factory>
            // to prevent initializing OkHttp on the main thread.
            .callFactory { okhttpCallFactory.get().newCall(it) }
            .addConverterFactory(
                networkJson.asConverterFactory("application/json".toMediaType()),
            )
            .build()
            .create(RetrofitPaperNetworkApi::class.java)
    }

    override suspend fun getItems(): List<NetworkItem> = networkApi.getItems()
}
