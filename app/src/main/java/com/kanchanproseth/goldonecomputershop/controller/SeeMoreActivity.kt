package com.kanchanproseth.goldonecomputershop.controller

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.adapter.SeeMoreAdapter
import com.kanchanproseth.goldonecomputershop.service.AllProductByCategory
import io.realm.RealmObject
import kotlinx.android.synthetic.main.activity_see_more.*
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class SeeMoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_more)
        val data = intent.extras.getInt("category_listPosition")

        print(data)

        back_from_see_more.setOnClickListener {
            onBackPressed()
        }

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
                            category_title.text = result.categories[data].category_name
                            seeMoreGv!!.adapter = SeeMoreAdapter(this, result.categories[data].asset)

                        },
                        { error ->
                            Log.e("Error", error.message)
                        }
                )


    }
}
