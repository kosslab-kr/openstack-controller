package openstack.contributhon.com.openstackcontroller.action;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import openstack.contributhon.com.openstackcontroller.IRestApi;
import openstack.contributhon.com.openstackcontroller.R;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.cHost;

public class ActionFragment extends Fragment {

    protected IRestApi mInterface;
    protected TextView mMyview;
    protected Retrofit mRetrofit;
    protected LinearLayout mContainer;
    protected View.OnClickListener mActionHandler;
    private ViewGroup.LayoutParams mLayoutParams;
    protected TextView mStatusView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_action, container, false);
        mLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        mMyview = view.findViewById(R.id.action_logo);
        mContainer = view.findViewById(R.id.container);
        mStatusView = view.findViewById(R.id.action_status);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mInterface = retrofit.create(IRestApi.class);
        return view;
    }

    public Button makeButton(String text, int id){
        Button t = new Button(getContext());
        t.setLayoutParams(mLayoutParams);
        t.setBackgroundResource(R.drawable.back);
        t.setText(text);
        t.setId(id);
        t.setOnClickListener(mActionHandler);
        mContainer.addView(t);
        return t;
    }
}
