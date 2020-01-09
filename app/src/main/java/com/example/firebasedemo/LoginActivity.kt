package com.example.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebasedemo.Utils.isEmailValid
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initView()
    }

    private fun initView() {
        btnLoginSubmit.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLoginSubmit -> {
                onLogin()
            }
        }
    }

    private fun onLogin() {
        val userName = edtUserName.text.toString()
        val password = edtPassword.text.toString()
        if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please fill in all input", Toast.LENGTH_SHORT).show()
        } else if (!isEmailValid(userName)) {
            Toast.makeText(this, "Email is not correct", Toast.LENGTH_SHORT).show()
        } else {
            auth.signInWithEmailAndPassword(userName, password)
                .addOnCompleteListener(object : OnCompleteListener<AuthResult> {
                    override fun onComplete(p0: Task<AuthResult>) {
                        if (p0.isSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Login successfull",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Login fail", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                })
        }
    }
}