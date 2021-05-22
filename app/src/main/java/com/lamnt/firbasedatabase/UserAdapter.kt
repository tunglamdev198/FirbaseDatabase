package com.lamnt.firbasedatabase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter constructor(
    private val users: ArrayList<User>,
    private val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName: TextView = itemView.findViewById(R.id.txtName)
        var txtPhone: TextView = itemView.findViewById(R.id.txtPhone)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = users[position]
        holder.txtName.text = user.name
        holder.txtPhone.text = user.phone
        holder.itemView.rootView.setOnClickListener {
            onItemClickListener.onClick(user)
        }
        holder.itemView.rootView.setOnLongClickListener {
            onItemClickListener.onLongClick(user)
            false
        }
    }

    override fun getItemCount(): Int = users.size

    fun submitData(users: List<User>) {
        this.users.clear()
        this.users.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onClick(user: User)
        fun onLongClick(user : User)
    }
}