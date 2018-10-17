package openstack.contributhon.com.openstackcontroller.neutron;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface INeutron {
    @GET("/v2.0/networks")
    Call<NetworkList> getNetworkList(@Header("X-Auth-Token") String token);
}
