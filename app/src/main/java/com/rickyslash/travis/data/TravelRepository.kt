package com.rickyslash.travis.data

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rickyslash.travis.api.ApiService
import com.rickyslash.travis.api.response.HighlightDataItem

class TravelRepository(private val apiService: ApiService) {
    fun getHighlight(): LiveData<PagingData<HighlightDataItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            pagingSourceFactory = {
                HighlightPagingSource(apiService)
            }
        ).liveData
    }
}