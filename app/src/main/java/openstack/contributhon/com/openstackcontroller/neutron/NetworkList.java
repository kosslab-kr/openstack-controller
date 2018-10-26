package openstack.contributhon.com.openstackcontroller.neutron;

import android.os.Bundle;
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
import openstack.contributhon.com.openstackcontroller.JsonConverter;
import openstack.contributhon.com.openstackcontroller.MakeBody;
import openstack.contributhon.com.openstackcontroller.MyList;
import openstack.contributhon.com.openstackcontroller.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class NetworkList extends MyList {

    private NetworkAdapter mAdapter;

    public static NetworkList newInstance() {
        return new NetworkList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        String[] host = cHost.split(":");
        host[1] = "http:" + host[1] + ":9696";
        mHost = host[1];
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void addDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getLayoutInflater().inflate(R.layout.dialog_addnetwork, null);
        Button addButton = view.findViewById(R.id.btnAdd);
        final EditText nameEdit = view.findViewById(R.id.name);
        final Spinner adminSpinner = view.findViewById(R.id.admin_state_up);
        final EditText mtuedit = view.findViewById(R.id.mtu);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Void> call = mInterface.createNetwork(cToken, MakeBody.createNetwork(nameEdit.getText().toString(), adminSpinner.getSelectedItem().toString(), mtuedit.getText().toString()));
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Create Success", Toast.LENGTH_SHORT).show();
                            getList();
                            mDialog.dismiss();
                        } else {
                            Toast.makeText(getContext(), "Create Fail : " + response.code(), Toast.LENGTH_SHORT).show();
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
                Call<Void> call = mInterface.deleteNetwork(cToken, mAdapter.getItem(pos).id);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Delete Success", Toast.LENGTH_SHORT).show();
                            getList();
                            mDialog.dismiss();
                        } else {
                            //Toast.makeText(getContext(), ErrorCode.gete(response.code()), Toast.LENGTH_SHORT).show();
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

    public void setPostiion(int position){
        cDetailId = mAdapter.getItem(position).id;
    }

    @Override
    public void getList(){
        Call<JsonConverter> call = mInterface.getNetworkList(cToken);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter list = response.body();
                    mAdapter = new NetworkAdapter(getContext(), list.getNetworks());
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
}
