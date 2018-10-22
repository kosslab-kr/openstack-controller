package openstack.contributhon.com.openstackcontroller;

import android.util.Log;

import okhttp3.MediaType;
import okhttp3.RequestBody;

import static openstack.contributhon.com.openstackcontroller.Config.MY_TAG;

public class MakeBody {
    public static RequestBody createServer(String name, String imageId, String flavorId, String networkId){
        String value = "{\"server\":{\"name\":\"" + name + "\",\"imageRef\":\"" + imageId + "\",\"flavorRef\":\"" + flavorId + "\",\"networks\":[{\"uuid\" : \""+networkId+"\"}]}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        Log.e(MY_TAG,value);
        return body;
    }
    public static RequestBody action(String action){
        String value = "{\"" + action + "\" : null}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
    public static RequestBody getToken(String domain, String user, String password){
        String value = "{\"auth\":{\"identity\":{\"methods\":[\"password\"],\"password\":" +
                "{\"user\":{\"domain\":{\"id\":\"" + domain + "\"},\"name\":\"" + user + "\",\"password\":\"" + password + "\"}}},\"scope\":{\"project\":{\"domain\":{\"id\":\"" + domain + "\"},\"name\":\"" + user + "\"}}}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
    public static RequestBody createImage(String name, String container, String disk){
        String value = "{\"container_format\":\"" + container + "\",\"disk_format\":\"" + disk + "\",\"name\":\"" + name + "\"}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
    public static RequestBody createFlavor(String name, String ram, String vcpus, String disk){
        String value = "{\"flavor\":{\"name\":\"" + name + "\",\"ram\":" + ram + ",\"vcpus\":" + vcpus + ",\"disk\":" + disk + "}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static RequestBody createKeypair(String name){
        String value = "{\"keypair\":{\"name\":\"" + name + "\"}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

    public static RequestBody createNetwork(String name, String admin_state_up, String mtu){
        String value = "{\"network\":{\"name\":\"" + name + "\",\"admin_state_up\":" + admin_state_up + ",\"mtu\":" + mtu + "}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        Log.e(MY_TAG,value);
        return body;
    }

    public static RequestBody createRouter(String name, String admin_state_up){
        String value = "{\"router\":{\"name\":\"" + name + "\",\"admin_state_up\":" + admin_state_up + "}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        Log.e(MY_TAG,value);
        return body;
    }

}



