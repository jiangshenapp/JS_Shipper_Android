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
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;
import com.base.frame.view.BaseActivity;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerActivityComponent;
import com.js.shipper.di.module.ActivityModule;
import com.js.shipper.global.Const;
import com.js.shipper.model.event.RemoveUserEvent;
import com.js.shipper.util.glide.CommonGlideImageLoader;
import com.js.shipper.model.bean.DictBean;
import com.js.shipper.model.request.AddStepTwo;
import com.js.shipper.presenter.DictPresenter;
import com.js.shipper.presenter.FilePresenter;
import com.js.shipper.presenter.contract.DictContract;
import com.js.shipper.presenter.contract.FileContract;
import com.js.shipper.ui.order.presenter.OrderSubmitPresenter;
import com.js.shipper.ui.order.presenter.contract.OrderSubmitContract;
import com.base.util.TimeUtils;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by huyg on 2019/5/6.
 */
public class OrderSubmitActivity extends BaseActivity<OrderSubmitPresenter> implements OrderSubmitContract.View, FileContract.View, InvokeListener, TakePhoto.TakeResultListener, DictContract.View {


    @BindView(R.id.good_weight)
    EditText mGoodWeight;
    @BindView(R.id.good_volume)
    EditText mGoodVolume;
    @BindView(R.id.good_name)
    TextView mGoodName;
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
    @BindView(R.id.pay_type_pay)
    RadioButton mTypePay;
    @BindView(R.id.pay_type_cash)
    RadioButton mTypeCash;
    @BindView(R.id.pay_way_online)
    RadioButton mWayOnline;
    @BindView(R.id.pay_way_offline)
    RadioButton mWayOffline;
    @BindView(R.id.cb_bail)
    CheckBox mBail;
    @BindView(R.id.bail_number)
    EditText mBailNumber;
    @BindView(R.id.good_package)
    TextView mPackType;
    @BindView(R.id.cb_banhuo)
    CheckBox mBanhuo;
    @BindView(R.id.cb_xiehuo)
    CheckBox mXiehuo;

    private long orderId;
    private String img1Url;
    private String img2Url;
    @Inject
    FilePresenter mFilePresenter;
    @Inject
    DictPresenter mDictPresenter;

    private int choseCode;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private int payType = 2;
    private int payWay = 1;
    private int feeWay = 1;
    private boolean isBail;//是否需要保证金
    private String[] items = {"拍摄", "从相册选择"};
    private OptionsPickerView pvOptions;
    private List<String> list;

    public static void action(Context context, long orderId) {
        Intent intent = new Intent(context, OrderSubmitActivity.class);
        intent.putExtra("orderId", orderId);
        context.startActivity(intent);
    }


    @Override
    protected void init() {
        initIntent();
        initView();
        initData();
    }

    private void initData() {
        mDictPresenter.getDictByType(Const.DICT_USE_CAR_TYPE_NAME);
    }

    private void initView() {
        mFilePresenter.attachView(this);
        mDictPresenter.attachView(this);

        mBanhuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mBanhuo.setBackground(getResources().getDrawable(R.drawable.shape_border_eca73f));
                mXiehuo.setBackground(getResources().getDrawable(R.drawable.shape_border_c8c8c8));
                mBanhuo.setTextColor(getResources().getColor(R.color._ECA73F));
                mXiehuo.setTextColor(getResources().getColor(R.color._B4B4B4));
                if (TextUtils.isEmpty(mRemark.getText().toString())) {
                    mRemark.setText(mRemark.getText().toString() + mBanhuo.getText().toString());
                } else {
                    mRemark.setText(mRemark.getText().toString() + " " + mBanhuo.getText().toString());
                }
            }
        });
        mXiehuo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mXiehuo.setBackground(getResources().getDrawable(R.drawable.shape_border_eca73f));
                mBanhuo.setBackground(getResources().getDrawable(R.drawable.shape_border_c8c8c8));
                mXiehuo.setTextColor(getResources().getColor(R.color._ECA73F));
                mBanhuo.setTextColor(getResources().getColor(R.color._B4B4B4));
                if (TextUtils.isEmpty(mRemark.getText().toString())) {
                    mRemark.setText(mRemark.getText().toString() + mXiehuo.getText().toString());
                } else {
                    mRemark.setText(mRemark.getText().toString() + " " + mXiehuo.getText().toString());
                }
            }
        });

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
                                s.toString().indexOf(".") + 2 + 1);
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
                                s.toString().indexOf(".") + 2 + 1);
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
                if (list != null && list.size() > 0) {
                    mCarType.setText(list.get(options1));
                }
            }
        }).build();

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


    @OnClick({R.id.ship_time_layout, R.id.car_type_layout,
            R.id.image_1, R.id.image_2, R.id.fee_mine_layout,
            R.id.power_layout, R.id.specified_release,
            R.id.release, R.id.good_type_layout,
            R.id.ship_package_layout})
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
            case R.id.good_type_layout:
                TypeInputActivity.action(OrderSubmitActivity.this, Const.DICT_GOODS_NAME);
                break;
            case R.id.ship_package_layout:
                TypeInputActivity.action(OrderSubmitActivity.this, Const.DICT_PICK_TYPE);
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
        String remark = mRemark.getText().toString().trim();
        String name = mGoodName.getText().toString().trim();
        String time = mShipTime.getText().toString().trim();
        String carType = mCarType.getText().toString().trim();
        String packType = mPackType.getText().toString().trim();

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


        AddStepTwo addStepTwo = new AddStepTwo();
        addStepTwo.setId(orderId);
        addStepTwo.setGoodsWeight(Double.parseDouble(weight));
        addStepTwo.setGoodsVolume(Double.parseDouble(volume));
        addStepTwo.setGoodsName(name);
        addStepTwo.setPackType(packType);
        addStepTwo.setUseCarType(carType);
        addStepTwo.setLoadingTime(time);
        addStepTwo.setImage1(img1Url);
        addStepTwo.setImage2(img2Url);
        addStepTwo.setRemark(remark);
        addStepTwo.setFeeType(feeWay);
        addStepTwo.setPayWay(payWay);
        addStepTwo.setPayType(payType);
        addStepTwo.setRequireDeposit(isBail);
        addStepTwo.setDeposit(TextUtils.isEmpty(mBailNumber.getText().toString()) ? 0 : Double.parseDouble(mBailNumber.getText().toString()));
        if (feeWay == 1) {
            addStepTwo.setFee(Double.parseDouble(mFee.getText().toString()));
        }
        mPresenter.submit(addStepTwo);
    }

    @Override
    public void onSubmit(boolean data) {
        if (data) {
            EventBus.getDefault().postSticky(new RemoveUserEvent());
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
        XXPermissions.with(mContext)
                //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
                .permission(
                        Permission.CAMERA)
                .request(new OnPermission() {

                    @Override
                    public void hasPermission(List<String> granted, boolean isAll) {
                        if (isAll) {
                            showDialog();
                        }
                    }

                    @Override
                    public void noPermission(List<String> denied, boolean quick) {
                        toast("请同意权限");
                    }
                });
    }

    private void showDialog() {
        new MaterialDialog.Builder(mContext)
                .items(items)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        if (position == 0) {
                            File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                            if (!file.getParentFile().exists()) {
                                file.getParentFile().mkdirs();
                            }
                            Uri imageUri = Uri.fromFile(file);
                            getTakePhoto().onPickFromCapture(imageUri);
                        } else {
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
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL()  + data, mImg1);
                break;
            case 2:
                img2Url = data;
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL()  + data, mImg2);
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
        if (requestCode == Const.CODE_REQ && resultCode == 111) {
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

    @Override
    public void onDictByType(String type, List<DictBean> dictBeans) {
        list = new ArrayList<>();
        for (DictBean dictBean : dictBeans) {
            list.add(dictBean.getLabel());
        }
        pvOptions.setPicker(list);
    }
}
