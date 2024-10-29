package com.example.paging3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSourceFactory
import com.example.paging3.presentation.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickRepository @Inject constructor(val api : RickMortyApiService) {

    companion object{
        const val MAX_ITEMS = 10
        const val  PREFETCH_ITEMS = 3
    }

    fun getAllCharacters(): Flow<PagingData<CharacterModel>> {
        return  Pager(config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                CharacterPagingSource(api)
            }).flow
    }
}