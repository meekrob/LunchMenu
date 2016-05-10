package madman.david.lunchmenudavidking;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by david on 5/6/16.
 */
public class RestaurantListFragment extends ListFragment implements AdapterView.OnItemClickListener{

    private Cursor listViewCursor; // Cursor housing the SELECT FROM DB that underlies the ListView
    private RestaurantAdapter adapter = null;
    private DBHelper dbHelper;

    public final static String ID_EXTRA = "madman.david.lunchmenudavidking._ID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.list_fragment, container, false);
    }
    @Override // handle a list item being clicked
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Used to be a private variable MainActivity.onListClick holding an object
        // of an anonymous class.
        // Now invokes DetailActivity.
        Intent i = new Intent(getActivity(), DetailActivity.class);
        i.putExtra(ID_EXTRA, String.valueOf(id)); // pass the record ID to DetailActivity
        startActivity(i);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dbHelper = new DBHelper(getActivity());
        listViewCursor = dbHelper.getAll(); // run the SELECT query
        getActivity().startManagingCursor(listViewCursor);

        // create an adapter
        adapter = new RestaurantAdapter(getActivity(), listViewCursor);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    private class RestaurantAdapter extends CursorAdapter {
        public RestaurantAdapter(Context context, Cursor c) {
            super(context, c, FLAG_REGISTER_CONTENT_OBSERVER);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            RestaurantHolder holder = new RestaurantHolder(row);
            row.setTag(holder);
            return row;
        }

        @Override
        public void bindView(View row, Context context, Cursor cursor) {
            RestaurantHolder holder = (RestaurantHolder)row.getTag();
            holder.populateFrom(cursor, dbHelper);
        }
    }

    // private nested class to wrap up the code to create one row
    static class RestaurantHolder {
        private TextView title = null;
        private TextView address = null;
        private ImageView icon = null;

        //Constructor
        RestaurantHolder(View row) {
            title = (TextView)row.findViewById(R.id.title);
            address = (TextView)row.findViewById(R.id.address);
            icon = (ImageView)row.findViewById(R.id.icon);
        }

        public void populateFrom(Cursor c, DBHelper helper) {
            Restaurant restaurant = helper.getRestaurant(c);
            title.setText(restaurant.getName());
            address.setText(restaurant.getAddress());

            // set the icon according to the restaurant type
            switch(restaurant.getType()) {
                case Restaurant.DELIVERY:
                    icon.setImageResource(R.drawable.delivery_48);
                    break;
                case Restaurant.CARRY_OUT:
                    icon.setImageResource(R.drawable.takeout_48);
                    break;
                case Restaurant.DINE_IN: // use the Dine-In for default
                default:
                    icon.setImageResource(R.drawable.dine_in_48);
                    break;
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }
}
