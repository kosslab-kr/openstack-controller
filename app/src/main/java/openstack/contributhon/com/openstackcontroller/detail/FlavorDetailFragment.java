package openstack.contributhon.com.openstackcontroller.detail;

import android.support.v4.util.Pair;

import openstack.contributhon.com.openstackcontroller.action.FlavorActionFragment;
import openstack.contributhon.com.openstackcontroller.nova.Model.FlavorVO;

public class FlavorDetailFragment extends DetailFragment {

    public static FlavorDetailFragment newInstance() {
        return new FlavorDetailFragment();
    }

    public void setAll(FlavorVO flavor){
        mItemList.clear();
        mItemList.add(Pair.create("Name", "Value"));
        mItemList.add(Pair.create("name", flavor.name));
        mItemList.add(Pair.create("id", flavor.id));
        mItemList.add(Pair.create("swap", flavor.swap));
        mItemList.add(Pair.create("vcpus", flavor.vcpus));
        mItemList.add(Pair.create("rxtx_factor", flavor.rxtx_factor));
        mItemList.add(Pair.create("disk", flavor.disk));
        mItemList.add(Pair.create("description", flavor.description));
        mDetailAdapter.notifyDataSetChanged();
    }
}
