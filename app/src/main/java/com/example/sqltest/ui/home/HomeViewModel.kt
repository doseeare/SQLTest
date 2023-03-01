package com.example.sqltest.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.sqltest.SqlApp
import com.example.sqltest.adapter.NamePagingSrc

class HomeViewModel : ViewModel() {

    val data = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false,
            initialLoadSize = 20
        ),
    ) {
        NamePagingSrc(SqlApp.db.dao(), true)
    }.flow.cachedIn(viewModelScope)

}