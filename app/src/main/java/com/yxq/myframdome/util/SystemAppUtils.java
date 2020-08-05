package com.yxq.myframdome.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;

import com.yxq.myframdome.AppConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Android系统界面调用工具类
 * Created by HG on 2018-01-23.
 */

public class SystemAppUtils {

    /**
     * 获取文件的URI
     *
     * @param context
     * @param filepath
     * @return
     */
    public Uri getFileUri(Context context, String filepath) {
        return getFileUri(context, new File(filepath));
    }

    /**
     * 获取文件的URI
     *
     * @param context
     * @param file
     * @return
     */
    public Uri getFileUri(Context context, File file) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());

            return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        }

        return Uri.fromFile(file);
    }

    private final String photoSavePath = AppConfig.IMAGE_LOADER_CACHE_PATH;
    private String photoName = "";
    private File photoFile = null;
    private int photoQuality;

    /**
     * 获取照片名称
     *
     * @return
     */
    public String getPhotoName() {
        return photoName;
    }

    /**
     * 获取照片文件
     *
     * @return
     */
    public File getPhotoFile() {
        return photoFile;
    }

    /**
     * 获取照片压缩质量
     *
     * @return
     */
    public int getPhotoQuality() {
        return photoQuality;
    }

    /**
     * 调用系统相机拍摄照片
     *
     * @param activity
     * @param requestCode
     * @param quality     压缩质量 0-100
     */
    public void takePhoto(Activity activity, int requestCode, int quality) {

        photoQuality = quality;
        if (photoQuality < 0) {
            photoQuality = 0;
        }
        if (photoQuality > 100) {
            photoQuality = 100;
        }
        photoName = System.currentTimeMillis() + ".jpg";
        photoFile = new File(photoSavePath + photoName);

        File file = new File(photoSavePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 启动拍照,并保存到临时文件
        Intent takePhotoIntent = new Intent();
        takePhotoIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(activity, photoFile));
        takePhotoIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
        activity.startActivityForResult(takePhotoIntent, requestCode);
    }

    /**
     * 调用系统相机拍摄照片返回处理
     *
     * @param activity
     * @return
     */
    public boolean onActivityResultForTakePhoto(Activity activity) {

        // 拍照
        if (photoFile == null) {
            return false;
        }

        String fileSrc = photoFile.getAbsolutePath();
        updateGallery(activity);

        // 获取图片的宽和高
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap img = BitmapFactory.decodeFile(fileSrc, options);

        // 压缩图片
        options.inSampleSize = Math.max(1,
                (int) Math.ceil(Math.max((double) options.outWidth / 1024f, (double) options.outHeight / 1024f)));
        options.inJustDecodeBounds = false;
        img = BitmapFactory.decodeFile(fileSrc, options);

        compressPhoto(img);

        return true;
    }

    /**
     * 扫描照片
     */
    private void updateGallery(Context context) {

        MediaScannerConnection.scanFile(context, new String[]{photoFile.getAbsolutePath()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {

                    }
                });
    }

    /**
     * 压缩图片
     */
    private void compressPhoto(Bitmap b) {
        savePhoto(b, photoSavePath, photoName, photoQuality, false);
    }

    /**
     * 安装APK文件
     *
     * @param context  context
     * @param filePath filePath
     */
    public void installAPK(Context context, String filePath) {

        Intent installAPKIntent = new Intent();
        installAPKIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installAPKIntent.setAction(Intent.ACTION_VIEW);
        installAPKIntent.setDataAndType(getFileUri(context, filePath), "application/vnd.android.package-archive");
        context.startActivity(installAPKIntent);
    }

    /**
     * 安装APK文件
     *
     * @param context context
     * @param file    file
     */
    public void installAPK(Context context, File file) {
        installAPK(context, file.getAbsolutePath());
    }

    /**
     * 是否可以安装APK文件
     *
     * @param context context
     * @return boolean
     */
    public boolean canInstallAPK(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.getPackageManager().canRequestPackageInstalls();
        }

        return true;
    }

    /**
     * 请求安装权限</br>
     * 8.0以上需要使用
     */
    public void requestInstallPermission(Activity activity, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, requestCode);
        }
    }

    private Uri albumPhotoUri = null;
    private String albumPhotoPath = "";

    public Uri getAlbumPhotoUri() {
        return albumPhotoUri;
    }

    public String getAlbumPhotoPath() {
        return albumPhotoPath;
    }

    /**
     * 打开相册
     *
     * @param activity
     * @param requestCode
     */
    public void openAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 打开相册返回处理
     *
     * @param context
     * @param data
     * @return
     */
    public boolean onActivityResultForOpenAlbum(Context context, Intent data) {

        albumPhotoUri = data.getData();
        albumPhotoPath = getRealFilePath(context, albumPhotoUri);

        if (albumPhotoUri == null || albumPhotoPath == null) {
            return false;
        }

        return true;
    }

    /**
     * 从Uri中解析物理路径
     *
     * @param context
     * @param uri
     * @return
     */
    public String getRealFilePath(Context context, Uri uri) {

        if (null == uri) {
            return null;
        }

        String scheme = uri.getScheme();
        String filepath = null;

        if (scheme == null) {
            filepath = uri.getPath();
        } else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            filepath = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        filepath = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }

        return filepath;
    }

    private final String videoSavePath = "";// SystemConfig.getVideoCachePath();
    private String videoName = "";
    private File videoFile = null;

    public String getVideoName() {
        return videoName;
    }

    public File getVideoFile() {
        return videoFile;
    }

    /**
     * 调用系统相机拍摄视频
     *
     * @param activity
     * @param requestCode
     * @param quality
     */
    public void takeVideo(Activity activity, int requestCode, float quality) {

        if (quality < 0f) {
            quality = 0f;
        }
        if (quality > 1f) {
            quality = 1f;
        }

        videoName = System.currentTimeMillis() + ".mp4";
        videoFile = new File(videoSavePath + videoName);

        File file = new File(videoSavePath);
        if (!file.exists()) {
            file.mkdirs();
        }

        // 启动视频,并保存到临时文件
        Intent mIntent = new Intent();
        mIntent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(activity, videoFile));
        mIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, quality);

        activity.startActivityForResult(mIntent, requestCode);
    }

    /**
     * 调用系统相机拍摄视频返回处理
     *
     * @return
     */
    public boolean onActivityResultForTakeVideo() {

        if (videoFile == null) {
            return false;
        }

        return true;
    }

    /**
     * 保存图片到本地
     *
     * @param bitmap
     * @param path
     * @param name
     * @param quality 质量 0-100
     * @param isPng   是否为PNG图片
     */
    public static void savePhoto(Bitmap bitmap, String path, String name, int quality, boolean isPng) {

        FileOutputStream b = null;

        File file = new File(path);
        if (!file.exists()) {
            // 创建文件夹
            file.mkdirs();
        }
        String fileName = path + name;

        try {
            b = new FileOutputStream(fileName);
            if (isPng) {
                bitmap.compress(Bitmap.CompressFormat.PNG, quality, b);// 把数据写入文件
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, b);// 把数据写入文件
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
