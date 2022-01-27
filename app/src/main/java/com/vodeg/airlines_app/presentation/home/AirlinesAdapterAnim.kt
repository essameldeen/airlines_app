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
import java.util.ArrayList

class AirlinesAdapterAnim : RecyclerView.Adapter<AirlinesAdapterAnim.AirlineViewHolder>() {
    inner class AirlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    var airlines: ArrayList<Airline> = ArrayList<Airline>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlineViewHolder {
        return AirlineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.airline_item_rv, parent, false),

            )
    }

    fun setData(items: ArrayList<Airline>) {

        airlines = ArrayList<Airline>()
        for (item in items) {
            airlines.add(item)
        }
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: AirlineViewHolder, position: Int) {
        val airline = airlines[position]
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
        return airlines.size
    }

    fun getItem(position: Int): Airline? {
        return airlines[position]
    }

    fun addItem(newModelData: Airline?, position: Int) {
        newModelData?.let { airlines.add(position, it) }
        notifyItemInserted(position)
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val modifiedItem: Airline = airlines.removeAt(fromPosition)
        airlines.add(toPosition, modifiedItem)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun removeItem(position: Int) {
        airlines.removeAt(position)
        notifyItemRemoved(position)
    }

    fun animateTo(models: List<Airline?>?) {
        applyAndAnimateRemovals(models as List<Airline>)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedStrings(models)
    }

    private fun applyAndAnimateRemovals(newModels: List<Airline>) {
        for (i in airlines.indices.reversed()) {
            val model: Airline = airlines.get(i)
            if (!newModels.contains(model)) {
                removeItem(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(models: List<Airline>) {
        var i = 0
        val count: Int = models.size
        while (i < count) {
            val model: Airline = models[i]
            if (!models.contains(model)) {
                addItem(model, i)
            }
            i++
        }
    }
    fun filter(allItems: List<Airline?>, query: String): ArrayList<Airline>? {
        var query = query
        query = query.toLowerCase()
        val filteredModelList: ArrayList<Airline> = ArrayList<Airline>()
        for (item in allItems) {
            val data: String = item.title.toString() + " " + item.category
            if (data.toLowerCase().contains(query)) {
                filteredModelList.add(item)
            }
        }
        return filteredModelList
    }

    private var onItemClickListener: ((Airline) -> Unit)? = null

    fun setOnItemClickListener(listener: (Airline) -> Unit) {
        onItemClickListener = listener
    }
}