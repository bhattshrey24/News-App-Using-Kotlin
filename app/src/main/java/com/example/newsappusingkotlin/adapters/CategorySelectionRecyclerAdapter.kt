package com.example.newsappusingkotlin.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.R

class CategorySelectionRecyclerAdapter(var parentContext: Context) :
    RecyclerView.Adapter<CategorySelectionRecyclerAdapter.ViewHolder>() {
    private val listOfCategories = listOf<String>(
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology"
    )
    private val selectedListOfCategories = mutableListOf<String>()

    fun getSelectedCategories(): MutableList<String> {
        if (selectedListOfCategories.size == 3) {
            return selectedListOfCategories
        } else {
            Toast.makeText(
                parentContext,
                "Select ${3 - selectedListOfCategories.size} more categories", Toast.LENGTH_SHORT
            ).show()
            return mutableListOf() // ie. empty list
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategorySelectionRecyclerAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: CategorySelectionRecyclerAdapter.ViewHolder,
        position: Int
    ) {
        holder.categoryTv.text = listOfCategories[position]
        if (selectedListOfCategories.contains(listOfCategories[position])) {
            holder.category_card.setCardBackgroundColor(Color.BLACK)
        } else {
            holder.category_card.setCardBackgroundColor(Color.BLUE)
        }
    }


    override fun getItemCount(): Int {
        return listOfCategories.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTv: TextView = itemView.findViewById(R.id.Tv_category_item)
        var category_card: CardView = itemView.findViewById(R.id.categorySelectionCardView)

        init {
            itemView.setOnClickListener {
                val pos = adapterPosition

                if (selectedListOfCategories.contains(listOfCategories[pos])) {
                    selectedListOfCategories.remove(listOfCategories[pos])
                    notifyItemChanged(pos)
                } else {
                    if (selectedListOfCategories.size == 3) {
                        Toast.makeText(
                            parentContext,
                            "Already Selected 3 Items",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        selectedListOfCategories.add(listOfCategories[pos])
                        category_card.setCardBackgroundColor(Color.BLACK)
                        notifyItemChanged(pos)
                    }
                }


            }
        }
    }
}