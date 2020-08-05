package com.yxq.myframdome.module.user.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.CommonAdapter;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.MsgVO;

import java.util.List;

import jp.shts.android.library.TriangleLabelView;

public class MainFragment_2_Adapter extends CommonAdapter<MsgVO> {

    public MainFragment_2_Adapter(Context context, int layoutId, List<MsgVO> datas, int clickName) {
        super(context, layoutId, datas);
    }

    @Override
    protected void convert(ViewHolder viewHolder, MsgVO msgVO, int i) {
        TextView tvKeyTwoWeeksAgoInfo = viewHolder.getView(R.id.tvKeyTwoWeeksAgoInfo);//两周以前
        TextView tvKeyMsgType = viewHolder.getView(R.id.tvKeyMsgType);//消息类型
        TextView tvValueDate = viewHolder.getView(R.id.tvValueDate);//日期
        TextView tvValueType = viewHolder.getView(R.id.tvValueType);//消息内容
        ImageView imgSpot = viewHolder.getView(R.id.imgSpot);//新消息提示
        TriangleLabelView tvZhuangtai = viewHolder.getView(R.id.tv_zhuangtai);//状态


        tvKeyMsgType.setText(msgVO.getMessageContent());
        tvValueDate.setText(msgVO.getCreateTime());
        tvValueType.setText(msgVO.getMemo());
        switch (msgVO.getStatus()) {
            case 0:
                imgSpot.setVisibility(View.VISIBLE);
                tvZhuangtai.setVisibility(View.GONE);
                break;
            case 1:
                imgSpot.setVisibility(View.GONE);
                tvZhuangtai.setVisibility(View.VISIBLE);
                break;
            default:
                imgSpot.setVisibility(View.GONE);
                tvZhuangtai.setVisibility(View.GONE);
                break;
        }
    }
}
