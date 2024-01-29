package sunny.mealrater;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;

public class DishDataSource {

    private SQLiteDatabase database;
    private DishRatingDBHelper dbHelper;

    private static final String SELECT_RESTAURANTS =
            "SELECT restaurantID, name, streetaddress FROM restaurant";

    public DishDataSource(Context context) {
        dbHelper = new DishRatingDBHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public HashMap<Integer, String> selectRestaurants() {
        HashMap<Integer, String> restaurants = new HashMap<>();

        try {
            open();
            Cursor cursor = database.rawQuery(SELECT_RESTAURANTS, null);

            // move to the first row
            cursor.moveToFirst();

            // utilized do-while loop to ensure even if there is only 1 restaurant in the db
            // it will show up
            do {
                restaurants.put(cursor.getInt(0), cursor.getString(1) + "\n" + cursor.getString(2));
                cursor.move(1);
            } while (!cursor.isLast());
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return restaurants;
    }
}
