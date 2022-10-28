package com.greedy.toyproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greedy.toyproject.databinding.ActivityPostdetailBinding


class PostdetailActivity: AppCompatActivity()  {

    val binding by lazy { ActivityPostdetailBinding.inflate(layoutInflater) }
    val helper = SqliteHelper(this, "post", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}