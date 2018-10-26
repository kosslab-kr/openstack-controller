package openstack.contributhon.com.openstackcontroller.neutron;

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

public class RouterAdapter extends ArrayAdapter<RouterVO> {

    private static class ViewHolder {
        TextView hostText;
        TextView userText;
        TextView dateText;
    }

    public RouterAdapter(@NonNull Context context, ArrayList<RouterVO> datas) {
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
        RouterVO vo = getItem(position);
        viewHolder.hostText.setText(vo.name);
        if(vo.routes.size()>0)
            viewHolder.userText.setText(vo.routes.get(0).destination);
        viewHolder.dateText.setText(vo.status);

        return convertView;
    }
}
