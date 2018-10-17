package openstack.contributhon.com.openstackcontroller.nova;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class FlavorList {
    @SerializedName("flavors")
    private ArrayList<FlavorVO> flavors;

    public ArrayList<FlavorVO> getFlavors() {
        return flavors;
    }
}
