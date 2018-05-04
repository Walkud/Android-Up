package com.walkud.self.module.qrc;

import android.content.res.AssetFileDescriptor;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;
import com.zbar.lib.camera.CameraManager;
import com.zbar.lib.decode.CaptureActivityHandler;
import com.zbar.lib.decode.Crop;
import com.zbar.lib.decode.DecodeListener;
import com.zbar.lib.decode.InactivityTimer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.zbar.lib.decode.CaptureActivityHandler.RESTART_PREVIEW;


/**
 * 扫描界面
 */
public class ScanQrcActivity extends BaseActivity implements Callback, DecodeListener {

    private static final float BEEP_VOLUME = 0.50f;//音量大小

    @BindView(R.id.capture_preview)
    SurfaceView capturePreview;
    @BindView(R.id.capture_scan_line)
    ImageView captureScanLine;
    @BindView(R.id.capture_crop_layout)
    FrameLayout captureCropLayout;
    @BindView(R.id.capture_containter)
    RelativeLayout captureContainter;

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qrc);
        ButterKnife.bind(this);

        // 初始化 CameraManager
        CameraManager.init(getApplication());
        inactivityTimer = new InactivityTimer(this);

        initScanAnim();

    }

    /**
     * 初始化扫描动画
     */
    private void initScanAnim() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scan_line_translate);
        animation.setInterpolator(new LinearInterpolator());
        captureScanLine.startAnimation(animation);
    }

    /**
     * 闪光灯
     *
     * @param flag
     */
    protected void light(boolean flag) {
        if (!flag) {
            // 开闪光灯
            CameraManager.get().openLight();
        } else {
            // 关闪光灯
            CameraManager.get().offLight();
        }

    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onResume() {
        super.onResume();
        SurfaceHolder surfaceHolder = capturePreview.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }

        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        if (inactivityTimer != null) {
            inactivityTimer.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void handleDecode(final String result) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        showToast(result);
        // 连续扫描，不发送此消息扫描一次结束后就不能再次扫描
        handler.sendEmptyMessageDelayed(RESTART_PREVIEW, 2000);
    }

    /**
     * 初始化摄像头
     *
     * @param surfaceHolder
     */
    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (Exception ex) {
            Log.e(TAG, "openDriver Exception", ex);
            showToast("请检查权限设置");
            finish();
            return;
        }


        Point point = CameraManager.get().getCameraResolution();
        int width = point.y;
        int height = point.x;

        int x = captureCropLayout.getLeft() * width / captureContainter.getWidth();
        int y = captureCropLayout.getTop() * height / captureContainter.getHeight();

        int cropWidth = captureCropLayout.getWidth() * width
                / captureContainter.getWidth();
        int cropHeight = captureCropLayout.getHeight() * height
                / captureContainter.getHeight();

        Crop crop = new Crop(x, y, cropWidth, cropHeight);


        if (handler == null) {
            handler = new CaptureActivityHandler(this, crop);
        }


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    /**
     * 初始化音效
     */
    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);
            AssetFileDescriptor file = null;
            try {
                file = getResources().openRawResourceFd(
                        R.raw.beep);
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                mediaPlayer = null;
            } finally {
                if (file != null) {
                    try {
                        file.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 播放解析成功声音
     */
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}