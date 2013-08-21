package suzuno.sampleinputmethod;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

/**
 * Created by suzuno on 13-8-21.
 */
public class InputMethodPreference extends PreferenceActivity{


    @Override
    public Intent getIntent() {
        final Intent modIntent = new Intent(super.getIntent());
        modIntent.putExtra(EXTRA_SHOW_FRAGMENT, Settings.class.getName());
        modIntent.putExtra(EXTRA_NO_HEADERS, true);
        return modIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We overwrite the title of the activity, as the default one is "Voice Search".
        setTitle("title");
    }

    public static class Settings extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
    }
}
