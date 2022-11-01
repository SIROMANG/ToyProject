package com.greedy.toyproject.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.greedy.toyproject.databinding.ListWeatherBinding

class WeatherAdapter (var items : Array<ModelWeather>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder> () {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
            val binding = ListWeatherBinding.inflate(LayoutInflater.from(parent.context),  parent, false)
            return ViewHolder(binding)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: WeatherAdapter.ViewHolder, position: Int) {
            val item = items[position]
            holder.setItem(item)
        }

        inner class ViewHolder(val binding: ListWeatherBinding) : RecyclerView.ViewHolder(binding.root){
            fun setItem(item : ModelWeather){
                binding.toTime.text = getTime(item.fcstTime)
                binding.rainType.text = getRainType(item.rainType)
                binding.humidity.text = item.humidity
                binding.sky.text = getSky(item.sky)
                binding.temp.text = item.temp + "°"
                binding.recommends.text = getRecommends(item.temp.toInt())
            }
        }

        fun getRainType(rainType: String) : String {
            return when(rainType) {
                "0" -> "없음"
                "1" -> "비"
                "2" -> "비/눈"
                "3" -> "눈"
                "5" -> "빗방울"
                "6" -> "빗방울눈날림"
                "7" -> "눈날림"
                else -> "오류 rainType : " + rainType
            }
        }

        fun getSky(sky : String) : String {
            return when(sky) {
                "1" -> "맑음"
                "3" -> "구름 많음"
                "4" -> "흐림"
                else -> "오류 rainType : " + sky
            }
        }

      fun getTime(factTime : String) : String {
          if(factTime != "지금") {
              var hourSystem : Int = factTime.toInt()
              var hourSystemString = ""

              if(hourSystem == 0) {
                  return "오전 12시"
              } else if(hourSystem > 2100) {
                  hourSystem -= 1200
                  hourSystemString = hourSystem.toString()
                  return "오후 ${hourSystemString[0]}${hourSystemString[1]}시"
              } else if(hourSystem == 1200){
                  return "오후 12시"
              } else if(hourSystem > 1200) {
                  hourSystem -= 1200
                  hourSystemString = hourSystem.toString()
                  return "오후 ${hourSystemString[0]}시"
              }

              else if(hourSystem >= 1000) {
                  hourSystemString = hourSystem.toString()

                  return "오전 ${hourSystemString[0]}${hourSystemString[1]}시"
              } else {
                  hourSystemString = hourSystem.toString()

                  return "오전 ${hourSystemString[0]}시"
              }
          } else {
              return factTime
          }
      }

      fun getRecommends(temp : Int) : String {
            return when (temp) {
                in 5..8 -> "울 코트, 가죽 옷, 기모"
                in 9..11 -> "트렌치 코트, 야상, 점퍼"
                in 12..16 -> "자켓, 가디건, 청자켓"
                in 17..19 -> "니트, 맨투맨, 후드, 긴바지"
                in 20..22 -> "블라우스, 긴팔 티, 슬랙스"
                in 23..27 -> "얇은 셔츠, 반바지, 면바지"
                in 28..50 -> "민소매, 반바지, 린넨 옷"
                else -> "패딩, 누빔 옷, 목도리"
            }
        }




    }
