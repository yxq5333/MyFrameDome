package com.yxq.myframdome.util;


import com.yxq.myframdome.api_entity.address.AreaBean;
import com.yxq.myframdome.api_entity.address.CityBean;
import com.yxq.myframdome.api_entity.address.ProvinceBean;
import com.yxq.myframdome.api_entity.address.StreetBean;

public interface OnAddressSelectedListener {
    void onAddressSelected(ProvinceBean province, CityBean city, AreaBean county, StreetBean street);
}
