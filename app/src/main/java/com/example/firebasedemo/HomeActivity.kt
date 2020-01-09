package com.example.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
    }

    private fun initView() {
        btnLogout.setOnClickListener(this)
        val databasePreference = FirebaseDatabase.getInstance().reference.child("Users")
        databasePreference.child("DangVinh").child("PhoneNumber").setValue("0905.705.981")
            .addOnFailureListener(object :
                OnFailureListener {
                override fun onFailure(p0: Exception) {
                    Log.i("FirebaseTest", p0.message.toString())
                }

            })
//        Log.i("FirebaseTest", "Add successfull")
        val hashMap = HashMap<String, Any>()
        hashMap.put("Name", "Nguyen Dang Vinh")
        hashMap.put("Phone_number", "0905705981")
        databasePreference.updateChildren(hashMap)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }
    }
}