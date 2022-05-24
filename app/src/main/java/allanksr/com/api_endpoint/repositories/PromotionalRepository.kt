package allanksr.com.api_endpoint.repositories

import allanksr.com.api_endpoint.common.Resource
import allanksr.com.api_endpoint.data.endpointDto.Promotion
import allanksr.com.api_endpoint.data.remote.local.PromotionalItem
import androidx.lifecycle.LiveData

interface PromotionalRepository {

    suspend fun insertPromotionalItem(promotionalItem: PromotionalItem)

    suspend fun deletePromotionalItem(promotionalItem: PromotionalItem)

    fun observeAllPromotionalItems(): LiveData<List<PromotionalItem>>

    suspend fun searchPromotionalCode(codePromo: String): Resource<Promotion>
}