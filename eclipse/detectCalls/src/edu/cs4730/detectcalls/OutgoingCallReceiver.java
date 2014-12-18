package edu.cs4730.detectcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/*
 * This is an outbound phone call detector. 
 * Note there is an example of how to stop hang up on out bound call.
 *   No, you can't hang up on incoming calls without rooting the phone.
 */

public class OutgoingCallReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
                Bundle bundle = intent.getExtras();
               
                if(null == bundle)
                        return;
               
                String phonenumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

                Log.i("OutgoingCallReceiver",phonenumber);
                Log.i("OutgoingCallReceiver",bundle.toString());
               
                String info = "Detect Calls sample application\nOutgoing number: " + phonenumber;
               
                Toast.makeText(context, info, Toast.LENGTH_LONG).show();
             //   if (phonenumber == "5556") {
                  setResultData(null);  //should stop phone call!
             //   }
        }
}
