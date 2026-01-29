package com.firsttoss.app

import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var iconPasswordToggle: ImageView
    private lateinit var formContainer: LinearLayout
    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize Views
        inputEmail = findViewById(R.id.input_email)
        inputPassword = findViewById(R.id.input_password)
        btnLogin = findViewById(R.id.button_login)
        iconPasswordToggle = findViewById(R.id.icon_password_toggle)
        formContainer = findViewById(R.id.form_container)

        // Start Onboarding Animation
        val slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up)
        formContainer.startAnimation(slideUp)

        setupListeners()
    }

    private fun setupListeners() {
        // Password Toggle
        iconPasswordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                inputPassword.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                iconPasswordToggle.alpha = 1.0f
            } else {
                inputPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                iconPasswordToggle.alpha = 0.5f
            }
            // Move cursor to end
            inputPassword.setSelection(inputPassword.text.length)
        }

        // Login Button Click with Animation
        btnLogin.setOnTouchListener { view, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    val pressAnim = AnimationUtils.loadAnimation(this, R.anim.button_press)
                    view.startAnimation(pressAnim)
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    val releaseAnim = AnimationUtils.loadAnimation(this, R.anim.button_release)
                    view.startAnimation(releaseAnim)
                }
            }
            false // return false to allow onClick to proceed if set
        }

        btnLogin.setOnClickListener {
            validateAndLogin()
        }
    }

    private fun validateAndLogin() {
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        var isValid = true

        if (email.isEmpty()) {
            inputEmail.error = "Email is required"
            isValid = false
        }

        if (password.isEmpty()) {
            inputPassword.error = "Password is required"
            isValid = false
        }

        if (isValid) {
            // Proceed with login logic (API call, etc.)
            // For now, just a placeholder action
        }
    }
}
