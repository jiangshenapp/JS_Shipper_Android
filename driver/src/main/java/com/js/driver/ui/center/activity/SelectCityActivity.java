package com.js.driver.ui.center.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.component.ComponentApp;
import com.js.driver.App;
import com.js.driver.R;
import com.js.driver.di.componet.DaggerActivityComponent;
import com.js.driver.di.module.ActivityModule;
import com.js.driver.model.bean.CityBean;
import com.js.driver.model.bean.SelectCity;
import com.js.driver.ui.center.adapter.CityAdapter;
import com.js.driver.ui.center.adapter.HeaderRecyclerAndFooterWrapperAdapter;
import com.js.driver.ui.center.adapter.ViewHolder;
import com.js.driver.ui.center.presenter.SelectCityPresenter;
import com.js.driver.ui.center.presenter.contract.SelectCityContract;
import com.mcxtzhang.indexlib.IndexBar.widget.IndexBar;
import com.mcxtzhang.indexlib.suspension.SuspensionDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by huyg on 2019-06-03.
 */
public class SelectCityActivity extends BaseActivity<SelectCityPresenter> implements SelectCityContract.View, BaseQuickAdapter.OnItemClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.indexBar)
    IndexBar mIndexBar;
    @BindView(R.id.tvSideBarHint)
    TextView mSideBarHint;
    @BindView(R.id.location)
    TextView mLocationCity;

    private CityAdapter mAdapter;
    private List<CityBean> mList;
    private HeaderRecyclerAndFooterWrapperAdapter mHeaderAdapter;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    protected void init() {
        initView();
        initData();
    }

    private void initData() {
        if (App.getInstance().mLocation != null) {
            mLocationCity.setText("当前定位城市："+App.getInstance().mLocation.getCity());
        }
        mPresenter.getCityList();
    }

    private void initView() {
        mAdapter = new CityAdapter(R.layout.item_city, mList);
        mHeaderAdapter = new HeaderRecyclerAndFooterWrapperAdapter(mAdapter) {
            @Override
            protected void onBindHeaderHolder(ViewHolder holder, int headerPos, int layoutId, Object o) {
                holder.setText(R.id.tvCity, (String) o);
            }
        };
//        mHeaderAdapter.setHeaderView(R.layout.item_city, "测试头部");
        mRecycler.setAdapter(mHeaderAdapter);
        mDecoration = new SuspensionDecoration(this, mList).setHeaderViewCount(mHeaderAdapter.getHeaderViewCount());
        mRecycler.addItemDecoration(mDecoration);

        mRecycler.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));
        mManager = new LinearLayoutManager(mContext);
        mRecycler.setLayoutManager(mManager);
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

        mIndexBar.setmPressedShowTextView(mSideBarHint)//设置HintTextView
                .setNeedRealIndex(true)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
    }

    @Override
    protected void initInject() {
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_city_select;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("城市选择");
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<CityBean> cityBeans = mAdapter.getData();
        CityBean cityBean = cityBeans.get(position);
        Intent intent = new Intent();
        intent.putExtra("city", cityBean.getCity());
        setResult(888, intent);
        finish();
    }

    @Override
    public void onCityList(List<SelectCity> selectCities) {
        mList = new ArrayList<>();
        for (SelectCity selectCity : selectCities) {
            CityBean cityBean = new CityBean();
            cityBean.setCity(selectCity.getAddress());//设置城市名称
            mList.add(cityBean);
        }
        mIndexBar.setmSourceDatas(mList)//设置数据
                .setHeaderViewCount(mHeaderAdapter.getHeaderViewCount())//设置HeaderView数量
                .invalidate();
        mAdapter.setNewData(mList);
        mHeaderAdapter.notifyDataSetChanged();
        mDecoration.setmDatas(mList);
    }
}
