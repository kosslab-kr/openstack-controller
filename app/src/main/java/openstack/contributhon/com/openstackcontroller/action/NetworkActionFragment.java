package openstack.contributhon.com.openstackcontroller.action;

public class NetworkActionFragment extends ActionFragment {

    public static NetworkActionFragment newInstance() {
        return new NetworkActionFragment();
    }

    public void update(String status){
        mStatusView.setText("Status : " + status);
    }

}
