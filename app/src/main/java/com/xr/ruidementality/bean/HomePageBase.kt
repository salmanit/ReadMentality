package com.xr.ruidementality.bean

/**
 * Created by Sage on 2017/10/11.
 * Description: 首页返回数据
 * showMore 为1加载更多，当为0的时候不再加载。
 */
data class HomePageBase(var showTime:Long,var list:List<HomePageData>,var showMore:Int,var adpic: DaySignPic)