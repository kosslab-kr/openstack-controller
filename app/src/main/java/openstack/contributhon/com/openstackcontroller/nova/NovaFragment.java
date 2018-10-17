package openstack.contributhon.com.openstackcontroller.nova;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import openstack.contributhon.com.openstackcontroller.MainActivity;
import openstack.contributhon.com.openstackcontroller.NavigationActivity;
import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.glance.IGlance;
import openstack.contributhon.com.openstackcontroller.glance.ImageList;
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
import openstack.contributhon.com.openstackcontroller.neutron.INeutron;
import openstack.contributhon.com.openstackcontroller.neutron.NetworkList;
import openstack.contributhon.com.openstackcontroller.neutron.NetworkVO;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class NovaFragment extends Fragment {

    private ServerAdapter mAdapter;
    ListView mListView;
    AlertDialog mServerinfoDialog;
    AlertDialog mServerDialog;
    private INova mNovaInterface;
    private Retrofit mRetrofit;

    public static NovaFragment newInstance() {
        return new NovaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nova, null);

        FloatingActionButton fab = view.findViewById(R.id.fab_nova);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDialog();
            }
        });

        mListView = view.findViewById(R.id.nova_list);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mNovaInterface = mRetrofit.create(INova.class);

        getServerList();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cDetailId = mAdapter.getItem(position).id;
                ((NavigationActivity)getActivity()).replaceFragment(ServerFragment.newInstance(), true);
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

    private void addDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_serverinfo, null);
        Button addButton = view.findViewById(R.id.btnAdd);
        final EditText nameEdit = view.findViewById(R.id.name);
        final Spinner imageSpinner = view.findViewById(R.id.image);
        final Spinner flavorSpinner = view.findViewById(R.id.flavor);
        final Spinner networkSpinner = view.findViewById(R.id.network);

        IGlance glanceInterface = mRetrofit.create(IGlance.class);
        Call<ImageList> imageCall = glanceInterface.getImageList(cToken);
        imageCall.enqueue(new Callback<ImageList>() {
            @Override
            public void onResponse(Call<ImageList> call, Response<ImageList> response) {
                if (response.isSuccessful()) {
                    ImageList list = response.body();
                    imageSpinner.setAdapter(new ImageSpinnerAdapter(getContext(), list.getImages()));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ImageList> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<FlavorList> flavorCall = mNovaInterface.getFlavorList(cToken);
        flavorCall.enqueue(new Callback<FlavorList>() {
            @Override
            public void onResponse(Call<FlavorList> call, Response<FlavorList> response) {
                if (response.isSuccessful()) {
                    FlavorList list = response.body();
                    flavorSpinner.setAdapter(new FlavorSpinnerAdapter(getContext(), list.getFlavors()));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<FlavorList> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String[] host = cHost.split(":");
        host[1] = "http:" + host[1] + ":9696";
        Retrofit network = new Retrofit.Builder()
                .baseUrl(host[1])
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        INeutron neutronInterface = network.create(INeutron.class);
        Call<NetworkList> networkCall = neutronInterface.getNetworkList(cToken);
        networkCall.enqueue(new Callback<NetworkList>() {
            @Override
            public void onResponse(Call<NetworkList> call, Response<NetworkList> response) {
                if (response.isSuccessful()) {
                    NetworkList list = response.body();
                    networkSpinner.setAdapter(new NetworkSpinnerAdapter(getContext(), list.getNetworks()));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<NetworkList> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = mNovaInterface.createServer(cToken, Nova.createServer(nameEdit.getText().toString(), ((ImageVO) imageSpinner.getSelectedItem()).id, ((FlavorVO) flavorSpinner.getSelectedItem()).id, ((NetworkVO) networkSpinner.getSelectedItem()).id));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Create Success", Toast.LENGTH_SHORT).show();
                            getServerList();
                            mServerinfoDialog.dismiss();
                        } else {
                            ;
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(MY_TAG, t.getMessage());
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setView(view);
        mServerinfoDialog = builder.create();
        mServerinfoDialog.show();
    }

    private void itemDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_server, null);
        final TextView name = view.findViewById(R.id.server_name);
        name.setText(mAdapter.getItem(pos).name);
        final TextView update = view.findViewById(R.id.server_update);
        final TextView delete = view.findViewById(R.id.server_delete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mServerDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = mNovaInterface.deleteServer(cToken, mAdapter.getItem(pos).id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                            getServerList();
                            mServerDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e(MY_TAG, t.getMessage());
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        builder.setView(view);
        mServerDialog = builder.create();
        mServerDialog.show();
    }

    public void getServerList() {
        Call<ServerList> call = mNovaInterface.getServerList(cToken);
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
