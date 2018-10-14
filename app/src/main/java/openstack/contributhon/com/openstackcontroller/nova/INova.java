package openstack.contributhon.com.openstackcontroller.nova;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface INova {
    @GET("/compute/v2.1/servers/detail")
    Call<ServerList> getServerDetail(@Header("X-Auth-Token") String token);

    @DELETE("/compute/v2.1/servers/{server_id}")
    Call<Void> deleteServer(@Header("X-Auth-Token") String token, @Path("server_id") String id);
}
