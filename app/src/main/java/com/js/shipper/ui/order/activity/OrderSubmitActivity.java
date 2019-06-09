package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.js.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.manager.CommonGlideImageLoader;
import com.js.shipper.model.request.AddStepTwo;
import com.js.shipper.presenter.FilePresenter;
import com.js.shipper.presenter.contract.FileContract;
import com.js.shipper.ui.order.presenter.OrderSubmitPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderSubmitContract;
import com.js.shipper.util.TimeUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Date;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/5/6.
 */
public class OrderSubmitActivity extends BaseActivity<OrderSubmitPresenter> implements OrderSubmitContract.View, FileContract.View, InvokeListener, TakePhoto.TakeResultListener {


    @BindView(R.id.good_weight)
    EditText mGoodWeight;
    @BindView(R.id.good_volume)
    EditText mGoodVolume;
    @BindView(R.id.good_type)
    TextView mGoodType;
    @BindView(R.id.ship_time)
    TextView mShipTime;
    @BindView(R.id.car_type)
    TextView mCarType;
    @BindView(R.id.remark)
    EditText mRemark;
    @BindView(R.id.fee)
    EditText mFee;
    @BindView(R.id.fee_img)
    ImageView mFeeImg;
    @BindView(R.id.power_img)
    ImageView mPowerImg;
    @BindView(R.id.pay_way)
    RadioGroup mPayWay;
    @BindView(R.id.pay_type)
    RadioGroup mPayType;
    @BindView(R.id.img1)
    ImageView mImg1;
    @BindView(R.id.img2)
    ImageView mImg2;

    private long orderId;
    private String img1Url;
    private String img2Url;
    private String[] cartype = {"零单", "整车"};
    @Inject
    FilePresenter mFilePresenter;
    private int choseCode;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private int payType = 1;
    private int payWay = 1;
    private int feeWay = 1;


    public static void action(Context context, long orderId) {
        Intent intent = new Intent(context, OrderSubmitActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
    }

    private void initView() {
        mFilePresenter.attachView(this);
        mPayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pay_type_pay:
                        payType = 1;
                        break;
                    case R.id.pay_type_cash:
                        payType = 2;
                        break;
                }
            }
        });
        mPayWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pay_way_online:
                        payWay = 1;
                        break;
                    case R.id.pay_way_offline:
                        payWay = 2;
                        break;
                }
            }
        });
    }

    private void initIntent() {
        orderId = getIntent().getLongExtra("orderId", 0);
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
        return R.layout.activity_order_submit;
    }

    @Override
    public void setActionBar() {
        mTitle.setText("确认订单");
    }


    @OnClick({R.id.ship_time_layout, R.id.car_type_layout, R.id.image_1, R.id.image_2, R.id.fee_mine_layout, R.id.power_layout, R.id.specified_release, R.id.release})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ship_time_layout:
                showDateTime();
                break;
            case R.id.car_type_layout:
                showUserCarType();
                break;
            case R.id.image_1:
                getPhoto(1);
                break;
            case R.id.image_2:
                getPhoto(2);
                break;
            case R.id.fee_mine_layout:
                mPowerImg.setImageResource(R.mipmap.ic_checkbox_default);
                mFeeImg.setImageResource(R.mipmap.ic_checkbox_selected);
                mFee.setEnabled(true);
                feeWay = 1;
                break;
            case R.id.power_layout:
                mFeeImg.setImageResource(R.mipmap.ic_checkbox_default);
                mPowerImg.setImageResource(R.mipmap.ic_checkbox_selected);
                mFee.setEnabled(false);
                feeWay = 2;
                break;
            case R.id.specified_release:

                break;
            case R.id.release:
                confirm();
                break;
        }
    }

    private void showUserCarType() {
        //条件选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mCarType.setText(cartype[options1]);
            }
        }).build();
        pvOptions.setPicker(Arrays.asList(cartype));
        pvOptions.show();
    }

    private void showDateTime() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mShipTime.setText(TimeUtils.formatYYMMDDHHMMSS(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
                .build();
        pvTime.show();
    }


    public void confirm() {
        String weight = mGoodWeight.getText().toString().trim();
        String volume = mGoodVolume.getText().toString().trim();
        String goodType = mGoodType.getText().toString().trim();
        String remark = mRemark.getText().toString().trim();
        String type = mGoodType.getText().toString().trim();
        String time = mShipTime.getText().toString().trim();
        String carType = mCarType.getText().toString().trim();

        if (TextUtils.isEmpty(weight)) {
            toast("请输入货物重量");
            return;
        }

        if (TextUtils.isEmpty(volume)) {
            toast("请输入货物体积");
            return;
        }

        if (TextUtils.isEmpty(goodType)) {
            toast("请输入货物类型");
            return;
        }

        if (TextUtils.isEmpty(time)) {
            toast("请选择时间");
            return;
        }

        if (TextUtils.isEmpty(img1Url) || TextUtils.isEmpty(img2Url)) {
            toast("请上传照片");
        }

        if (TextUtils.isEmpty(carType)) {
            toast("请选择用车类型");
            return;
        }
        if (feeWay == 1 && TextUtils.isEmpty(mFee.getText().toString().trim())) {
            toast("请输入价格");
            return;
        }
        AddStepTwo addStepTwo = new AddStepTwo();
        addStepTwo.setId(orderId);
        addStepTwo.setGoodsWeight(Integer.parseInt(weight));
        addStepTwo.setGoodsVolume(Integer.parseInt(volume));
        addStepTwo.setGoodsType(type);
        addStepTwo.setUseCarType(carType);
        addStepTwo.setLoadingTime(time);
        addStepTwo.setImage1(img1Url);
        addStepTwo.setImage2(img2Url);
        addStepTwo.setRemark(remark);
        addStepTwo.setFeeType(feeWay);
        addStepTwo.setPayWay(payWay);
        addStepTwo.setPayType(payType);
        if (feeWay == 1) {
            addStepTwo.setFee(Double.parseDouble(mFee.getText().toString()));
        }
        mPresenter.submit(addStepTwo);
    }

    @Override
    public void onSubmit(boolean data) {
        if (data) {
            OrdersActivity.action(mContext, 0);
            finish();
        } else {
            toast("下单失败");
        }

    }


    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public void getPhoto(int choseCode) {
        this.choseCode = choseCode;
        getTakePhoto().onPickFromGallery();
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }

    @Override
    public void onUploadFile(String data) {
        switch (choseCode) {
            case 1:
                img1Url = data;
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.js.http.global.Const.IMG_URL + data, mImg1);
                break;
            case 2:
                img2Url = data;
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.js.http.global.Const.IMG_URL + data, mImg2);
                break;
        }
    }

    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(mContext, type, invokeParam, this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getTakePhoto().onCreate(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mFilePresenter != null) {
            mFilePresenter.detachView();
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        if (result.getImage() == null) {
            return;
        }
        mFilePresenter.uploadFile(new File(result.getImage().getOriginalPath()));
    }

    @Override
    public void takeFail(TResult result, String msg) {
        Log.d(getClass().getSimpleName(), msg);
    }

    @Override
    public void takeCancel() {
        Log.d(getClass().getSimpleName(), "takeCancel");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
    }

}
