package com.app.taskapp.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.app.taskapp.data.repo.ApiRepository
import com.app.taskapp.model.InputParams
import com.app.taskapp.model.ResultData
import com.app.taskapp.service.Resource
import com.app.taskapp.util.Utils
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class ApiViewModel @ViewModelInject constructor(application: Application,private val apiRepository: ApiRepository):AndroidViewModel(application) {

    fun getImages(inputParams: InputParams) = liveData<Resource<List<ResultData>>> {
        if (Utils.isOnline()) {
            apiRepository.getImages(inputParams)
                .onStart {
                    emit(Resource.loading(data = null))
                }
                .catch {
                    emit(Resource.error(data = null, msg = "Cannot reach server..try again"))
                }
                .collect {
                    if (it.stat.equals("ok")){
                        emit(Resource.success(it.photos!!.photo))
                    }
                    else{
                        emit(Resource.error(data = null, msg = "No data found.."))
                    }
                }
        } else {
            emit(Resource.error(data = null, msg = "No internet connection"))
        }

    }

}