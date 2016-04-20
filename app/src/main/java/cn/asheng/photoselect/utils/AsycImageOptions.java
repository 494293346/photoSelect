package cn.asheng.photoselect.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import cn.asheng.photoselect.R;

/**
 * Created by Administrator on 2016/4/19.
 */
public class AsycImageOptions {
    private static AsycImageOptions instance = new AsycImageOptions();
    public static AsycImageOptions getInstance() {
        return instance;
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(Color.WHITE).showImageForEmptyUri(Color.WHITE)
                .bitmapConfig(Bitmap.Config.RGB_565).imageScaleType(ImageScaleType.EXACTLY).cacheInMemory(true)
                .cacheOnDisk(true).build();
    }
    /**
     * 不保存到SD卡
     *
     * @return DisplayImageOptions
     */
    public static DisplayImageOptions getNoDiscCacheOptions() {
        return getImgOptions(false, true);
    }
    /**
     * @param isCacheOnDisk 是否缓存到SD卡
     * @param isCacheInMem  是否缓存到内存
     * @return DisplayImageOptions
     */
    private static DisplayImageOptions getImgOptions(boolean isCacheOnDisk, boolean isCacheInMem) {
        return getImgOptions(isCacheOnDisk, isCacheInMem,R.drawable.icon_default_image);
    }
    /**
     * @param isCacheOnDisk 是否缓存到SD卡
     * @param isCacheInMem  是否缓存到内存
     * @param imgLocalId    使用本地图片
     * @return DisplayImageOptions
     */
    private static DisplayImageOptions getImgOptions(boolean isCacheOnDisk, boolean isCacheInMem, int imgLocalId) {
        return new DisplayImageOptions.Builder().showImageOnLoading(imgLocalId)
                .showImageForEmptyUri(imgLocalId).showImageOnFail(imgLocalId).cacheOnDisk(isCacheOnDisk)
                .cacheInMemory(isCacheInMem).build();
    }
}
