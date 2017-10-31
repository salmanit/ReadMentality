package com.xr.ruidementality.fragment.home

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.xr.ruidementality.R
import com.xr.ruidementality.adapter.AdapterRv
import com.xr.ruidementality.adapter.ItemDetectorGrid
import com.xr.ruidementality.adapter.ViewHolderRv
import com.xr.ruidementality.app.APIManager
import com.xr.ruidementality.app.MyConstant
import com.xr.ruidementality.bean.TypeAllMedia
import com.xr.ruidementality.bean.TypeBig
import com.xr.ruidementality.bean.TypeMedia
import com.xr.ruidementality.custom.CustomCallBack
import com.xr.ruidementality.fragment.FragmentBase
import com.xr.ruidementality.ui.ActivityHome
import com.xr.ruidementality.util.UtilNormal
import kotlinx.android.synthetic.main.fragment_type_choose.*
import org.simple.eventbus.EventBus

/**
 * Created by Sage on 2017/10/12.
 * Description: 音频视频直播分类选择界面
 */
class FragmentTypeChoose : FragmentBase {
    override fun getLayout(): Int {
        return R.layout.fragment_type_choose
    }
    constructor()
    constructor(type: Int):super(){
        setFromType(type)
    }
    var type = 1;//从哪里点过来的，1，2，3分别对应音频，视频，直播
    var checkedID = "0"//选中的id
    fun setFromType(type: Int): FragmentTypeChoose {
        when (type) {
            1 -> checkedID = MyConstant.currentAudioTypeId
            2 -> checkedID = MyConstant.currentVideoTypeId
            3 -> checkedID = MyConstant.currentLiveTypeId
        }
        this.type = type
        return this
    }

    var itemSpace = 30
    var defaultLists = ArrayList<TypeBig>()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addPaddingMatchStateBar(layout_toolbar)
        iv_back.setOnClickListener {
            activity.supportFragmentManager.popBackStack()
        }
        iv_right.setOnClickListener {

        }
        itemSpace = UtilNormal.dp2px(activity, 10f).toInt()

        rv_type.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = object : AdapterRv<TypeBig>() {
                override fun layoutId(viewType: Int): Int {
                    return R.layout.item_home_type_c_big
                }

                override fun handleData(holder: ViewHolderRv, position: Int, data: TypeBig) {
                    holder.setText(R.id.tv_type, data.name)
                    holder.getView<View>(R.id.layout_top).setOnClickListener {
                        handleItemClick(data, 0)
                    }
                    holder.getView<RecyclerView>(R.id.rv_types).apply {
                        layoutManager = GridLayoutManager(activity, 2)
                        addItemDecoration(ItemDetectorGrid(itemSpace, true))
                        adapter = object : AdapterRv<TypeMedia>(data.types) {
                            override fun layoutId(viewType: Int): Int {
                                return R.layout.item_home_type_small
                            }

                            override fun handleData(holder: ViewHolderRv, position: Int, t: TypeMedia) {
                                holder.setImageUrl(R.id.iv_logo, t.smallThumb)
                                holder.setText(R.id.tv_type_name, t.catname)
                                holder.getView<ViewGroup>(R.id.layout_item).setBackgroundResource(
                                        if ((data.type == type) and (TextUtils.equals(checkedID, t.catid)))
                                            R.drawable.shape_bg_white_corner6_stroke_theme
                                        else R.drawable.shape_bg_white_corner6
                                )

                                holder.itemView.setOnClickListener {
                                    handleItemClick(data, position)
                                }
                            }

                        }
                    }
                }
            }

            getRvAdapter().addFooter(
                    LayoutInflater.from(activity).inflate(R.layout.item_placeholder_bottom, rv_type, false))
        }

        defaultLists.clear()
        defaultLists.add(TypeBig("音频", 1))
        defaultLists.add(TypeBig("视频", 2))
        defaultLists.add(TypeBig("直播", 3))
        postHttp()
    }

    fun handleItemClick(data: TypeBig, position: Int) {
        var typeID=data.types[position].catid
        if(TextUtils.equals(typeID ,checkedID)){

        }
        when(data.type){
            1->{
               if(!TextUtils.equals(MyConstant.currentAudioTypeId,typeID)){
                   MyConstant.currentAudioTypeId=typeID
                   EventBus.getDefault().post(typeID, "refreshAudio")
               }
                (activity as ActivityHome).goWhere(1)
            }
            2->{
                if(!TextUtils.equals(MyConstant.currentVideoTypeId,typeID)){
                    MyConstant.currentVideoTypeId=typeID
                    EventBus.getDefault().post(typeID, "refreshVideo")
                }
                (activity as ActivityHome).goWhere(2)
            }
            3->{
                if(!TextUtils.equals(MyConstant.currentLiveTypeId,typeID)){
                    MyConstant.currentLiveTypeId=typeID
//                    EventBus.getDefault().post(typeID, "refreshLive")
                }
                (activity as ActivityHome).goWhere(3)
            }
        }
        activity.supportFragmentManager.popBackStack()
    }

    fun getRvAdapter(): AdapterRv<TypeBig> {
        return rv_type.adapter as AdapterRv<TypeBig>
    }

    fun postHttp() {
        APIManager.getCategoryList2(object : CustomCallBack<TypeAllMedia> {
            override fun onSuccess(t: TypeAllMedia) {
                defaultLists[0].types.addAll(t.audio)
                defaultLists[1].types.addAll(t.video)
                getRvAdapter().setData(defaultLists)
            }

            override fun onError(type: Int, msg: String) {

            }

            override fun onComplete() {

            }
        })
    }
}