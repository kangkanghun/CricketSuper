package com.moingay.cricketsuper.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil.network.HttpException
import com.moingay.cricketsuper.data.model.response.Datum
import com.moingay.cricketsuper.data.model.response.Item
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okio.IOException

class SeriesMatchDataSource(
    private val competitionId: Long,
    private val iCricketApi: ICricketApi,
) : PagingSource<Int, Item>() {

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        delay(2000L)
        val page = params.key ?: 1
        val itemResponse = iCricketApi.getCompetitionMatches(competitionId, page).body()
        return try {
            LoadResult.Page(
                data = itemResponse?.response?.items ?: emptyList(),
                prevKey = if (page == 1) null else page.minus(1),
                nextKey = if (itemResponse?.response?.items?.isEmpty() == true) null else page.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}