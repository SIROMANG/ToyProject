package com.greedy.toyproject

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.greedy.toyproject.databinding.ActivityPtregistBinding

class PtregistActivity: AppCompatActivity()  {

    val binding by lazy { ActivityPtregistBinding.inflate(layoutInflater) }
    val helper = SqliteHelper(this, "post", 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = RecyclerAdapter()
        adapter.helper = helper

        adapter.listData.addAll(helper.selectPost())

        /* main activity의 recyclerView에 생성한 어댑터 연결하고 레이아웃 설정 */
//        binding.recyclerView.adapter = adapter
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        /* 메모 저장 버튼 이벤트 */
        binding.btnregi.setOnClickListener {
            /* 메모 내용이 입력 된 경우만 동작 */
            if (binding.editText.text.toString().isNotEmpty()) {
                val post = Post(
                    null, binding.editText.text.toString(),
                    System.currentTimeMillis()
                )
                helper.insertPost(post)

                /* DB가 변동 되었을 때 화면도 변동될 수 있도록 adapter의 data를 수정하고
                * 데이터가 변화 했음을 알린다. */
                adapter.listData.clear()
                adapter.listData.addAll(helper.selectPost())
                adapter.notifyDataSetChanged()

                /* 입력란 비우기 */
                binding.editText.setText("")

            }





            binding.btnregi.setOnClickListener {
                val intent = Intent(this, PostActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                this.startActivity(intent)

            }

        }



        /* 카메라 버튼 클릭 시 권한 확인 메소드 호촐*/
        binding.btnSelect.setOnClickListener{
            checkPermission()
        }
    }

    /* 권한 확인 메소드 [카메라]*/
    fun checkPermission(){
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        if(cameraPermission == PackageManager.PERMISSION_GRANTED){
            startProcess()
        } else{
            requestPermission()

        }

    }

    /* 권한이 있을 경우 동작하는 코드[카메라]*/
    fun startProcess(){
        Toast.makeText(this,"카메라를 실행합니다.", Toast.LENGTH_SHORT).show()
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 99 )
    }



    /* 권한이 승인 되었을 때 동작할 콜백 메소드[카메라]*/
    override fun onRequestPermissionsResult(
        requestCode: Int,                   //요청 주체를 확인하는 코드
        permissions: Array<out String>,     //요청한 권한 목록
        grantResults: IntArray              //권한 목록에 대한 승인/미승인 값(권한 목록의 갯수와 같은 수가 전달)
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            99 -> {
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    startProcess()
                }else{
                    finish()

                }
            }
        }
    }

}
