package madman.david.lunchmenudavidking;

//import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the menu, this adds items to the action bar, if present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.app_settings:
                startActivity(new Intent(this, PreferenceActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(this, About.class));
                break;
            case R.id.help_menu_item:
                startActivity(new Intent(this, HelpActivity.class));
                break;
            case R.id.add:
                // Get an intent for DetailActivity
                Intent intent = new Intent(this, DetailActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                finish();
                break;
            default:
                break;
        }
        return true;
    }

}


