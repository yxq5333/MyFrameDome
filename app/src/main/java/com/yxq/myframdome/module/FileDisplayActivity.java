package com.yxq.myframdome.module;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.crazyhuskar.myandroidsdk.base.MyBaseActivity;
import com.crazyhuskar.myandroidsdk.util.MyUtilActivity;
import com.tencent.smtt.sdk.TbsReaderView;
import com.yxq.myframdome.R;
import com.yxq.myframdome.util.FileUtils;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;

/** 文件浏览
 * @author CrazyHuskar
 * caeat at 2018-12-19  10:44
 */
public class FileDisplayActivity extends MyBaseActivity implements TbsReaderView.ReaderCallback {
    @BindView(R.id.rl_tbsView)
    RelativeLayout rlTbsView;
    private TbsReaderView mTbsReaderView;

    @Override
    public int setLayoutID() {
        return R.layout.activity_file_display;
    }

    @Override
    public AppCompatActivity registerActivity() {
        return this;
    }

    @Override
    public void init() {
        mTbsReaderView = new TbsReaderView(this, this);
        rlTbsView.addView(mTbsReaderView, new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        String path = getIntent().getStringExtra("path");
        boolean needDownload = getIntent().getBooleanExtra("download", false);
        if (TextUtils.isEmpty(path)) {
            MyUtilActivity.getInstance().finishActivity(this);
        } else {
            if (needDownload) {
                setCommonTitle(null, "文件浏览", "下载", R.drawable.arrow_back_24dp, null, null, null);
                commonTitle.setCommonTitleRightListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(path);
                        String path2 = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Download/";
                        FileUtils.copyFile(paths, path2, FileUtils.CODE_COPY);

                        new AlertDialog.Builder(FileDisplayActivity.this)
                                .setTitle("温馨提示")
                                .setMessage("文件已保存至：\n"
                                        + path2 + (new File(path).getName())
                                ).show();
                    }
                });
            } else {
                setCommonTitle("文件浏览");
            }
            displayFile(getIntent().getStringExtra("path"));
            commonTitle.setCommonTitleLeftListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyUtilActivity.getInstance().finishActivity(FileDisplayActivity.this);
                }
            });
        }
    }

    @Override
    public void onCallBackAction(Integer integer, Object o, Object o1) {

    }

    /**
     * 加载显示文件内容
     */
    private void displayFile(String path) {
        Bundle bundle = new Bundle();
        bundle.putString("filePath", path);
        bundle.putString("tempPath", Environment.getExternalStorageDirectory()
                .getPath());
        boolean result = mTbsReaderView.preOpen(parseFormat(path), false);
        if (result) {
            mTbsReaderView.openFile(bundle);
        } else {
            File file = new File(path);
            if (file.exists()) {
                Intent openintent = new Intent();
                openintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                String type = getMIMEType(file);
                // 设置intent的data和Type属性。
                openintent.setDataAndType(/* uri */Uri.fromFile(file), type);
                // 跳转
                startActivity(openintent);
                finish();
            }
        }
    }

    private String getMIMEType(File file) {

        String type = "*/*";
        String fName = file.getName();
        // 获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0) {
            return type;
        }
        /* 获取文件的后缀名 */
        String end = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (end == "")
            return type;
        // 在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (end.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    private final String[][] MIME_MapTable = {
            // {后缀名，MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx",
                    "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
            {".xls", "application/vnd.ms-excel"},
            {".xlsx",
                    "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx",
                    "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
            {".prop", "text/plain"}, {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"}, {".rtf", "application/rtf"},
            {".sh", "text/plain"}, {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"}, {".txt", "text/plain"},
            {".wav", "audio/x-wav"}, {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"}, {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/x-zip-compressed"}, {"", "*/*"}};

    private String parseFormat(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTbsReaderView != null) {
            mTbsReaderView.onStop();
        }
    }
}
