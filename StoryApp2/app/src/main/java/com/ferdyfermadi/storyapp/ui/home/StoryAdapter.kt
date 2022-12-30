package com.ferdyfermadi.storyapp.ui.home

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.ferdyfermadi.storyapp.data.response.ListStoryItem
import com.ferdyfermadi.storyapp.databinding.ItemStoriesBinding
import com.ferdyfermadi.storyapp.ui.detail.DetailActivity

class StoryAdapter : PagingDataAdapter<ListStoryItem, StoryAdapter.StoriesViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoriesViewHolder {
        val binding = ItemStoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StoriesViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)
        }
    }

    class StoriesViewHolder(private val binding: ItemStoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ListStoryItem) {
            binding.apply {
                Glide.with(itemView)
                    .load(data.photoUrl)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(storiesImage)
                tvUsername.text = data.name

                itemView.setOnClickListener {
                    val i = Intent(itemView.context, DetailActivity::class.java)
                    i.putExtra(DetailActivity.EXTRA_NAME, data.name)
                    i.putExtra(DetailActivity.EXTRA_PHOTO, data.photoUrl)
                    i.putExtra(DetailActivity.EXTRA_DESCRIPTION, data.description)

                    val optionsCompat: ActivityOptionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(
                            itemView.context as Activity,
                            Pair(tvUsername, "name"),
                            Pair(storiesImage, "image")
                        )

                    itemView.context.startActivity(i, optionsCompat.toBundle())
                }
            }
        }
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListStoryItem>() {
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem) =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem) =
                oldItem.id == newItem.id
        }
    }
}