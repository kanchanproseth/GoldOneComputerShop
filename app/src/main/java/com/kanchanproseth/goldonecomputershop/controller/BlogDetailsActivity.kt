package com.kanchanproseth.goldonecomputershop.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kanchanproseth.goldonecomputershop.R
import kotlinx.android.synthetic.main.activity_blog_details.*

class BlogDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_details)
        back_btn.setOnClickListener {
            onBackPressed()
        }
    }
}
