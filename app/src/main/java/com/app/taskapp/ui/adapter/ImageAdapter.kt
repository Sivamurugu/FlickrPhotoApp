package com.app.taskapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.taskapp.R
import com.app.taskapp.databinding.ItemMovieBinding
import com.app.taskapp.model.ResultData
import com.bumptech.glide.Glide


class ImageAdapter (val context: Context) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var list = emptyList<ResultData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return ImageAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        val model = list[position]
        with(holder){
            try {
                val photoUrl="http://farm"+model.farm+".static.flickr.com/"+model.server+"/"+model.id+"_"+model.secret+".jpg"
                Glide.with(context).load(photoUrl).into(binding.imgPhoto)
            } catch (e: Exception) {
            }
        }
    }

    internal fun setPhotos(words: List<ResultData>) {
        this.list = words
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemMovieBinding.bind(itemView)
    }

}