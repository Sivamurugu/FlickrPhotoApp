package com.app.taskapp.service

import com.app.taskapp.model.PhotosResponse
import com.app.taskapp.util.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    /*@GET(Constants.IMAGE_URL)
    suspend fun imageList(@Query("method") method: String,@Query("api_key") api_key: String,
                          @Query("format") format: String, @Query("nojsoncallback") nojsoncallback: String,
                          @Query("safe_search") safe_search: String, @Query("text") text: String): PhotosResponse
*/
    @GET(Constants.IMAGE_URL)
    suspend fun imageList(@Query("method") method: String,@Query("api_key") api_key: String,
                          @Query("format") format: String, @Query("nojsoncallback") nojsoncallback: String,
                          @Query("safe_search") safe_search: String, @Query("text") text: String): PhotosResponse
}