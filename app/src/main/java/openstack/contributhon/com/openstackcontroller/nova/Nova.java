package openstack.contributhon.com.openstackcontroller.nova;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Nova {
    public static RequestBody createServer(String name, String imageId, String flavorId, String networkId){
        String value = "{\"server\":{\"name\":\"" + name + "\",\"imageRef\":\"" + imageId + "\",\"flavorRef\":\"" + flavorId + "\",\"networks\":[{\"uuid\" : \""+networkId+"\"}]}}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }
    public static RequestBody action(String action){
        String value = "{\"" + action + "\" : null}";
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body;
    }

}
