package openstack.contributhon.com.openstackcontroller.nova;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;

public class ServerTabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;

    public ServerTabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DetailFragment.newInstance();
            case 1:
                return ActionFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
