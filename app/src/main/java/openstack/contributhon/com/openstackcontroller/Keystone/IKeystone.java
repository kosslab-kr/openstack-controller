package openstack.contributhon.com.openstackcontroller.Keystone;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IKeystone {
    @Headers("Content-Type: application/json")
    @POST("/identity/v3/auth/tokens")
    Call<ResponseBody> getToken(@Body RequestBody body);
}
