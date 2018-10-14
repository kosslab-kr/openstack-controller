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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import openstack.contributhon.com.openstackcontroller.glance.GlanceFragment;
import openstack.contributhon.com.openstackcontroller.neutron.NeutronFragment;
import openstack.contributhon.com.openstackcontroller.nova.NovaFragment;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class NavigationActivity extends AppCompatActivity {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        getSupportActionBar().setTitle("Instance");

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
        fragmentTransaction.add(R.id.drawer_layout, NovaFragment.newInstance()).commit();

        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.menu_server:
                        getSupportActionBar().setTitle("Instance");
                        replaceFragment(NovaFragment.newInstance());
                        break;
                    case R.id.menu_image:
                        getSupportActionBar().setTitle("Image");
                        replaceFragment(GlanceFragment.newInstance());
                        break;
                    case R.id.menu_network:
                        getSupportActionBar().setTitle("Network");
                        replaceFragment(NeutronFragment.newInstance());
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
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
        } else {
            super.onBackPressed();
        }
    }
}
