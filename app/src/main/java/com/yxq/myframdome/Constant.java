package com.yxq.myframdome;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bm.library.PhotoView;
import com.yxq.myframdome.util.FileDisplayUtil;
import com.yxq.myframdome.util.ImageUtil;
import com.yxq.myframdome.util.ImageUtils;

import java.io.File;

import pub.devrel.easypermissions.EasyPermissions;

public class Constant {

    /**
     * 判断字符串是否符合手机号码格式
     * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
     * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186
     * 电信号段: 133,149,153,170,173,177,180,181,189
     *
     * @param mobileNums
     * @return 待检测的字符串
     */
    public static boolean isMobileNO(String mobileNums) {

        String telRegex = "^((13[0-9])|(14[5,7,9])|(15[^4])|(18[0-9])|(17[0,1,3,5,6,7,8]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums)) {
            return false;
        } else {
            return mobileNums.matches(telRegex);
        }
    }

    /**
     * 查看图片和文件
     *
     * @param fName 图片名称
     * @return
     */
    public static void seeFileByPath(Context mContext, String fName) {
        if (null == fName || TextUtils.isEmpty(fName)) {
            Toast.makeText(mContext, "文件地址为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if (ImageUtils.isImageFile(fName)) {
            PhotoView imageView = new PhotoView(mContext);
            imageView.enable();
            File file = new File(fName);
            String url = file.exists() ? fName : APIConfig.BASEURL + "core/down/" + fName;
            ImageUtil.show((AppCompatActivity) mContext, url);
        } else if (ImageUtils.isOfficeFile(fName)) {
            String[] perms = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            if (!EasyPermissions.hasPermissions(mContext, perms)) {
                EasyPermissions.requestPermissions((Activity) mContext, "需要访问手机存储权限！", 10086, perms);
            } else {
                FileDisplayUtil.fileDisplay(mContext, fName);
            }
        } else {
            Toast.makeText(mContext, "数据格式不对", Toast.LENGTH_SHORT).show();
        }
    }

}
