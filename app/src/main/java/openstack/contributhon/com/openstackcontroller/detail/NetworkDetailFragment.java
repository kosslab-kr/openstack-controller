package openstack.contributhon.com.openstackcontroller.detail;

import android.support.v4.util.Pair;

import openstack.contributhon.com.openstackcontroller.action.FlavorActionFragment;
import openstack.contributhon.com.openstackcontroller.neutron.NetworkVO;

public class NetworkDetailFragment extends DetailFragment {

    public static NetworkDetailFragment newInstance() {
        return new NetworkDetailFragment();
    }

    public void setAll(NetworkVO network){
        mItemList.clear();
        mItemList.add(Pair.create("Name", "Value"));
        mItemList.add(Pair.create("name", network.name));
        mItemList.add(Pair.create("id", network.id));
        mItemList.add(Pair.create("project_id", network.project_id));
        mItemList.add(Pair.create("qos_policy_id", network.qos_policy_id));
        mItemList.add(Pair.create("provider", network.provider));
        mItemList.add(Pair.create("status", network.status));
        mItemList.add(Pair.create("dns_domain", network.dns_domain));
        mItemList.add(Pair.create("admin_state_up", network.admin_state_up));
        mItemList.add(Pair.create("ipv4_address_scope", network.ipv4_address_scope));
        mItemList.add(Pair.create("ipv6_address_scope", network.ipv6_address_scope));
        mItemList.add(Pair.create("l2_adjacency", network.l2_adjacency));
        mItemList.add(Pair.create("mtu", network.mtu));
        mItemList.add(Pair.create("port_security_enabled", network.port_security_enabled));
        mItemList.add(Pair.create("created_at", network.created_at));
        mItemList.add(Pair.create("updated_at", network.updated_at));
        mDetailAdapter.notifyDataSetChanged();
    }
}
