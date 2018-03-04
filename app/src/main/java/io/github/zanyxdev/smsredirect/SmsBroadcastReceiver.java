package io.github.zanyxdev.smsredirect;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * A broadcast receiver who listens for incoming SMS
 */

public class SmsBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "SmsBroadcastReceiver";
    public final static String PARAM_SMS_SENDER = "sms_sender";
    public final static String PARAM_SMS_BODY = "sms_body";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            String smsSender = "";
            String smsBody = "";

            for (SmsMessage smsMessage : Telephony.Sms.Intents.getMessagesFromIntent(intent)) {
                smsBody += smsMessage.getMessageBody();
            }


            /**
             *
             * Start service with @param PARAM_SMS_SENDER, @param PARAM_SMS_BODY
             *
             */

            context.startService(new Intent(context,ProcessingSMS.class)
                                        .putExtra(PARAM_SMS_SENDER,smsSender)
                                        .putExtra(PARAM_SMS_BODY,smsBody)
                                );

            Log.d(TAG, "SMS detected: From " + smsSender + " With text " + smsBody);
        }
    }
}
