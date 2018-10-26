package openstack.contributhon.com.openstackcontroller.detail;

import android.support.v4.util.Pair;

import openstack.contributhon.com.openstackcontroller.action.FlavorActionFragment;
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
public class ImageDetailFragment extends DetailFragment {

    public static ImageDetailFragment newInstance() {
        return new ImageDetailFragment();
    }

    public void setAll(ImageVO image){
        mItemList.clear();
        mItemList.add(Pair.create("Name","Value"));
        mItemList.add(Pair.create("name",image.name));
        mItemList.add(Pair.create("id",image.id));
        mItemList.add(Pair.create("disk_format",image.disk_format));
        mItemList.add(Pair.create("container_format",image.container_format));
        mItemList.add(Pair.create("status",image.status));
        mItemList.add(Pair.create("created_at",image.created_at));
        mItemList.add(Pair.create("updated_at",image.updated_at));
        mItemList.add(Pair.create("os_hidden",image.os_hidden));
        mItemList.add(Pair.create("size",image.size));
        mItemList.add(Pair.create("min_disk",image.min_disk));
        mItemList.add(Pair.create("visibility",image.visibility));
        mDetailAdapter.notifyDataSetChanged();
    }


}
