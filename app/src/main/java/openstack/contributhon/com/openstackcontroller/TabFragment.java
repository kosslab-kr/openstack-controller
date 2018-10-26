package openstack.contributhon.com.openstackcontroller;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import static openstack.contributhon.com.openstackcontroller.Config.DETAIL;
import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cCurrentTab;

public class TabFragment extends Fragment {

    private Float mElevation;
    private TabAdapter mTabAdapter;

    public static TabFragment newInstance() {
        return new TabFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mElevation = ((AppCompatActivity)getActivity()).getSupportActionBar().getElevation();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
        View view = inflater.inflate(R.layout.fragment_serverdetail, container, false);
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setElevation(mElevation);
        final ViewPager viewPager = view.findViewById(R.id.main_viewpager);
        mTabAdapter = new TabAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mTabAdapter);
        cCurrentTab = DETAIL;
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                cCurrentTab = tab.getPosition();
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        mTabAdapter.close();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(mElevation);
        super.onDestroyView();
    }
}




