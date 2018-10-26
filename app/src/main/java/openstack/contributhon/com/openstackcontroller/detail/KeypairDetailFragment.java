package openstack.contributhon.com.openstackcontroller.detail;

import android.support.v4.util.Pair;

import openstack.contributhon.com.openstackcontroller.action.FlavorActionFragment;
import openstack.contributhon.com.openstackcontroller.nova.Model.Keypair;
public class KeypairDetailFragment extends DetailFragment {

    public static KeypairDetailFragment newInstance() {
        return new KeypairDetailFragment();
    }


    public void setAll(Keypair keypair){
        mItemList.clear();
        mItemList.add(Pair.create("Name", "Value"));
        mItemList.add(Pair.create("name", keypair.name));
        mItemList.add(Pair.create("fingerprint", keypair.fingerprint));
        mItemList.add(Pair.create("type", keypair.type));
        mItemList.add(Pair.create("public_key", keypair.public_key));
        mItemList.add(Pair.create("user_id", keypair.user_id));
        mItemList.add(Pair.create("deleted", keypair.deleted));
        mItemList.add(Pair.create("created_at", keypair.created_at));
        mItemList.add(Pair.create("updated_at", keypair.updated_at));
        mItemList.add(Pair.create("deleted_at", keypair.deleted_at));
        mItemList.add(Pair.create("id", keypair.id));
        mDetailAdapter.notifyDataSetChanged();
    }
}
