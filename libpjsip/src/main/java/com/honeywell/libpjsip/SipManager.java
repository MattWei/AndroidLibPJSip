package com.honeywell.libpjsip;

import android.util.Log;

import org.pjsip.pjsua2.Endpoint;
import org.pjsip.pjsua2.EpConfig;
import org.pjsip.pjsua2.TransportConfig;
import org.pjsip.pjsua2.pjsip_transport_type_e;

/**
 * Created by weiminji on 10/29/17.
 */

public class SipManager {
    private String TAG = SipManager.class.getName();
    static {
        System.loadLibrary("pjsua2");
        System.out.println("Library loaded");
    }

    Endpoint mEP = null;
    public SipManager(int port) {
        // Create endpoint
        mEP = new Endpoint();
        try {
            mEP.libCreate();

            // Initialize endpoint
            EpConfig epConfig = new EpConfig();
            mEP.libInit( epConfig );

            // Create SIP transport. Error handling sample is shown
            TransportConfig sipTpConfig = new TransportConfig();
            sipTpConfig.setPort(port);
            mEP.transportCreate(pjsip_transport_type_e.PJSIP_TRANSPORT_UDP, sipTpConfig);
            // Start the library
            mEP.libStart();
        } catch (Exception e) {
            e.printStackTrace();
            mEP = null;
        }
    }

    public void onDestroy() {
        // Explicitly destroy and delete endpoint
        if (mEP != null) {
            try {
                mEP.libDestroy();
                mEP.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
