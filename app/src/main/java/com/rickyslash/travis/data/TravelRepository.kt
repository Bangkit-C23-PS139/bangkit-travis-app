package com.rickyslash.travis.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rickyslash.travis.api.ApiService
import com.rickyslash.travis.api.response.HighlightDataItem
import com.rickyslash.travis.api.response.ServiceDataItem

class TravelRepository(private val apiService: ApiService) {
    fun getHighlight(city: String): LiveData<PagingData<HighlightDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                HighlightPagingSource(apiService, city)
            }
        ).liveData
    }
    fun getService(city: String): LiveData<PagingData<ServiceDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                initialLoadSize = 5
            ),
            pagingSourceFactory = {
                ServicePagingSource(apiService, city)
            }
        ).liveData
    }
}