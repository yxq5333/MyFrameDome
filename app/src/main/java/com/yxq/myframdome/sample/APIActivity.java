package com.yxq.myframdome.sample;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.crazyhuskar.myandroidsdk.adapter.recyclerview.MultiItemTypeAdapter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpActivity;
import com.crazyhuskar.myandroidsdk.view.MultipleStatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxq.myframdome.R;
import com.yxq.myframdome.api_entity.UserVO;
import com.yxq.myframdome.sample.adapter.APIAdapter;
import com.yxq.myframdome.sample.mvp.APIContract;
import com.yxq.myframdome.sample.mvp.APIPresenterImpl;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * @author CrazyHuskar
 * caeat at 2018-12-04  15:09
 */
public class APIActivity extends MyBaseMvpActivity<APIContract.APIPresenter> implements APIContract.APIView {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.multiplestatusview)
    MultipleStatusView multiplestatusview;
    @BindView(R.id.floating_search_view)
    FloatingSearchView floatingSearchView;
    private ArrayList<String> arrayList = new ArrayList();
    private APIAdapter adapter;

    @Override
    protected APIContract.APIPresenter createPresenter() {
        return new APIPresenterImpl();
    }

    @Override
    public int setLayoutID() {
        return R.layout.activity_sample_api_list;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {
        arrayList.add("登录");
        adapter = new APIAdapter(this, R.layout.item_sample_api_list, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                smartrefreshlayout.finishLoadMore();
            }
        });
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartrefreshlayout.finishRefresh();
            }
        });
//        FileDisplayUtil.fileDisplay(this,"L2FG6V2p.doc");
    }

    /**
     * 登录成功
     *
     * @param userVO
     */
    @Override
    public void loginSuccess(UserVO userVO) {

    }

    @Override
    public void loginFail(UserVO userVO) {

    }
}
