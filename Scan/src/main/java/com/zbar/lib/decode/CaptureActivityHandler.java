package com.zbar.lib.decode;

import android.os.Handler;
import android.os.Message;

import com.zbar.lib.camera.CameraManager;

/**
 * 作者: 陈涛(1076559197@qq.com)
 * 
 * 时间: 2014年5月9日 下午12:23:32
 *
 * 版本: V_1.0.0
 *
 * 描述: 扫描消息转发
 */
public final class CaptureActivityHandler extends Handler {

	public static final int AUTO_FOCUS = 31001;
	public static final int DECODE = 31002;
	public static final int DECODE_FAILED = 31003;
	public static final int DECODE_SUCCEEDED = 31004;
	public static final int RESTART_PREVIEW = 31005;
	public static final int QUIT_CODE = 31006;

	DecodeThread decodeThread = null;
	DecodeListener listener = null;
	private State state;

	private enum State {
		PREVIEW, SUCCESS, DONE
	}

	public CaptureActivityHandler(DecodeListener listener,Crop crop) {
		this.listener = listener;
		decodeThread = new DecodeThread(this,crop);
		decodeThread.start();
		state = State.SUCCESS;
		CameraManager.get().startPreview();
		restartPreviewAndDecode();
	}

	@Override
	public void handleMessage(Message message) {

		switch (message.what) {
		case AUTO_FOCUS:
			if (state == State.PREVIEW) {
				CameraManager.get().requestAutoFocus(this, AUTO_FOCUS);
			}
			break;
		case RESTART_PREVIEW:
			restartPreviewAndDecode();
			break;
		case DECODE_SUCCEEDED:
			state = State.SUCCESS;
			listener.handleDecode((String) message.obj);// 解析成功，回调
			break;

		case DECODE_FAILED:
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),DECODE);
			break;
		}

	}

	public void quitSynchronously() {
		state = State.DONE;
		CameraManager.get().stopPreview();
		removeMessages(DECODE_SUCCEEDED);
		removeMessages(DECODE_FAILED);
		removeMessages(DECODE);
		removeMessages(AUTO_FOCUS);
	}

	private void restartPreviewAndDecode() {
		if (state == State.SUCCESS) {
			state = State.PREVIEW;
			CameraManager.get().requestPreviewFrame(decodeThread.getHandler(),DECODE);
			CameraManager.get().requestAutoFocus(this, AUTO_FOCUS);
		}
	}

}
