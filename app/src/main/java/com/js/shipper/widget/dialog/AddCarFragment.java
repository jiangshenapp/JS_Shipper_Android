package com.js.shipper.widget.dialog;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.js.shipper.R;
import com.js.shipper.model.bean.CarBean;
import com.js.shipper.model.event.AddCarEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2020/01/16
 * desc   :
 * version: 3.0.0
 */
public class AddCarFragment extends DialogFragment {

    private CarBean mCarBean;
    private long mType; //类型，1自由车辆，2外调车辆

    @BindView(R.id.car_remark)
    EditText mCarRemark;

    public AddCarFragment(CarBean carBean) {
        mCarBean = carBean;
        mType = 1;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);//取消对话框fragment的标题
        View view = inflater.inflate(R.layout.dialog_add_car, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.75), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @OnClick({R.id.car_type_own, R.id.car_type_other, R.id.negative, R.id.positive})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.car_type_own:
                mType = 1;
                break;
            case R.id.car_type_other:
                mType = 2;
                break;
            case R.id.negative:
                dismiss();
                break;
            case R.id.positive:
                EventBus.getDefault().post(new AddCarEvent(mCarBean.getCarId(),
                        mCarRemark.getText().toString().trim(),
                        mType));
                break;
        }
    }
}
