package com.greedy.toyproject

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.toyproject.databinding.ActivityMypageBinding
import com.greedy.toyproject.databinding.ActivityPostBinding
import com.greedy.toyproject.databinding.ItemRecyclerBinding

class PostActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityPostBinding.inflate(layoutInflater) }
    val helper = SqliteHelper(this, "post", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        val adapter = RecyclerAdapter()
        adapter.helper = helper

        adapter.listData.addAll(helper.selectPost())

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.btnRegist.setOnClickListener {
            val intent = Intent(this, PtregistActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            this.startActivity(intent)

        }
        binding.homebtn.setOnClickListener {
            val intent = Intent(this, MypageActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            this.startActivity(intent)

        }

    }
}