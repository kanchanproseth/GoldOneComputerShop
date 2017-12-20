package com.kanchanproseth.goldonecomputershop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kanchanproseth.goldonecomputershop.R
import com.kanchanproseth.goldonecomputershop.model.MyData
import kotlinx.android.synthetic.main.product_cardview.view.*

/**
 * Created by kanchanproseth on 12/19/17.
 */
class SeeMoreAdapter(context: Context, mData: List<MyData.Asset>) : BaseAdapter() {
    var data: List<MyData.Asset>? = null
    var context: Context? = null

    init {
        this.context = context
        this.data = mData
    }

    override fun getCount(): Int {
        return data!!.count()
    }

    override fun getItem(position: Int): Any {
        return data!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val asset = this.data!![position]


        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var seeMore = inflator.inflate(R.layout.product_cardview, null)
        seeMore.short_desc.text = asset.asset_model


        return seeMore
    }
}