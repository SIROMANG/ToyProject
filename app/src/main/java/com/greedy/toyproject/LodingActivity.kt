package com.greedy.toyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.greedy.toyproject.databinding.ActivityLodingBinding
import com.greedy.toyproject.databinding.ActivityMainBinding
import com.greedy.toyproject.weather.WeatherActivity
import kotlin.concurrent.thread

class LodingActivity : AppCompatActivity() {

    val binding by lazy { ActivityLodingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        /* progreesbar를 3초 정도 있다가 사라지게 하기 */
        /* 스레드 처리*/
        thread(start = true) {
            Thread.sleep(3500) // 동작 3초 후에 중지

            runOnUiThread {
                showLoding(false)
            }
        }
    }

    /* 안보이게 처리 */
    fun showLoding(show: Boolean){
        binding.snowman.visibility = if(show) View.VISIBLE else View.GONE
        binding.lodingBack.visibility = if(show) View.VISIBLE else View.GONE
        //show 따라 View 처리가 VISIBLE or GONE
        startActivity(Intent(this, MypageActivity::class.java))

        finish()
    }
}