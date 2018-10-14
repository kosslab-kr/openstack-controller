package openstack.contributhon.com.openstackcontroller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;


import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class SessionAdapter extends RealmBaseAdapter<SessionVO> implements ListAdapter {

    private static class ViewHolder {
        TextView hostText;
        TextView userText;
        TextView dateText;
    }

    SessionAdapter(OrderedRealmCollection<SessionVO> realmResults) {
        super(realmResults);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.hostText = convertView.findViewById(R.id.row_host);
            viewHolder.userText = convertView.findViewById(R.id.row_user);
            viewHolder.dateText = convertView.findViewById(R.id.row_date);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(adapterData != null){
            final SessionVO vo = adapterData.get(position);
            viewHolder.hostText.setText(vo.host);
            viewHolder.userText.setText(vo.domain + "/" + vo.user);
            viewHolder.dateText.setText(vo.date);
        }

        return convertView;
    }
}
