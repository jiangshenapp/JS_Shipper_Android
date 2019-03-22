package com.xlgcx.http.rx;


import android.text.TextUtils;

import com.xlgcx.http.HttpResult;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;

/**
 * Created by huyg on 2018/8/27.
 */

public class RxResult {

    public static <T> ObservableTransformer<HttpResult<T>, T> handleResult() {
        return upstream -> {
            return upstream.flatMap(result -> {
                        if (result.getResultCode() == 0) {
                            return createData(result.getResultValue());
                        } else if (result.getResultCode() == -1) {
                            return Observable.error(new Exception("请您重新登录!"));
                        } else {
                            if (TextUtils.isEmpty(result.getResultMsg())) {
                                return Observable.error(new Exception("请稍后重试"));
                            } else {
                                return Observable.error(new Exception(result.getResultMsg()));
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
