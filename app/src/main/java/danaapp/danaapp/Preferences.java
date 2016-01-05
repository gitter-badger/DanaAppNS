package danaapp.danaapp;

import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.os.Bundle;
import android.preference.PreferenceManager;

import danaapp.danaapp.NS.NSStatusEvent;

/**
 * Created by mike on 02.01.2016.
 */
public class Preferences extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("ns_enable") || key.equals("ns_url") || key.equals("ns_api_secret")) {
            // socket.io has a bug. unable to use reconnection
            //MainApp.bus().post(new NSStatusEvent("ForceReconnect"));
        } else
            MainApp.getNSClient().readPreferences();

    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_danar);
            addPreferencesFromResource(R.xml.pref_ns);
            addPreferencesFromResource(R.xml.pref_sync);
        }
    }

}
