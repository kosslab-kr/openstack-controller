package openstack.contributhon.com.openstackcontroller.glance;

import openstack.contributhon.com.openstackcontroller.nova.ServerList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface IGlance {
    @GET("/image/v2/images")
    Call<ImageList> getImageList(@Header("X-Auth-Token") String token);
}
