package ru.nhmt.authtestapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.nhmt.authtestapp.databinding.ItemUsersListBinding
import ru.nhmt.authtestapp.module.RegData

class UserListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var data: ArrayList<RegData> = arrayListOf()
    private lateinit var context:Context
    inner class UsersViewHolder(val binding: ItemUsersListBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val binding = ItemUsersListBinding.inflate(LayoutInflater.from(context), parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = (holder as UsersViewHolder).binding
        item.firstName.text = data[position].firstName
        item.lastName.text = data[position].lastName
        item.emailTx.text = data[position].email
    }

    override fun getItemCount(): Int = data.size

    fun update(newData:ArrayList<RegData>){
        data = newData
        notifyDataSetChanged()
    }



}
