package openstack.contributhon.com.openstackcontroller.Keystone;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class KeyStone {
    public static RequestBody getToken(String domain, String user, String password){
        String value = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":" +
                "{\"user\":{\"domain\":{\"id\":\"" + domain + "\"},\"name\":\"" + user + "\",\"password\":\"" + password + "\"}}},\"scope\":{\"project\":{\"domain\":{\"id\":\"" + domain + "\"},\"name\":\"" + user + "\"}}}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);       
        return body;
    }
}
