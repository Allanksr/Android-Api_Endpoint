package allanksr.com.api_endpoint.repositories

import allanksr.com.api_endpoint.BuildConfig
import allanksr.com.api_endpoint.common.Resource
import allanksr.com.api_endpoint.common.StringResource.define_api_key
import allanksr.com.api_endpoint.common.StringResource.internet_error
import allanksr.com.api_endpoint.common.StringResource.unknown_error_occurred
import allanksr.com.api_endpoint.data.endpointDto.Promotion
import allanksr.com.api_endpoint.data.remote.IEndPointApi
import allanksr.com.api_endpoint.data.remote.local.PromotionalDao
import allanksr.com.api_endpoint.data.remote.local.PromotionalItem
import androidx.lifecycle.LiveData
import javax.inject.Inject

class DefaultPromotionalRepository @Inject constructor(
    private val promotionalDao: PromotionalDao,
    private val endPointApi: IEndPointApi
) : PromotionalRepository {

    override suspend fun insertPromotionalItem(promotionalItem: PromotionalItem) {
        promotionalDao.insertPromotionalItem(promotionalItem)
    }

    override suspend fun deletePromotionalItem(promotionalItem: PromotionalItem) {
        promotionalDao.deletePromotionalItem(promotionalItem)
    }

    override fun observeAllPromotionalItems(): LiveData<List<PromotionalItem>> {
        return promotionalDao.observeAllPromotionalItems()
    }

    override suspend fun searchPromotionalCode(codePromo: String): Resource<Promotion> {
        return try {
            val response = endPointApi.callEndpoint(codePromo)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error(unknown_error_occurred, null)
            } else {
                Resource.error(unknown_error_occurred, null)
            }
        } catch(e: Exception) {
            Resource.error(internet_error, null)
        }
    }


}














