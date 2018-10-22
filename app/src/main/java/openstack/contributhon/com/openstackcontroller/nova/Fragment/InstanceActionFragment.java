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
import openstack.contributhon.com.openstackcontroller.MakeBody;
import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class InstanceActionFragment extends Fragment {
    private IRestApi novaInterface;
    private TextView myview;

    public static InstanceActionFragment newInstance() {
        return new InstanceActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);
        myview = view.findViewById(R.id.action_logo);
        Button Btn1 = view.findViewById(R.id.action_start);
        Button Btn2 = view.findViewById(R.id.action_reboot);
        Button Btn3 = view.findViewById(R.id.action_rebuild);
        Button Btn4 = view.findViewById(R.id.action_suspend);
        Button Btn5 = view.findViewById(R.id.action_lock);

        Btn1.setOnClickListener(mActionHandler);
        Btn2.setOnClickListener(mActionHandler);
        Btn3.setOnClickListener(mActionHandler);
        Btn4.setOnClickListener(mActionHandler);
        Btn5.setOnClickListener(mActionHandler);

        Retrofit nova = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        novaInterface = nova.create(IRestApi.class);
        refresh();
        return view;
    }

    View.OnClickListener mActionHandler = new View.OnClickListener(){
        String actionEvent;
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.action_start:
                case R.id.action_reboot:
                case R.id.action_rebuild:
                    actionEvent = "os-start";
                    break;
                case R.id.action_suspend:
                    actionEvent = "suspend";
                    break;
                case R.id.action_lock:
                    actionEvent = "lock";
                    break;
            }

            Call<Void> novaCall = novaInterface.action(cToken, cDetailId, MakeBody.action(actionEvent));
            novaCall.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(getContext(), "Action Success", Toast.LENGTH_SHORT).show();
                        if(response != null)
                            myview.setText(response.message());
                    } else {
                        if(response != null)
                            myview.setText(response.message());
                        Toast.makeText(getContext(), "Action Fail", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e(MY_TAG,t.toString());
                    Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    };

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
