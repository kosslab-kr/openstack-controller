package openstack.contributhon.com.openstackcontroller.detail;

import android.support.v4.util.Pair;

import openstack.contributhon.com.openstackcontroller.action.FlavorActionFragment;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;

public class InstanceDetailFragment extends DetailFragment {

    public static InstanceDetailFragment newInstance() {
        return new InstanceDetailFragment();
    }

    public void setAll(ServerVO server){
        mItemList.clear();
        mItemList.add(Pair.create("Name", "Value"));
        mItemList.add(Pair.create("name", server.name));
        mItemList.add(Pair.create("id", server.id));
        mItemList.add(Pair.create("address", server.address));
        mItemList.add(Pair.create("IPv4", server.accessIPv4));
        mItemList.add(Pair.create("IPv6", server.accessIPv6));
        mItemList.add(Pair.create("key_name", server.key_name));
        mItemList.add(Pair.create("status", server.status));
        mItemList.add(Pair.create("created", server.created));
        mItemList.add(Pair.create("updated", server.updated));
        mItemList.add(Pair.create("user_id", server.user_id));
        mItemList.add(Pair.create("power", server.power));
        mDetailAdapter.notifyDataSetChanged();
    }
}
