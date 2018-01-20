package com.kanchanproseth.goldonecomputershop.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.kanchanproseth.goldonecomputershop.R
import kotlinx.android.synthetic.main.activity_blog_details.*


class BlogDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_details)
        back_btn.setOnClickListener {
            onBackPressed()
        }


        val imageuri = intent.getStringExtra("imageuri")
        val dateAuthor = intent.getStringExtra("date_author")
        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("long_desc")
        date_author.text = dateAuthor
        Glide.with(this)
                .load(imageuri)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .into(blog_imageView_details)
        desc_details.text = desc
        title_details.text = title


    }
}
