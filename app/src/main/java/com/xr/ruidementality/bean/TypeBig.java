package com.xr.ruidementality.bean;

import com.xr.ruidementality.R;

import java.util.ArrayList;

/**
 * Created by Sage on 2017/9/5.
 * Description:
 */

public class TypeBig {
    public String name;
    public int type;
    public ArrayList<TypeMedia> types = new ArrayList<>();

    public TypeBig(String name, int type) {
        this.name = name;
        this.type = type;
        types.add(new TypeMedia("0", "全部", "drawable://" + R.mipmap.rd_catalog_all_small_gray, "drawable://" + R.mipmap.rd_catalog_all_small_red,""));
    }
}
