package allanksr.com.api_endpoint.data.remote

import allanksr.com.api_endpoint.data.endpointDto.Promotion
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IEndPointApi {
    @GET("exec")
    suspend fun callEndpoint(
        @Query("code_promo") codePromo: String?,
    ): Response<Promotion>
}