package com.rickyslash.travis.ui.travelplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rickyslash.travis.api.MLApiConfig
import com.rickyslash.travis.api.response.DestinationRecomResponse
import com.rickyslash.travis.api.response.DestinationRecommendationItem
import com.rickyslash.travis.api.response.HotelRecommendationItem
import com.rickyslash.travis.api.response.RestaurantRecommendationItem
import com.rickyslash.travis.helper.addKabupatenKotaPrefix
import com.rickyslash.travis.model.CurrentStateModel
import com.rickyslash.travis.model.CurrentStatePreferences
import com.rickyslash.travis.model.DestinationRecomRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TravelPlanViewModel(private val currentPreferences: CurrentStatePreferences): ViewModel() {

    private val _listTravelPlanItem = MutableLiveData<List<DestinationRecommendationItem>>()
    val listTravelPlanItem: LiveData<List<DestinationRecommendationItem>> = _listTravelPlanItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    private val _responseMessage = MutableLiveData<String?>()
    val responseMessage: LiveData<String?> = _responseMessage

    fun getPreferences(): CurrentStateModel {
        return currentPreferences.getCurrentState()
    }

    @Suppress("UNCHECKED_CAST")
    fun getTravelPlan(travelPref: List<String>, restoPref: List<String>) {
        _isLoading.value = true

        var city = addKabupatenKotaPrefix(getPreferences().currentLocation!!)
        if (!city.equals("YOGYAKARTA", ignoreCase = true) && !city.equals("JAKARTA PUSAT", ignoreCase = true)) {
            city = "Kota Yogyakarta"
        }

        val request = DestinationRecomRequest(
            city = city,
            userDestinationPreferences = travelPref,
            userRestaurantPreferences = restoPref
        )

        val client = MLApiConfig.getApiService().getDestinationRecom(request)
        client.enqueue(object : Callback<DestinationRecomResponse> {
            override fun onResponse(
                call: Call<DestinationRecomResponse>,
                response: Response<DestinationRecomResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val destinationRecom = responseBody.result.destinationRecommendation
                        val hotelRecom = castHotelListToDestinationItems(responseBody.result.hotelRecommendation)
                        val restoRecom = castRestoListToDestinationItems(responseBody.result.restaurantRecommendation)
                        val allRecom = destinationRecom + hotelRecom + restoRecom
                        _isError.value = false
                        _listTravelPlanItem.value = allRecom
                        _responseMessage.value = null
                    }
                } else {
                    _isLoading.value = false
                    _isError.value = true
                    _responseMessage.value = response.message()
                    _responseMessage.value = null
                }
            }

            override fun onFailure(call: Call<DestinationRecomResponse>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
                _responseMessage.value = t.message
                _responseMessage.value = null
            }
        })
    }

    private fun castHotelListToDestinationItems(listHotelItem: List<HotelRecommendationItem>): List<DestinationRecommendationItem> {
        return listHotelItem.map { hotelItem ->
            DestinationRecommendationItem(
                about = hotelItem.about,
                imgUrl = hotelItem.imgUrl,
                name = hotelItem.name,
                id = hotelItem.id,
                category = hotelItem.category,
                address = hotelItem.address,
                city = hotelItem.city,
                latitude = hotelItem.latitude,
                mapUrl = hotelItem.mapUrl,
                rating = hotelItem.rating,
                totalReview = hotelItem.totalReview,
                longitude = hotelItem.longitude,
                keywordCategory = hotelItem.keywordCategory
            )
        }
    }

    private fun castRestoListToDestinationItems(listRestoItem: List<RestaurantRecommendationItem>): List<DestinationRecommendationItem> {
        return listRestoItem.map { restoItem ->
            DestinationRecommendationItem(
                about = restoItem.about,
                imgUrl = restoItem.imgUrl,
                name = restoItem.name,
                id = restoItem.id,
                category = restoItem.category,
                address = restoItem.address,
                city = restoItem.city,
                latitude = restoItem.latitude,
                mapUrl = restoItem.mapUrl,
                rating = restoItem.rating,
                totalReview = restoItem.totalReview,
                longitude = restoItem.longitude,
                keywordCategory = restoItem.keywordCategory
            )
        }
    }

}