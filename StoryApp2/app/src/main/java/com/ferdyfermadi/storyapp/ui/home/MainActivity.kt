package com.ferdyfermadi.storyapp.ui.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ferdyfermadi.storyapp.R
import com.ferdyfermadi.storyapp.databinding.ActivityMainBinding
import com.ferdyfermadi.storyapp.databinding.ItemStoriesBinding
import com.ferdyfermadi.storyapp.preferences.UserPreference
import com.ferdyfermadi.storyapp.preferences.UserViewModel
import com.ferdyfermadi.storyapp.preferences.ViewModelFactory
import com.ferdyfermadi.storyapp.ui.addstory.UploadStoryActivity
import com.ferdyfermadi.storyapp.ui.camerax.CameraActivity
import com.ferdyfermadi.storyapp.ui.maps.MapsActivity
import com.ferdyfermadi.storyapp.ui.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userpreference")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemBinding: ItemStoriesBinding
    private lateinit var adapter: StoryAdapter
    private lateinit var userViewModel: UserViewModel
    private val storiesViewModel by viewModels<StoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        itemBinding = ItemStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupRecyclerView()
    }



    private fun setupRecyclerView() {
        adapter = StoryAdapter()
        adapter.notifyDataSetChanged()
        binding.apply {
            rvStories.layoutManager = LinearLayoutManager(this@MainActivity)
            rvStories.setHasFixedSize(true)
            rvStories.adapter = adapter.withLoadStateFooter(
                footer = LoadingStateAdapter {
                    adapter.retry()
                }
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.navigation_addstory -> {
                showLoading(true)
                Intent(this, UploadStoryActivity::class.java).also{
                    startActivity(it)
                }
            }
            R.id.navigation_maps -> {
                showLoading(true)
                Intent(this, MapsActivity::class.java).also{
                    startActivity(it)
                }
            }
            R.id.navigation_logout -> {
                showLoading(true)
                userViewModel.logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupViewModel() {
        userViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[UserViewModel::class.java]

        userViewModel.getUserSession().observe(this) {
            if (it.token.isNullOrBlank()) {
                val i = Intent(this, WelcomeActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(i)
                finish()
            } else {
                storiesViewModel.getAllStory(it.token).observe(this) { pagingData ->
                    adapter.submitData(lifecycle, pagingData)
                }
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
}