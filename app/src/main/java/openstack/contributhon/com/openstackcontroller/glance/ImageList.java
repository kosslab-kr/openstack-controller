package openstack.contributhon.com.openstackcontroller.glance;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import openstack.contributhon.com.openstackcontroller.nova.ServerVO;

public class ImageList {
    @SerializedName("images")
    private ArrayList<ImageVO> images;

    public ArrayList<ImageVO> getImages() {
        return images;
    }
}
