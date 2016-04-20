package cn.asheng.photoselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import cn.asheng.photoselect.adapter.PhotoGridAdapter;
import cn.asheng.photoselect.model.PhotoInfo;
import cn.asheng.photoselect.utils.PhotoUtils;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PhotoSelectActivity extends Activity implements AdapterView.OnItemClickListener{
    //图片
    private String dirId;
    private PhotoGridAdapter mPhotoGridAdapter;
    private GridView mGridview;
    private ArrayList<PhotoInfo> mPhotoList = new ArrayList<>();
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        initView();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(PhotoSelectActivity.this, "无法检测本地相册,请重新插入SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        dirId=getIntent().getStringExtra(PhotoDirSelectActivity.INTENT_PHOTO_DIR_ID);
        getPicList();
    }

    private void initView() {
        mGridview = (GridView) findViewById(R.id.photo_grid);
        mPhotoGridAdapter = new PhotoGridAdapter(this, mPhotoList, imageLoader);
        mGridview.setAdapter(mPhotoGridAdapter);
        mGridview.setOnItemClickListener(this);
    }

    private void getPicList() {
        PhotoUtils.getPicList(PhotoSelectActivity.this, dirId, new PhotoUtils.PicListener() {
            @Override
            public void onstar() {
            //显示dialog
            }

            @Override
            public void onfinish(ArrayList<PhotoInfo> photoInfoArrayList) {
                //隐藏dialog
                if (null != photoInfoArrayList) {
                    mPhotoList.clear();
                    mPhotoList.addAll(photoInfoArrayList);
                }
                mPhotoGridAdapter.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoInfo info = mPhotoList.get(position);
        Intent it = new Intent();
        it.putExtra(PhotoDirSelectActivity.INTENT_PHOTO_SINGLE_PATH, info.getPicPath());
        setResult(MainActivity.RESULT_CODE_SELECT_PIC, it);
        finish();
    }
}
