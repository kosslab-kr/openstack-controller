package openstack.contributhon.com.openstackcontroller;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import openstack.contributhon.com.openstackcontroller.glance.ImageVO;
import openstack.contributhon.com.openstackcontroller.neutron.NetworkVO;
import openstack.contributhon.com.openstackcontroller.neutron.RouterVO;
import openstack.contributhon.com.openstackcontroller.nova.Model.Keypair;
import openstack.contributhon.com.openstackcontroller.nova.Model.FlavorVO;
import openstack.contributhon.com.openstackcontroller.nova.Model.KeypairVO;
import openstack.contributhon.com.openstackcontroller.nova.Model.ServerVO;

public class JsonConverter {

    private ArrayList<ServerVO> servers;

    private ServerVO server;

    private ArrayList<KeypairVO> keypairs;

    private Keypair keypair;

    private ArrayList<FlavorVO> flavors;

    private FlavorVO flavor;

    private ArrayList<NetworkVO> networks;
    private NetworkVO network;
    private ArrayList<RouterVO> routers;
    private RouterVO router;
    @SerializedName("images")
    private ArrayList<ImageVO> images;
    private ImageVO image;

    public ArrayList<ImageVO> getImages() {
        return images;
    }

    public ImageVO getDetailImage() {
        return image;
    }

    public ArrayList<NetworkVO> getNetworks() {
        return networks;
    }

    public NetworkVO getDetailnetwork() {
        return network;
    }

    public ArrayList<RouterVO> getRouters() {
        return routers;
    }

    public RouterVO getDetailRouter() {
        return router;
    }

    public ArrayList<FlavorVO> getFlavors() {
        return flavors;
    }

    public FlavorVO getDetailFlavor() {
        return flavor;
    }

    public ArrayList<ServerVO> getServers() {
        return servers;
    }

    public ServerVO getDetailServer() {
        return server;
    }

    public ArrayList<KeypairVO> getKeypairs() {
        return keypairs;
    }

    public Keypair getDetailKeypair() {
        return keypair;
    }
}
