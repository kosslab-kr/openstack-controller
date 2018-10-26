package openstack.contributhon.com.openstackcontroller.action;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cDetailId;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class ImageActionFragment extends ActionFragment {
    private Button activeBtn;
    private boolean isActive = false;
    public static ImageActionFragment newInstance() {
        return new ImageActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mActionHandler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case 0:
                        Call<ResponseBody> glanceCall;
                        if (isActive)
                            glanceCall = mInterface.deactivate(cToken, cDetailId);
                        else
                            glanceCall = mInterface.reactivate(cToken, cDetailId);
                        glanceCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                try {
                                    if (response.isSuccessful()) {
                                        Toast.makeText(getContext(), "Action Success", Toast.LENGTH_SHORT).show();
                                        mMyview.setText("Success");
                                        if (activeBtn.getText().equals("Activate")) {
                                            activeBtn.setText("Deactivate");
                                        } else {
                                            activeBtn.setText("Activate");
                                        }
                                    } else {
                                        mMyview.setText(response.errorBody().string());
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Log.e(MY_TAG, t.toString());
                                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                        break;
                }
            }
        };

        activeBtn = makeButton("Activate",0);

        return view;
    }

    public void update(String status) {
        if (status.equals("active")) {
            activeBtn.setText("Deactivate");
            isActive = true;
        } else {
            activeBtn.setText("Activate");
            isActive = false;
        }
        mStatusView.setText("Status : " + status);
    }
}
