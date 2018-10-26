package openstack.contributhon.com.openstackcontroller.nova.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;

public class ServerAdapter extends ArrayAdapter<ServerVO> {

    private static class ViewHolder {
        TextView hostText;
        TextView userText;
        TextView dateText;
    }

    public ServerAdapter(@NonNull Context context, ArrayList<ServerVO> datas) {
        super(context, 0, datas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.hostText = convertView.findViewById(R.id.row_title);
            viewHolder.userText = convertView.findViewById(R.id.row_below);
            viewHolder.dateText = convertView.findViewById(R.id.row_option);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ServerVO vo = getItem(position);
        viewHolder.hostText.setText(vo.name);
        viewHolder.userText.setText(vo.power);
        viewHolder.dateText.setText(vo.status);
        return convertView;
    }
}
