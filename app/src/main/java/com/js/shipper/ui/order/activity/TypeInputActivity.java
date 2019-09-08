package com.js.shipper.ui.order.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.base.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.ui.order.adapter.TypeInputAdapter;
import com.js.shipper.ui.order.presenter.TypeInputPresenter;
import com.js.shipper.ui.order.presenter.contract.TypeInputContract;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-30.
 */
public class TypeInputActivity extends BaseActivity<TypeInputPresenter> implements TypeInputContract.View, BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.input_type)
    EditText mInputType;
    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.confirm)
    TextView mConfirm;


    @OnClick(R.id.confirm)
    public void onClick(){
        if (TextUtils.isEmpty(mInputType.getText())){
            toast("请输入类型");
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("content",mInputType.getText().toString());
        intent.putExtra("type",type);
        setResult(111,intent);
        finish();
    }

    private int type;
    private TypeInputAdapter mAdapter;
    private List<DictBean> mDictBeans;

    public static void action(Activity context, int type) {
        Intent intent = new Intent(context, TypeInputActivity.class);
        intent.putExtra("type", type);
        context.startActivityForResult(intent, Const.CODE_REQ);
    }

    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initData() {
        switch (type) {
            case Const.DICT_PICK_TYPE:
                mTitle.setText("包装类型");
                mPresenter.getDictByType(Const.DICT_PICK_TYPE_NAME);
                break;
            case Const.DICT_GOODS_NAME:
                mTitle.setText("货品名称");
                mPresenter.getDictByType(Const.DICT_GOODS_NAME_);
                break;
        }
    }

    private void initIntent() {
        type = getIntent().getIntExtra("type", 0);
    }

    private void initView() {
        initRecycler();
    }

    private void initRecycler() {
        mAdapter = new TypeInputAdapter(R.layout.item_window_dict, mDictBeans);
        mRecycler.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecycler.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);

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
        return R.layout.activity_type_input;
    }

    @Override
    public void setActionBar() {

    }

    @Override
    public void onDictByType(String type, List<DictBean> dictBeans) {
        mAdapter.setNewData(dictBeans);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        List<DictBean> dictBeans = adapter.getData();
        DictBean dictBean = dictBeans.get(position);
        if (dictBean != null) {
            mInputType.setText(dictBean.getLabel());
            mInputType.setSelection(mInputType.getText().length());
        }
    }
}
