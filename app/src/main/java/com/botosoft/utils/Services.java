package com.botosoft.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.content.DialogInterface;
import android.nfc.tech.MifareClassic;

public class Services {
    public static void ensureSensorIsOn(final Activity ac, NfcAdapter nfc)
    {
        if(!nfc.isEnabled())
        {
            // Alert the user that NFC is off


            new AlertDialog.Builder(ac)
                    .setTitle("NFC Sensor Turned Off")
                    .setMessage("In order to use this application, the NFC sensor must be turned on. Do you wish to turn it on?")
                    .setPositiveButton("Go to Settings", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            // Send the user to the settings page and hope they turn it on
                            if (android.os.Build.VERSION.SDK_INT >= 16)
                            {
                                ac.startActivity(new Intent(android.provider.Settings.ACTION_NFC_SETTINGS));
                            }
                            else
                            {
                                ac.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                            }
                        }
                    })
                    .setNegativeButton("Do Nothing", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i)
                        {
                            // Do nothing
                        }
                    })
                    .show();
        }
    }

    public static void enableNfcForeground(NfcAdapter nfcAdapter, Activity act){



        if (nfcAdapter != null)
        {
            ensureSensorIsOn(act,nfcAdapter);

            // We'd like to listen to all incoming NFC tags that support the IsoDep interface
            nfcAdapter.enableForegroundDispatch(act,
                    PendingIntent.getActivity(act, 0, new Intent(act,act.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0),
                    new IntentFilter[]{ new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED) },
                    new String[][] { new String[] { MifareClassic.class.getName() }});


        }

    }
    public static void disableNfcForeground(NfcAdapter nfcAdapter, Activity act){

        if (nfcAdapter != null)
        {
            nfcAdapter.disableForegroundDispatch(act);
        }
    }

}
