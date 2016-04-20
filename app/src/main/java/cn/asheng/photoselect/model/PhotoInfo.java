package cn.asheng.photoselect.model;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PhotoInfo {

    private String picName;
    private String picId;
    private String picPath;
    private Bitmap picBitmap;

    public String getPicName() {
        return picName;
    }

    public void setPicName(String picName) {
        this.picName = picName;
    }

    public String getPicId() {
        return picId;
    }

    public void setPicId(String picId) {
        this.picId = picId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public Bitmap getPicBitmap() {
        return picBitmap;
    }

    public void setPicBitmap(Bitmap picBitmap) {
        this.picBitmap = picBitmap;
    }
}
