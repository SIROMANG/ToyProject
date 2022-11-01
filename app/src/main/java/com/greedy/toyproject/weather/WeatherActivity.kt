package com.greedy.toyproject.weather

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.toyproject.MypageActivity
import com.greedy.toyproject.R
import com.greedy.toyproject.databinding.ActivityWeatherBinding
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class WeatherActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityWeatherBinding.inflate(layoutInflater) }

    private var base_date = "20210510"
    private var base_time = "1400"
    private var nx = "55"
    private var ny = "127"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.weatherRecyclerView.layoutManager = LinearLayoutManager(this)

        binding.toDate.text =
            SimpleDateFormat("MM월 dd일", Locale.getDefault()).format(Calendar.getInstance().time) + " 날씨🌞"
        setWeather(nx, ny)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(WeatherService::class.java)

        val call = ApiObject.retrofitService.getWeather(60, 1, "JSON", base_date, base_time, nx, ny)


        call.enqueue(object : retrofit2.Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if (response.isSuccessful) {

                    val it: List<Item> = response.body()!!.response.body.items.item

                    val weatherArr = arrayOf(ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather(), ModelWeather())

                    var index = 0
                    val totalCount = response.body()!!.response.body.totalCount - 1
                    for (i in 0..totalCount) {
                        index %= 6
                        when(it[i].category) {
                            "PTY" -> weatherArr[index].rainType = it[i].fcstValue
                            "REH" -> weatherArr[index].humidity = it[i].fcstValue
                            "SKY" -> weatherArr[index].sky = it[i].fcstValue
                            "T1H" -> weatherArr[index].temp = it[i].fcstValue
                            else -> continue
                        }
                        index++
                    }

                    weatherArr[0].fcstTime = "지금"
                    for (i in 0..5) weatherArr[i].fcstTime = it[i].fcstTime

                    binding.weatherRecyclerView.adapter = WeatherAdapter(weatherArr)

                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                val weatherError = findViewById<TextView>(R.id.weatherError)
                weatherError.text = "api fail : " +  t.message.toString() + "\n 다시 시도해주세요."
                weatherError.visibility = View.VISIBLE
                Log.d("api fail", t.message.toString())
            }


        })

        val moveHome = Intent(this, MypageActivity::class.java)
        binding.HomeButton.setOnClickListener {
            startActivity(moveHome)
        }
    }

    private fun getBaseTime(h : String, m : String) : String {
        var result = ""


        if (m.toInt() < 45) {
            if (h == "00") result = "2330"
            else {
                var resultH = h.toInt() - 1
                if (resultH < 10) result = "0" + resultH + "30"
                else result = resultH.toString() + "30"
            }
        }
        else result = h + "30"

        return result
    }

    fun setWeather(nx: String, ny: String) {
        val cal = Calendar.getInstance()
        base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)
        val timeH = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        val timeM = SimpleDateFormat("HH", Locale.getDefault()).format(cal.time)
        base_time = getBaseTime(timeH, timeM)
        if (timeH == "00" && base_time == "2330") {
            cal.add(Calendar.DATE, -1).toString()
            base_date = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.time)

        }
    }



}
