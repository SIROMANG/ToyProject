package com.greedy.toyproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.toyproject.databinding.ItemRecyclerBinding
import java.text.SimpleDateFormat

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.Holder>(){
    var listData = mutableListOf<Post>()
    var helper:SqliteHelper? = null

    inner class Holder(val binding: ItemRecyclerBinding) : RecyclerView.ViewHolder(binding.root){

        var pPost: Post? = null


//        init {
//            binding.deleteButton.setOnClickListener{
//                helper?.deletePost(pPost!!)
//                listData.remove(pPost)
//                notifyDataSetChanged()
//            }
//        }

        fun setPost(post: Post){
            binding.textNo.text = "${post.no}"
            binding.textContent.text = post.content
            val sdf = SimpleDateFormat("yyyy/MM/dd")
            binding.textDatetime.text = "${sdf.format(post.datetime)}"

            pPost = post
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  Holder(binding)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val post = listData[position]
        holder.setPost(post)

    }

}