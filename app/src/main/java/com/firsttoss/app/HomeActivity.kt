package com.firsttoss.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        val tvWelcome = findViewById<TextView>(R.id.tv_welcome)
        val btnLogout = findViewById<Button>(R.id.btn_logout)

        if (user != null) {
            val dbRef = FirebaseDatabase.getInstance().getReference("Users").child(user.uid)
            dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val name = snapshot.child("name").getValue(String::class.java)
                    tvWelcome.text = "Welcome, $name!"
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
        } else {
            // Handle user not logged in
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
