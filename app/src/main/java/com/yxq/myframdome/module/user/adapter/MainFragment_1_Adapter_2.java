package com.yxq.myframdome.module.user.adapter;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ItemViewDelegate;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.base.ViewHolder;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.bean.CommonBean;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.MenuVO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-13  9:30
 */
public class MainFragment_1_Adapter_2 implements ItemViewDelegate<CommonBean<MainFragment_1_Entity>> {
    private Context context;

    public MainFragment_1_Adapter_2(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_main_1_fragment_2;
    }

    @Override
    public boolean isForViewType(CommonBean<MainFragment_1_Entity> item, int position) {
        return item.itemType == MainFragment_1_Entity.TYPE_2;
    }

    @Override
    public void convert(ViewHolder holder, CommonBean<MainFragment_1_Entity> item, int position) {
        MenuVO menuVO = item.data.getMenuVO();
        holder.setText(R.id.title, menuVO.getName());
        RecyclerView recyclerView = holder.getView(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 3));
        recyclerView.setHasFixedSize(true);

        List<MenuVO> menuVOList = new ArrayList<>();
        for (MenuVO menuVO1 : menuVO.getMenuVOList()) {
            for (MenuVO menuVO2 : menuVO1.getMenuVOList()) {
                menuVOList.add(menuVO2);
            }
            if (menuVO1.getMenuVOList().size() == 0) {
                menuVOList.add(menuVO1);
            }
        }
        MainFragment_1_Adapter_2_1 mainFragment_1_adapter_2_1 = new MainFragment_1_Adapter_2_1(context, R.layout.item_main_1_fragment_2_1, menuVOList);
        recyclerView.setAdapter(mainFragment_1_adapter_2_1);
    }
}
