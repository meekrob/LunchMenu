package madman.david.lunchmenudavidking;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Debug;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by david on 4/11/16.
 */
public class DBHelper extends SQLiteOpenHelper {
    // constants for database version and name
    private static final String DBNAME = "restaurants.db";
    private static final int SCHEMA_VERSION = 1;
    public static final String TABLE_NAME = "restaurants"; // don't put something stupid here like a period
    private Context _context;

    public DBHelper(Context context) {
        super(context, DBNAME, null, SCHEMA_VERSION);
        _context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = String.format(
                "CREATE TABLE `%s` (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, address TEXT, type TEXT, note TEXT)",
                TABLE_NAME);

        Log.d("DBHelper", sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void insert(Restaurant restaurant) {
        ContentValues cv = new ContentValues();
        cv.put("name", restaurant.getName());
        cv.put("address", restaurant.getAddress());
        cv.put("type", restaurant.getType());
        cv.put("note",restaurant.getNote());
        getWritableDatabase().insert(TABLE_NAME, "abc", cv);
    }

    // retrieval functions
    public Cursor getAll() {
        String sql = String.format("SELECT _id, name, address, type, note FROM `%s` ORDER BY name", TABLE_NAME);
        return getReadableDatabase().rawQuery(sql, null);
    }

    public Restaurant getRestaurant(Cursor c) {
        Restaurant r = new Restaurant(
                getName(c),
                getAddress(c),
                getType(c),
                getNote(c),
                getID(c));

        return r;
    }
    public Restaurant getRestaurant(String id) {
        //first run a query to get by id
        //create an array of parameters to pass to query
        String[] args = {id};
        String sql = String.format("SELECT * FROM `%s` WHERE _id=?", TABLE_NAME);
        //get a cursor that points to one row from the database
        Cursor cursor = getReadableDatabase().rawQuery(sql, args);
        cursor.moveToFirst();
        return getRestaurant(cursor);
    }

    public String getName(Cursor c) {
        return c.getString(c.getColumnIndex("name"));
    }
    public String getAddress(Cursor c) {
        return c.getString(c.getColumnIndex("address"));
    }
    public String getNote(Cursor c) {
        return c.getString(c.getColumnIndex("note"));
    }
    public String getType(Cursor c) {return c.getString(c.getColumnIndex("type"));}
    public String getID(Cursor c) {
        int id = c.getInt(c.getColumnIndex("_id"));
        return Integer.toString(id);
    }

    public void update(Restaurant restaurant) {
        ContentValues cv = new ContentValues();
        cv.put("name", restaurant.getName());
        cv.put("address", restaurant.getAddress());
        cv.put("type", restaurant.getType());
        cv.put("note", restaurant.getNote());
        // use single ID argument
        String args[] = {restaurant.getID()};
        getWritableDatabase().update(TABLE_NAME, cv, "_id=?", args);
    }

    public void delete(String id) {
        String args[] = {id};
        getWritableDatabase().delete(TABLE_NAME, "_id=?", args);
    }

}
