package cn.asheng.photoselect;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import cn.asheng.photoselect.utils.ViewUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RESULT_CODE_SELECT_PIC = 1;

    public static final int REQUEST_CODE_SELECT_PIC = 1;
    public static final int REQUEST_CODE_PHOTO_CUT_REQUEST_CODE = 2;

    private static final int PHOTO_CUT_SIZE = 160;

    private Button btnSelect;
    private ImageView imgPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initSet();
    }

    /**
     * 得到屏幕的宽度平存储，请自行优化
     */
    private void initSet() {
        int width = ViewUtil.getDisplayWidth(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(ViewUtil.KEY_WIDTH, width);
        editor.commit();
    }

    //打开截图界面
    private void startPhotoZoom(Uri uri, int size) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", true);

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", size);
        intent.putExtra("outputY", size);
        intent.putExtra("return-data", true);
        try {
            startActivityForResult(intent, REQUEST_CODE_PHOTO_CUT_REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置图片，如果是选择头像功能，可在这里完成上传头像操作
     */
    private void setImage(Bundle extras) {
        Bitmap bm = extras.getParcelable("data");
        imgPhoto.setImageBitmap(bm);
    }

    private void initView() {
        imgPhoto = (ImageView) findViewById(R.id.imgPhoto);
        btnSelect = (Button) findViewById(R.id.btnSelect);
        btnSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSelect) {
            Intent intent = new Intent(MainActivity.this, PhotoDirSelectActivity.class);
            startActivityForResult(intent, REQUEST_CODE_SELECT_PIC);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CODE_SELECT_PIC) {
            //打开截图界面
            try {
                startPhotoZoom(
                        Uri.fromFile(new File(data.getStringExtra(PhotoDirSelectActivity.INTENT_PHOTO_SINGLE_PATH))),
                        PHOTO_CUT_SIZE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_CODE_PHOTO_CUT_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                setImage(extras);
            }
        }
    }
}
