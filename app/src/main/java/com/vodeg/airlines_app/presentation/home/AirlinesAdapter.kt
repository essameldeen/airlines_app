package com.vodeg.airlines_app.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.vodeg.airlines_app.R
import com.vodeg.airlines_app.data.model.Airline
import kotlinx.android.synthetic.main.airline_item_rv.view.*

class AirlinesAdapter : RecyclerView.Adapter<AirlinesAdapter.AirlineViewHolder>() {
    inner class AirlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


    private val differCallBack = object : DiffUtil.ItemCallback<Airline>() {
        override fun areItemsTheSame(oldItem: Airline, newItem: Airline): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Airline, newItem: Airline): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlineViewHolder {
        return AirlineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.airline_item_rv, parent, false),

            )
    }

    override fun onBindViewHolder(holder: AirlineViewHolder, position: Int) {
        val airline = differ.currentList[position]
        holder.itemView.apply {
            tv_airlineName.text = airline.name
            setOnClickListener {
                onItemClickListener?.let {
                    it(airline)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Airline) -> Unit)? = null

    fun setOnItemClickListener(listener: (Airline) -> Unit) {
        onItemClickListener = listener
    }
}