package openstack.contributhon.com.openstackcontroller.glance;

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
import openstack.contributhon.com.openstackcontroller.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cDetailId;
import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class ActionFragment extends Fragment {
    private IRestApi glanceInterface;
    private TextView myview;
    private Button activeBtn;
    private boolean isActive = false;

    public static ActionFragment newInstance() {
        return new ActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_glance_action, container, false);
        myview = view.findViewById(R.id.action_logo);
        activeBtn = view.findViewById(R.id.action_activate);
        activeBtn.setOnClickListener(mActionHandler);

        Retrofit glance = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        glanceInterface = glance.create(IRestApi.class);
        refresh();
        return view;
    }

    View.OnClickListener mActionHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.action_activate:
                    Call<Void> glanceCall;
                    if (isActive)
                        glanceCall = glanceInterface.deactivate(cToken, cDetailId);
                    else
                        glanceCall = glanceInterface.reactivate(cToken, cDetailId);
                    glanceCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Action Success", Toast.LENGTH_SHORT).show();
                                if (response != null)
                                    myview.setText(response.message());
                            } else {
                                Toast.makeText(getContext(), "Action Fail", Toast.LENGTH_SHORT).show();
                                if (response != null)
                                    myview.setText(response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Log.e(MY_TAG, t.toString());
                            Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    break;
            }
        }
    };

    public void refresh() {
        Call<ImageVO> glanceCall = glanceInterface.getImageDetail(cToken, cDetailId);
        glanceCall.enqueue(new Callback<ImageVO>() {
            @Override
            public void onResponse(Call<ImageVO> call, Response<ImageVO> response) {
                if (response.isSuccessful()) {
                    ImageVO myImage = response.body();
                    if (myImage.status.equals("active")) {
                        activeBtn.setText("Deactivate");
                        isActive = true;
                    } else {
                        activeBtn.setText("Activate");
                        isActive = false;
                    }
                } else {
                    if (response != null)
                        myview.setText(response.message());
                }
            }

            @Override
            public void onFailure(Call<ImageVO> call, Throwable t) {
                Log.e(MY_TAG, t.toString());
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
