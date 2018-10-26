package openstack.contributhon.com.openstackcontroller.nova.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import okhttp3.ResponseBody;
import openstack.contributhon.com.openstackcontroller.IRestApi;
import openstack.contributhon.com.openstackcontroller.JsonConverter;
import openstack.contributhon.com.openstackcontroller.MakeBody;
import openstack.contributhon.com.openstackcontroller.MyList;
import openstack.contributhon.com.openstackcontroller.R;
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
import openstack.contributhon.com.openstackcontroller.neutron.NetworkVO;
import openstack.contributhon.com.openstackcontroller.nova.Model.KeypairVO;
import openstack.contributhon.com.openstackcontroller.nova.adapter.FlavorSpinnerAdapter;
import openstack.contributhon.com.openstackcontroller.nova.adapter.ImageSpinnerAdapter;
import openstack.contributhon.com.openstackcontroller.nova.Model.FlavorVO;
import openstack.contributhon.com.openstackcontroller.nova.adapter.KeypairSpinnerAdapter;
import openstack.contributhon.com.openstackcontroller.nova.adapter.NetworkSpinnerAdapter;
import openstack.contributhon.com.openstackcontroller.nova.adapter.ServerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class InstanceList extends MyList {

    private ServerAdapter mAdapter;
    private TimerTask tt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        tt = new TimerTask() {
            @Override
            public void run() {
                getList();
            }
        };
        Timer timer = new Timer();
        timer.schedule(tt, 3000, 3000);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public static InstanceList newInstance() {
        return new InstanceList();
    }

    public void addDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_addserver, null);
        Button addButton = view.findViewById(R.id.btnAdd);
        final EditText nameEdit = view.findViewById(R.id.name);
        final Spinner imageSpinner = view.findViewById(R.id.image);
        final Spinner flavorSpinner = view.findViewById(R.id.flavor);
        final Spinner networkSpinner = view.findViewById(R.id.network);
        final Spinner keypairSpinner = view.findViewById(R.id.keypair);

        IRestApi glanceInterface = mRetrofit.create(IRestApi.class);
        Call<JsonConverter> imageCall = glanceInterface.getImageList(cToken);
        imageCall.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter list = response.body();
                    imageSpinner.setAdapter(new ImageSpinnerAdapter(getContext(), list.getImages()));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<JsonConverter> flavorCall = mInterface.getFlavorList(cToken);
        flavorCall.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter list = response.body();
                    flavorSpinner.setAdapter(new FlavorSpinnerAdapter(getContext(), list.getFlavors()));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String[] host = cHost.split(":");
        host[1] = "http:" + host[1] + ":9696";
        Retrofit network = new Retrofit.Builder()
                .baseUrl(host[1])
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRestApi neutronInterface = network.create(IRestApi.class);
        Call<JsonConverter> networkCall = neutronInterface.getNetworkList(cToken);
        networkCall.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter list = response.body();
                    networkSpinner.setAdapter(new NetworkSpinnerAdapter(getContext(), list.getNetworks()));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<JsonConverter> keypairCall = mInterface.getKeypairList(cToken);
        keypairCall.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter list = response.body();

                    keypairSpinner.setAdapter(new KeypairSpinnerAdapter(getContext(), list.getKeypairs()));

                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseBody> call = mInterface.createServer(cToken, MakeBody.createServer(nameEdit.getText().toString(), ((ImageVO) imageSpinner.getSelectedItem()).id, ((FlavorVO) flavorSpinner.getSelectedItem()).id, ((NetworkVO) networkSpinner.getSelectedItem()).id, ((KeypairVO) keypairSpinner.getSelectedItem()).keypair.name));
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Create Success", Toast.LENGTH_SHORT).show();
                            getList();
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Create Fail : " + getError(mRetrofit, response.errorBody(), 0), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e(MY_TAG, t.getMessage());
                        Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setView(view);
        mDialog = builder.create();
        mDialog.show();
    }

    public void itemDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_longpopup, null);
        final TextView name = view.findViewById(R.id.name);
        name.setText(mAdapter.getItem(pos).name);
        final TextView edit = view.findViewById(R.id.edit);
        final TextView delete = view.findViewById(R.id.delete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = mInterface.deleteServer(cToken, mAdapter.getItem(pos).id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                            getList();
                            mDialog.dismiss();
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
        mDialog = builder.create();
        mDialog.show();
    }

    public void setPostiion(int position) {
        cDetailId = mAdapter.getItem(position).id;
    }

    public void getList() {
        Call<JsonConverter> call = mInterface.getServerList(cToken);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter list = response.body();
                    try {
                        mAdapter = new ServerAdapter(getContext(), list.getServers());
                    } catch (NullPointerException e) {
                        tt.cancel();
                    }
                    mListView.setAdapter(mAdapter);
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        tt.cancel();
        super.onDestroyView();
    }
}
