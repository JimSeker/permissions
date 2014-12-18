package edu.cs4730.runningapp;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * this demos how to get all the running processes on the phone.
 *  
 * 
 */
public class MainFragment extends Fragment {

	Button call;
	TextView output;
	
	public MainFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View myView = inflater.inflate(R.layout.fragment_main, container, false);
        myView.findViewById(R.id.btn).setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				getlist();
			}
        });
		output = (TextView) myView.findViewById(R.id.ouput);
		return myView;
	}

	public void getlist() {
		
		ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
		// getRunningServices,  getRunningAppProcesses, getRunningTasks, and getRecentTasks 
	    List<RunningServiceInfo> runServices = activityManager.getRunningServices(Integer.MAX_VALUE);
		List<RunningAppProcessInfo> runApp = activityManager.getRunningAppProcesses();
		List<RunningTaskInfo> runTasks = activityManager.getRunningTasks(Integer.MAX_VALUE); 
		List<RecentTaskInfo> recentTasks = activityManager.getRecentTasks(Integer.MAX_VALUE, ActivityManager.RECENT_WITH_EXCLUDED);
		
		output.setText("Running Tasks list:");
		for (RunningTaskInfo task : runTasks) {
			output.append("\n"+ task.baseActivity.getPackageName()+"("+ task.baseActivity.getShortClassName()+")");
		}
		
		output.append("\n\nRunning Services list:");
		for (RunningServiceInfo task : runServices) {
			output.append("\n"+ task.service.getPackageName());
		}
		
		output.append("\n\nAppProcess list:");
		for (RunningAppProcessInfo task : runApp) {
			//output.append("\n"+ task.importanceReasonComponent.getPackageName());
			output.append("\n"+ task.processName);
		}
		
		output.append("\n\nRecent Task list:");
		for (RecentTaskInfo task : recentTasks) {
			//output.append("\n"+ task.origActivity.getPackageName());
			output.append("\n"+ task.baseIntent.getComponent().getPackageName());
		}
		
		
		
	}
	
}
