package com.example.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.*
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var auth: FirebaseAuth
    val languages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initView()
        auth = FirebaseAuth.getInstance()
//        addValueToCloudFireStore()
        getValueToCloudFireStore()
    }

    private fun getValueToCloudFireStore() {
        val firebaseFirestore = FirebaseFirestore.getInstance()
//        val documentReference = firebaseFirestore.collection("Country").document("Capital")
//        documentReference.get().addOnCompleteListener(object : OnCompleteListener<DocumentSnapshot> {
//            override fun onComplete(p0: Task<DocumentSnapshot>) {
//                if (p0.isSuccessful) {
//                    Toast.makeText(this@HomeActivity, "Success", Toast.LENGTH_SHORT).show()
//                    Log.i("FirebaseDemo", p0.result?.data.toString())
//                } else {
//                    Toast.makeText(this@HomeActivity, "Fail", Toast.LENGTH_LONG).show()
//                }
//            }
//        })
        firebaseFirestore.collection("Country").whereEqualTo("VietNam", "Da Nang")
            .get().addOnCompleteListener(object : OnCompleteListener<QuerySnapshot> {
                override fun onComplete(p0: Task<QuerySnapshot>) {
                    if (p0.isSuccessful) {
                        Toast.makeText(this@HomeActivity, "Success", Toast.LENGTH_SHORT).show()
                        p0.result?.forEach { Log.i("FirebaseDemo", "Id = ${it.id} : ${it.data}") }
                    } else {
                        Toast.makeText(this@HomeActivity, "Fail", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    private fun addValueToCloudFireStore() {
        val firebaseFirestore = FirebaseFirestore.getInstance()

        // ---- Add value ----
//        val cities = HashMap<String, Any>()
//        cities.put("Japanese", "Tokyo")
//        cities.put("Korean", "Seoul")
//        cities.put("VietName", "Ha Noi")
//        cities.put("Thailand", "Bangkok")
//        firebaseFirestore.collection("Country").document("Capital").set(cities)
//            .addOnCompleteListener(object : OnCompleteListener<Void> {
//                override fun onComplete(p0: Task<Void>) {
//                    if (p0.isSuccessful) {
//                        Toast.makeText(this@HomeActivity, "Add successful", Toast.LENGTH_SHORT).show()
//                    } else {
//                        Toast.makeText(this@HomeActivity, "Add Fail", Toast.LENGTH_SHORT).show()
//                    }
//                }
// })
        // ---- Set value ---- SetOptions.merge()
        /*val city = HashMap<String, Any>()
        city.put("Singapore", "Singapore")
        firebaseFirestore.collection("Country").document("Capital").set(city, SetOptions.merge())
            .addOnCompleteListener(object : OnCompleteListener<Void> {
                override fun onComplete(p0: Task<Void>) {
                    if (p0.isSuccessful) {
                        Toast.makeText(this@HomeActivity, "Add value successfully.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@HomeActivity, "Add Fail", Toast.LENGTH_SHORT).show()
                    }
                }
            })*/
        // ---- Set value with a unique document name
//        val cities = HashMap<String, Any>()
//        cities.put("England", "London")
//        cities.put("France", "Paris")
//        firebaseFirestore.collection("Country").add(cities).addOnCompleteListener {
//            if (it.isSuccessful) {
//                Toast.makeText(this@HomeActivity, "Add cities with unique key successfully", Toast.LENGTH_SHORT).show()
//            } else{
//                Toast.makeText(this@HomeActivity, "Add Fail", Toast.LENGTH_SHORT).show()
//            }
//        }
        val documentReference = firebaseFirestore.collection("Country").document("Capital")
        documentReference.update("VietNam", "DaNang").addOnCompleteListener(object : OnCompleteListener<Void> {
            override fun onComplete(p0: Task<Void>) {
                if (p0.isSuccessful) {
                    Toast.makeText(this@HomeActivity, "Add cities with unique key successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@HomeActivity, "Add Fail", Toast.LENGTH_SHORT).show()
                }
            }
        })
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
        val databasePreference = FirebaseDatabase.getInstance().reference.child("Users")
        databasePreference.child("FirebaseDemo").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                languages.clear()
                p0.children.forEach {
                    val branch = it.getValue(Branch::class.java)
                    languages.add("${branch?.name}: ${branch?.email}")
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(this@HomeActivity, "Fail to read value!!!", Toast.LENGTH_SHORT).show()
                Log.i("FirebaseDebug", p0.message)
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
//                databasePreference.child("DangVinh").child("Language")
//                    .child(edtLanguageShortName.text.toString())
//                    .setValue(edtLanguageName.text.toString())
//                edtLanguageShortName.setText("")
//                edtLanguageName.text = Editable.Factory.getInstance().newEditable("")
            }
        }
    }
}
