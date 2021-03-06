package com.kanchanproseth.goldonecomputershop.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.controller.SeeMoreActivity
import com.kanchanproseth.goldonecomputershop.model.MyData
import kotlinx.android.synthetic.main.category_cardview.view.*

/**
 * Created by kanchanproseth on 12/15/17.
 */

class HomeAdapter(internal var mContext: Context, data: MyData) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var response: MyData? = null
    var context: Context? = null

    class CategoryCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category_name: TextView
        var recyclerView: RecyclerView
        var cardView: CardView
        var seeMoreBtn: Button

        init {
            this.seeMoreBtn = itemView.see_more_button
            this.category_name = itemView.cate_name
            this.recyclerView = itemView.product_rc
            this.cardView = itemView.cate_cardview
        }
    }


    init {
        this.response = data
        this.context = mContext
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_cardview, parent, false)
        return CategoryCardViewHolder(view)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, listPosition: Int) {


        var result = response!!.categories
        (holder as CategoryCardViewHolder).category_name.text = result[listPosition].category_name
        holder.recyclerView.setHasFixedSize(true)
        holder.seeMoreBtn.setOnClickListener {

            val intent = Intent(context, SeeMoreActivity::class.java)
            intent.putExtra("category_listPosition", listPosition)
            context!!.startActivity(intent)

        }
        // use a linear layout manager
        var mLayoutManager = LinearLayoutManager(mContext)
        holder.recyclerView.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)

        var mAdapter = productAdapter(mContext, result[listPosition]!!, listPosition)
        holder.recyclerView.adapter = mAdapter


    }

    override fun getItemCount(): Int {
        return response!!.categories.count()
    }

}
