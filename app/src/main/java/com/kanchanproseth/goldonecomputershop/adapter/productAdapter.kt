package com.kanchanproseth.goldonecomputershop.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.controller.ProductByModelActivity
import com.kanchanproseth.goldonecomputershop.model.MyData
import kotlinx.android.synthetic.main.product_cardview.view.*

/**
 * Created by kanchanproseth on 12/15/17.
 */

class productAdapter(internal var mContext: Context, data: MyData.Categories) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var response: MyData.Categories? = null

    class ProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_name: TextView
        var product_image: ImageView
        var cardView: CardView

        init {

            this.product_name = itemView.short_desc
            this.product_image = itemView.image_product
            this.cardView = itemView.product_cardView
        }
    }


    init {
        this.response = data
        print(response!!.asset.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_cardview, parent, false)
        return ProductCardViewHolder(view)

    }



    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, listPosition: Int) {

        if (listPosition <= 10) {
            val list_asset = response!!.asset[listPosition]
            (holder as ProductCardViewHolder).product_name.text = list_asset!!.asset_model
            holder.itemView.setOnClickListener {
                val intent = Intent(mContext, ProductByModelActivity::class.java)
                intent.putExtra("listPosition", listPosition)
                mContext.startActivity(intent)
            }
            print(list_asset.asset_model)
        }
    }

    override fun getItemCount(): Int {
        return response!!.asset.size
    }

}