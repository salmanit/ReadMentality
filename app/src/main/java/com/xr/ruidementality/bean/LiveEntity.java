package com.xr.ruidementality.bean;

/**
 * Created by Sage on 2017/8/14.
 * Description:直播列表实体类
 */

public class LiveEntity {
    public LiveEntity(int tv_id) {
        this.tv_id = tv_id;
    }

    public LiveEntity() {
    }

    /**
     * tv_id : 4
     * tv_title : 测试直播2
     * tv_cover_url : http://readymade1.srapi.cn/Uploads/Tv/598be753c2c34.jpg
     * tv_point : 50瑞点
     * tv_status : 1
     * tv_status_name : 预告
     * tv_start_time : 8/10 12:55-21:55
     * anchor_name : 罗丽
     * tv_video_hour_long : 0
     */

    private int tv_id;
    private String tv_title;
    private String tv_cover_url;
    private String tv_point;
    private String tv_point_money;//不带瑞点两个字的
    private int tv_status;
    private String tv_status_name;
    private String tv_start_time;
    private String anchor_name;
    private long tv_video_hour_long;
    public boolean showTitle = false;
    public boolean hideLine = false;
    /**
     * tv_start_unix_time : 1502340900
     * tv_play_url : rtmp://live.readymade.cn/readymade/liveSteam4?auth_key=1503636953-0-0-ea4a2060e3
     * tv_file_overdue_time : 0
     * user_tv_remind_status : 0
     * user_tv_buy_status : 0
     * chart_webview_url : http://readymade1.srapi.cn/Api/Waptv/tv_detail?source=chart&user_id=1&tv_id=4
     */

    private String tv_start_unix_time;
    private String tv_play_url;
    private int tv_file_overdue_time;
    private int user_tv_remind_status;
    private int user_tv_buy_status;
    private String chart_webview_url;
//    private Anchor.AnchorInfoBean anchor_info;//主播信息
//    private Anchor.AlbumListBean album_info;//主播专辑信息
//
//    public Anchor.AnchorInfoBean getAnchor_info() {
//        return anchor_info;
//    }
//
//    public void setAnchor_info(Anchor.AnchorInfoBean anchor_info) {
//        this.anchor_info = anchor_info;
//    }
//
//    public Anchor.AlbumListBean getAlbum_info() {
//        return album_info;
//    }
//
//    public void setAlbum_info(Anchor.AlbumListBean album_info) {
//        this.album_info = album_info;
//    }

    public String getTv_point_money() {
        return tv_point_money;
    }

    public void setTv_point_money(String tv_point_money) {
        this.tv_point_money = tv_point_money;
    }

    public int getTv_id() {
        return tv_id;
    }

    public void setTv_id(int tv_id) {
        this.tv_id = tv_id;
    }

    public String getTv_title() {
        return tv_title;
    }

    public void setTv_title(String tv_title) {
        this.tv_title = tv_title;
    }

    public String getTv_cover_url() {
        return tv_cover_url;
    }

    public void setTv_cover_url(String tv_cover_url) {
        this.tv_cover_url = tv_cover_url;
    }

    public String getTv_point() {
        return tv_point;
    }

    public void setTv_point(String tv_point) {
        this.tv_point = tv_point;
    }

    public int getTv_status() {
        return tv_status;
    }

    public void setTv_status(int tv_status) {
        this.tv_status = tv_status;
    }

    public String getTv_status_name() {
        return tv_status_name;
    }

    public void setTv_status_name(String tv_status_name) {
        this.tv_status_name = tv_status_name;
    }

    public String getTv_start_time() {
        return tv_start_time;
    }

    public void setTv_start_time(String tv_start_time) {
        this.tv_start_time = tv_start_time;
    }

    public String getAnchor_name() {
        return anchor_name;
    }

    public void setAnchor_name(String anchor_name) {
        this.anchor_name = anchor_name;
    }

    public long getTv_video_hour_long() {
        return tv_video_hour_long;
    }

    public void setTv_video_hour_long(long tv_video_hour_long) {
        this.tv_video_hour_long = tv_video_hour_long;
    }

    public String getTv_start_unix_time() {
        return tv_start_unix_time;
    }

    public void setTv_start_unix_time(String tv_start_unix_time) {
        this.tv_start_unix_time = tv_start_unix_time;
    }

    public String getTv_play_url() {
        return tv_play_url;
    }

    public void setTv_play_url(String tv_play_url) {
        this.tv_play_url = tv_play_url;
    }

    public int getTv_file_overdue_time() {
        return tv_file_overdue_time;
    }

    public void setTv_file_overdue_time(int tv_file_overdue_time) {
        this.tv_file_overdue_time = tv_file_overdue_time;
    }

    public int getUser_tv_remind_status() {
        return user_tv_remind_status;
    }

    public void setUser_tv_remind_status(int user_tv_remind_status) {
        this.user_tv_remind_status = user_tv_remind_status;
    }

    public int getUser_tv_buy_status() {
        return user_tv_buy_status;
    }

    public void setUser_tv_buy_status(int user_tv_buy_status) {
        this.user_tv_buy_status = user_tv_buy_status;
    }

    public String getChart_webview_url() {
        return chart_webview_url;
    }

    public void setChart_webview_url(String chart_webview_url) {
        this.chart_webview_url = chart_webview_url;
    }

    @Override
    public String toString() {
        return "LiveEntity{" +
                "tv_id=" + tv_id +
                ", tv_title='" + tv_title + '\'' +
                ", tv_cover_url='" + tv_cover_url + '\'' +
                ", tv_point='" + tv_point + '\'' +
                ", tv_status=" + tv_status +
                ", tv_status_name='" + tv_status_name + '\'' +
                ", tv_start_time='" + tv_start_time + '\'' +
                ", anchor_name='" + anchor_name + '\'' +
                ", tv_video_hour_long=" + tv_video_hour_long +
                ", tv_start_unix_time='" + tv_start_unix_time + '\'' +
                ", tv_play_url='" + tv_play_url + '\'' +
                ", tv_file_overdue_time=" + tv_file_overdue_time +
                ", user_tv_remind_status=" + user_tv_remind_status +
                ", user_tv_buy_status=" + user_tv_buy_status +
                ", chart_webview_url='" + chart_webview_url + '\'' +
                '}';
    }
}
