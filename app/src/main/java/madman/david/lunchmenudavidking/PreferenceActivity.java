package madman.david.lunchmenudavidking;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by david on 4/6/16.
 */
public class PreferenceActivity extends android.preference.PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().
                beginTransaction().replace(
                android.R.id.content,
                new MyPreferenceFragment())
                .commit()
        ;
    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // inflate the XML file into the UI
            addPreferencesFromResource(R.xml.preference);
        }
    }
}
