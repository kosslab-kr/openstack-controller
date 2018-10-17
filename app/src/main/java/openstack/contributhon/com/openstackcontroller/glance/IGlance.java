package openstack.contributhon.com.openstackcontroller.glance;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface IGlance {
    @GET("/image/v2/images")
    Call<ImageList> getImageList(@Header("X-Auth-Token") String token);
}
