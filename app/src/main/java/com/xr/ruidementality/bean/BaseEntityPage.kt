package com.xr.ruidementality.bean

/**
 * Created by Sage on 2017/9/29.
 * Description:
 */
data class BaseEntityPage<T>(var page:String,var total_page:String,var list:ArrayList<T>)