package com.kanchanproseth.goldonecomputershop.adapter

import android.content.Context
import android.content.Intent
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
import com.kanchanproseth.goldonecomputershop.controller.BlogDetailsActivity
import com.kanchanproseth.goldonecomputershop.model.BlogModel
import kotlinx.android.synthetic.main.blog_card_view.view.*

/**
 * Created by kanchanproseth on 12/19/17.
 */


class BlogAdapter(internal var mContext: Context, data: BlogModel) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var response: BlogModel? = null

    class CategoryCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView
        var blog_title: TextView
        var posted_date: TextView
        var desc: TextView
        var cardView: CardView

        init {

            this.imageView = itemView.blog_image
            this.blog_title = itemView.blog_title
            this.posted_date = itemView.posted_date
            this.desc = itemView.short_desc
            this.cardView = itemView.blog_card
        }
    }


    init {
        this.response = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_card_view, parent, false)
        return CategoryCardViewHolder(view)

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, listPosition: Int) {

        var result = response!!.Blogs
        var imageuri = "http://10.0.2.2:80/GoldOneProductAPI/" + result[listPosition].image_uri

        (holder as CategoryCardViewHolder).blog_title.text = result[listPosition].blog_title

        Glide.with(mContext)
                .load(imageuri)
                .asBitmap()
                .into(object : BitmapImageViewTarget(holder.imageView) {
                    override fun setResource(resource: Bitmap) {
                        //Play with bitmap
                        super.setResource(resource)
                        val newBitmap = ImageHelper.getRoundedCornerBitmap(resource, 10)
                        holder.imageView.setImageBitmap(newBitmap)
                    }
                })
//        holder.imageView.setImageResource()

        holder.posted_date.text = result[listPosition].posted_date + " | " + result[listPosition].Author
        holder.desc.text = result[listPosition].short_desc
        holder.itemView.setOnClickListener {
            val intent = Intent(mContext, BlogDetailsActivity::class.java)
            intent.putExtra("imageuri", imageuri)
            intent.putExtra("date_author", result[listPosition].posted_date + " | " + result[listPosition].Author)
            intent.putExtra("title", result[listPosition].blog_title)
            intent.putExtra("long_desc", result[listPosition].long_desc)
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return response!!.Blogs.count()
    }

}
