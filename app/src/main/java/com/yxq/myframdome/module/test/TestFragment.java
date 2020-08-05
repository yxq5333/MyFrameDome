package com.yxq.myframdome.module.test;

import android.view.View;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.MultiItemTypeAdapter;
import com.crazyhuskar.myandroidsdk.base.MyBaseFragment;
import com.yxq.myframdome.R;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by Hollow Goods on 2019-04-10.
 */
public class TestFragment extends MyBaseFragment {

    @BindView(R.id.rv_result)
    RecyclerView result;

    private TestAdapter adapter;
    private ArrayList<Test> data = new ArrayList<>();

    @Override
    protected int setLayoutID() {
        return R.layout.fragment_test;
    }

    @Override
    protected Fragment registerFragment() {
        return this;
    }

    @Override
    protected void init() {

        result.setHasFixedSize(true);
        result.setItemAnimator(new DefaultItemAnimator());
        result.setLayoutManager(new LinearLayoutManager(getActivity()));

        for (int i = 0; i < 100; i++) {
            data.add(new Test());
        }

        adapter = new TestAdapter(getActivity(), R.layout.item_test, data);
        result.setAdapter(adapter);

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                adapter.setCheckedPosition(position);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return true;
            }
        });
    }

}
