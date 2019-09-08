package com.js.driver.model.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * author : hzb
 * e-mail : hanzhanbing@evcoming.com
 * time   : 2019/06/08
 * desc   :
 * version: 3.0.0
 */
public class AccountInfo implements Parcelable {

    /**
     * driverDepositState : 1
     * subscriberId : 2
     * id : 2
     * balanceState : 2
     * driverDeposit : 0
     * tradeDeposit : 0
     * balance : 0.01
     * consignorDeposit : 0
     */

    private int driverDepositState;
    private int subscriberId;
    private int id;
    private int balanceState;
    private double driverDeposit;
    private double tradeDeposit;
    private double balance;
    private double consignorDeposit;

    public int getDriverDepositState() {
        return driverDepositState;
    }

    public void setDriverDepositState(int driverDepositState) {
        this.driverDepositState = driverDepositState;
    }

    public int getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(int subscriberId) {
        this.subscriberId = subscriberId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBalanceState() {
        return balanceState;
    }

    public void setBalanceState(int balanceState) {
        this.balanceState = balanceState;
    }

    public double getDriverDeposit() {
        return driverDeposit;
    }

    public void setDriverDeposit(double driverDeposit) {
        this.driverDeposit = driverDeposit;
    }

    public double getTradeDeposit() {
        return tradeDeposit;
    }

    public void setTradeDeposit(double tradeDeposit) {
        this.tradeDeposit = tradeDeposit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getConsignorDeposit() {
        return consignorDeposit;
    }

    public void setConsignorDeposit(double consignorDeposit) {
        this.consignorDeposit = consignorDeposit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected AccountInfo(Parcel in) {
        driverDepositState = in.readInt();
        subscriberId = in.readInt();
        id = in.readInt();
        balanceState = in.readInt();
        driverDeposit = in.readDouble();
        tradeDeposit = in.readDouble();
        balance = in.readDouble();
        consignorDeposit = in.readDouble();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // 序列化过程：必须按成员变量声明的顺序进行封装
        dest.writeInt(driverDepositState);
        dest.writeInt(subscriberId);
        dest.writeInt(id);
        dest.writeInt(balanceState);
        dest.writeDouble(driverDeposit);
        dest.writeDouble(tradeDeposit);
        dest.writeDouble(balance);
        dest.writeDouble(consignorDeposit);
    }

    // 反序列过程：必须实现Parcelable.Creator接口，并且对象名必须为CREATOR
    // 读取Parcel里面数据时必须按照成员变量声明的顺序，Parcel数据来源上面writeToParcel方法，读出来的数据供逻辑层使用
    public static final Creator<AccountInfo> CREATOR = new Creator<AccountInfo>() {

        @Override
        public AccountInfo createFromParcel(Parcel in) {
            return new AccountInfo(in);
        }

        @Override
        public AccountInfo[] newArray(int size) {
            return new AccountInfo[size];
        }
    };
}
