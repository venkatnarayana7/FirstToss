package com.firsttoss.app

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
// The line below is only needed if your package name logic is tricky,
// but it is safe to keep.
import com.firsttoss.app.R

class LoginActivity : AppCompatActivity() {

    // Declare Views
    private lateinit var etEmail: EditText
    private lateinit var etPass: EditText
    private lateinit var btnLogin: AppCompatButton
    private lateinit var ivPassToggle: ImageView
    private lateinit var cbRemember: CheckBox
    private lateinit var btnGoogle: AppCompatButton
    private lateinit var btnApple: AppCompatButton

    // Variables for logic
    private var isPasswordVisible = false
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Initialize Views
        initViews()

        // 2. Check "Remember Me" status
        checkRememberMe()

        // 3. Setup Click Listeners
        setupListeners()
    }

    private fun initViews() {
        etEmail = findViewById(R.id.et_email)
        etPass = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
        ivPassToggle = findViewById(R.id.iv_password_toggle)
        cbRemember = findViewById(R.id.cb_remember)
        btnGoogle = findViewById(R.id.btn_google)


        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE)
    }

    private fun setupListeners() {
        // --- Login Button Logic ---
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString().trim()

            if (validateInput(email, password)) {
                // Checkbox Logic
                if (cbRemember.isChecked) {
                    saveRememberMe(true)
                } else {
                    saveRememberMe(false)
                }

                // Navigate
                navigateToHome()
            }
        }

        // --- Password Eye Toggle Logic ---
        ivPassToggle.setOnClickListener {
            togglePasswordVisibility()
        }

        // --- Social Buttons (Placeholders) ---
        btnGoogle.setOnClickListener {
            Toast.makeText(this, "Google Login Clicked", Toast.LENGTH_SHORT).show()
        }

        // btnApple.setOnClickListener { ... }
    }

    private fun validateInput(email: String, pass: String): Boolean {
        if (email.isEmpty()) {
            etEmail.error = "Email is required"
            etEmail.requestFocus()
            return false
        }
        if (pass.isEmpty()) {
            etPass.error = "Password is required"
            etPass.requestFocus()
            return false
        }

        // Hardcoded credentials for testing
        if (email == "admin" && pass == "123456") {
            return true
        } else {
            Toast.makeText(this, "Invalid credentials. Use admin/123456", Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Hide Password
            etPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            ivPassToggle.setImageResource(R.drawable.ic_eye)
            ivPassToggle.alpha = 0.5f
        } else {
            // Show Password
            etPass.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            ivPassToggle.alpha = 1.0f
        }
        // Move cursor to the end
        etPass.setSelection(etPass.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun checkRememberMe() {
        val isRemembered = sharedPreferences.getBoolean("REMEMBER_ME", false)
        if (isRemembered) {
            cbRemember.isChecked = true
            etEmail.setText("admin") // Pre-fill for convenience
            // Toast.makeText(this, "Welcome back!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveRememberMe(isChecked: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean("REMEMBER_ME", isChecked)
        editor.apply()
    }

    private fun navigateToHome() {
        Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}