package com.greedy.toyproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.greedy.toyproject.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val binding by lazy { ActivityJoinBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        binding.btnJoin.setOnClickListener {
            val email = binding.emeil.text.toString()
            val password = binding.password.text.toString()
            val checkPassword = binding.checkPassword.text.toString()

            if (email.isEmpty() || password.isEmpty() || checkPassword.isEmpty()) {
                Toast.makeText(this, "모두 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if(!password.equals(checkPassword)){
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            } else if(password.length < 6) {
                Toast.makeText(this, "6자리 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }else {
                createAccount(email, password)
            }

        }
        binding.login.setOnClickListener { finish() }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "회원 가입 완료", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "6자리 이상의 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        Log.d("email", "${email.toString()} ")
        Log.d("password", "${password.toString()} ")


    }
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            finish()
        }
    }
}

