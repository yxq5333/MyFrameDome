package com.yxq.myframdome.module.user.fragment;

import android.view.View;
import android.widget.TextView;

import com.crazyhuskar.myandroidsdk.adapter.recyclerview.MultiItemTypeAdapter;
import com.crazyhuskar.myandroidsdk.base.mvp.MyBaseMvpFragment;
import com.crazyhuskar.myandroidsdk.util.MyUtilThreadPool;
import com.crazyhuskar.myandroidsdk.util.MyUtilToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yxq.myframdome.AppCache;
import com.yxq.myframdome.R;
import com.yxq.myframdome.RecyclerViewNoBugLinearLayoutManager;
import com.yxq.myframdome.api_entity.MsgVO;
import com.yxq.myframdome.module.user.adapter.MainFragment_2_Adapter;
import com.yxq.myframdome.module.user.fragment.mvp.MainFragment_2_Contract;
import com.yxq.myframdome.module.user.fragment.mvp.MainFragment_2_PresenterImpl;
import com.yxq.myframdome.util.CircleRelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;


/**
 * @author tangjin
 * @Title: {标题}
 * @Description:{空白页}
 * @date 2017/11/9
 */
public class MainFragment_2 extends MyBaseMvpFragment<MainFragment_2_Contract.MainFragment_2_Presenter> implements MainFragment_2_Contract.MainFragment_2_View {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.smartrefreshlayout)
    SmartRefreshLayout smartrefreshlayout;
    @BindView(R.id.crlCallMsg)
    CircleRelativeLayout crlCallMsg;
    @BindView(R.id.itemNum1)
    TextView itemNum1;
    @BindView(R.id.crlWorkMsg)
    CircleRelativeLayout crlWorkMsg;
    @BindView(R.id.itemNum2)
    TextView itemNum2;
//    @BindView(R.id.ivCallMsg)
//    ImageView ivCallMsg;
//    @BindView(R.id.ivWorkMsg)
//    ImageView ivWorkMsg;

    private ArrayList<MsgVO> arrayList = new ArrayList();
    private MainFragment_2_Adapter adapter;
    private int clickName = 0;
    private String title = "";
    private int pageIndex = 1;
    private int pageSize = 10;
    private boolean canLoadMore = true;
    private boolean isLoadMore = false;
    private Map<String, Object> param = new HashMap<>();
    private int postion = 0;

    @Override
    protected MainFragment_2_Contract.MainFragment_2_Presenter createPresenter() {
        return new MainFragment_2_PresenterImpl();
    }

    @Override
    protected int setLayoutID() {
        return R.layout.fragment_main_2;
    }

    @Override
    protected Fragment registerFragment() {
        return this;
    }

    @Override
    protected void init() {
//        title = "消息中心";
//        setCommonTitle(null, title, null, null, null, null, null);

        adapter = new MainFragment_2_Adapter(mContext, R.layout.item_hometab_two_list, arrayList, clickName);
        recyclerView.setLayoutManager(new RecyclerViewNoBugLinearLayoutManager(mContext));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                if (arrayList.size() > 0) {
                    switch (arrayList.get(i).getStatus()) {
                        case 0:
                            if (mContext != null) {
                                postion = i;
                                presenter.updateState(AppCache.getInstance(mContext).getUserInfo().getToken(), arrayList.get(i).getId());
                            }
                            break;
                        case 1:

                            break;
                        default:

                            break;
                    }
                }
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder viewHolder, int i) {
                return false;
            }
        });
        smartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                adapter.notifyDataSetChanged();
                smartrefreshlayout.finishLoadMore();
                //上拉加载
                MyUtilThreadPool.getInstance().getScheduledExecutorService().shutdown();
                doLoadMore();
            }
        });
        smartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//
                //下拉刷新
                MyUtilThreadPool.getInstance().getScheduledExecutorService().shutdown();
                doRefresh();
                smartrefreshlayout.finishRefresh();
            }
        });
        param.clear();
        param.put("resourceType", "");
        param.put("page", pageIndex);
        param.put("limit", pageSize);
        if (mContext != null) {
            presenter.loadData(AppCache.getInstance(mContext).getUserInfo().getToken(), "", param);

            MyUtilThreadPool.getInstance().scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    doRefresh();
                }
            }, 0, 10000);
        }
    }

    private void doLoadMore() {
        if (canLoadMore) {
            pageIndex++;
            isLoadMore = true;
            param.clear();
            param.put("resourceType", "");
            param.put("page", pageIndex);
            param.put("limit", pageSize);
            presenter.loadData(AppCache.getInstance(mContext).getUserInfo().getToken(), "", param);
        } else {
            recyclerView.postDelayed(new Runnable() {

                @Override
                public void run() {
                    MyUtilToast.showShort(mContext, "没有更多数据了");
                    smartrefreshlayout.finishLoadMore();
                }
            }, 1000);

        }
    }

    private void doRefresh() {
        pageIndex = 1;
        canLoadMore = true;
        isLoadMore = false;
        //加载数据并包含搜索
        param.clear();
        param.put("resourceType", "");
        param.put("page", pageIndex);
        param.put("limit", pageSize);
        presenter.loadData(AppCache.getInstance(mContext).getUserInfo().getToken(), "", param);
    }

    /**
     * 加载数据成功
     *
     * @param listVO
     */
    @Override
    public void getSuccess(List<MsgVO> listVO) {

        if (listVO != null) {
            if (listVO.size() < pageSize) {
                canLoadMore = false;
            }

            if (!isLoadMore) {
                arrayList.clear();
            }

            arrayList.addAll(listVO);
        } else {
            MyUtilToast.showShort(mContext, "没有数据");
        }

        adapter.notifyDataSetChanged();
        isLoadMore = false;

        if (MyUtilThreadPool.getInstance().getScheduledExecutorService().isShutdown()) {
            MyUtilThreadPool.getInstance();//重新开启服务
        }
    }

    @Override
    public void getFail(String s) {
        MyUtilToast.showShort(mContext, s);
    }

    @Override
    public void Success(String s) {
//        doRefresh();
        if (arrayList.size() > 0) {
            arrayList.get(postion).setStatus(1);
        }

        adapter.notifyDataSetChanged();
//        MyUtilToast.showShort(mContext, s);
    }

    @Override
    public void Fail(String s) {
        MyUtilToast.showShort(mContext, s);
    }

//    public void RefreshData() {
//        doRefresh();
//    }

    @OnClick({R.id.crlCallMsg, R.id.crlWorkMsg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.crlCallMsg:
//                MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, CallMsgListActivity.class);
                break;
            case R.id.crlWorkMsg:
//                MyUtilActivity.getInstance().startActivity((AppCompatActivity) mContext, WorkMsgListActivity.class);
                break;
        }
    }
}
