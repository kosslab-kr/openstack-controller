package openstack.contributhon.com.openstackcontroller.nova;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import openstack.contributhon.com.openstackcontroller.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;
import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class NovaFragment extends Fragment {

    private ServerAdapter mAdapter;
    ListView mListView;
    AlertDialog mServerDialog;
    private INova mNovaInterface;

    public static NovaFragment newInstance() {
        return new NovaFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova,null);
        mListView = view.findViewById(R.id.nova_list);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNovaInterface = retrofit.create(INova.class);

        getServerList();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mAdapter.getItem(position).isExpand = !mAdapter.getItem(position).isExpand;
                mAdapter.notifyDataSetChanged();
            }
        });

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int pos, long id) {
                itemDialog(pos);
                return true;
            }
        });

        return view;
    }

    private void itemDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_server, null);
        final TextView name = view.findViewById(R.id.server_name);
        name.setText(mAdapter.getItem(pos).name);
        final TextView run = view.findViewById(R.id.hidden_run);
        if(mAdapter.getItem(pos).status.equals("activity"))
            run.setText("Stop");
        final TextView edit = view.findViewById(R.id.server_edit);
        final TextView delete = view.findViewById(R.id.server_delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //userDialog(mAdapter.getItem(pos).id);
                mServerDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = mNovaInterface.deleteServer(cToken, mAdapter.getItem(pos).id);
                Log.e(MY_TAG,"id : " + mAdapter.getItem(pos).id);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                            getServerList();
                            mServerDialog.dismiss();
                        } else {
                            Log.e(MY_TAG,response.body().toString());
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(MY_TAG,t.getMessage());
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        builder.setView(view);
        mServerDialog = builder.create();
        mServerDialog.show();

    }

    public void getServerList(){
        Call<ServerList> call = mNovaInterface.getServerDetail(cToken);
        call.enqueue(new Callback<ServerList>() {
            @Override
            public void onResponse(Call<ServerList> call, Response<ServerList> response) {
                if (response.isSuccessful()) {
                    ServerList list = response.body();
                    mAdapter = new ServerAdapter(getContext(), list.getServers());
                    mListView.setAdapter(mAdapter);
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerList> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
