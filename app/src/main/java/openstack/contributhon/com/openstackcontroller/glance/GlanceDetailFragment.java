package openstack.contributhon.com.openstackcontroller.glance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import openstack.contributhon.com.openstackcontroller.DetailAdapter;
import openstack.contributhon.com.openstackcontroller.IRestApi;
import openstack.contributhon.com.openstackcontroller.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.cDetailId;
import static openstack.contributhon.com.openstackcontroller.Config.cHost;
import static openstack.contributhon.com.openstackcontroller.Config.cToken;

public class GlanceDetailFragment extends Fragment {

    ListView mListView;

    public static GlanceDetailFragment newInstance() {
        return new GlanceDetailFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        mListView = view.findViewById(R.id.view);
        final DetailAdapter mDetailAdapter = new DetailAdapter(getContext(), R.layout.row_detail);
        mListView.setAdapter( mDetailAdapter);

        Retrofit glance = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        IRestApi glanceInterface = glance.create(IRestApi.class);
        Call<ImageVO> glanceCall = glanceInterface.getImageDetail(cToken, cDetailId);
        glanceCall.enqueue(new Callback<ImageVO>() {
            @Override
            public void onResponse(Call<ImageVO> call, Response<ImageVO> response) {
                if (response.isSuccessful()) {
                    ImageVO myImage = response.body();
                    mDetailAdapter.add(Pair.create("Name","Value"));
                    mDetailAdapter.add(Pair.create("name",myImage.name));
                    mDetailAdapter.add(Pair.create("id",myImage.id));
                    mDetailAdapter.add(Pair.create("disk_format",myImage.disk_format));
                    mDetailAdapter.add(Pair.create("container_format",myImage.container_format));
                    mDetailAdapter.add(Pair.create("status",myImage.status));
                    mDetailAdapter.add(Pair.create("created_at",myImage.created_at));
                    mDetailAdapter.add(Pair.create("updated_at",myImage.updated_at));
                    mDetailAdapter.add(Pair.create("os_hidden",myImage.os_hidden));
                    mDetailAdapter.add(Pair.create("size",myImage.size));
                    mDetailAdapter.add(Pair.create("min_disk",myImage.min_disk));
                    mDetailAdapter.add(Pair.create("visibility",myImage.visibility));
                } else
                    Toast.makeText(getContext(), "Connect Error!!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ImageVO> call, Throwable t) {
                Toast.makeText(getContext(), "Connect Error!! : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
