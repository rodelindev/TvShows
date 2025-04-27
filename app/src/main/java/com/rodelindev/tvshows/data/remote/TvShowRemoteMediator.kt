package com.rodelindev.tvshows.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rodelindev.tvshows.data.mapper.toDomainList
import com.rodelindev.tvshows.data.remote.response.TvShowResponseDTO
import com.rodelindev.tvshows.data.remote.service.TvShowApi
import com.rodelindev.tvshows.domain.model.TvShow
import com.rodelindev.tvshows.domain.model.TvShowFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException


class TvShowRemoteMediator(
    private val tvShowApi: TvShowApi,
    private val tvShowFilter: TvShowFilter,
) : PagingSource<Int, TvShow>() {

    override fun getRefreshKey(state: PagingState<Int, TvShow>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvShow> {
        return try {
            val currentPage = params.key ?: 1

            val request = when (tvShowFilter) {
                TvShowFilter.POPULAR -> handleRequest { tvShowApi.getPopularTvShows(page = currentPage) }
                TvShowFilter.TOP_RATED -> handleRequest { tvShowApi.getTopRatedTvShows(page = currentPage) }
                TvShowFilter.ON_TV -> handleRequest { tvShowApi.getOnTheAirTvShows(page = currentPage) }
                TvShowFilter.AIRING_TODAY -> handleRequest { tvShowApi.getAiringTodayTvShows(page = currentPage) }
            }

            LoadResult.Page(
                data = request.results.toDomainList(),
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (request.results.isEmpty()) null else request.page.plus(1)
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    private suspend fun handleRequest(request: suspend () -> TvShowResponseDTO): TvShowResponseDTO {
        return withContext(Dispatchers.IO) {
            request()
        }
    }
}
