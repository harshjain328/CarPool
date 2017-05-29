package com.noobs.carpool.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.noobs.carpool.services.listeners.SmsListener;


/**
 * Created by deepak on 9/5/17.
 */

public class SmsReader extends BroadcastReceiver {

    private static SmsListener msgListener;
    private static String senderName;


    public static void bindListener(final SmsListener listener){
        msgListener = listener;
    }

    public static void setSenderName(final String sender){
        senderName = sender;
    }

    public SmsReader(){ }

    public SmsReader(SmsListener listener){
        msgListener = listener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("SMS", "Receiver-Called");
        //Toast.makeText(context, "RECEIVER-CALLED", Toast.LENGTH_LONG).show();
        final Bundle bundle = intent.getExtras();
        if (bundle != null) {

            final Object[] pdusArr = (Object[]) bundle.get("pdus");
            for (int i = 0; i < pdusArr.length; i++)
            {
                SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusArr[i]);
                String senderAddress = currentMessage.getDisplayOriginatingAddress();
                String message = currentMessage.getDisplayMessageBody();
                Log.i("SMS : ", "senderNum: " + senderAddress + " message: " + message);

                if (senderName != null && senderAddress.contains(senderName)) {
                    if (msgListener != null) {
                        msgListener.onMessageReceive(message); //calling listener
                    }
                }
            }
        }


    }

}