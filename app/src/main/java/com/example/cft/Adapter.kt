package com.example.cft

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cft.databinding.PeopleBaseBinding

class Adapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<Adapter.ViewHolder>() {

    var persons = ArrayList<Person>()

    inner class ViewHolder(
        private val binding: PeopleBaseBinding,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        fun bind(person: Person) {
            Glide.with(binding.root)
                .load(person.picture.thumbnail)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.ivPersonPhoto)
            binding.tvFioInfo.text = person.fio
            binding.tvAddressInfo.text = person.address
            binding.tvPhoneInfo.text = person.phone
            binding.root.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            onItemClickListener.onItemClick(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            PeopleBaseBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onItemClickListener
        )

    override fun getItemCount(): Int = persons.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(persons[position])
    }
}