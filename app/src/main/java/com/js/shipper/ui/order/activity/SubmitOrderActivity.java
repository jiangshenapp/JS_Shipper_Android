package com.js.shipper.ui.order.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
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
import com.js.shipper.global.Const;
import com.js.shipper.manager.CommonGlideImageLoader;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.model.bean.OrderBean;
import com.js.shipper.model.bean.ShipBean;
import com.js.shipper.model.event.DictSelectEvent;
import com.js.shipper.model.request.AddOrder;
import com.js.shipper.presenter.DictPresenter;
import com.js.shipper.presenter.FilePresenter;
import com.js.shipper.presenter.contract.DictContract;
import com.js.shipper.presenter.contract.FileContract;
import com.js.shipper.ui.order.presenter.SubmitOrderPresenter;
import com.js.shipper.ui.order.presenter.contract.SubmitOrderContract;
import com.js.shipper.ui.ship.activity.SelectAddressActivity;
import com.js.shipper.util.TimeUtils;
import com.js.shipper.widget.window.ItemWindow;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019-06-18.
 * 综合下单
 */
public class SubmitOrderActivity extends BaseActivity<SubmitOrderPresenter> implements SubmitOrderContract.View, FileContract.View, DictContract.View, InvokeListener, TakePhoto.TakeResultListener {


    @BindView(R.id.good_weight)
    EditText mGoodWeight;
    @BindView(R.id.good_volume)
    EditText mGoodVolume;
    @BindView(R.id.good_name)
    TextView mGoodName;
    @BindView(R.id.ship_time)
    TextView mShipTime;
    @BindView(R.id.car_type)
    TextView mUseCarType;
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

    @BindView(R.id.ship_start_address)
    TextView mStartAddress;
    @BindView(R.id.ship_end_address)
    TextView mEndAddress;
    @BindView(R.id.ship_mileage)
    TextView mMileage;
    @BindView(R.id.ship_car_extent)
    TextView mCarExtent;
    @BindView(R.id.ship_car_type)
    TextView mCarType;
    @BindView(R.id.pay_type_pay)
    RadioButton mTypePay;
    @BindView(R.id.pay_type_cash)
    RadioButton mTypeCash;
    @BindView(R.id.pay_way_online)
    RadioButton mWayOnline;
    @BindView(R.id.pay_way_offline)
    RadioButton mWayOffline;
    @BindView(R.id.release)
    TextView mRelease;
    @BindView(R.id.cb_bail)
    CheckBox mBail;
    @BindView(R.id.bail_number)
    EditText mBailNumber;
    @BindView(R.id.good_package)
    TextView mPackType;


    private long matchId;
    private String img1Url;
    private String img2Url;

    private int choseCode;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private int payType = 2;
    private int payWay = 1;
    private int feeWay = 1;

    private String startAddress;
    private String endAddress;
    private ShipBean mSendShip = new ShipBean();
    private ShipBean mEndShip = new ShipBean();
    private Gson mGson = new Gson();
    private DecimalFormat df = new DecimalFormat("#####0.0");
    private ItemWindow mTypeWindow;
    private ItemWindow mLengthWindow;
    private String typeStr;
    private String lengthStr;
    private OrderBean mOrderBean;
    private boolean isBail;//是否需要保证金
    private String[] items = {"拍摄","从相册选择"};
    private List<String> list;
    private OptionsPickerView pvOptions;

    @Inject
    DictPresenter mDictPresenter;
    @Inject
    FilePresenter mFilePresenter;


    public static void action(Context context, long matchId, OrderBean orderBean) {
        Intent intent = new Intent(context, SubmitOrderActivity.class);
        intent.putExtra("matchId", matchId);
        intent.putExtra("orderBean", orderBean);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initIntent() {
        matchId = getIntent().getLongExtra("matchId", 0);
        mOrderBean = getIntent().getParcelableExtra("orderBean");
    }

    private void initData() {
        mDictPresenter.getDictByType(Const.DICT_CAR_TYPE_NAME);
        mDictPresenter.getDictByType(Const.DICT_LENGTH_NAME);
        mDictPresenter.getDictByType(Const.DICT_USE_CAR_TYPE_NAME);
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
        return R.layout.activity_submit_order;
    }


    private void initView() {

        if (matchId != 0) {
            mRelease.setText("指定发布");
        }

        mTypeWindow = new ItemWindow(mContext, Const.DICT_CAR_TYPE);
        mLengthWindow = new ItemWindow(mContext, Const.DICT_LENGTH);
        mFilePresenter.attachView(this);
        mDictPresenter.attachView(this);
        mPayType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pay_type_pay: //到付
                        payType = 1;
                        mWayOffline.setChecked(true);
                        payWay = 2;
                        mWayOnline.setClickable(false);
                        break;
                    case R.id.pay_type_cash: //现付
                        payType = 2;
                        mWayOnline.setClickable(true);
                        break;
                }
            }
        });
        mPayWay.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.pay_way_online: //在线支付
                        payWay = 1;
                        mTypeCash.setChecked(true);
                        payType = 2;
                        mTypePay.setClickable(false);
                        break;
                    case R.id.pay_way_offline: //线下支付
                        payWay = 2;
                        mTypePay.setClickable(true);
                        break;
                }
            }
        });
        mBail.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isBail = true;
                    mBailNumber.setEnabled(true);
                } else {
                    isBail = false;
                    mBailNumber.setEnabled(false);
                }
            }
        });

        mBailNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2+1);
                        mBailNumber.setText(s);
                        mBailNumber.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mBailNumber.setText(s);
                    mBailNumber.setSelection(2);
                }

                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mBailNumber.setText(s.subSequence(0, 1));
                        mBailNumber.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mFee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 2+1);
                        mFee.setText(s);
                        mFee.setSelection(s.length()); //光标移到最后
                    }
                }
                //如果"."在起始位置,则起始位置自动补0
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    mFee.setText(s);
                    mFee.setSelection(2);
                }

                //如果起始位置为0,且第二位跟的不是".",则无法后续输入
                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        mBailNumber.setText(s.subSequence(0, 1));
                        mBailNumber.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //条件选择器
        pvOptions = new OptionsPickerBuilder(mContext, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                mUseCarType.setText(list.get(options1));
            }
        }).build();

        initDetail();

    }

    private void initDetail() {
        if (mOrderBean != null) {
            mStartAddress.setText(mOrderBean.getSendAddress());
            mEndAddress.setText(mOrderBean.getReceiveAddress());
            mGoodWeight.setText(String.valueOf(mOrderBean.getGoodsWeight()));
            mShipTime.setText(mOrderBean.getLoadingTime());
            mGoodVolume.setText(String.valueOf(mOrderBean.getGoodsVolume()));
            mGoodName.setText(mOrderBean.getGoodsName());
            mUseCarType.setText(mOrderBean.getUseCarType());
            mPackType.setText(mOrderBean.getPackType());
            switch (mOrderBean.getPayWay()) {
                case 1:
                    mWayOnline.setChecked(true);
                    payWay = 1;
                    break;
                case 2:
                    mWayOffline.setChecked(true);
                    payWay = 2;
                    break;
            }

            switch (mOrderBean.getPayType()) {
                case 1:
                    mTypePay.setChecked(true);
                    payType = 1;
                    break;
                case 2:
                    mTypeCash.setChecked(true);
                    payType = 2;
                    break;
            }

            switch (mOrderBean.getFeeType()) {
                case 1:
                    feeWay = 1;
                    mFee.setText(String.valueOf(mOrderBean.getFee()));
                    break;
                case 2:
                    payWay = 2;
                    mFee.setText("电议");
                    break;
            }
            if (!TextUtils.isEmpty(mOrderBean.getRemark())) {
                mRemark.setVisibility(View.VISIBLE);
                mRemark.setText(mOrderBean.getRemark());
            } else {
                mRemark.setVisibility(View.GONE);
            }
            lengthStr = mOrderBean.getCarLength();
            typeStr = mOrderBean.getCarModel();
            img1Url = mOrderBean.getImage1();
            img2Url = mOrderBean.getImage2();

            mCarExtent.setText(mOrderBean.getCarLengthName());
            mCarType.setText(mOrderBean.getCarModelName());

            mSendShip.setPosition(mOrderBean.getSendPosition());
            mSendShip.setAddress(mOrderBean.getSendAddress());
            mSendShip.setAddressCode(Integer.parseInt(mOrderBean.getSendAddressCode()));
            mSendShip.setPhone(mOrderBean.getSendMobile());
            mSendShip.setName(mOrderBean.getSendName());

            mEndShip.setPosition(mOrderBean.getReceivePosition());
            mEndShip.setAddress(mOrderBean.getReceiveAddress());
            mEndShip.setAddressCode(Integer.parseInt(mOrderBean.getReceiveAddressCode()));
            mEndShip.setPhone(mOrderBean.getReceiveMobile());
            mEndShip.setName(mOrderBean.getReceiveName());

            CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.js.http.global.Const.IMG_URL + mOrderBean.getImage1(), mImg1);
            CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.js.http.global.Const.IMG_URL + mOrderBean.getImage2(), mImg2);

            if (mSendShip != null && mEndShip != null) {
                double distance = DistanceUtil.getDistance(mGson.fromJson(mSendShip.getPosition(), LatLng.class), mGson.fromJson(mEndShip.getPosition(), LatLng.class));
                mMileage.setText("总里程" + (distance > 1000 ? df.format(distance / 1000) + " Km" : ((int) distance) + "米"));
            }
        }
    }

    @Override
    public void setActionBar() {
        mTitle.setText("发货");
    }

    @OnClick({R.id.ship_time_layout, R.id.car_type_layout,
            R.id.image_1, R.id.image_2, R.id.fee_mine_layout,
            R.id.power_layout, R.id.specified_release, R.id.release,
            R.id.ship_start_layout, R.id.ship_end_layout,
            R.id.ship_car_extent_layout, R.id.ship_car_type_layout,
            R.id.good_type_layout,R.id.ship_package_layout})
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
            case R.id.ship_start_layout://发货地址
                Intent startIntent = new Intent(mContext, SelectAddressActivity.class);
                startIntent.putExtra("type", 0);
                startIntent.putExtra("ship", mSendShip);
                startActivityForResult(startIntent, Const.CODE_REQ);
                break;
            case R.id.ship_end_layout://收货地址
                Intent endIntent = new Intent(mContext, SelectAddressActivity.class);
                endIntent.putExtra("type", 1);
                endIntent.putExtra("ship", mEndShip);
                startActivityForResult(endIntent, Const.CODE_REQ);
                break;
            case R.id.ship_car_extent_layout://车长
                mLengthWindow.showAsDropDown(mToolbar, 0, 0);
                break;
            case R.id.ship_car_type_layout://车型
                mTypeWindow.showAsDropDown(mToolbar, 0, 0);
                break;
            case R.id.good_type_layout:
                TypeInputActivity.action(SubmitOrderActivity.this, Const.DICT_GOODS_NAME);
                break;
            case R.id.ship_package_layout:
                TypeInputActivity.action(SubmitOrderActivity.this, Const.DICT_PICK_TYPE);
                break;
        }
    }

    private void showUserCarType() {
        pvOptions.show();
    }

    private void showDateTime() {
        Calendar startTime = Calendar.getInstance();
        Calendar endTime = Calendar.getInstance();
        endTime.add(Calendar.YEAR, 2000);
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(mContext, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (date.before(new Date())){
                    toast("装货时间必须大于当前时间");
                    return;
                }
                mShipTime.setText(TimeUtils.formatYYMMDDHHMMSS(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
//                .setRangDate(startTime, endTime)
                .build();
        pvTime.show();
    }


    public void confirm() {
        String weight = mGoodWeight.getText().toString().trim();
        String volume = mGoodVolume.getText().toString().trim();
        String goodName = mGoodName.getText().toString().trim();
        String remark = mRemark.getText().toString().trim();
        String time = mShipTime.getText().toString().trim();
        String carType = mUseCarType.getText().toString().trim();
        String pickType = mPackType.getText().toString().trim();

        if (TextUtils.isEmpty(mStartAddress.getText().toString())) {
            toast("请输入发货地址");
            return;
        }

        if (TextUtils.isEmpty(mEndAddress.getText().toString())) {
            toast("请输入收货地址");
            return;
        }

        if (TextUtils.isEmpty(weight)) {
            toast("请输入货物重量");
            return;
        }

        if (TextUtils.isEmpty(volume)) {
            toast("请输入货物体积");
            return;
        }


        if (TextUtils.isEmpty(time)) {
            toast("请选择时间");
            return;
        }

        if (TextUtils.isEmpty(carType)) {
            toast("请选择用车类型");
            return;
        }
        if (feeWay == 1 && TextUtils.isEmpty(mFee.getText().toString().trim())) {
            toast("请输入价格");
            return;
        }
        if (isBail && TextUtils.isEmpty(mBailNumber.getText().toString())) {
            toast("请输入保证金");
            return;
        }

        AddOrder addOrder = new AddOrder();
        addOrder.setGoodsWeight(Double.parseDouble(weight));
        addOrder.setGoodsVolume(Double.parseDouble(volume));
        addOrder.setGoodsName(goodName);
        addOrder.setUseCarType(carType);
        addOrder.setLoadingTime(time);
        addOrder.setPackType(pickType);
        addOrder.setImage1(img1Url);
        addOrder.setImage2(img2Url);
        addOrder.setRemark(remark);
        addOrder.setFeeType(feeWay);
        addOrder.setPayWay(payWay);
        addOrder.setPayType(payType);
        addOrder.setCarLength(lengthStr);
        addOrder.setCarModel(typeStr);
        addOrder.setReceiveAddress(mEndShip.getAddress());
        addOrder.setReceiveAddressCode(String.valueOf(mEndShip.getAddressCode()));
        addOrder.setReceiveMobile(mEndShip.getPhone());
        addOrder.setReceivePosition(mEndShip.getPosition());
        addOrder.setReceiveName(mEndShip.getName());
        addOrder.setSendAddress(mSendShip.getAddress());
        addOrder.setSendMobile(mSendShip.getPhone());
        addOrder.setSendAddressCode(String.valueOf(mSendShip.getAddressCode()));
        addOrder.setSendName(mSendShip.getName());
        addOrder.setSendPosition(mSendShip.getPosition());
        addOrder.setRequireDeposit(isBail);
        addOrder.setDeposit(TextUtils.isEmpty(mBailNumber.getText().toString()) ? 0 : Double.parseDouble(mBailNumber.getText().toString()));
        if (matchId == 0) {
            addOrder.setMatchId("");
        } else {
            addOrder.setMatchId(String.valueOf(matchId));
        }
        if (feeWay == 1) {
            addOrder.setFee(Double.parseDouble(mFee.getText().toString()));
        }

        mPresenter.submitOrder(addOrder);
    }


    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public void getPhoto(int choseCode) {
        this.choseCode = choseCode;
        showDialog();
    }

    private void showDialog(){
        new MaterialDialog.Builder(mContext)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position==0){
                            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            Uri imageUri = Uri.fromFile(file);
                            getTakePhoto().onPickFromCapture(imageUri);
                        }else {
                            getTakePhoto().onPickFromGallery();
                        }
                    }
                }).show();
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

        if (mDictPresenter != null) {
            mDictPresenter.detachView();
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
        if (requestCode == Const.CODE_REQ && resultCode == 888) {
            if (data != null) {
                ShipBean shipBean = data.getParcelableExtra("ship");
                switch (shipBean.getType()) {
                    case 0:
                        mSendShip = shipBean;
                        mStartAddress.setText(shipBean.getAddress());
                        break;
                    case 1:
                        mEndShip = shipBean;
                        mEndAddress.setText(shipBean.getAddress());
                        break;
                }
            }
            if (mSendShip != null && mEndShip != null) {
                double distance = DistanceUtil.getDistance(mGson.fromJson(mSendShip.getPosition(), LatLng.class), mGson.fromJson(mEndShip.getPosition(), LatLng.class));
                mMileage.setText("总里程" + (distance > 1000 ? df.format(distance / 1000) + " Km" : ((int) distance) + "米"));
            }

        } else if (requestCode == Const.CODE_REQ && resultCode == 111) {
            String content = data.getStringExtra("content");
            int type = data.getIntExtra("type", 0);
            switch (type) {
                case Const.DICT_PICK_TYPE:
                    mPackType.setText(content);
                    break;
                case Const.DICT_GOODS_NAME:
                    mGoodName.setText(content);
                    break;
            }
        }
    }

    @Subscribe
    public void onEvent(DictSelectEvent event) {
        switch (event.type) {
            case Const.DICT_LENGTH:
                mCarExtent.setText(event.labelStr);
                lengthStr = event.valueStr;
                break;
            case Const.DICT_CAR_TYPE:
                mCarType.setText(event.labelStr);
                typeStr = event.valueStr;
                break;
        }
    }


    @Override
    public void onDictByType(String type, List<DictBean> dictBeans) {
        switch (type) {
            case Const.DICT_CAR_TYPE_NAME:
                mTypeWindow.setData(dictBeans);
                break;
            case Const.DICT_LENGTH_NAME:
                mLengthWindow.setData(dictBeans);
                break;
            case Const.DICT_USE_CAR_TYPE_NAME:
                list = new ArrayList<>();
                for (DictBean dictBean : dictBeans) {
                    list.add(dictBean.getLabel());
                }
                pvOptions.setPicker(list);
                break;
        }
    }


    @Override
    public void onSubmitOrder(Boolean isOk) {
        if (isOk) {
            OrdersActivity.action(mContext, 0);
            finish();
        } else {
            toast("下单失败");
        }
    }
}
