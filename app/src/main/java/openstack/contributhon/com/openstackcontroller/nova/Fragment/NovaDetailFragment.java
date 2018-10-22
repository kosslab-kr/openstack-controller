package openstack.contributhon.com.openstackcontroller.nova.Fragment;

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
import openstack.contributhon.com.openstackcontroller.*;
import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.nova.Model.Keypair;
import openstack.contributhon.com.openstackcontroller.nova.Model.FlavorVO;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static openstack.contributhon.com.openstackcontroller.Config.*;

public class NovaDetailFragment extends Fragment {

    ListView mListView;

    public static NovaDetailFragment newInstance() {
        return new NovaDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mListView = view.findViewById(R.id.view);
        final DetailAdapter mDetailAdapter = new DetailAdapter(getContext(), R.layout.row_detail);
        mListView.setAdapter( mDetailAdapter);

        Retrofit nova = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRestApi novaInterface = nova.create(IRestApi.class);

        Call<JsonConverter> novaCall;
        switch(cCurrentFragmentId) {
            case R.id.menu_instance:
                novaCall = novaInterface.getServerDetail(cToken, cDetailId);
                novaCall.enqueue(new Callback<JsonConverter>() {
                    @Override
                    public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                        if (response.isSuccessful()) {
                            JsonConverter server = response.body();
                            ServerVO myServer = server.getDetailServer();
                            mDetailAdapter.add(Pair.create("Name", "Value"));
                            mDetailAdapter.add(Pair.create("name", myServer.name));
                            mDetailAdapter.add(Pair.create("id", myServer.id));
                            mDetailAdapter.add(Pair.create("address", myServer.address));
                            mDetailAdapter.add(Pair.create("IPv4", myServer.accessIPv4));
                            mDetailAdapter.add(Pair.create("IPv6", myServer.accessIPv6));
                            mDetailAdapter.add(Pair.create("key_name", myServer.key_name));
                            mDetailAdapter.add(Pair.create("status", myServer.status));
                            mDetailAdapter.add(Pair.create("created", myServer.created));
                            mDetailAdapter.add(Pair.create("updated", myServer.updated));
                            mDetailAdapter.add(Pair.create("user_id", myServer.user_id));
                            mDetailAdapter.add(Pair.create("power", myServer.power));
                        } else
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonConverter> call, Throwable t) {
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.menu_flavor:
                novaCall = novaInterface.getFlavorDetail(cToken, cDetailId);
                novaCall.enqueue(new Callback<JsonConverter>() {
                    @Override
                    public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                        if (response.isSuccessful()) {
                            JsonConverter server = response.body();
                            FlavorVO myServer = server.getDetailFlavor();
                            mDetailAdapter.add(Pair.create("Name", "Value"));
                            mDetailAdapter.add(Pair.create("name", myServer.name));
                            mDetailAdapter.add(Pair.create("id", myServer.id));
                            mDetailAdapter.add(Pair.create("swap", myServer.swap));
                            mDetailAdapter.add(Pair.create("vcpus", myServer.vcpus));
                            mDetailAdapter.add(Pair.create("rxtx_factor", myServer.rxtx_factor));
                            mDetailAdapter.add(Pair.create("disk", myServer.disk));
                            mDetailAdapter.add(Pair.create("description", myServer.description));
                        } else
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<JsonConverter> call, Throwable t) {
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.menu_keypair:
                novaCall = novaInterface.getKeypairDetail(cToken, cDetailId);
                novaCall.enqueue(new Callback<JsonConverter>() {
                    @Override
                    public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                        if (response.isSuccessful()) {
                            JsonConverter server = response.body();
                            Keypair myServer = server.getDetailKeypair();
                            mDetailAdapter.add(Pair.create("Name", "Value"));
                            mDetailAdapter.add(Pair.create("name", myServer.name));
                            mDetailAdapter.add(Pair.create("fingerprint", myServer.fingerprint));
                            mDetailAdapter.add(Pair.create("type", myServer.type));
                            mDetailAdapter.add(Pair.create("public_key", myServer.public_key));
                            mDetailAdapter.add(Pair.create("user_id", myServer.user_id));
                            mDetailAdapter.add(Pair.create("deleted", myServer.deleted));
                            mDetailAdapter.add(Pair.create("created_at", myServer.created_at));
                            mDetailAdapter.add(Pair.create("updated_at", myServer.updated_at));
                            mDetailAdapter.add(Pair.create("deleted_at", myServer.deleted_at));
                            mDetailAdapter.add(Pair.create("id", myServer.id));
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
