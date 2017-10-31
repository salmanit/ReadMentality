package com.xr.ruidementality.bean

/**
 * Created by Sage on 2017/10/17.
 * Description:
 */
data class SearchBaseS<T>(var page: String, var total_page: String, var search_result: ArrayList<T>)