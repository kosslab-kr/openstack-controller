package openstack.contributhon.com.openstackcontroller.action;


public class RouterActionFragment extends ActionFragment {

    public static RouterActionFragment newInstance() {
        return new RouterActionFragment();
    }

    public void update(String status){
        mStatusView.setText("Status : " + status);
    }

}
