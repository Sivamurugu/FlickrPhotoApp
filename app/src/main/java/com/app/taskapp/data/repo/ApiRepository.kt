package com.app.taskapp.data.repo

import com.app.taskapp.model.InputParams
import com.app.taskapp.model.PhotosResponse
import com.app.taskapp.service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiRepository @Inject constructor(val apiService: ApiService) {

    fun getImages(inputParams: InputParams): Flow<PhotosResponse> {
        return flow {
            val response = apiService.imageList(inputParams.method!!,inputParams.api_key!!,inputParams.format!!,inputParams.nojsoncallback!!,inputParams.safe_search!!,inputParams.text!!)
            emit(response)
        }.flowOn(Dispatchers.IO)
    }
}