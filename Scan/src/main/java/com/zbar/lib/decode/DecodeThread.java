package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.CountDownLatch;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * <p>
 * 时间: 2014年5月9日 下午12:24:34
 * <p>
 * 版本: V_1.0.0
 * <p>
 * 描述: 解码线程
 */
final class DecodeThread extends Thread {

    CaptureActivityHandler captureActivityHandler;
    private Handler handler;
    private final CountDownLatch handlerInitLatch;
    private Crop crop;

    DecodeThread(CaptureActivityHandler captureActivityHandler, Crop crop) {
        this.captureActivityHandler = captureActivityHandler;
        this.crop = crop;
        handlerInitLatch = new CountDownLatch(1);
    }

    Handler getHandler() {
        try {
            handlerInitLatch.await();
        } catch (InterruptedException ie) {
            // continue?
        }
        return handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        handler = new DecodeHandler(captureActivityHandler, crop);
        handlerInitLatch.countDown();
        Looper.loop();
    }

}
