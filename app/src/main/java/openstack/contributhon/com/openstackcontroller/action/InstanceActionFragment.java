package openstack.contributhon.com.openstackcontroller.action;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import openstack.contributhon.com.openstackcontroller.MakeBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class InstanceActionFragment extends ActionFragment {
    private Button mStartBtn, mLockBtn;
    private boolean isStart;
    private int mBtnIdx = 0;
    private String mStatus;

    public static InstanceActionFragment newInstance() {
        return new InstanceActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        mActionHandler = new View.OnClickListener(){
            String actionEvent;
            RequestBody action = null;
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case 0:
                        if(isStart)
                            action = MakeBody.action("os-start");
                        else
                            action = MakeBody.action("os-stop");
                        break;
                    case 1:
                    case 2:
                    case 3:
                        action = MakeBody.action("suspend");
                        break;
                    case 4:
                        action = MakeBody.action("lock");
                        break;
                }

                Call<ResponseBody> novaCall = mInterface.action(cToken, cDetailId, action);
                novaCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Action Success", Toast.LENGTH_SHORT).show();
                            if (response != null)
                                mMyview.setText(response.message());
                            if(actionEvent.equals("lock")){
                                mLockBtn.setText("Unlock");
                            }
                        } else {
                            mMyview.setText(getError(mRetrofit, response.errorBody(), 1));
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(MY_TAG,t.toString());
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        mStartBtn = makeButton("Start", mBtnIdx++);
        Button Btn2 = makeButton("Reboot", mBtnIdx++);
        Button Btn3 = makeButton("Rebuild", mBtnIdx++);
        Button Btn4 = makeButton("Suspend", mBtnIdx++);
        Button Btn5 = makeButton("Lock", mBtnIdx++);
        return view;
    }

    public void update(String status){
        if (status.equals("SHUTOFF")) {
            mStartBtn.setText("Start");
            isStart = true;
        } else {
            mStartBtn.setText("Stop");
            isStart = false;
        }
        mStatusView.setText("Status : " + status);
    }
}
