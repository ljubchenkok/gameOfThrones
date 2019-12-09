package ru.skillbranch.gameofthrones.repositories

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url
import ru.skillbranch.gameofthrones.AppConfig
import ru.skillbranch.gameofthrones.data.remote.res.CharacterRes
import ru.skillbranch.gameofthrones.data.remote.res.HouseRes

interface GameOfThronesApi {
    @GET("/api/houses")
    fun getAllHouses(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Deferred<Response<List<HouseRes>>>
    @GET("/api/houses")
    fun getHouse(@Query("name") name: String): Deferred<Response<List<HouseRes>>>
    @GET("/api/characters/{id}")
    fun getCharacter(@Path(value="id") id:String): Deferred<Response<CharacterRes>>

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
