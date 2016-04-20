package cn.asheng.photoselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import cn.asheng.photoselect.adapter.PhotoDirSelectAdapter;
import cn.asheng.photoselect.model.PhotoDirInfo;
import cn.asheng.photoselect.utils.PhotoUtils;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PhotoDirSelectActivity extends Activity implements AdapterView.OnItemClickListener{
    public static final String INTENT_PHOTO_SINGLE_PATH="intent_photo_single_path";
    public static final String INTENT_PHOTO_DIR_ID="intent_photo_dir_id";
    //图片目录
    private ArrayList<PhotoDirInfo> mPhotoDirList = new ArrayList<>();
    private ListView photoListview;
    private PhotoDirSelectAdapter mPhotoDirSelectAdapter;
    private ImageLoader imageLoader = ImageLoader.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_dir_select);
        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        initView();
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(PhotoDirSelectActivity.this, "无法检测本地相册,请重新插入SD卡", Toast.LENGTH_SHORT).show();
            return;
        }
        getPicDirList();
    }

    private void initView() {
        photoListview = (ListView) findViewById(R.id.photoListview);
        mPhotoDirSelectAdapter = new PhotoDirSelectAdapter(this, mPhotoDirList,imageLoader);
        photoListview.setAdapter(mPhotoDirSelectAdapter);
        photoListview.setOnItemClickListener(this);
    }
    private void getPicDirList() {
        PhotoUtils.getPicDirList(PhotoDirSelectActivity.this, new PhotoUtils.PicdirListener() {
            @Override
            public void onstar() {
//             显示dialog
            }

            @Override
            public void onfinish(ArrayList<PhotoDirInfo> photoDirInfoArrayList) {
//                隐藏dialog
                if (null != photoDirInfoArrayList && photoDirInfoArrayList.size() > 0) {
                    mPhotoDirList.clear();
                    mPhotoDirList.addAll(photoDirInfoArrayList);
                }
                mPhotoDirSelectAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(resultCode==MainActivity.RESULT_CODE_SELECT_PIC){
                setResult(MainActivity.RESULT_CODE_SELECT_PIC, data);
                finish();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PhotoDirInfo info = mPhotoDirList.get(position);
        Intent it = new Intent(this, PhotoSelectActivity.class);
        it.putExtra(INTENT_PHOTO_DIR_ID, info.getDirId());
        startActivityForResult(it, 0);
    }
}
