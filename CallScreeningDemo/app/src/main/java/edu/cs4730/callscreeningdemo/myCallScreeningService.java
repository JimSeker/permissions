package edu.cs4730.callscreeningdemo;

import android.telecom.Call;
import android.telecom.CallScreeningService;
import android.telephony.PhoneNumberUtils;
import android.util.Log;

import java.util.Locale;

import androidx.annotation.NonNull;

public class myCallScreeningService extends CallScreeningService {
    private static final String TAG = "CallScreenService";
    CallScreenData myCallScreenData = new CallScreenData();

    public myCallScreeningService() {
    }

    @Override
    public void onScreenCall(@NonNull Call.Details callDetails) {
        if (callDetails.getCallDirection() == Call.Details.DIRECTION_INCOMING) {
            String num = callDetails.getHandle().getSchemeSpecificPart();
            String phonenumber = PhoneNumberUtils.formatNumber(num, Locale.getDefault().getCountry());
            Log.wtf(TAG, "phone number is " + phonenumber);

            //get my discisions
            MainActivity.loadTitlePref(getApplicationContext(), myCallScreenData);
            /**
             * Now we build a response to the call, ie ignore it or that sort of thing.
             *  As of 29 we have the following to set true/false
             *    Not documented in api, so I'm guessing the meaning, based on the name.
             *
             *    also this is very buggy.  api 30, it seems better.
             */
             CallResponse.Builder myCallReponse = new CallResponse.Builder();
             myCallReponse
                 .setSilenceCall(myCallScreenData.noring) //don't ring   //this one can bve true the rest false.

                 .setDisallowCall(myCallScreenData.disallow)  //disallow the call  this one seems to need to be true for the rest below to work.
                 .setRejectCall(myCallScreenData.reject)  //reject the call, with disallow, ends the call.
                 .setSkipCallLog(myCallScreenData.nolog)  //With disallow, also don't log the call
                 .setSkipNotification(myCallScreenData.nonot);  //with disallow, also no notification of the call (which sort didn't show anyway).

             //now set the response.
            respondToCall(callDetails, myCallReponse.build() );

        } else if (callDetails.getCallDirection() == Call.Details.DIRECTION_OUTGOING) {

            Log.wtf(TAG, "outgoing phone direction" );
            //lets not let them call 307-766-5555  .
            String num = callDetails.getHandle().getSchemeSpecificPart();
            String phonenumber = PhoneNumberUtils.formatNumber(num, Locale.getDefault().getCountry());
            Log.wtf(TAG, "outbound phone number is " + phonenumber);
            if (phonenumber.compareTo("(307) 766-5555") ==0) {
                CallResponse.Builder myCallReponse = new CallResponse.Builder();
                myCallReponse
                    .setDisallowCall(true)  //disallow the call
                    .setRejectCall(true)  //reject the call
                    .setSilenceCall(true) //don't ring
                    .setSkipCallLog(true)  //don't log the call
                    .setSkipNotification(true);  //also no notification of the call

                //now set the response.
                //AS of API 30 docs, response to call is ignored, except for incoming calls.
                respondToCall(callDetails, myCallReponse.build() );
                //so this is only left as a test, but is not expected to work.
                Log.wtf(TAG, "Should be blocked, but likely not?");
            } else {
                Log.wtf(TAG, "not blocked?");
            }
        } else {
            Log.wtf(TAG, "unknown phone direction" );
        }
    }


}
