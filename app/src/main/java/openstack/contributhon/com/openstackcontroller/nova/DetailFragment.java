package openstack.contributhon.com.openstackcontroller.nova;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

import openstack.contributhon.com.openstackcontroller.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class DetailFragment extends Fragment {
    public static DetailFragment newInstance() {
        return new DetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        final TextView myview = view.findViewById(R.id.view);

        Retrofit nova = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INova novaInterface = nova.create(INova.class);
        Call<ServerList> novaCall = novaInterface.getServerDetail(cToken, cDetailId);
        novaCall.enqueue(new Callback<ServerList>() {
            @Override
            public void onResponse(Call<ServerList> call, Response<ServerList> response) {
                if (response.isSuccessful()) {
                    ServerList server = response.body();
                    ServerVO myServer = server.getDetailServer();
                    StringBuilder str = new StringBuilder();
                        for (Field field : myServer.getClass().getDeclaredFields()) {
                            try {
                                str.append(field.getName() + " : " + field.get(myServer) + "\n");
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    myview.setText(str);


            /*        myview.setText("name : " + myServer.name + "\n");
                    myview.setText("status : " + myServer.status + "\n");
                    myview.setText("power : " + myServer.power + "\n");
                    myview.setText("id : " + myServer.id + "\n");
                    myview.setText("address : " + myServer.address + "\n");
                    myview.setText("created : " + myServer.created + "\n");
                    myview.setText("updated : " + myServer.updated + "\n");
                    myview.setText("accessIPv4 : " + myServer.accessIPv4 + "\n");
                    myview.setText("accessIPv6 : " + myServer.accessIPv6 + "\n");
                    myview.setText("image : " + myServer.image + "\n");
                    myview.setText("user_id : " + myServer.user_id + "\n");
                    myview.setText("key_name : " + myServer.key_name + "\n");*/
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerList> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
