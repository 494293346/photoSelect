package cn.asheng.photoselect.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.asheng.photoselect.R;
import cn.asheng.photoselect.model.PhotoInfo;
import cn.asheng.photoselect.utils.AsycImageOptions;
import cn.asheng.photoselect.utils.ViewUtil;

/**
 * Created by Administrator on 2015/12/15.
 */
public class PhotoGridAdapter extends ArrayAdapter<PhotoInfo> {
    private LayoutInflater inflater = null;
    private DisplayImageOptions imageOptions;
    private ImageLoader imageLoader;
    private int picWidth;
    private Context mContext;

    public PhotoGridAdapter(Context context, List<PhotoInfo> objects, ImageLoader imageLoader) {
        super(context, R.layout.item_photo_grid, objects);
        mContext=context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageLoader = imageLoader;
        imageOptions = AsycImageOptions.getNoDiscCacheOptions();
        picWidth = (getDispalyWidth() - 5 * ViewUtil.dip2px(context, 5)) / 3;
    }

    /**
     * 得到存储在sp中的文件宽度，请自行优化
     */
    private int getDispalyWidth() {
        int width;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        width = preferences.getInt(ViewUtil.KEY_WIDTH, 480);
        return width;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final PhotoInfo info = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_photo_grid, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.photo_img);
            holder.image.setLayoutParams(new RelativeLayout.LayoutParams(picWidth, picWidth));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        imageLoader.displayImage("file://" + info.getPicPath(), holder.image, imageOptions);
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
    }
}