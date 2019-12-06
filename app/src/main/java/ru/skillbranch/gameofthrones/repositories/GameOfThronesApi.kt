package ru.skillbranch.gameofthrones.repositories

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface GameOfThronesApi {
    @GET("/api/houses")
    fun getHouses(): Deferred<Response<List<HouseRes>>>

}

class ApiFactory() {

    val url:String = AppConfig.BASE_URL

    fun retrofit() = Retrofit.Builder()
        .client(OkHttpClient.Builder().build())
        .baseUrl(url)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val gameOfThronesApi: GameOfThronesApi = retrofit()
        .create(GameOfThronesApi::class.java)


}
