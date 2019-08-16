package com.religion76.mvpkotlin.ui.post

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.data.model.RedditPostResult
import com.religion76.mvpkotlin.utils.StringUtils
import com.religion76.mvpkotlin.utils.inflateChild
import com.religion76.mvpkotlin.utils.loadRedditPostImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_post.*

/**
 * Created by SunChao on 2019-08-13.
 */
class RedditPostAdapter : PagedListAdapter<RedditPostResult, RedditPostAdapter.PostItemViewHolder>(ITEM_DIFF) {

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<RedditPostResult>() {
            override fun areItemsTheSame(oldItem: RedditPostResult, newItem: RedditPostResult): Boolean {
                return oldItem.data.id == newItem.data.id
            }

            override fun areContentsTheSame(oldItem: RedditPostResult, newItem: RedditPostResult): Boolean {
                return oldItem.data.title == newItem.data.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostItemViewHolder {
        return PostItemViewHolder(parent.inflateChild(R.layout.item_post))
    }

    override fun onBindViewHolder(holder: PostItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class PostItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View?
            get() = itemView

        fun bind(post: RedditPostResult) {
            tvSubreddit.text = post.data.subredditPrefixed
            tvPostInfo.text = String.format(StringUtils.getString(R.string.post_info_format), post.data.author)
            tvContent.text = post.data.title
            post.data.preview?.let { sdvContent.loadRedditPostImage(it) }
            itemView.setOnClickListener {
                post.data.url?.let { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    it.context.startActivity(intent)
                }
            }
        }
    }
}