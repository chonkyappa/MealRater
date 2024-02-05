package sunny.mealrater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class DishDataSource {

    private SQLiteDatabase database;
    private DishRatingDBHelper dbHelper;

    private static final String SELECT_RESTAURANTS =
            "SELECT name FROM restaurant";

    private static final String SELECT_RESTAURANTID =
            "SELECT restaurantID FROM restaurant WHERE name = ";

    public DishDataSource(Context context) {
        dbHelper = new DishRatingDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public String[] selectRestaurants() {
        String[] restaurants = new String[1];

        try {
            open();
            Cursor cursor = database.rawQuery(SELECT_RESTAURANTS, null);

            // returns number of rows and creates array with that size
            restaurants = new String[cursor.getCount()];

            int i = 0;
            while (cursor.moveToNext()) {
                restaurants[i++] = cursor.getString(0);
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurants;
    }

    public int selectRestaurantID(String restaurantName) {
        int id = -1;
        try {
            open();
            Cursor cursor = database.rawQuery(SELECT_RESTAURANTID + " '" + restaurantName + "'", null);
            cursor.moveToFirst();
            id = cursor.getInt(0);
        } catch (Exception e) {

        }
        return id;
    }

    public boolean insertRating(Dish d) {
        boolean didSucceed = false;

        try {
            open();
            ContentValues initialValues = new ContentValues();
            initialValues.put("name", d.getName());
            initialValues.put("type", d.getType());
            initialValues.put("rating", d.getRating());
            initialValues.put("restaurantID", d.getRestaurantID());

            didSucceed = database.insert("dish", null, initialValues) > 0;
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return didSucceed;
    }

    public boolean isDuplicateRating(String dishName, int restaurantID) {
        boolean isDuplicate = false;

        try {
            open();
            String query = "SELECT * FROM dish WHERE name = '" + dishName + "' AND restaurantID = '" + restaurantID + "'";
            Cursor cursor = database.rawQuery(query, null);
            isDuplicate = cursor.getCount() > 0;
            close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return isDuplicate;
    }
}