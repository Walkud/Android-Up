package com.walkud.self.module.share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.walkud.self.R;
import com.walkud.self.module.BaseActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 微信朋友圈只支持分享照片，可带文字
 * 微信朋友支持单个图片、单个文件、多图片、不支持多个非图片格式文件
 * 微博只支持图片分享(单个图片、多个图片)，不支持文件分享，可以带文字
 * <p>
 * Android Intent 分享
 * Created by Walkud on 17/10/16.
 */

public class IntentShareActivity extends BaseActivity {

    private boolean isMi = true;

    @BindView(R.id.single_img_btn)
    Button singleImgBtn;
    @BindView(R.id.mulit_img_btn)
    Button mulitImgBtn;
    @BindView(R.id.single_file_btn)
    Button singleFileBtn;
    @BindView(R.id.mulit_file_btn)
    Button mulitFileBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_share);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.single_img_btn, R.id.mulit_img_btn, R.id.single_file_btn, R.id.mulit_file_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.single_img_btn:
                sendSingleImg();
                break;
            case R.id.mulit_img_btn:
                sendMulitImg();
                break;
            case R.id.single_file_btn:
                sendSingleFile();
                break;
            case R.id.mulit_file_btn:
                sendMulitFile();
                break;
        }
    }


    private void sendSingleImg() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
        File f = new File(isMi ? getSingleImgMi() : getSingleImg());
        intent.putExtra("Kdescription", "微信Key");
        Uri uri = Uri.fromFile(f);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void sendMulitImg() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
        intent.putExtra("Kdescription", "微信Key");
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (File file : isMi ? getMulitImgMi() : getMulitImg()) {
            imageUris.add(Uri.fromFile(file));
        }

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivity(Intent.createChooser(intent, "Share"));
    }


    private void sendSingleFile() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
        File f = new File(isMi ? getSingleFileMi() : getSingleFile());
        intent.putExtra("Kdescription", "微信Key");
        Uri uri = Uri.fromFile(f);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void sendMulitFile() {
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.putExtra(Intent.EXTRA_TEXT, "I have successfully share my message through my app");
        intent.putExtra("Kdescription", "微信Key");
        ArrayList<Uri> imageUris = new ArrayList<>();
        for (File file : isMi ? getMulitFileMi() : getMulitFile()) {
            imageUris.add(Uri.fromFile(file));
        }

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private List<File> getMulitImg() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        List<File> files = new ArrayList<>();
        files.add(new File(sdcard + "/aaa/20170714_152203.jpg"));
        files.add(new File(sdcard + "/aaa/index.jpg"));
        files.add(new File(sdcard + "/aaa/88ttese2013012138.jpg"));
        return files;
    }

    private List<File> getMulitFile() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        List<File> files = new ArrayList<>();
        files.add(new File(sdcard + "/aaa/fttest.txt"));
        files.add(new File(sdcard + "/aaa/测试2.doc"));
        files.add(new File(sdcard + "/aaa/88ttese2013012138.jpg"));
        return files;
    }

    private String getSingleImg() {
        return Environment.getExternalStorageDirectory() + "/aaa/20170714_152203.jpg";
    }

    private String getSingleFile() {
        return Environment.getExternalStorageDirectory() + "/aaa/fttest.txt";
    }

    private String getSingleImgMi() {
        return Environment.getExternalStorageDirectory() + "/Download/20160617084724_70959.jpg";
    }

    private String getSingleFileMi() {
        return Environment.getExternalStorageDirectory() + "/mobileservice/data_file.txt";
    }

    private List<File> getMulitImgMi() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        List<File> files = new ArrayList<>();
        files.add(new File(sdcard + "/Download/20160617084724_70959.jpg"));
        files.add(new File(sdcard + "/tencent/MicroMsg/WeiXin/wx_camera_1504920296864.jpg"));
        files.add(new File(sdcard + "/tencent/MicroMsg/WeiXin/wx_camera_1504920196402.jpg"));
        return files;
    }

    private List<File> getMulitFileMi() {
        String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
        List<File> files = new ArrayList<>();
        files.add(new File(sdcard + "/mobileservice/data_file.txt"));
        files.add(new File(sdcard + "/tencent/QQfile_recv/20170231问题.docx"));
        files.add(new File(sdcard + "/Download/20160617084724_70959.jpg"));
        return files;
    }


}
