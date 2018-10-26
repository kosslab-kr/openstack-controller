package openstack.contributhon.com.openstackcontroller;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;

public class Config {
    public static final String MY_TAG="stack_controller";
    public static final int DETAIL = 0;
    public static final int ACTION = 1;

    public static String cToken;
    public static String cUser;
    public static String cHost;
    public static String cDetailId;
    public static int cCurrentFragmentId;
    public static int cCurrentTab;

    public static boolean cIsDetail = false;
    public static String getError(Retrofit retrofit, ResponseBody error, int id){        ;
        try {
            ErrorMessage restError  = (ErrorMessage) retrofit.responseBodyConverter( ErrorMessage.class, ErrorMessage.class.getAnnotations()).convert(error);
            return restError.getMesage(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error Message is gone";
    }
}
