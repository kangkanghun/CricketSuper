package com.moingay.cricketsuper.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.moingay.cricketsuper.data.model.response.Datum
import kotlinx.coroutines.delay
import okio.IOException

class SportDataSource(
    private val iSportApi: ISportApi
) : PagingSource<Int, Datum>() {
    override fun getRefreshKey(state: PagingState<Int, Datum>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Datum> {
        delay(2000L)
        val page = params.key ?: 1
        val newsResponse = iSportApi.getSportPost(page).body()
        return try {
            LoadResult.Page(
                data = newsResponse?.data ?: emptyList(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (newsResponse?.data?.isEmpty() == true) null else page.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

}