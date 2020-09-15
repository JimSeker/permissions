package edu.cs4730.detectcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

/**
 * This will detect incoming calls  
 * Note you can change the phone number, so the user (ie phone app) see the wrong phone number
 * I didn't do that here, but you can.
 *
 * as of API 29, this app is deprecated in favor of a "CallScreeningService"
 *
 */

public class IncomingCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
               
                if(null == bundle)
                        return;
               
                Log.i("IncomingCallReceiver",bundle.toString());
               
                String state = bundle.getString(TelephonyManager.EXTRA_STATE);
                               
                Log.i("IncomingCallReceiver","State: "+ state);
               
                if(state.equalsIgnoreCase(TelephonyManager.EXTRA_STATE_RINGING))
                {
                        String phonenumber = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                               
                        Log.i("IncomingCallReceiver","Incomng Number: " + phonenumber);
                       
                        String info = "Detect Calls sample application\nIncoming number: " + phonenumber;
                       
                        Toast.makeText(context, info, Toast.LENGTH_LONG).show();
                        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                        //telephony.
                        
                }
        }

}
