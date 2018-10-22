package openstack.contributhon.com.openstackcontroller;

import java.util.HashMap;

public class ErrorCode {
    public final static HashMap<Integer, String> ResponseString = new HashMap<Integer, String>();
    static {
        ResponseString.put(400, "Can not find requested image");
        ResponseString.put(409, "There are one or more ports still in use on the network");
    }
    public static String gete(int code){
        return ResponseString.get(code);
    }
}
