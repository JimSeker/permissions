package edu.cs4730.wifitoggle;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple demo of turning the wifi on and off.
 * See the permissions in the androidManifest.xml as well.
 * 
 */
public class MainFragment extends Fragment {

	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View myView = inflater.inflate(R.layout.fragment_main, container, false);
		

		Switch toggle = (Switch) myView.findViewById(R.id.wifi_switch);
		//set the switch correctly for first instance.
		WifiManager wifiManager = (WifiManager) getActivity()
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager != null && wifiManager.isWifiEnabled()) {
			toggle.setChecked(true);  //api 14+ method, but otherwise the rest of the code will work back to at least api 10.
		}
		
		toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					toggleWiFi(true);
					Toast.makeText(getActivity().getApplicationContext(), "Wi-Fi Enabled!", Toast.LENGTH_LONG).show();
				} else {
					toggleWiFi(false);
					Toast.makeText(getActivity().getApplicationContext(), "Wi-Fi Disabled!", Toast.LENGTH_LONG).show();
				}
			}
		});
		return myView;
	}

	
	public void toggleWiFi(boolean status) {
		WifiManager wifiManager = (WifiManager) getActivity()
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager != null && status == true && !wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(true);
		} else if (wifiManager != null && status == false && wifiManager.isWifiEnabled()) {
			wifiManager.setWifiEnabled(false);
		}
	}
}
