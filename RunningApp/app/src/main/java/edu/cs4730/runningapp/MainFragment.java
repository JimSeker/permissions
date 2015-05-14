package edu.cs4730.runningapp;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RecentTaskInfo;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
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
        //Note, getRunningTasks and getRecentTasks in Lollipop and beyond will only return the current running task (ie this one), for better privacy.
        //Both should return the same as (API 21+) getAppTasks() which is get the list of tasks associated with the calling application.
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
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {  //not available before API 21.
            List<ActivityManager.AppTask> appTasks = activityManager.getAppTasks();
            output.append("\n\nApp Task list:");
            for (ActivityManager.AppTask task : appTasks) {
                //everything shows a null... not doing something correct here.
                //output.append("\n" + task.getTaskInfo().taskDescription.getLabel());  // comes out null...
                //output.append("\n" + task.getTaskInfo().description);
                //at least this one, gives me information, just about the intent that launched this activity.
                output.append("\n" + task.getTaskInfo().baseIntent.toUri(0));
            }
        }

    }
	
}
