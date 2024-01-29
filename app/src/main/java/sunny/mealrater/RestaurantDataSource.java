package sunny.mealrater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

public class RestaurantDataSource  {

    private SQLiteDatabase database;
    private DishRatingDBHelper dbHelper;

    private static final String SELECT_RESTAURANTS =
            "SELECT restaurantID, name, streetaddress FROM restaurant";

    public RestaurantDataSource(Context context) {
        dbHelper = new DishRatingDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertRestaurant(Restaurant r) {
        boolean didSucceed = false;

        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", r.getName());
            initialValues.put("streetaddress", r.getStreetAddress());
            initialValues.put("city", r.getCity());
            initialValues.put("state", r.getState());
            initialValues.put("zipcode", r.getZipcode());

            didSucceed = database.insert("restaurant", null, initialValues) > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return didSucceed;
    }

    public int getLastRestaurantID() {
        int lastID;

        try {
            String query = "SELECT MAX(restaurantID) from restaurant";

            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();
            lastID = cursor.getInt(0);
            cursor.close();
        } catch (Exception e){
            lastID = -1;
        }

        return lastID;
    }

    public HashMap<Integer, String> selectRestaurants() {
        HashMap<Integer, String> restaurants = new HashMap<>();

        try {
            Cursor cursor = database.rawQuery(SELECT_RESTAURANTS, null);

            while (!cursor.isLast()) {
                restaurants.put(cursor.getInt(0), cursor.getString(1) + "\n" + cursor.getString(2));
                cursor.move(1);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
