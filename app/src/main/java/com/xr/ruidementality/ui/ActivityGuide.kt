package com.xr.ruidementality.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.xr.ruidementality.R
import kotlinx.android.synthetic.main.activity_guide.*

class ActivityGuide : AppCompatActivity() {

    var pics = arrayListOf(R.mipmap.rd_guide1, R.mipmap.rd_guide2, R.mipmap.rd_guide3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)

        vp_guide.apply {
            adapter = object : PagerAdapter() {
                override fun isViewFromObject(view: View, o: Any): Boolean {
                    return view == o
                }

                override fun getCount(): Int {
                    return pics.size
                }

                override fun instantiateItem(container: ViewGroup, position: Int): Any {
                    var iv = ImageView(container.context)
                    iv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                    iv.setBackgroundResource(pics[position])
                    container.addView(iv)
                    return iv
                }

                override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
                    container.removeView(o as View)
                }
            }
            addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

                }

                override fun onPageSelected(position: Int) {
                    if (position == pics.size - 1) {
                        tv_login.visibility = View.VISIBLE
                        tv_guest.visibility = View.VISIBLE
                        vp_indicator.visibility=View.GONE
                    } else {
                        tv_login.visibility = View.INVISIBLE
                        tv_guest.visibility = View.INVISIBLE
                        vp_indicator.visibility=View.VISIBLE
                    }
                }
            })
        }
        vp_indicator.setViewPager(vp_guide)

        tv_login.setOnClickListener {
            startActivity(Intent(this,ActivityHome::class.java))
            startActivity(Intent(this,ActivityLogin::class.java).putExtra("tag",1))
            finish()
        }

        tv_guest.setOnClickListener {
            startActivity(Intent(this,ActivityHome::class.java))
            finish()
        }
    }
}
