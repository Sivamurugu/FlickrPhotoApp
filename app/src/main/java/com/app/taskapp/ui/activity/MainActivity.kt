package com.app.taskapp.ui.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.app.taskapp.R
import com.app.taskapp.databinding.ActivityMainBinding
import com.app.taskapp.model.InputParams
import com.app.taskapp.model.ResultData
import com.app.taskapp.service.Status
import com.app.taskapp.ui.adapter.ImageAdapter
import com.app.taskapp.util.viewBinding
import com.app.taskapp.viewmodel.ApiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val binding: ActivityMainBinding by viewBinding()
    private val apiViewModel:ApiViewModel by viewModels()
    var imageAdapter:ImageAdapter?=null
    var imageList:ArrayList<ResultData>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageList= ArrayList()
        initViews()

        binding.btnSearch.setOnClickListener {
            val searchTxt=binding.edtSearch.text.toString().trim()
            if(TextUtils.isEmpty(searchTxt)){
                binding.edtSearch.error="Required"
            }else{
                getImages(searchTxt)
            }
        }
    }

    private fun initViews() {
        binding.recyclePhotos.layoutManager = GridLayoutManager(this,3)
        imageAdapter = ImageAdapter(this)
        binding.recyclePhotos.adapter = imageAdapter
        checkEmpty()
    }

    private fun notifyData(){
        imageAdapter!!.setPhotos(imageList!!)
        checkEmpty()
    }

    private fun getImages(searchText:String) {
        val inputParams = InputParams()

        inputParams.method = "flickr.photos.search"
        inputParams.api_key = "3e7cc266ae2b0e0d78e279ce8e361736"
        inputParams.format = "json"
        inputParams.nojsoncallback = "1"
        inputParams.safe_search = "1"
        inputParams.text = searchText

        imageList!!.clear()

        apiViewModel.getImages(inputParams).observe(this, Observer {
            it?.let {
                when(it.status){
                    Status.LOADING -> {
                        showProgress()
                    }
                    Status.SUCCESS -> {
                        dismissProgress()
                        if (it.data!!.isNotEmpty()){
                            imageList!!.addAll(it.data)
                        }
                        else {
                            Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        }
                        notifyData()
                    }
                    Status.ERROR -> {
                        dismissProgress()
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                        notifyData()
                    }
                }
            }
        })

    }

    fun showProgress(){
        binding.progressLoad.visibility = View.VISIBLE
    }
    fun dismissProgress(){
        binding.progressLoad.visibility = View.GONE
    }

    fun checkEmpty(){
        if(imageList!!.size>0){
            binding.tvNoData.visibility=View.GONE
            binding.recyclePhotos.visibility=View.VISIBLE
        }else{
            binding.tvNoData.visibility=View.VISIBLE
            binding.recyclePhotos.visibility=View.GONE
        }
    }
}