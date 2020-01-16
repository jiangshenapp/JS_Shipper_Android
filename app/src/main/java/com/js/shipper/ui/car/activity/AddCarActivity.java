package com.js.shipper.ui.car.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.base.frame.view.BaseActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.model.event.AddCarEvent;
import com.js.shipper.model.event.CommentEvent;
import com.js.shipper.model.request.OrderComment;
import com.js.shipper.ui.car.adapter.AddCarAdapter;
import com.js.shipper.ui.car.presenter.AddCarPresenter;
import com.js.shipper.ui.car.presenter.contract.AddCarContract;
import com.js.shipper.widget.adapter.Divider;
import com.js.shipper.widget.dialog.AddCarFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/12
 * desc   :
 * version: 3.0.0
 */
public class AddCarActivity extends BaseActivity<AddCarPresenter> implements AddCarContract.View, BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.refresh)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.et_search_no)
    EditText mSearch;

    private AddCarAdapter mAdapter;
    private List<CarBean> mCars;

    public static void action(Context context) {
        Intent intent = new Intent(context, AddCarActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void init() {
        initView();
    }

    private void initView() {
        initRecycler();
        initRefresh();
        mSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void initRefresh() {
        mRefresh.setEnableLoadMore(false);
        mRefresh.setEnableRefresh(false);
    }

    private void initRecycler() {
        mAdapter = new AddCarAdapter(R.layout.item_car, mCars);
        mRecycler.addItemDecoration(new Divider(getResources().getDrawable(R.drawable.divider_center_cars), LinearLayoutManager.VERTICAL));
        mRecycler.setAdapter(mAdapter);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
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
        return R.layout.activity_add_car;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("添加运力");
    }

    @Override
    public void onQueryCarList(List<CarBean> carBeans) {
        mCars = carBeans;
        mAdapter.setNewData(mCars);
    }

    @Override
    public void onAddCar(boolean isOk) {
        toast("添加成功");
        finish();
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        List<CarBean> carBeans = adapter.getData();
        CarBean carBean = carBeans.get(position);
        switch (view.getId()) {
            case R.id.item_submit:
                AddCarFragment carFragment = new AddCarFragment(carBean);
                carFragment.show(getSupportFragmentManager(),"Add");
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @OnClick(R.id.tv_search)
    public void onClick() {
        mPresenter.queryCarList(mSearch.getText().toString());
    }

    @Subscribe
    public void onEvent(AddCarEvent carEvent) {
        mPresenter.addCar(carEvent.carId, carEvent.remark, carEvent.type);
    }
}
