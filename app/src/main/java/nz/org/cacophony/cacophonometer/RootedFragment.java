package nz.org.cacophony.cacophonometer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class RootedFragment extends Fragment {

    private static final String TAG = "RootedFragment";


    private Switch swRooted;
    private Button btnFinished;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_rooted, container, false);

        setUserVisibleHint(false);

            btnFinished = (Button) view.findViewById(R.id.btnFinished);
            btnFinished.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {

                    ((AdvancedWizardActivity)getActivity()).nextPageView();
                }
            });



        swRooted = (Switch) view.findViewById(R.id.swRooted);
        swRooted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Prefs prefs = new Prefs(getActivity());
                prefs.setHasRootAccess(swRooted.isChecked());
            }
        });

        return view;
    }

    @Override
    public void setUserVisibleHint(final boolean visible) {
        super.setUserVisibleHint(visible);
        if (getActivity() == null){
            return;
        }
        if (visible) {
            Prefs prefs = new Prefs(getActivity());
            ((Switch) getView().findViewById(R.id.swRooted)).setChecked(prefs.getHasRootAccess());

            if (prefs.getSettingsForTestServerEnabled()){
                ((Button) getView().findViewById(R.id.btnFinished)).setVisibility(View.INVISIBLE);
            }else{
                ((Button) getView().findViewById(R.id.btnFinished)).setVisibility(View.VISIBLE);
            }
        }
    }

}
