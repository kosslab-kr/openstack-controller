package openstack.contributhon.com.openstackcontroller;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DetailAdapter extends ArrayAdapter<Pair<String, String>> {
    private class ViewHolder {
        TextView keyField;
        TextView valueField;
    }

    public DetailAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_detail, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.keyField = convertView.findViewById(R.id.value1);
            viewHolder.valueField = convertView.findViewById(R.id.value2);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Pair<String, String> vo = getItem(position);
        viewHolder.keyField.setText(vo.first);
        viewHolder.valueField.setText(vo.second);

        if(position == 0){
            viewHolder.keyField.setBackgroundColor(Color.DKGRAY);
            viewHolder.valueField.setBackgroundColor(Color.DKGRAY);
            viewHolder.keyField.setGravity(Gravity.CENTER);
            viewHolder.valueField.setGravity(Gravity.CENTER);
            viewHolder.keyField.setTypeface(null, Typeface.BOLD);
            viewHolder.valueField.setTypeface(null, Typeface.BOLD);
            viewHolder.keyField.setTextColor(Color.WHITE);
            viewHolder.valueField.setTextColor(Color.WHITE);
            viewHolder.keyField.setPadding(0,5,0,5);
            viewHolder.valueField.setPadding(0,5,0,5);
        } else if(position %2 == 1) {
            viewHolder.keyField.setBackgroundColor(Color.LTGRAY);
            viewHolder.valueField.setBackgroundColor(Color.LTGRAY);
        }else{
            viewHolder.keyField.setBackgroundColor(Color.WHITE);
            viewHolder.valueField.setBackgroundColor(Color.WHITE);
        }

        return convertView;
    }
}
