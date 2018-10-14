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
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;

public class NetworkAdapter extends ArrayAdapter<NetworkVO> {

    private static class ViewHolder {
        TextView hostText;
        TextView userText;
        TextView dateText;
    }

    public NetworkAdapter(@NonNull Context context, ArrayList<NetworkVO> datas) {
        super(context, 0, datas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_neutron, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.hostText = convertView.findViewById(R.id.row__neutron_host);
            viewHolder.userText = convertView.findViewById(R.id.row__neutron_user);
            viewHolder.dateText = convertView.findViewById(R.id.row__neutron_date);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        NetworkVO vo = getItem(position);
        viewHolder.hostText.setText(vo.name);
        viewHolder.userText.setText(vo.provider);
        viewHolder.dateText.setText(vo.status);

        return convertView;
    }
}
