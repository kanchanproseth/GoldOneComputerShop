package com.kanchanproseth.goldonecomputershop.Helper

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.adapter.HomeAdapter
import com.kanchanproseth.goldonecomputershop.model.MyData
import com.kanchanproseth.goldonecomputershop.service.AllProductByCategory
import com.kanchanproseth.goldonecomputershop.util.AppBuilder
import io.realm.Realm
import io.realm.RealmObject
import kotlinx.android.synthetic.main.fragment_home.view.*
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import android.webkit.WebSettings
import android.webkit.WebView
import kotlinx.android.synthetic.main.fragment_blogs.view.*


/**
 * Created by kanchanproseth on 12/8/17.
 */
class PageFragment : Fragment() {
    private var mPageNo: Int = 0
    private var mWebView: WebView? = null
//    var realm: Realm? = null
//    var results: MyData? = null
//    var app = AppBuilder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPageNo = arguments.getInt(ARG_PAGE)



    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view: View? = null

        if (mPageNo == 1){
            view = inflater!!.inflate(R.layout.fragment_home, container, false)
            view!!.home_rc!!.setHasFixedSize(true)
            // use a linear layout manager
            val mLayoutManager = LinearLayoutManager(view.context)
            view!!.home_rc!!.layoutManager = mLayoutManager

            val gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
                override fun shouldSkipField(f: FieldAttributes?): Boolean {
                    return  f!!.declaringClass == RealmObject::class.java
                }

                override fun shouldSkipClass(clazz: Class<*>?): Boolean {
                    return  false
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

                                var mAdapter = HomeAdapter(view!!.context, result)
                                view!!.home_rc.adapter = mAdapter
                                print(result)
                            },
                            { error ->
                                Log.e("Error", error.message)
                            }
                    )

        }else if (mPageNo == 2){
            view = inflater!!.inflate(R.layout.fragment_blogs, container, false)
            mWebView = view!!.webview
            val webSettings = mWebView!!.settings
            webSettings.javaScriptEnabled = true

            mWebView!!.loadUrl("http://beta.html5test.com/");


        }else if (mPageNo == 3){
            view = inflater!!.inflate(R.layout.fragment_details, container, false)
        }

        return view
    }




    companion object {
        val ARG_PAGE = "ARG_PAGE"
        var results: MyData? = null

        fun newInstance(pageNo: Int): PageFragment {

            val args = Bundle()
            args.putInt(ARG_PAGE, pageNo)
            val fragment = PageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
