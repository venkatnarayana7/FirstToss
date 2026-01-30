package com.firsttoss.app.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.firsttoss.app.LoginActivity
import com.firsttoss.app.R
import com.firsttoss.app.utils.SignupData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupStep4ProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_step4_profile)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val etName = findViewById<EditText>(R.id.et_name)
        val btnFinish = findViewById<AppCompatButton>(R.id.btn_finish)

        btnFinish.setOnClickListener {
            val name = etName.text.toString().trim()
            if (name.isNotEmpty()) {
                val user = auth.currentUser
                if (user != null) {
                    val dbRef = database.getReference("Users").child(user.uid)
                    val userData = mapOf(
                        "name" to name,
                        "email" to SignupData.email,
                        "phone" to SignupData.phone
                    )
                    dbRef.setValue(userData).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, LoginActivity::class.java))
                            finishAffinity()
                        } else {
                            Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
