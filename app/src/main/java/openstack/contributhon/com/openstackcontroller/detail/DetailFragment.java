package openstack.contributhon.com.openstackcontroller.detail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import openstack.contributhon.com.openstackcontroller.DetailAdapter;
import openstack.contributhon.com.openstackcontroller.R;

public class DetailFragment extends Fragment {
    protected ListView mListView;
    protected DetailAdapter mDetailAdapter;
    protected ArrayList<Pair<String, String>> mItemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mItemList = new ArrayList<>();
        mListView = view.findViewById(R.id.view);
        mItemList.add(Pair.create("Name", "Value"));
        mDetailAdapter = new DetailAdapter(getContext(), R.layout.row_detail, mItemList);
        mListView.setAdapter( mDetailAdapter);
        return view;
    }


}
