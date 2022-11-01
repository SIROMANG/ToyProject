package com.greedy.toyproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.toyproject.databinding.ActivityMypageBinding
import kotlin.concurrent.thread

class MypageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityMypageBinding.inflate(layoutInflater) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.currentUser.text = "${auth.currentUser?.email}"
        binding.btnLogout.setOnClickListener{
            val intent = Intent (this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            auth.signOut()
        }

        /* 회원탈퇴 */
        binding.btnUnregister.setOnClickListener{

            val intent = Intent (this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            Toast.makeText(this, "탈퇴 완료 되었습니다.", Toast.LENGTH_SHORT).show()
            deleteId()
            auth.signOut()
            finish()

        }


        binding.btnLook.setOnClickListener {
            val intent = Intent (this, PostActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            this.startActivity(intent)
        }

        binding.btnNews.setOnClickListener {
            val intent = Intent (this, NewsList::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            this.startActivity(intent)
        }


    }

}

private fun deleteId() {
    FirebaseAuth.getInstance().currentUser!!.delete().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            FirebaseAuth.getInstance().signOut()
        }
    }
}


