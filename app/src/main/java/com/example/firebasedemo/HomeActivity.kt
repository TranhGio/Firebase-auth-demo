package com.example.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    val languages = ArrayList<String>()
    var databasePreference = FirebaseDatabase.getInstance().reference.child("Users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        auth = FirebaseAuth.getInstance()
    }

    private fun initView() {
        btnLogout.setOnClickListener(this)
        btnAddLanguage.setOnClickListener(this)
//        databasePreference.child("DangVinh").child("PhoneNumber").setValue("0905.705.981")
//            .addOnFailureListener(object :
//                OnFailureListener {
//                override fun onFailure(p0: Exception) {
//                    Log.i("FirebaseTest", p0.message.toString())
//                }
//
//            })
////        Log.i("FirebaseTest", "Add successfull")
//        val hashMap = HashMap<String, Any>()
//        hashMap.put("Name", "Nguyen Dang Vinh")
//        hashMap.put("Phone_number", "0905705981")
//        databasePreference.updateChildren(hashMap)
//        val hashMap = HashMap<String, Any>()
//        hashMap.put("L1", "Java")
//        hashMap.put("L2", "Kotlin")
//        hashMap.put("L3", ".Net")
//        hashMap.put("L4", "HTML")
//        databasePreference.child("DangVinh").child("Language").updateChildren(hashMap)

        val adapter = LanguageAdapter(languages, this)
        recyclerView.setHasFixedSize(true)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            RecyclerView.VERTICAL
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.adapter = adapter
        databasePreference.child("DangVinh").child("Language").addValueEventListener(object :
            ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@HomeActivity, "Fail to read value!!!", Toast.LENGTH_SHORT)
                    .show()
                Log.i("FirebaseDebug", p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                languages.clear()
                p0.children.forEach {
                    languages.add(it.value.toString())
                }
                adapter.notifyDataSetChanged()
            }
        })

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnLogout -> {
                auth.signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            R.id.btnAddLanguage -> {
                databasePreference.child("DangVinh").child("Language")
                    .child(edtLanguageShortName.text.toString())
                    .setValue(edtLanguageName.text.toString())
                edtLanguageShortName.setText("")
                edtLanguageName.text = Editable.Factory.getInstance().newEditable("")
            }
        }
    }
}