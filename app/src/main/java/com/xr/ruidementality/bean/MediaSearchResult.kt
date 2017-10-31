package com.xr.ruidementality.bean

/**
 * Created by Sage on 2017/10/17.
 * Description:data里返回的搜索结果。音频视频放到一起了
 */
data class MediaSearchResult(var album_result: SearchBaseS<SearchAlbumS>?
                             , var audio_result: SearchBaseS<SearchAudioS>?
                             , var video_result: SearchBaseS<SearchVideoS>?)