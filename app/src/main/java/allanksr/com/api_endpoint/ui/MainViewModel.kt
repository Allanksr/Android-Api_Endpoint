package allanksr.com.api_endpoint.ui

import allanksr.com.api_endpoint.DispatcherProvider
import allanksr.com.api_endpoint.common.Constants
import allanksr.com.api_endpoint.common.Event
import allanksr.com.api_endpoint.common.Resource
import allanksr.com.api_endpoint.common.StringResource.exceed_characters
import allanksr.com.api_endpoint.common.StringResource.field_must_not_be_empty
import allanksr.com.api_endpoint.common.StringResource.invalid_input
import allanksr.com.api_endpoint.common.StringResource.loading
import allanksr.com.api_endpoint.common.StringResource.timerFormat
import allanksr.com.api_endpoint.data.endpointDto.Promotion
import allanksr.com.api_endpoint.data.remote.local.PromotionalItem
import allanksr.com.api_endpoint.repositories.PromotionalRepository
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val promotionalRepository: PromotionalRepository,
    private val dispatchers: DispatcherProvider
) : ViewModel() {

    val promotionalItems = promotionalRepository.observeAllPromotionalItems()

    private val _endPointApiResponse = MutableLiveData<Event<Resource<Promotion>>>()
    val endPointApiResponseStatus: LiveData<Event<Resource<Promotion>>> = _endPointApiResponse

    fun insertPromotionalItemIntoDb(promotionalItem: PromotionalItem) = viewModelScope.launch {
        promotionalRepository.insertPromotionalItem(promotionalItem)
    }

    fun insertPromotionalItem(promotionalCode: String) {
        if (promotionalCode.isEmpty()) {
            _endPointApiResponse.postValue(Event(Resource.error(field_must_not_be_empty, null)))
            return
        }
        if (promotionalCode.length > Constants.maxPromoLength) {
            _endPointApiResponse.postValue(
                Event(
                    Resource.error(
                        String.format(exceed_characters, Constants.maxPromoLength),
                        null
                    )
                )
            )
            return
        }

        _endPointApiResponse.postValue(Event(Resource.loading(loading, null)))
        searchPromotionalCode(promotionalCode)
    }

    fun deletePromotionalItem(promotionalItem: PromotionalItem) = viewModelScope.launch {
        promotionalRepository.deletePromotionalItem(promotionalItem)
    }

    private fun searchPromotionalCode(codePromo: String) {
        viewModelScope.launch {
            val response = promotionalRepository.searchPromotionalCode(codePromo)
            _endPointApiResponse.value = Event(response)
        }
    }

    private val _counterTime = MutableLiveData<Event<Resource<String>>>()
    val counterTime: LiveData<Event<Resource<String>>> = _counterTime
    val runningTime: MutableState<Boolean> = mutableStateOf(false)
    fun counter(timeWasApplied: Long, totalTime: Long) {
        if (timeWasApplied < 1 && totalTime < 1) {
            _counterTime.value = Event(Resource.error(invalid_input, null))
            return
        }
        viewModelScope.launch(dispatchers.main) {
            while (runningTime.value && System.currentTimeMillis() - timeWasApplied < totalTime) {
                val days =
                    TimeUnit.MILLISECONDS.toDays(totalTime - (System.currentTimeMillis() - timeWasApplied))
                val hours =
                    TimeUnit.MILLISECONDS.toHours(totalTime - (System.currentTimeMillis() - timeWasApplied)) % 24
                val minutes =
                    TimeUnit.MILLISECONDS.toMinutes(totalTime - (System.currentTimeMillis() - timeWasApplied)) % 60
                val seconds =
                    TimeUnit.MILLISECONDS.toSeconds(totalTime - (System.currentTimeMillis() - timeWasApplied)) % 60

                delay(1000)

                _counterTime.value = Event(
                    Resource.success(
                        String.format(
                            timerFormat,
                            days,
                            hours,
                            minutes,
                            seconds
                        )
                    )
                )
            }
        }
    }
}
