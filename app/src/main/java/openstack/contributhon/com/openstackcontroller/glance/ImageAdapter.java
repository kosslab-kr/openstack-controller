package openstack.contributhon.com.openstackcontroller.glance;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import openstack.contributhon.com.openstackcontroller.R;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;

public class ImageAdapter extends ArrayAdapter<ImageVO> {

    private static class ViewHolder {
        TextView hostText;
        TextView userText;
        TextView dateText;
    }

    public ImageAdapter(@NonNull Context context, ArrayList<ImageVO> datas) {
        super(context, 0, datas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_glance, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.hostText = convertView.findViewById(R.id.row_host1);
            viewHolder.userText = convertView.findViewById(R.id.row_user1);
            viewHolder.dateText = convertView.findViewById(R.id.row_date1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ImageVO vo = getItem(position);
        viewHolder.hostText.setText(vo.name);
        viewHolder.userText.setText(vo.disk_format);
        viewHolder.dateText.setText(vo.status);

        return convertView;
    }
}
