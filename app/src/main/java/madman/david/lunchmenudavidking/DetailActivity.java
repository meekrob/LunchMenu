package madman.david.lunchmenudavidking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by david on 5/6/16.
 */
public class DetailActivity extends FragmentActivity implements View.OnClickListener, ConfirmDialog.ConfirmDialogListener
{
    // get the form input controls:- name, address, type
    EditText name = null;
    EditText address = null;
    EditText note = null;
    RadioGroup types = null;
    String restaurantId = null;
    DBHelper dbHelper;
    Restaurant selectedRestaurant = null;
    AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        // find form fields
        name = (EditText)findViewById(R.id.name_entry);
        note = (EditText)findViewById(R.id.note_entry);
        address = (EditText)findViewById(R.id.address_entry);
        types = (RadioGroup)findViewById(R.id.delivery_type);

        // find the buttons, set click listener to this
        View saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
        View cancelButton = findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(this);
        View deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(this);

        // get the data passed from RestaurantListFragment.onItemClick (which starts this Intent)
        restaurantId = getIntent().getStringExtra(RestaurantListFragment.ID_EXTRA); // null if there is none
        dbHelper = new DBHelper(this);

        // if there WAS a click from RestaurantListFragment, load it
        if (restaurantId != null) { load(); }

    }

    // handle the form buttons
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.save_button:
                // find the EditText fields using the id fields
                String restaurant_name = name.getText().toString();
                String restaurant_address = address.getText().toString();
                String restaurant_note = note.getText().toString();
                //figure out which radio button is clicked
                String restaurant_type = "";
                switch(types.getCheckedRadioButtonId()) {
                    case R.id.delivery_choice:
                        restaurant_type = Restaurant.DELIVERY;
                        break;
                    case R.id.takeout_choice:
                        restaurant_type = Restaurant.CARRY_OUT;
                        break;
                    case R.id.dinein_choice:
                        restaurant_type = Restaurant.DINE_IN;
                        break;
                }
                // if this is an update, we already have a restaurant object loaded from
                // RestaurantListFragment.OnItemClick invoking this class, causing restaurantID to be set via getStringExtra
                // and thereby invoking load()
                if (selectedRestaurant == null) { // Add
                    selectedRestaurant = new Restaurant(restaurant_name,restaurant_address, restaurant_type, restaurant_note);

                    dbHelper.insert(selectedRestaurant);
                    Toast.makeText(this,
                            String.format(
                                    getString(R.string.add_toast_format_string), selectedRestaurant.getName()),
                            Toast.LENGTH_LONG).show();

                }
                else { // Update
                    selectedRestaurant.setName(restaurant_name);
                    selectedRestaurant.setAddress(restaurant_address);
                    selectedRestaurant.setType(restaurant_type);
                    selectedRestaurant.setNote(restaurant_note);

                    dbHelper.update(selectedRestaurant);
                    Toast.makeText(this,
                            String.format(
                                    getString(R.string.update_toast_format_string), selectedRestaurant.getName()),
                            Toast.LENGTH_LONG).show();
                }
                // close this form when we are done
                finish();
                break;
            case R.id.delete_button:
                if (selectedRestaurant != null) {
                    showConfirmDialog();
                }
                else // we got here via Add
                {
                    Toast.makeText(this, "What is there to delete?", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.cancel_button:
                // just close the form
                finish();
                break;
            default:
                break;
        }
    }

    private void load() {
        // get the Restaurant for the current id from the DBHelper class
        // we need the restaurantID to be a class level variable to do updates
        selectedRestaurant = dbHelper.getRestaurant(restaurantId);

        // set each value on the form from the current instance
        name.setText(selectedRestaurant.getName());
        address.setText(selectedRestaurant.getAddress());
        note.setText(selectedRestaurant.getNote());
        if (selectedRestaurant.isDelivery()) {
            types.check(R.id.delivery_choice);
        }
        else if (selectedRestaurant.isDineIn()){
            types.check(R.id.dinein_choice);
        }
        else if (selectedRestaurant.isCarryOut()) {
            types.check(R.id.takeout_choice);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

    public void showConfirmDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new ConfirmDialog();
        dialog.show(getFragmentManager(), "ConfirmDialog");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Toast.makeText(this, "Deleting " + selectedRestaurant.getName() + "...", Toast.LENGTH_LONG).show();
        dbHelper.delete(selectedRestaurant.getID());
        finish(); // go back to the list
    }
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Toast.makeText(this, "Cancelled delete", Toast.LENGTH_SHORT).show();
    }


}
