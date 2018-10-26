package openstack.contributhon.com.openstackcontroller.nova;

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
import openstack.contributhon.com.openstackcontroller.nova.Model.Keypair;
import openstack.contributhon.com.openstackcontroller.nova.Model.KeypairVO;

public class KeypairAdapter extends ArrayAdapter<KeypairVO> {

    private static class ViewHolder {
        TextView hostText;
        TextView userText;
      //  TextView userText2;
    }

    public KeypairAdapter(@NonNull Context context, ArrayList<KeypairVO> datas) {
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
            //viewHolder.userText2 = convertView.findViewById(R.id.row_nova_status);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Keypair vo = getItem(position).keypair;
        viewHolder.hostText.setText(vo.name);
        viewHolder.userText.setText(vo.fingerprint);
        //viewHolder.userText2.setText(vo.type);
        return convertView;
    }
}
