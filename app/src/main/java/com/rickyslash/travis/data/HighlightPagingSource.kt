package com.rickyslash.travis.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rickyslash.travis.api.ApiService
import com.rickyslash.travis.api.response.HighlightDataItem

class HighlightPagingSource(private val apiService: ApiService, private var city: String) : PagingSource<Int, HighlightDataItem>() {

    override fun getRefreshKey(state: PagingState<Int, HighlightDataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, HighlightDataItem> {
        return try {

            if (!city.equals("YOGYAKARTA", ignoreCase = true) && !city.equals("JAKARTA PUSAT", ignoreCase = true)) {
                city = "YOGYAKARTA"
            }

            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData = apiService.getHighlight(position, params.loadSize, city).data.data

            LoadResult.Page(
                data = responseData,
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private companion object {
        const val INITIAL_PAGE_INDEX = 0
    }

}