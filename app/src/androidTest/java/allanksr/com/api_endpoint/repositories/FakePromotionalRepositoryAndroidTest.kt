package allanksr.com.api_endpoint.repositories

import allanksr.com.api_endpoint.common.Resource
import allanksr.com.api_endpoint.data.endpointDto.Promotion
import allanksr.com.api_endpoint.data.remote.local.PromotionalItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class FakePromotionalRepositoryAndroidTest : PromotionalRepository {

    private val promotionalItems = mutableListOf<PromotionalItem>()

    private val observablePromotionalItems = MutableLiveData<List<PromotionalItem>>(promotionalItems)

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData() {
        observablePromotionalItems.postValue(promotionalItems)
    }

    override suspend fun insertPromotionalItem(promotionalItem: PromotionalItem) {
        promotionalItems.add(promotionalItem)
        refreshLiveData()
    }

    override suspend fun deletePromotionalItem(promotionalItem: PromotionalItem) {
        promotionalItems.remove(promotionalItem)
        refreshLiveData()
    }

    override fun observeAllPromotionalItems(): LiveData<List<PromotionalItem>> {
        return observablePromotionalItems
    }

    override suspend fun searchPromotionalCode(codePromo: String): Resource<Promotion> {
        return if(shouldReturnNetworkError) {
            Resource.error("Error", null)
        } else {
            Resource.success(Promotion(listOf()))
        }
    }

}











