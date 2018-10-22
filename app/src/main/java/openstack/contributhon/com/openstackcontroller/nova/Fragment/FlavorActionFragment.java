package openstack.contributhon.com.openstackcontroller.nova.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import openstack.contributhon.com.openstackcontroller.IRestApi;
import openstack.contributhon.com.openstackcontroller.JsonConverter;
import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cDetailId;
import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class FlavorActionFragment extends Fragment {
    private IRestApi novaInterface;
    private TextView myview;

    public static FlavorActionFragment newInstance() {
        return new FlavorActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);
        myview = view.findViewById(R.id.action_logo);

        Retrofit nova = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        novaInterface = nova.create(IRestApi.class);
        refresh();
        return view;
    }

    public void refresh(){
        Call<JsonConverter> novaCall = novaInterface.getServerDetail(cToken, cDetailId);
        novaCall.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter server = response.body();
                    ServerVO myServer = server.getDetailServer();
                    if(myServer.fault != null)
                        myview.setText(myServer.fault.message);
                } else {
                    if(response != null)
                        myview.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
                Log.e(MY_TAG,t.toString());
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
