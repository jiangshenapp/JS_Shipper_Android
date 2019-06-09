package com.js.shipper.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huyg on 2019-06-08.
 */
public class ShipBean implements Parcelable {

    private int type;
    private String address;
    private String addressName;
    private int addressCode;
    private String position;
    private String name;
    private String phone;
    private String addressDetail;

    public ShipBean(){

    }

    public ShipBean(String phone, String name, String addressDetail) {
        this.phone = phone;
        this.name = name;
        this.addressDetail = addressDetail;
    }


    protected ShipBean(Parcel in) {
        address = in.readString();
        addressName = in.readString();
        addressCode = in.readInt();
        position = in.readString();
        name = in.readString();
        phone = in.readString();
        addressDetail = in.readString();
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeString(addressName);
        dest.writeInt(addressCode);
        dest.writeString(position);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(addressDetail);
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShipBean> CREATOR = new Creator<ShipBean>() {
        @Override
        public ShipBean createFromParcel(Parcel in) {
            return new ShipBean(in);
        }

        @Override
        public ShipBean[] newArray(int size) {
            return new ShipBean[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public int getAddressCode() {
        return addressCode;
    }

    public void setAddressCode(int addressCode) {
        this.addressCode = addressCode;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
