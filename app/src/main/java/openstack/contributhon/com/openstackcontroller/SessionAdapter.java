package openstack.contributhon.com.openstackcontroller;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

public class SessionAdapter extends RealmBaseAdapter<SessionVO> implements ListAdapter {

    static SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");

    private static class ViewHolder {
        TextView nameText;
        TextView hostText;
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
            viewHolder.nameText = convertView.findViewById(R.id.row_title);
            viewHolder.hostText = convertView.findViewById(R.id.row_below);
            viewHolder.dateText = convertView.findViewById(R.id.row_option);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(adapterData != null){
            final SessionVO vo = adapterData.get(position);
            viewHolder.nameText.setText(vo.name);
            viewHolder.hostText.setText(vo.address);
            if(vo.date != null)
                viewHolder.dateText.setText("Last accessed : " + sdf.format(vo.date));
        }

        return convertView;
    }
}
