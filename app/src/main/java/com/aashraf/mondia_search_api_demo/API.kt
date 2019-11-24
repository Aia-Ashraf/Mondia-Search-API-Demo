package com.example.search

import com.aashraf.mondia_search_api_demo.model.mondiaModels.DataModel
import com.example.search.model.BaseModel

import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Observable;
import retrofit2.http.Header
import retrofit2.http.POST




interface MondiaSearchAPI {

    @GET("v2/api/sayt/flat")
    fun getData(
        @Header("Authorization") authorization: String,
        @Query("query") query: String,
        @Query("limit") limit: Int
    ): Observable<ArrayList<DataModel>>
}

interface AccessTokenAPI {

    @POST("v0/api/gateway/token/client")
    fun getAccessToken(
        @Header("Content-Type") contentType: String,
        @Header("X-MM-GATEWAY-KEY") getwayKey: String
    ): Observable<BaseModel>

}