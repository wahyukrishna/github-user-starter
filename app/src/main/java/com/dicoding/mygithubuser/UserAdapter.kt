package com.dicoding.mygithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.mygithubuser.databinding.ItemRowUserBinding

class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val mData = ArrayList<UserItems>()
    fun setData(items: ArrayList<UserItems>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val mView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_user, parent, false)
        return UserViewHolder(mView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(mData[position])
        val user = mData[position]

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intentDetailActivity = Intent(context, DetailUserActivity::class.java)
            intentDetailActivity.putExtra(DetailUserActivity.EXTRA_USER, user)
            context.startActivity(intentDetailActivity)
        }
    }

    override fun getItemCount(): Int = mData.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemRowUserBinding.bind(itemView)
        fun bind(userItems: UserItems) {
            Glide.with(itemView.context)
                .load(userItems.avatar)
                .apply(RequestOptions().override(55, 55))
                .into(binding.imgItemAvatar)
            binding.tvItemName.text = userItems.username
        }
    }
}