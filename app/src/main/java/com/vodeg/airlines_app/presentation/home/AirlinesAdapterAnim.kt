package com.vodeg.airlines_app.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vodeg.airlines_app.R
import com.vodeg.airlines_app.data.model.Airline
import kotlinx.android.synthetic.main.airline_item_rv.view.*

class AirlinesAdapterAnim : RecyclerView.Adapter<AirlinesAdapterAnim.AirlineViewHolder>() {
    inner class AirlineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private var airlines: MutableList<Airline> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirlineViewHolder {
        return AirlineViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.airline_item_rv, parent, false),
        )
    }

    fun setData(items: MutableList<Airline>) {

        airlines = items
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

    fun animateTo(models: MutableList<Airline>) {
        applyAndAnimateRemovals(models)
        applyAndAnimateAdditions(models)
        applyAndAnimateMovedStrings(models)
    }

    private fun applyAndAnimateRemovals(newModels: MutableList<Airline>) {
        for (i in airlines.indices.reversed()) {
            val model: Airline = airlines[i]
            if (!newModels!!.contains(model)) {
                removeItem(i)
            }
        }
    }

    private fun applyAndAnimateAdditions(models: MutableList<Airline>) {
        var i = 0
        val count: Int = models?.size ?: 0
        while (i < count) {
            val model: Airline = models?.get(i)!!
            if (!models.contains(model)) {
                addItem(model, i)
            }
            i++
        }
    }

    fun filter(allItems: MutableList<Airline>, query: String): MutableList<Airline>? {
        var query = query
        query = query.toLowerCase()
         var filteredModelList: MutableList<Airline> = mutableListOf()
        for (item in allItems) {
            val name: String? = item?.name
            if (name?.toLowerCase()?.contains(query) == true) {
                filteredModelList.add(item)
            }
        }
        return filteredModelList
    }


    private fun applyAndAnimateMovedStrings(airlineModels: MutableList<Airline>) {
        for (toPosition in (airlineModels?.size?.minus(1) ?: 0) downTo 0) {
            val model: Airline? = airlineModels?.get(toPosition)
            val fromPosition: Int = airlines.indexOf(model)
            if (fromPosition >= 0 && fromPosition != toPosition) {
                moveItem(fromPosition, toPosition)
            }
        }
    }

    private var onItemClickListener: ((Airline) -> Unit)? = null

    fun setOnItemClickListener(listener: (Airline) -> Unit) {
        onItemClickListener = listener
    }
}