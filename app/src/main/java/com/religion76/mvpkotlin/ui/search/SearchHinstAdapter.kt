package com.religion76.mvpkotlin.ui.search

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.religion76.mvpkotlin.R
import com.religion76.mvpkotlin.utils.inflateChild
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_hint.view.*

/**
 * Created by SunChao on 2019-08-16.
 */
class SearchHinstAdapter : RecyclerView.Adapter<SearchHinstAdapter.HintItemViewHolder>() {

    var onHintClick: ((String) -> Unit)? = null

    var hints: List<String>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HintItemViewHolder {
        return HintItemViewHolder(parent.inflateChild(R.layout.item_hint))
    }

    override fun getItemCount(): Int = hints?.size ?: 0

    override fun onBindViewHolder(holder: HintItemViewHolder, position: Int) {
        hints?.get(position)?.let { hint ->
            holder.itemView.tvHint.text = hint
            holder.itemView.setOnClickListener {
                onHintClick?.invoke(hint)
            }
        }
    }

    class HintItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {
        override val containerView: View?
            get() = itemView

    }
}