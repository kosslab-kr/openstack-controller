package openstack.contributhon.com.openstackcontroller.neutron;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.glance.IGlance;
import openstack.contributhon.com.openstackcontroller.glance.ImageAdapter;
import openstack.contributhon.com.openstackcontroller.glance.ImageList;
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class NeutronFragment extends Fragment {

    private NetworkAdapter mAdapter;
    ListView mListView;

    public static NeutronFragment newInstance() {
        return new NeutronFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_neutron,null);
        mListView = view.findViewById(R.id.neutron_list);

        getNetworkList();

        return view;
    }

    public void getNetworkList(){
        String[] host = cHost.split(":");
        host[1] = "http:" + host[1] + ":9696";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(host[1])
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INeutron neutron = retrofit.create(INeutron.class);
        Call<NetworkList> call = neutron.getNetworkList(cToken);
        call.enqueue(new Callback<NetworkList>() {
            @Override
            public void onResponse(Call<NetworkList> call, Response<NetworkList> response) {
                if (response.isSuccessful()) {
                    NetworkList list = response.body();
                    for(NetworkVO e : list.getNetworks())
                        Log.e(MY_TAG,"test : " + e.name);
                    mAdapter = new NetworkAdapter(getContext(), list.getNetworks());
                    mListView.setAdapter(mAdapter);
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NetworkList> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
