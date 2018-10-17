package openstack.contributhon.com.openstackcontroller.nova;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import openstack.contributhon.com.openstackcontroller.R;

public class ServerFragment extends Fragment {

    private Float mElevation;

    public static ServerFragment newInstance() {
        return new ServerFragment();
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

        ServerTabAdapter pagerAdapter = new ServerTabAdapter(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        // Set TabSelectedListener
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
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
        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(mElevation);
        super.onDestroyView();
    }
}




