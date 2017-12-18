package com.kanchanproseth.goldonecomputershop.Helper

import android.annotation.SuppressLint
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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.view.*
import kotlinx.android.synthetic.main.fragment_blogs.view.*
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.android.synthetic.main.fragment_details.view.*
import kotlin.math.floor


/**
 * Created by kanchanproseth on 12/8/17.
 */
class PageFragment : Fragment() {

    private var mPageNo: Int = 0
    private var mWebView: WebView? = null
    var mMapView:MapView? = null
    var map:GoogleMap? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPageNo = arguments.getInt(ARG_PAGE)






    }



    @SuppressLint("MissingPermission")
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

            mWebView!!.loadUrl("http://www.goldonecomputer.com/index.php?route=blog/blog")


        }else if (mPageNo == 3){
            view = inflater!!.inflate(R.layout.fragment_details, container, false)

//            var mapFragment : SupportMapFragment? = null
            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync { googleMap ->

                val gold_one_kampuchea_krom = LatLng(11.5676194, 104.9041716)
                val gold_one_electronic = LatLng(11.5530783, 104.9130798)
                googleMap!!.addMarker(MarkerOptions().position(gold_one_kampuchea_krom).title("Gold One kampuchea krom"));
                googleMap!!.addMarker(MarkerOptions().position(gold_one_electronic).title("Gold One Electronic"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gold_one_kampuchea_krom, 12F))

            }


        }

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


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
