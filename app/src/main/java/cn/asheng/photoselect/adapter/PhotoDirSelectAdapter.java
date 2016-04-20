package cn.asheng.photoselect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.asheng.photoselect.R;
import cn.asheng.photoselect.model.PhotoDirInfo;
import cn.asheng.photoselect.utils.AsycImageOptions;

/**
 * Created by Administrator on 2016/4/19.
 */
public class PhotoDirSelectAdapter extends ArrayAdapter<PhotoDirInfo> {
    private LayoutInflater inflater = null;
    private DisplayImageOptions imageOptions;
    private ImageLoader imageLoader;

    public PhotoDirSelectAdapter(Context context, List<PhotoDirInfo> objects, ImageLoader imageLoader) {
        super(context, R.layout.item_photo_dir_select, objects);
        imageOptions = AsycImageOptions.getInstance().getNoDiscCacheOptions();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageLoader = imageLoader;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        PhotoDirInfo info = getItem(position);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_photo_dir_select, null);
            holder = new ViewHolder();

            holder.image = (ImageView) convertView.findViewById(R.id.pic_dir_img);
            holder.name = (TextView) convertView.findViewById(R.id.pic_dir_name);
            holder.count = (TextView) convertView.findViewById(R.id.pic_dir_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(info.getDirName());
        holder.count.setVisibility(View.VISIBLE);
        holder.count.setText("(" + info.getPicCount() + ")");
        imageLoader.displayImage("file://" + info.getFirstPicPath(), holder.image, imageOptions);
        return convertView;
    }

    static class ViewHolder {
        ImageView image;
        TextView name;
        TextView count;
    }
}
