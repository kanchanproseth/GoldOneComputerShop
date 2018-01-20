package com.kanchanproseth.goldonecomputershop.controller

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.kanchanproseth.goldonecomputershop.Helper.CustomViewPager
import com.kanchanproseth.goldonecomputershop.Helper.PageFragment
import com.kanchanproseth.goldonecomputershop.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.custom_tab.view.*


class HomeActivity : AppCompatActivity() {


    private var mTabLayout: TabLayout? = null

    //array tab icon image
    private val mTabsIcons = intArrayOf(R.drawable.ic_home_selector, R.drawable.ic_blogger_selector, R.drawable.ic_aboutus_selector)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        supportActionBar!!.hide()
        setContentView(R.layout.activity_home)



        // Setup the viewPager
        val viewPager = view_pager as CustomViewPager
        val pagerAdapter = MyPagerAdapter(supportFragmentManager)

        if (viewPager != null) {
            viewPager.isPagingEnabled = false
            viewPager.adapter = pagerAdapter//setAdapter(pagerAdapter)
        }

        mTabLayout = tab_layout as TabLayout
        if (mTabLayout != null) {
            mTabLayout!!.setupWithViewPager(viewPager)

            for (i in 0 until mTabLayout!!.tabCount) {
                val tab = mTabLayout!!.getTabAt(i)
                if (tab != null)
                    tab.customView = pagerAdapter.getTabView(i)
            }

            mTabLayout!!.getTabAt(0)!!.customView!!.isSelected = true



        }


    }


    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        //page number
        val PAGE_COUNT = 3

        //tab title array
        private val mTabsTitle = arrayOf("Home", "Blogs", "About Us")

        fun getTabView(position: Int): View {
            // set up tab icon image and title tab
            val view = LayoutInflater.from(this@HomeActivity).inflate(R.layout.custom_tab, null)
            val title = view.title as TextView
            title.text = mTabsTitle[position]
            val icon = view.icon as ImageView
            icon.setImageResource(mTabsIcons[position])
            return view
        }

        override fun getItem(pos: Int): Fragment? {
            when (pos) {

            //calling to fragment to each page we select
                0 -> return PageFragment.newInstance(1)
                1 -> return PageFragment.newInstance(2)
                2 -> return PageFragment.newInstance(3)
            }
            return null
        }

        override fun getCount(): Int {
            // page number
            return PAGE_COUNT
        }

        override fun getPageTitle(position: Int): CharSequence {
            //page title
            return mTabsTitle[position]
        }
    }

}

