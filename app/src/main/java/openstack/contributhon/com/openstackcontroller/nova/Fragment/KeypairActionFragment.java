package openstack.contributhon.com.openstackcontroller.nova.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import openstack.contributhon.com.openstackcontroller.action.ActionFragment;

public class KeypairActionFragment extends ActionFragment {

    public static KeypairActionFragment newInstance() {
        return new KeypairActionFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

}
