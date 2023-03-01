package com.example.sqltest.adapter

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sqltest.db.Dao
import com.example.sqltest.mapper.map
import com.example.sqltest.model.NameModel
import kotlinx.coroutines.delay

class NamePagingSrc(
    private val dao: Dao,
    private val isParent: Boolean,
    private val parentId: Int = 0
) : PagingSource<Int, NameModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NameModel> {
        val page = params.key ?: 0

        return try {
            val entities = if (isParent) {
                dao.getPagedParentList(params.loadSize, page * params.loadSize)
            } else {
                dao.getPagesChildList(params.loadSize, page * params.loadSize, parentId)
            }
            // simulate page loading
            if (page != 0) delay(1000)

            LoadResult.Page(
                data = entities.map { it.map() },
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, NameModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}