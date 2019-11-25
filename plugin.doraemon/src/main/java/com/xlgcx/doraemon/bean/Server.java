package com.xlgcx.doraemon.bean;

/**
 * @Author: jason_hzb
 * @Time: 2018/7/16 下午5:37
 * @Company：小灵狗出行
 * @Description:服务环境model
 */
public class Server {

    public String name;
    public String host;


    public Server(String host,String name){
        this.name = name;
        this.host = host;
    }


}
