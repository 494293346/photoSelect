package cn.asheng.photoselect.utils;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.asheng.photoselect.model.PhotoDirInfo;
import cn.asheng.photoselect.model.PhotoInfo;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PhotoUtils {
    /**
     * 获取所有图片包路径
     *
     * @return
     */
    public static void getPicDirList(Context context, PicdirListener picdirListener) {
        new GetPicDirListTask(context, picdirListener).execute();
    }

    //图片路径处理
    private static class GetPicDirListTask extends AsyncTask<Void, Void, ArrayList<PhotoDirInfo>> {
        PicdirListener listener;
        Context mContext;

        public GetPicDirListTask(Context context, PicdirListener picdirListener) {
            mContext = context;
            listener = picdirListener;
        }

        @Override
        protected void onPreExecute() {
            listener.onstar();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PhotoDirInfo> doInBackground(Void... params) {
            return taskGetPicDirList(mContext);
        }

        @Override
        protected void onPostExecute(ArrayList<PhotoDirInfo> photoDirInfoArrayList) {
            listener.onfinish(photoDirInfoArrayList);
            super.onPostExecute(photoDirInfoArrayList);
        }
    }

    private static ArrayList<PhotoDirInfo> taskGetPicDirList(Context context) {
        // 查找所有图片文件
        String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_ID, // 直接包含该图片文件的文件夹ID，防止在不同下的文件夹重名
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // 直接包含该图片文件的文件夹名
                MediaStore.Images.Media.DISPLAY_NAME, // 图片文件名
                MediaStore.Images.Media.DATA // 图片绝对路径
        };

        // 查找所有图片文件
        Cursor c = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null,
                null);
        HashMap<String, PhotoDirInfo> map = new HashMap<String, PhotoDirInfo>();

        while (c != null && c.moveToNext()) {
            String dirId = c.getString(1);
            if (map.containsKey(dirId)) {
                PhotoDirInfo pinfo = map.get(dirId);
                pinfo.setPicCount(pinfo.getPicCount() + 1);
            } else {
                PhotoDirInfo pinfo = new PhotoDirInfo();
                String picPath = c.getString(4);
                if (picPath != null && picPath.length() > 0) {
                    pinfo.setDirId(dirId);
                    pinfo.setFirstPicPath(picPath);
                    pinfo.setDirName(c.getString(2));
                    pinfo.setPicCount(1);
                    map.put(dirId, pinfo);
                }
            }
        }
        if (c != null) {// by Fooyou
            c.close();
        }
        ArrayList<PhotoDirInfo> photoDirInfoArrayList = new ArrayList<>();
        //将map转list
        if (map != null) {
            for (Map.Entry<String, PhotoDirInfo> entry : map.entrySet()) {
                photoDirInfoArrayList.add(entry.getValue());
            }
        }
        return photoDirInfoArrayList;
    }

    public interface PicdirListener {
        void onstar();

        void onfinish(ArrayList<PhotoDirInfo> photoDirInfoArrayList);
    }


    /**
     * 根据文件夹路径获取文件夹下面所有图片
     */
    public static void getPicList(Context context, String dirId, PicListener listener) {
        new GetPicListTask(context, listener, dirId).execute();
    }

    private static class GetPicListTask extends AsyncTask<Void, Void, ArrayList<PhotoInfo>> {
        PicListener listener;
        Context mContext;
        String mDirId;

        public GetPicListTask(Context context, PicListener picListener, String dirId) {
            mContext = context;
            listener = picListener;
            mDirId = dirId;
        }

        @Override
        protected void onPreExecute() {
            listener.onstar();
            super.onPreExecute();
        }

        @Override
        protected ArrayList<PhotoInfo> doInBackground(Void... params) {
            return taskGetPicList(mContext, mDirId);
        }

        @Override
        protected void onPostExecute(ArrayList<PhotoInfo> result) {
            listener.onfinish(result);
            super.onPostExecute(result);
        }
    }

    private static ArrayList<PhotoInfo> taskGetPicList(Context context, String mDirId) {
        ArrayList<PhotoInfo> list = new ArrayList<>();
        try {
            // 查找所有图片文件
            String[] projection = {MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME, // 图片文件名
                    MediaStore.Images.Media.DATA // 图片绝对路径
            };

            // 查找所有图片文件
            Cursor c = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection,
                    MediaStore.Images.Media.BUCKET_ID + "=" + mDirId, null, null);
            while (c.moveToNext()) {
                PhotoInfo info = new PhotoInfo();
                info.setPicId(c.getString(0));
                info.setPicName(c.getString(1));
                info.setPicPath(c.getString(2));
                list.add(info);
            }
        } catch (Exception e) {

        }
        return list;
    }

    public interface PicListener {
        void onstar();

        void onfinish(ArrayList<PhotoInfo> photoInfoArrayList);
    }
}
