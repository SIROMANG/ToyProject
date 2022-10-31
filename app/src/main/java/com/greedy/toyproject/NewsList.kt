package com.greedy.toyproject

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.signin.zab.API
import com.greedy.toyproject.databinding.ActivityMainBinding
import com.greedy.toyproject.databinding.ActivityNewsListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsList : AppCompatActivity() {

    val binding by lazy { ActivityNewsListBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //val data: MutableList<Items> = loadData()

        val CLIENT_ID = "z8TbA_26TaKGjjN20pKd"
        val CLIENT_SECRET = "1VavSJb9Rj"
        val BASE_URL_NAVER_API = "https://openapi.naver.com/"

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL_NAVER_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(NaverApi::class.java)
        val callGetSearchNews = api.getSearchNews(CLIENT_ID, CLIENT_SECRET, "날씨", 10, 1)
        //val data: MutableList<Items> = mutableListOf()

        callGetSearchNews.enqueue(object : Callback<ResultGetSearchNews> {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<ResultGetSearchNews>,
                response: Response<ResultGetSearchNews>
            ) {
                Log.d(TAG, "성공 : ${response.body()}")
                //data.addAll(response.body()?.items!!)
                var adapter = NewsRecyclerAdapter()
                adapter.listData = response.body()?.items!!.toMutableList()
                binding.newsRecycler.adapter = adapter
                binding.newsRecycler.layoutManager = LinearLayoutManager(baseContext)

            }
            override fun onFailure(call: Call<ResultGetSearchNews>, t: Throwable) {
                Log.d(TAG, "실패 : $t")
            }
        })

        val moveHome = Intent(this, MypageActivity::class.java)

        binding.HomeButton.setOnClickListener {
            startActivity(moveHome)
        }

       /* var adapter = NewsRecyclerAdapter()
        adapter.listData = data
        binding.newsRecycler.adapter = adapter
        binding.newsRecycler.layoutManager = LinearLayoutManager(this)
*/
    }


}