package madman.david.lunchmenudavidking;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;

/**
 * Created by david on 5/8/16.
 */
public class ConfirmDialog extends DialogFragment {
    private String message = "Confirm Action";
    private String positiveMessage = "Perform Action";
    private String negativeMessage = "Cancel Action";

    public static ConfirmDialog newInstance(int index) {
        ConfirmDialog f = new ConfirmDialog();
        return f;
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to
     * query it. */
    public interface ConfirmDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
        void onDialogNegativeClick(DialogFragment dialog);
    }

    // Use this instance of the interface to deliver action events
    ConfirmDialogListener mListener;

    // Override the Fragment.onAttach() method to instantiate the ConfirmDialogListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // link back to the host activity
            mListener = (ConfirmDialogListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString() + " must implement ConfirmDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build the dialog with click handlers
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Delete Record")
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Send the positive button back to the Activity
                        mListener.onDialogPositiveClick(ConfirmDialog.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Send the positive button back to the Activity
                        mListener.onDialogNegativeClick(ConfirmDialog.this);
                    }
                });


        return builder.create();
    }

}
