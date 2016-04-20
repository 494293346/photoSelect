package cn.asheng.photoselect.model;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PhotoDirInfo {
    private String dirName;
    private int picCount = 0;
    private String dirId; // 目录的ID
    private String firstPicPath;

    public String getDirName() {
        return dirName;
    }

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public int getPicCount() {
        return picCount;
    }

    public void setPicCount(int picCount) {
        this.picCount = picCount;
    }

    public String getFirstPicPath() {
        return firstPicPath;
    }

    public void setFirstPicPath(String firstPicPath) {
        this.firstPicPath = firstPicPath;
    }

    public String getDirId() {
        return dirId;
    }

    public void setDirId(String dirId) {
        this.dirId = dirId;
    }
}
