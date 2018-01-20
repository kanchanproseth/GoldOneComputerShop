package com.kanchanproseth.goldonecomputershop.adapter

import android.content.Context
import android.graphics.Bitmap
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.kanchanproseth.goldonecomputershop.Helper.ImageHelper
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.model.MyData
import kotlinx.android.synthetic.main.list_product_cardview.view.*


/**
 * Created by kanchanproseth on 12/27/17.
 */

class ProductByModelAdapter(internal var mContext: Context, data: MyData.Asset) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var response: MyData.Asset? = null

    class ProductCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product_name: TextView
        var product_image: ImageView
        var price_text: TextView
        var cardView: CardView

        init {
            this.price_text = itemView.price_text
            this.product_name = itemView.product_name
            this.product_image = itemView.product_image
            this.cardView = itemView.list_product_card_view
        }
    }


    init {
        this.response = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_product_cardview, parent, false)
        return ProductCardViewHolder(view)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, listPosition: Int) {

        if (listPosition <= 10) {
            val list_product = response!!.list_product[listPosition]
            (holder as ProductCardViewHolder).product_name.text = list_product!!.product_name + " " + list_product.short_desc
            holder.price_text.text = list_product.price
            var imageuri = "http://10.0.2.2:80/GoldOneProductAPI/" + list_product.image_uri

            Glide.with(mContext)
                    .load(imageuri)
                    .asBitmap()
                    .into(object : BitmapImageViewTarget(holder.product_image) {
                        override fun setResource(resource: Bitmap) {
                            //Play with bitmap
                            super.setResource(resource)
                            val newBitmap = ImageHelper.getRoundedCornerBitmap(resource, 5)
                            holder.product_image.setImageBitmap(newBitmap)
                        }
                    })

        }
    }

    override fun getItemCount(): Int {
        return response!!.list_product.size
    }

}