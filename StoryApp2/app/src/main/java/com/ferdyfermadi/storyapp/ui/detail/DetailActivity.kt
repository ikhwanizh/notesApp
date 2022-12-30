package com.ferdyfermadi.storyapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferdyfermadi.storyapp.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_PHOTO = "extra_photo"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setupView()
    }


    private fun setupView() {
        val name = intent.getStringExtra(EXTRA_NAME)
        val photo = intent.getStringExtra(EXTRA_PHOTO)
        val description = intent.getStringExtra(EXTRA_DESCRIPTION)

        binding.apply {
            Glide.with(this@DetailActivity)
                .load(photo)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(storiesImage)

            tvUsername.text = name.toString()
            tvDescription.text = description.toString()
        }
    }
}