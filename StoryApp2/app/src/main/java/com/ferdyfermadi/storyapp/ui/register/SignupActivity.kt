package com.ferdyfermadi.storyapp.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.ferdyfermadi.storyapp.R
import com.ferdyfermadi.storyapp.databinding.ActivityRegisterBinding
import com.ferdyfermadi.storyapp.ui.login.LoginActivity


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        playAnimation()
        registerAction()
        registerResponse()
    }


    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val nameEditTextLayout = ObjectAnimator.ofFloat(binding.nameEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)


        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                nameEditTextLayout,
                emailTextView,
                emailEditTextLayout,
                passwordTextView,
                passwordEditTextLayout,
                signup
            )
            startDelay = 500
        }.start()
    }

    private fun registerAction() {
        val name = binding.nameEditText
        val email = binding.emailEditText
        val password = binding.passwordEditText
        binding.signupButton.setOnClickListener {
            if (name.text.isNullOrBlank()) {
                name.error = getString(R.string.name_cannot_empty)
            }
            if (email.text.isNullOrBlank()) {
                email.error = getString(R.string.invalid_email)
            }
            if (password.text.isNullOrBlank()) {
                password.error = getString(R.string.must_6_character)
            }
            if (name.error.isNullOrBlank() &&
                email.error.isNullOrBlank() &&
                password.error.isNullOrBlank()
            ) {
                showLoading(true)
                val nameText = name.text.toString().trim()
                val emailText = email.text.toString()
                val passwordText = password.text.toString()
                viewModel.saveUser(nameText, emailText, passwordText)
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.failed_title))
                    setMessage(getString(R.string.invalid_input_data))
                    create()
                    show()
                }
            }
        }
    }

    private fun registerResponse() {
        viewModel.getRegisterResponse().observe(this) {
            if (it != null) {
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                showLoading(true)
                startActivity(i)
                finish()
            } else {
                binding.apply {
                    emailEditTextLayout.error = getString(R.string.invalid_email)
                    showLoading(false)
                }
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.failed_title))
                    setMessage(getString(R.string.invalid_input_data))
                    create()
                    show()
                }
            }
        }
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }
}