package com.kanchanproseth.goldonecomputershop.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.adapter.ProductByModelAdapter
import com.kanchanproseth.goldonecomputershop.service.AllProductByCategory
import io.realm.RealmObject
import kotlinx.android.synthetic.main.activity_product_by_model.*
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ProductByModelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_by_model)

        back_btn.setOnClickListener {
            onBackPressed()
        }

        val position = intent.extras.getInt("listPosition")
        var category_position = intent.extras.getInt("category_list_position")



        this.product_by_model_rc!!.setHasFixedSize(true)
        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(this)
        this.product_by_model_rc!!.layoutManager = mLayoutManager

        val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes?): Boolean {
                return f!!.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                return false
            }
        }).create()

        val retrofit = Retrofit.Builder().
                addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson!!))
                .baseUrl("http://10.0.2.2:5000").build()

        val allProductbyCategoryService: AllProductByCategory = retrofit!!.create(AllProductByCategory::class.java)

        allProductbyCategoryService.getAllProductByCategory().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->

                            var mAdapter = ProductByModelAdapter(this, result.categories[category_position].asset[position])
                            this.product_by_model_rc.adapter = mAdapter
                            print(result)
                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )

    }
}
