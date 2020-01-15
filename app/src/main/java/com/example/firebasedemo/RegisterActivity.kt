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
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        initView()
    }

    private fun initView() {
        btnRegisterSubmit.setOnClickListener(this)
        auth = FirebaseAuth.getInstance()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegisterSubmit -> {
                onRegister()
            }
        }
    }

    private fun onRegister() {
        val userName = edtUserName.text.toString()
        val password = edtPassword.text.toString()
        val retypePassword = edtPasswordRetype.text.toString()
        if (password != retypePassword) {
            Toast.makeText(this, "Password retype not correct!!!", Toast.LENGTH_SHORT).show()
        } else if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(password) || TextUtils.isEmpty(retypePassword)) {
            Toast.makeText(this, "Must be not empty", Toast.LENGTH_SHORT).show()
        } else if (password.length < 6) {
            Toast.makeText(this, "Password at less 6 character", Toast.LENGTH_SHORT).show()
        } else if (!isEmailValid(userName)) {
            Toast.makeText(this, "Email is not correct", Toast.LENGTH_SHORT).show()
        } else {
            registerUser(userName, password)
        }
    }

    private fun registerUser(userName: String, password: String) {
        auth.createUserWithEmailAndPassword(userName, password).addOnCompleteListener(this, object :
            OnCompleteListener<AuthResult> {
            override fun onComplete(p0: Task<AuthResult>) {
                if (p0.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Register Successfull",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Register Fail",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        })
    }
}
