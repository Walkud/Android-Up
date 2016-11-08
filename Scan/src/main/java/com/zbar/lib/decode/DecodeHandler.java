package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.zbar.lib.ZbarManager;

import static com.zbar.lib.decode.CaptureActivityHandler.DECODE;
import static com.zbar.lib.decode.CaptureActivityHandler.DECODE_FAILED;
import static com.zbar.lib.decode.CaptureActivityHandler.DECODE_SUCCEEDED;
import static com.zbar.lib.decode.CaptureActivityHandler.QUIT_CODE;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * <p>
 * 时间: 2014年5月9日 下午12:24:13
 * <p>
 * 版本: V_1.0.0
 * <p>
 * 描述: 接受消息后解码
 */
final class DecodeHandler extends Handler {

    CaptureActivityHandler handler;
    Crop crop;

    DecodeHandler(CaptureActivityHandler handler, Crop crop) {
        this.handler = handler;
        this.crop = crop;
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case DECODE:
                decode((byte[]) message.obj, message.arg1, message.arg2);
                break;
            case QUIT_CODE:
                Looper.myLooper().quit();
                break;
        }
    }

    private void decode(byte[] data, int width, int height) {
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;

        ZbarManager manager = new ZbarManager();
        String result = manager.decode(rotatedData, width, height, true,
                crop.getX(), crop.getY(), crop.getCropWidth(),
                crop.getCropHeight());

        if (result != null) {
            Message msg = new Message();
            msg.obj = result;
            msg.what = DECODE_SUCCEEDED;
            handler.sendMessage(msg);
        } else {
            handler.sendEmptyMessage(DECODE_FAILED);
        }
    }

}
