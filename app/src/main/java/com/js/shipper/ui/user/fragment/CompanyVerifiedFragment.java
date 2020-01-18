package com.js.shipper.ui.user.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.afollestad.materialdialogs.MaterialDialog;
import com.base.frame.view.SimpleWebActivity;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.base.frame.view.BaseFragment;
import com.js.shipper.App;
import com.js.shipper.R;
import com.js.shipper.di.componet.DaggerFragmentComponent;
import com.js.shipper.di.module.FragmentModule;
import com.js.shipper.global.Const;
import com.js.shipper.util.glide.CommonGlideImageLoader;
import com.js.shipper.model.bean.AuthInfo;
import com.js.shipper.model.bean.ShengBean;
import com.js.shipper.model.event.UserStatusChangeEvent;
import com.js.shipper.presenter.FilePresenter;
import com.js.shipper.presenter.contract.FileContract;
import com.js.shipper.ui.main.activity.MainActivity;
import com.js.shipper.ui.user.presenter.CompanyVerifiedPresenter;
import com.js.shipper.ui.user.presenter.contract.CompanyVerifiedContract;
import com.js.shipper.util.GetJsonDataUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/05/26
 * desc   : 公司认证
 * version: 3.0.0
 */
public class CompanyVerifiedFragment extends BaseFragment<CompanyVerifiedPresenter> implements CompanyVerifiedContract.View, FileContract.View, TakePhoto.TakeResultListener, InvokeListener{

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_idcard)
    EditText etIdcard;
    @BindView(R.id.et_address)
    EditText etAddress;
    @BindView(R.id.iv_arrow_address)
    ImageView ivArrowAddress;
    @BindView(R.id.ll_address)
    LinearLayout llAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.auth_business_license)
    ImageView authBusinessLicense;
    @BindView(R.id.cb_select)
    CheckBox cbSelect;
    @BindView(R.id.tv_protocal)
    TextView tvProtocal;
    @BindView(R.id.ll_bottom)
    LinearLayout llBottom;
    @BindView(R.id.auth_submit)
    TextView authSubmit;

    @Inject
    FilePresenter mFilePresenter;

    private int choseCode;
    private InvokeParam invokeParam;
    private TakePhoto takePhoto;
    private AuthInfo mAuthInfo;
    private int authState;
    private String[] items = {"拍摄","从相册选择"};

    // 省
    private List<ShengBean> options1Items = new ArrayList<ShengBean>();
    // 市
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    // 区
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();


    public static CompanyVerifiedFragment newInstance() {
        return new CompanyVerifiedFragment();
    }

    @Override
    protected void initInject() {
        DaggerFragmentComponent.builder()
                .fragmentModule(new FragmentModule(this))
                .appComponent(App.getInstance().getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verified_company;
    }

    @Override
    protected void init() {
        mFilePresenter.attachView(this);
        mAuthInfo = new AuthInfo();
        authState = App.getInstance().companyConsignorVerified;
        if (authState != 0) { //0:未认证
            initAuthData();
            if (authState != 3) { //3:认证失败
                initAuthView();
            }
        }
    }

    public void initAuthData() {
        mPresenter.getCompanyVerifiedInfo();
    }

    @Override
    public void onCompanyVerifiedInfo(AuthInfo authInfo) {
        mAuthInfo = authInfo;

        etName.setText(authInfo.getCompanyName());
        etIdcard.setText(authInfo.getRegistrationNumber());
        etAddress.setText(authInfo.getAddress());
        etDetailAddress.setText(authInfo.getDetailAddress());

        CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL()  + authInfo.getBusinessLicenceImage()
                , authBusinessLicense, mContext.getResources().getDrawable(R.mipmap.img_authentication_id));
    }

    @Override
    public void onSubmitCompanyVerified() {
        toast("提交成功，请耐心等待审核");
        EventBus.getDefault().post(new UserStatusChangeEvent(UserStatusChangeEvent.CHANGE_SUCCESS));
        MainActivity.action(mContext);
    }

    public void initAuthView() {

        etName.setFocusable(false);
        etIdcard.setFocusable(false);
        etDetailAddress.setFocusable(false);
        llAddress.setClickable(false);
        etAddress.setEnabled(false);
        ivArrowAddress.setVisibility(View.GONE);
        authBusinessLicense.setClickable(false);
        llBottom.setVisibility(View.GONE);
    }

    @OnClick({R.id.ll_address, R.id.et_address, R.id.auth_business_license, R.id.cb_select, R.id.tv_protocal, R.id.auth_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_address:
            case R.id.et_address:
                // 解析数据
                parseAddressData();
                // 展示省市区选择器
                showAddressPickerView();
                break;
            case R.id.auth_business_license:
                getPhoto(Const.AUTH_BUSINESS_LICENSE);
                XXPermissions.with(getActivity())
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
                                toast("请同意拍照或录像权限");
                            }
                        });
                break;
            case R.id.cb_select:
                cbSelect.setChecked(cbSelect.isChecked());
                break;
            case R.id.tv_protocal:
                SimpleWebActivity.action(getActivity(), Const.H5_RegisterProtocal, "用户协议");
                break;
            case R.id.auth_submit:
                submitAction();
                break;
        }
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


    /**
     * 解析省市区数据并组装成自己想要的list
     */
    private void parseAddressData() {
        String jsonStr = new GetJsonDataUtil().getJson(getActivity(), "province.json");//获取assets目录下的json文件数据
        // 数据解析
        Gson gson = new Gson();
        java.lang.reflect.Type type = new TypeToken<List<ShengBean>>() {
        }.getType();
        List<ShengBean> shengList = gson.fromJson(jsonStr, type);
        // 把解析后的数据组装成想要的list
        options1Items = shengList;
        // 遍历省
        for (int i = 0; i < shengList.size(); i++) {
            // 存放城市
            ArrayList<String> cityList = new ArrayList<>();
            // 存放区
            ArrayList<ArrayList<String>> province_AreaList = new ArrayList<>();
            // 遍历市
            for (int c = 0; c < shengList.get(i).city.size(); c++) {
                // 拿到城市名称
                String cityName = shengList.get(i).city.get(c).name;
                cityList.add(cityName);

                ArrayList<String> city_AreaList = new ArrayList<>();//该城市的所有地区列表
                if (shengList.get(i).city.get(c).area == null || shengList.get(i).city.get(c).area.size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(shengList.get(i).city.get(c).area);
                }
                province_AreaList.add(city_AreaList);
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList);
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList);
        }
    }

    /**
     * 展示省市区地址选择器
     */
    private void showAddressPickerView() {// 弹出选择器

        OptionsPickerView pvOptions = new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).name +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                etAddress.setText(tx);
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    /**
     * 提交审核
     */
    public void submitAction() {

        String name = etName.getText().toString().trim();
        String idcard = etIdcard.getText().toString().trim();
        String address = etAddress.getText().toString().trim();
        String detailAddress = etDetailAddress.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            toast("请输入公司名称");
            return;
        }
        if (TextUtils.isEmpty(idcard)) {
            toast("请输入企业信用代码/注册号");
            return;
        }
        if (TextUtils.isEmpty(address)) {
            toast("请选择所在地");
            return;
        }
        if (TextUtils.isEmpty(detailAddress)) {
            toast("请输入详细地址");
            return;
        }
        if (TextUtils.isEmpty(mAuthInfo.getBusinessLicenceImage())) {
            toast("请上传公司营业执照");
            return;
        }
        if (cbSelect.isChecked() == false) {
            toast("请勾选用户协议");
            return;
        }

        mPresenter.submitCompanyVerified(name, idcard, address, detailAddress, mAuthInfo.getBusinessLicenceImage());
    }

    @Override
    public void onUploadFile(String data) {
        Log.d(getClass().getSimpleName(), data);
        switch (choseCode) {
            case Const.AUTH_BUSINESS_LICENSE:
                mAuthInfo.setBusinessLicenceImage(data);
                CommonGlideImageLoader.getInstance().displayNetImage(mContext, com.base.http.global.Const.IMG_URL()  + data, authBusinessLicense);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.TPermissionType type = PermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.handlePermissionsResult(getActivity(), type, invokeParam, this);
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
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type = PermissionManager.checkPermission(TContextWrap.of(this), invokeParam.getMethod());
        if (PermissionManager.TPermissionType.WAIT.equals(type)) {
            this.invokeParam = invokeParam;
        }
        return type;
    }

    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public void getPhoto(int choseCode) {
        this.choseCode = choseCode;
    }

    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this, this));
        }
        return takePhoto;
    }
}
