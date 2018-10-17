package openstack.contributhon.com.openstackcontroller.nova;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface INova {
    @GET("/compute/v2.1/servers/detail")
    Call<ServerList> getServerList(@Header("X-Auth-Token") String token);

    @GET("/compute/v2.1/servers/{server_id}")
    Call<ServerList> getServerDetail(@Header("X-Auth-Token") String token, @Path("server_id") String id);

    @POST("/compute/v2.1/servers")
    Call<Void> createServer(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @DELETE("/compute/v2.1/servers/{server_id}")
    Call<Void> deleteServer(@Header("X-Auth-Token") String token, @Path("server_id") String id);

    @GET("/compute/v2.1/flavors")
    Call<FlavorList> getFlavorList(@Header("X-Auth-Token") String token);

    @POST("/compute/v2.1/{server_id}/action")
    Call<Void> action(@Header("X-Auth-Token") String token, @Path("server_id") String id, @Body RequestBody body);
}
