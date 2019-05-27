package com.js.shipper.rx;


import android.text.TextUtils;

import com.js.http.HttpResponse;
import com.js.shipper.manager.UserManager;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by huyg on 2018/8/27.
 */

public class RxResult {

    public static <T> ObservableTransformer<HttpResponse<T>, T> handleResult() {
        return upstream -> {
            return upstream.flatMap(result -> {
                        if (result.isSuccess()) {
                            return createData(result.getData());
                        } else if (result.getCode() == 401) {
                            UserManager.getUserManager().logout();
                            return Observable.error(new Exception("请您重新登录!"));
                        } else {
                            if (TextUtils.isEmpty(result.getMsg())) {
                                return Observable.error(new Exception("请稍后重试"));
                            } else {
                                return Observable.error(new Exception(result.getMsg()));
                            }
                        }
                    }

            );
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }
}
