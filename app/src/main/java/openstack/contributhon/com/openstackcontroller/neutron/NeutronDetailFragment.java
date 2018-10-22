package openstack.contributhon.com.openstackcontroller.neutron;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import openstack.contributhon.com.openstackcontroller.DetailAdapter;
import openstack.contributhon.com.openstackcontroller.IRestApi;
import openstack.contributhon.com.openstackcontroller.JsonConverter;
import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.nova.Model.FlavorVO;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.cCurrentFragmentId;
import static openstack.contributhon.com.openstackcontroller.Config.cDetailId;
import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class NeutronDetailFragment extends Fragment {

    ListView mListView;

    public static NeutronDetailFragment newInstance() {
        return new NeutronDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mListView = view.findViewById(R.id.view);
        final DetailAdapter mDetailAdapter = new DetailAdapter(getContext(), R.layout.row_detail);
        mListView.setAdapter( mDetailAdapter);

        String[] host = cHost.split(":");
        host[1] = "http:" + host[1] + ":9696";

        Retrofit nova = new Retrofit.Builder()
                .baseUrl(host[1])
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRestApi Interface = nova.create(IRestApi.class);

        Call<JsonConverter> novaCall;
        switch(cCurrentFragmentId) {
            case R.id.menu_network:
                novaCall = Interface.getNetworkDetail(cToken, cDetailId);
                novaCall.enqueue(new Callback<JsonConverter>() {
                    @Override
                    public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                        if (response.isSuccessful()) {
                            JsonConverter server = response.body();
                            NetworkVO vo = server.getDetailnetwork();
                            mDetailAdapter.add(Pair.create("Name", "Value"));
                            mDetailAdapter.add(Pair.create("name", vo.name));
                            mDetailAdapter.add(Pair.create("id", vo.id));
                            mDetailAdapter.add(Pair.create("project_id", vo.project_id));
                            mDetailAdapter.add(Pair.create("qos_policy_id", vo.qos_policy_id));
                            mDetailAdapter.add(Pair.create("provider", vo.provider));
                            mDetailAdapter.add(Pair.create("status", vo.status));
                            mDetailAdapter.add(Pair.create("dns_domain", vo.dns_domain));
                            mDetailAdapter.add(Pair.create("admin_state_up", vo.admin_state_up));
                            mDetailAdapter.add(Pair.create("ipv4_address_scope", vo.ipv4_address_scope));
                            mDetailAdapter.add(Pair.create("ipv6_address_scope", vo.ipv6_address_scope));
                            mDetailAdapter.add(Pair.create("l2_adjacency", vo.l2_adjacency));
                            mDetailAdapter.add(Pair.create("mtu", vo.mtu));
                            mDetailAdapter.add(Pair.create("port_security_enabled", vo.port_security_enabled));
                            mDetailAdapter.add(Pair.create("created_at", vo.created_at));
                            mDetailAdapter.add(Pair.create("updated_at", vo.updated_at));
                        } else
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonConverter> call, Throwable t) {
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.menu_router:
                novaCall = Interface.getRouterDetail(cToken, cDetailId);
                novaCall.enqueue(new Callback<JsonConverter>() {
                    @Override
                    public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                        if (response.isSuccessful()) {
                            JsonConverter server = response.body();
                            RouterVO vo = server.getDetailRouter();
                            mDetailAdapter.add(Pair.create("Name", "Value"));
                            mDetailAdapter.add(Pair.create("name", vo.name));
                            mDetailAdapter.add(Pair.create("status", vo.status));
                            mDetailAdapter.add(Pair.create("id", vo.id));
                            mDetailAdapter.add(Pair.create("flavor_id", vo.flavor_id));
                            mDetailAdapter.add(Pair.create("project_id", vo.project_id));
                            mDetailAdapter.add(Pair.create("tenant_id", vo.tenant_id));
                            mDetailAdapter.add(Pair.create("service_type_id", vo.service_type_id));
                            if(vo.routes.size() > 0) {
                                mDetailAdapter.add(Pair.create("routes:destination", vo.routes.get(0).destination));
                                mDetailAdapter.add(Pair.create("routes:nexthoop", vo.routes.get(0).nexthop));
                            }
                            mDetailAdapter.add(Pair.create("revision_number", vo.revision_number));
                            mDetailAdapter.add(Pair.create("ha", vo.ha));
                            mDetailAdapter.add(Pair.create("description", vo.description));
                            mDetailAdapter.add(Pair.create("created_at", vo.created_at));
                            mDetailAdapter.add(Pair.create("updated_at", vo.updated_at));
                        } else
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonConverter> call, Throwable t) {
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return view;
    }
}
