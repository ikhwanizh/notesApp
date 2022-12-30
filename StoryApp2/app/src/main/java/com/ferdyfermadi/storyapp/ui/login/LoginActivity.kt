package com.ferdyfermadi.storyapp.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.ferdyfermadi.storyapp.R
import com.ferdyfermadi.storyapp.databinding.ActivityLoginBinding
import com.ferdyfermadi.storyapp.preferences.UserPreference
import com.ferdyfermadi.storyapp.preferences.UserViewModel
import com.ferdyfermadi.storyapp.preferences.ViewModelFactory
import com.ferdyfermadi.storyapp.ui.home.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userpreference")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSystemUI()
        playAnimation()
        loginAction()
        loginResponse()
        setupUserViewModel()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val emailEditTextLayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val passwordEditTextLayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(title, emailTextView, emailEditTextLayout, passwordTextView, passwordEditTextLayout, login)
            startDelay = 500
            start()
        }
    }

    private fun loginAction(){
        val email = binding.emailEditText
        val pass = binding.passwordEditText
        binding.loginButton.setOnClickListener {
            if (email.text.isNullOrBlank()) {
                email.error = getString(R.string.invalid_email)
            }
            if (pass.text.isNullOrBlank()) {
                pass.error = getString(R.string.must_6_character)
            }
            if (email.error.isNullOrBlank() &&
                pass.error.isNullOrBlank()
            ) {
                showLoading(true)
                val emailText = email.text.toString()
                val passText = pass.text.toString()
                viewModel.setLogin(emailText, passText)
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

    private fun loginResponse() {
        viewModel.getLoginResponse().observe(this) {
            if (it != null) {
                userViewModel.saveUserSession(it.name, it.userId, it.token)
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle(getString(R.string.failed_title))
                    setMessage(getString(R.string.wrong_input))
                    create()
                    show()
                }
                showLoading(false)
            }
        }
    }

    private fun setupUserViewModel() {
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[UserViewModel::class.java]

        userViewModel.getUserSession().observe(this) {
            if (!it.token.isNullOrBlank()) {
                val i = Intent(this, MainActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            }
        }
    }

    private fun showLoading(state: Boolean){
        if(state) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
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