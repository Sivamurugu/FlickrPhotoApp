package com.app.taskapp.model


data class PhotosResponse(val photos: PhotosData?, val stat: String?)
data class PhotosData(val photo: List<ResultData>?, val page: Int?)

data class ResultData(val id: String?, val owner: String?, val secret: String?, val server: String?
                      , val farm: Int?, val title: String?, val ispublic: Int?, val isfriend: Int?, val isfamily: Int?)
