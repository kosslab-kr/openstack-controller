package openstack.contributhon.com.openstackcontroller;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import openstack.contributhon.com.openstackcontroller.glance.ImageList;
import openstack.contributhon.com.openstackcontroller.neutron.NetworkList;
import openstack.contributhon.com.openstackcontroller.neutron.RouterList;
import openstack.contributhon.com.openstackcontroller.nova.Fragment.KeypairList;
import openstack.contributhon.com.openstackcontroller.nova.Fragment.FlavorList;
import openstack.contributhon.com.openstackcontroller.nova.Fragment.InstanceList;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class NavigationActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getSupportActionBar().setTitle("Instance");
        cCurrentFragmentId = R.id.menu_instance;
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        drawer = (DrawerLayout) inflater.inflate(R.layout.navigation_drawer, null);

        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);

        FrameLayout container = drawer.findViewById(R.id.content_frame);
        container.addView(child);
        decor.addView(drawer);

        NavigationView navigationView = findViewById(R.id.main_drawer_view);
        View view = navigationView.getHeaderView(0);

        ((TextView)view.findViewById(R.id.header_host)).setText(cHost);
        ((TextView)view.findViewById(R.id.header_user)).setText(cUser);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.drawer_layout, InstanceList.newInstance()).commit();

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                cCurrentFragmentId = item.getItemId();
                cIsDetail = false;
                replaceFragment();
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    public void replaceFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = null;

        if(cIsDetail){
            fragment = TabFragment.newInstance();
        }else {
            switch (cCurrentFragmentId) {
                case R.id.menu_instance:
                    getSupportActionBar().setTitle("Instance");
                    fragment = InstanceList.newInstance();
                    break;
                case R.id.menu_flavor:
                    getSupportActionBar().setTitle("Flavor");
                    fragment = FlavorList.newInstance();
                    break;
                case R.id.menu_keypair:
                    getSupportActionBar().setTitle("Keypair");
                    fragment = KeypairList.newInstance();
                    break;
                case R.id.menu_image:
                    getSupportActionBar().setTitle("Image");
                    fragment = ImageList.newInstance();
                    break;
                case R.id.menu_network:
                    getSupportActionBar().setTitle("Network");
                    fragment = NetworkList.newInstance();
                    break;
                case R.id.menu_router:
                    getSupportActionBar().setTitle("Router");
                    fragment = RouterList.newInstance();
                    break;
            }
        }

        fragmentTransaction.replace(R.id.drawer_layout, fragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return false;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (cIsDetail){
            cIsDetail = false;
            replaceFragment();
        }
        else {
            super.onBackPressed();
        }
    }
}
