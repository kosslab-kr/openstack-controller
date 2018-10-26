package openstack.contributhon.com.openstackcontroller;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRestApi {
    @GET("/compute/v2.1/servers/detail")
    Call<JsonConverter> getServerList(@Header("X-Auth-Token") String token);

    @GET("/compute/v2.1/servers/{server_id}")
    Call<JsonConverter> getServerDetail(@Header("X-Auth-Token") String token, @Path("server_id") String id);

    @POST("/compute/v2.1/servers")
    Call<ResponseBody> createServer(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @DELETE("/compute/v2.1/servers/{server_id}")
    Call<Void> deleteServer(@Header("X-Auth-Token") String token, @Path("server_id") String id);

    @POST("/compute/v2.1/servers/{server_id}/action")
    Call<ResponseBody> action(@Header("X-Auth-Token") String token, @Path("server_id") String id, @Body RequestBody body);

    @GET("/compute/v2.1/flavors")
    Call<JsonConverter> getFlavorList(@Header("X-Auth-Token") String token);

    @GET("/compute/v2.1/flavors/{flavor_id}")
    Call<JsonConverter> getFlavorDetail(@Header("X-Auth-Token") String token, @Path("flavor_id") String id);

    @POST("/compute/v2.1/flavors")
    Call<Void> createFlavor(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @DELETE("/compute/v2.1/flavors/{flavor_id}")
    Call<Void> deleteFlavor(@Header("X-Auth-Token") String token, @Path("flavor_id") String id);

    @GET("/compute/v2.1/os-keypairs")
    Call<JsonConverter> getKeypairList(@Header("X-Auth-Token") String token);

    @POST("/compute/v2.1/os-keypairs")
    Call<Void> createKeypair(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @GET("/compute/v2.1/os-keypairs/{keypair_name}")
    Call<JsonConverter> getKeypairDetail(@Header("X-Auth-Token") String token, @Path("keypair_name") String name);

    @DELETE("/compute/v2.1/os-keypairs/{keypair_name}")
    Call<Void> deleteKeypair(@Header("X-Auth-Token") String token, @Path("keypair_name") String name);

    @GET("/v2.0/networks")
    Call<JsonConverter> getNetworkList(@Header("X-Auth-Token") String token);

    @GET("/v2.0/networks/{network_id}")
    Call<JsonConverter> getNetworkDetail(@Header("X-Auth-Token") String token, @Path("network_id") String id);

    @POST("/v2.0/networks")
    Call<Void> createNetwork(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @GET("/v2.0/routers")
    Call<JsonConverter> getRouterList(@Header("X-Auth-Token") String token);

    @POST("/v2.0/routers")
    Call<Void> createRouter(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @GET("/v2.0/routers/{router_id}")
    Call<JsonConverter> getRouterDetail(@Header("X-Auth-Token") String token, @Path("router_id") String id);

    @DELETE("/v2.0/networks/{network_id}")
    Call<Void> deleteNetwork(@Header("X-Auth-Token") String token, @Path("network_id") String id);

    @DELETE("/v2.0/routers/{router_id}")
    Call<Void> deleteRouter(@Header("X-Auth-Token") String token, @Path("router_id") String id);

    @Headers("Content-Type: application/json")
    @POST("/identity/v3/auth/tokens")
    Call<ResponseBody> getToken(@Body RequestBody body);

    @GET("/image/v2/images")
    Call<JsonConverter> getImageList(@Header("X-Auth-Token") String token);

    @GET("/image/v2/images/{image_id}")
    Call<ImageVO> getImageDetail(@Header("X-Auth-Token") String token, @Path("image_id") String id);

    @POST("/image/v2/images")
    Call<Void> createImage(@Header("X-Auth-Token") String token, @Body RequestBody body);

    @POST("/image/v2/images/{image_id}/actions/deactivate")
    Call<ResponseBody> deactivate(@Header("X-Auth-Token") String token, @Path("image_id") String id);

    @POST("/image/v2/images/{image_id}/actions/reactivate")
    Call<ResponseBody> reactivate(@Header("X-Auth-Token") String token, @Path("image_id") String id);

    @DELETE("/image/v2/images/{image_id}")
    Call<Void> deleteImage(@Header("X-Auth-Token") String token, @Path("image_id") String id);
}
