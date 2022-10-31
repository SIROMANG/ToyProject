package com.greedy.toyproject

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.greedy.toyproject.databinding.ItemRecyclerBinding
import com.greedy.toyproject.databinding.NewsBinding

@RequiresApi(Build.VERSION_CODES.N)
class NewsRecyclerAdapter : RecyclerView.Adapter<Holder>() {


    var listData = mutableListOf<Items>()

    var createViewHolderCount: Int = 0
    var bindViewHolderCount: Int = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding
                = NewsBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        Log.d("Adapter",  "CreateView ${createViewHolderCount++}")

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val memo = listData[position]
        holder.setMemo(memo)

        Log.d("Adapter", "BindView ${bindViewHolderCount++}")

    }

    override fun getItemCount(): Int {
        return listData.size
    }
}

@RequiresApi(Build.VERSION_CODES.N)
class Holder(val binding: NewsBinding) : RecyclerView.ViewHolder(binding.root) {


    fun setMemo(memo: Items) {
        binding.newsTitle.text = "${memo.titleWithoutHTML}"
        binding.newsContent.text = "${memo.descriptionWithoutHTML}"
    }

}

