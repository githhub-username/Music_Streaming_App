package com.example.musicstreamingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.example.musicstreamingapp.MainActivity
import com.example.musicstreamingapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {

    lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.redirectToLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.signUpButton.setOnClickListener {

            signUp()
        }
    }

    private fun signUp() {

        val email = binding.emailInputText.text.toString()
        val password = binding.passwordInputText.text.toString()
        val confirmPassword = binding.confirmPasswordInputText.text.toString()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailInputText.error = "Email ID is invalid"
            return
        }

        // Check if the password is at least 6 characters long
        if (password.length < 6) {
            binding.passwordInputText.error = "Password must be at least 6 characters";
            return
        }

        // Check if the password contains at least one alphabetic character
        if (!password.any { it.isLetter() }) {
            binding.passwordInputText.error = "Password must contain at least one alphabetic character"
            return
        }

// Check if the password contains at least one digit
        if (!password.any { it.isDigit() }) {
            binding.passwordInputText.error = "Password must contain at least one digit"
            return
        }

//        // Check if the password contains at least one uppercase letter
//        if (!password.any { it.isUpperCase() }) {
//            binding.passwordInputText.setError("Password must contain at least one uppercase letter")
//        }
//
//// Check if the password contains at least one lowercase letter
//        if (!password.any { it.isLowerCase() }) {
//            binding.passwordInputText.setError("Password must contain at least one lowercase letter")
//        }

//// Check if the password contains at least one special character
//        val specialChars = "!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?"
//        if (!password.any { it in specialChars }) {
//            binding.passwordInputText.setError("Password must contain at least one special character")
//        }

        if(password != confirmPassword) {
            binding.confirmPasswordInputText.error = "Password doesn't matches"
            return
        }

        signUpWithFirebase(email, password)

    }

    private fun setProgressBar(progressStatus: Boolean) {

        if (progressStatus) {
            binding.progressBar.visibility = View.VISIBLE
            binding.signUpButton.isClickable = false
        }
        else {
            binding.progressBar.visibility = View.GONE
            binding.signUpButton.isClickable = true
        }
    }

    private fun signUpWithFirebase(email: String, password: String) {

        setProgressBar(true)

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                            Toast.makeText(applicationContext, "Account Successfully created",Toast.LENGTH_LONG).show()
                            setProgressBar(false)

                            startActivity(Intent(this, MainActivity::class.java))
                        }

            .addOnFailureListener {
                Toast.makeText(applicationContext, it.localizedMessage?: "Some Error Occurred", Toast.LENGTH_LONG).show()
                setProgressBar(false)
            }
    }
}