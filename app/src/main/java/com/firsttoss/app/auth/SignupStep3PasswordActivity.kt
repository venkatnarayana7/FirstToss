package com.firsttoss.app.auth

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.firsttoss.app.R
import com.firsttoss.app.utils.SignupData
import com.google.firebase.auth.FirebaseAuth

class SignupStep3PasswordActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_step3_password)

        auth = FirebaseAuth.getInstance()

        val etPassword = findViewById<EditText>(R.id.et_password)
        val etConfirmPassword = findViewById<EditText>(R.id.et_confirm_password)

        findViewById<AppCompatButton>(R.id.btn_continue).setOnClickListener {
            val password = etPassword.text.toString()
            val confirmPassword = etConfirmPassword.text.toString()

            if (password.isNotEmpty() && password == confirmPassword) {
                auth.createUserWithEmailAndPassword(SignupData.email!!, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            SignupData.password = password
                            startActivity(Intent(this, SignupStep4ProfileActivity::class.java))
                        } else {
                            Toast.makeText(baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
