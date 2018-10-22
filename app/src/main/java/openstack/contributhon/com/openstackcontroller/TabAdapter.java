package openstack.contributhon.com.openstackcontroller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import openstack.contributhon.com.openstackcontroller.glance.GlanceDetailFragment;
import openstack.contributhon.com.openstackcontroller.neutron.NeutronDetailFragment;
import openstack.contributhon.com.openstackcontroller.nova.Fragment.NovaDetailFragment;
import openstack.contributhon.com.openstackcontroller.nova.Fragment.InstanceActionFragment;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cCurrentFragmentId;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public TabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                switch(cCurrentFragmentId) {
                    case R.id.menu_instance:
                    case R.id.menu_flavor:
                    case R.id.menu_keypair:
                        return NovaDetailFragment.newInstance();
                    case R.id.menu_image:
                        return GlanceDetailFragment.newInstance();
                    case R.id.menu_network:
                    case R.id.menu_router:
                        return NeutronDetailFragment.newInstance();
                }
                return NovaDetailFragment.newInstance();
            case 1:
                switch(cCurrentFragmentId) {
                    case R.id.menu_instance:
                    case R.id.menu_flavor:
                    case R.id.menu_keypair:
                    case R.id.menu_image:
                    case R.id.menu_network:
                    case R.id.menu_router:
                        return InstanceActionFragment.newInstance();
                  /*  case R.id.menu_instance:
                        return InstanceActionFragment.newInstance();
                    case R.id.menu_flavor:
                        return FlavorActionFragment.newInstance();
                    case R.id.menu_keypair:
                        return FlavorActionFragment.newInstance();*/
                }
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
