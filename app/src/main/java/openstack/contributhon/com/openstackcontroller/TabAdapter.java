package openstack.contributhon.com.openstackcontroller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import openstack.contributhon.com.openstackcontroller.action.NetworkActionFragment;
import openstack.contributhon.com.openstackcontroller.action.RouterActionFragment;
import openstack.contributhon.com.openstackcontroller.detail.FlavorDetailFragment;
import openstack.contributhon.com.openstackcontroller.detail.KeypairDetailFragment;
import openstack.contributhon.com.openstackcontroller.action.ImageActionFragment;
import openstack.contributhon.com.openstackcontroller.detail.ImageDetailFragment;
import openstack.contributhon.com.openstackcontroller.detail.NetworkDetailFragment;
import openstack.contributhon.com.openstackcontroller.detail.RouterDetailFragment;
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
import openstack.contributhon.com.openstackcontroller.action.FlavorActionFragment;
import openstack.contributhon.com.openstackcontroller.nova.Fragment.KeypairActionFragment;
import openstack.contributhon.com.openstackcontroller.detail.InstanceDetailFragment;
import openstack.contributhon.com.openstackcontroller.action.InstanceActionFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static openstack.contributhon.com.openstackcontroller.Config.*;

public class TabAdapter extends FragmentStatePagerAdapter {

    private int tabCount;
    private Timer mTimer;
    private TimerTask mTask;
    private IRestApi mInterface, mInterfaceNetwork;
    InstanceDetailFragment mInstanceDetailFragmnet;
    FlavorDetailFragment mFlavorDetailFragmnet;
    KeypairDetailFragment mKeypairDetailFragmnet;
    ImageDetailFragment mImageDetailFragmnet;
    NetworkDetailFragment mNetworkDetailFragmnet;
    RouterDetailFragment mRouterDetailFragmnet;
    InstanceActionFragment mInstanceActionFragmnet;
    ImageActionFragment mImageActionFragment;
    NetworkActionFragment mNetworkActionFragment;
    RouterActionFragment mRouterActionFragment;

    public TabAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(cHost)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String[] host = cHost.split(":");
        host[1] = "http:" + host[1] + ":9696";
        Retrofit networkRetrofit = new Retrofit.Builder()
                .baseUrl(host[1])
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mInterface = retrofit.create(IRestApi.class);
        mInterfaceNetwork = networkRetrofit.create(IRestApi.class);
        mTimer = new Timer();
        mTask = new TimerTask() {
            @Override
            public void run() {
                refresh();
            }
        };
    }

    public synchronized void refresh() {
        switch (cCurrentFragmentId) {
            case R.id.menu_instance:
                getServerDetail();
                break;
            case R.id.menu_flavor:
                getFlavorDetail();
                break;
            case R.id.menu_keypair:
                getKeypairDetail();
                break;
            case R.id.menu_image:
                getImageDetail();
                break;
            case R.id.menu_network:
                getNetworkDetail();
                break;
            case R.id.menu_router:
                getRouterDetail();
                break;
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case DETAIL:
                switch (cCurrentFragmentId) {
                    case R.id.menu_instance:
                        mInstanceDetailFragmnet = InstanceDetailFragment.newInstance();
                        mTimer.schedule(mTask, 0, 3000);
                        return mInstanceDetailFragmnet;
                    case R.id.menu_flavor:
                        mFlavorDetailFragmnet = FlavorDetailFragment.newInstance();
                        mTimer.schedule(mTask, 0, 3000);
                        return mFlavorDetailFragmnet;
                    case R.id.menu_keypair:
                        mKeypairDetailFragmnet = KeypairDetailFragment.newInstance();
                        mTimer.schedule(mTask, 0, 3000);
                        return mKeypairDetailFragmnet;
                    case R.id.menu_image:
                        mImageDetailFragmnet = ImageDetailFragment.newInstance();
                        mTimer.schedule(mTask, 0, 3000);
                        return mImageDetailFragmnet;
                    case R.id.menu_network:
                        mNetworkDetailFragmnet = NetworkDetailFragment.newInstance();
                        mTimer.schedule(mTask, 0, 3000);
                        return mNetworkDetailFragmnet;
                    case R.id.menu_router:
                        mRouterDetailFragmnet = RouterDetailFragment.newInstance();
                        mTimer.schedule(mTask, 0, 3000);
                        return mRouterDetailFragmnet;
                }
            case ACTION:
                switch (cCurrentFragmentId) {
                    case R.id.menu_instance:
                        mInstanceActionFragmnet = InstanceActionFragment.newInstance();
                        return mInstanceActionFragmnet;
                    case R.id.menu_flavor:
                        return FlavorActionFragment.newInstance();
                    case R.id.menu_keypair:
                        return KeypairActionFragment.newInstance();
                    case R.id.menu_image:
                        mImageActionFragment = ImageActionFragment.newInstance();
                        return mImageActionFragment;
                    case R.id.menu_network:
                        mNetworkActionFragment = NetworkActionFragment.newInstance();
                        return mNetworkActionFragment;
                    case R.id.menu_router:
                        mRouterActionFragment = RouterActionFragment.newInstance();
                        return mRouterActionFragment;
                }
                return null;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }

    public void getServerDetail() {
        if (mInstanceDetailFragmnet == null)
            return;
        Call<JsonConverter> call = mInterface.getServerDetail(cToken, cDetailId);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter server = response.body();
                    if (cCurrentTab == DETAIL)
                        mInstanceDetailFragmnet.setAll(server.getDetailServer());
                    else
                        mInstanceActionFragmnet.update(server.getDetailServer().status);
                }
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
            }
        });
    }

    public void getFlavorDetail() {
        if (mFlavorDetailFragmnet == null)
            return;
        Call<JsonConverter> call = mInterface.getFlavorDetail(cToken, cDetailId);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter flavor = response.body();
                    if (cCurrentTab == DETAIL)
                        mFlavorDetailFragmnet.setAll(flavor.getDetailFlavor());
                }
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
            }
        });
    }

    public void getKeypairDetail() {
        if (mKeypairDetailFragmnet == null)
            return;
        Call<JsonConverter> call = mInterface.getKeypairDetail(cToken, cDetailId);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter keypair = response.body();
                    if (cCurrentTab == DETAIL)
                        mKeypairDetailFragmnet.setAll(keypair.getDetailKeypair());
                }
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
            }
        });
    }

    public void getImageDetail() {
        if (mImageDetailFragmnet == null)
            return;
        Call<ImageVO> call = mInterface.getImageDetail(cToken, cDetailId);
        call.enqueue(new Callback<ImageVO>() {
            @Override
            public void onResponse(Call<ImageVO> call, Response<ImageVO> response) {

                if (response.isSuccessful()) {
                    ImageVO image = response.body();
                    if (cCurrentTab == DETAIL)
                        mImageDetailFragmnet.setAll(image);
                    else
                        mImageActionFragment.update(image.status);
                } else {
                    try {
                        Log.e(MY_TAG, response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ImageVO> call, Throwable t) {
            }
        });
    }

    public void getNetworkDetail() {
        if (mNetworkDetailFragmnet == null)
            return;
        Call<JsonConverter> call = mInterfaceNetwork.getNetworkDetail(cToken, cDetailId);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter network = response.body();
                    if (cCurrentTab == DETAIL)
                        mNetworkDetailFragmnet.setAll(network.getDetailnetwork());
                    else
                        mNetworkActionFragment.update(network.getDetailnetwork().status);
                }
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
            }
        });
    }

    public void getRouterDetail() {
        if (mRouterDetailFragmnet == null)
            return;
        Call<JsonConverter> call = mInterfaceNetwork.getRouterDetail(cToken, cDetailId);
        call.enqueue(new Callback<JsonConverter>() {
            @Override
            public void onResponse(Call<JsonConverter> call, Response<JsonConverter> response) {
                if (response.isSuccessful()) {
                    JsonConverter router = response.body();
                    if (cCurrentTab == DETAIL)
                        mRouterDetailFragmnet.setAll(router.getDetailRouter());
                    else
                        mRouterActionFragment.update(router.getDetailRouter().status);
                }
            }

            @Override
            public void onFailure(Call<JsonConverter> call, Throwable t) {
            }
        });

    }

    public void close() {
        mTask.cancel();
    }
}
