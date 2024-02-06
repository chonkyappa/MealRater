package sunny.mealrater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
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

    public boolean updateRating(Dish updatedDish) {
        boolean updateSuccessful = false;

        try {
            int rowID = updatedDish.getDishID();
            ContentValues updatedValues = new ContentValues();
            open();
            //String query = "UPDATE dish SET rating = " + updatedDish.getRating() + " WHERE dishID = " + updatedDish.getRestaurantID();
            //Cursor cursor = database.rawQuery(query, null);

            //updateSuccessful = cursor.getCount() > 0;
            updatedValues.put("dishID", updatedDish.getDishID());
            updatedValues.put("name", updatedDish.getName());
            updatedValues.put("type", updatedDish.getType());
            updatedValues.put("rating", updatedDish.getRating());
            updatedValues.put("restaurantID", updatedDish.getRestaurantID());
            updateSuccessful = database.update("dish", updatedValues, "dishID =" + rowID, null) > 0;
            close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return updateSuccessful;
    }

    public boolean isDuplicateDish(Dish dish) {
        boolean isDuplicate = false;

        try {
            open();
            String query = "SELECT * FROM dish WHERE name = '" + dish.getName() + "' AND restaurantID = " + dish.getRestaurantID();
            Cursor cursor = database.rawQuery(query, null);
            isDuplicate = cursor.getCount() > 0;
            close();
        } catch (Exception e){
            e.printStackTrace();
        }

        return isDuplicate;
    }
    public ArrayList<Dish> getReviews(int restaurantID) {
        ArrayList<Dish> reviews = new ArrayList<Dish>();
        Dish newReview;

        try {
            String query = "SELECT * FROM dish WHERE restaurantID = " + restaurantID;
            open();
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {
                newReview = new Dish();
                newReview.setDishID(cursor.getInt(0));
                newReview.setName(cursor.getString(1));
                newReview.setType(cursor.getString(2));
                newReview.setRating(Double.valueOf(cursor.getString(3)));
                newReview.setRestaurantID(restaurantID);
                reviews.add(newReview);
                cursor.moveToNext();
            }
            close();
        } catch (Exception e){
            // any exception -> return an empty list and print stack trace
            e.printStackTrace();
            reviews = new ArrayList<Dish>();
        }

        return reviews;
    }

    public Dish getSpecificDish(int dishID, int restaurantID) {
        Dish specificDish = new Dish();

        try {
            open();
            String query = "SELECT * FROM dish WHERE dishID = " + dishID + " AND restaurantID = " + restaurantID;
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();

            specificDish.setDishID(cursor.getInt(0));
            specificDish.setName(cursor.getString(1));
            specificDish.setType(cursor.getString(2));
            specificDish.setRating(Double.parseDouble(cursor.getString(3)));
            specificDish.setRestaurantID(cursor.getInt(4));

            close();
        } catch (Exception e) {
            specificDish = new Dish(); // any error, just return an empty review
            e.printStackTrace();
        }

        return specificDish;
    }

    public String getSpecificRestaurant(int restaurantID) {
        String specificRestaurant;

        try {
            open();
            String query = "SELECT name FROM restaurant WHERE restaurantID = " + restaurantID;
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();

            specificRestaurant = cursor.getString(0);
            close();
        } catch (Exception e) {
            e.printStackTrace();
            specificRestaurant = "None";
        }

        return specificRestaurant;
    }

    public boolean deleteReview(int dishID) {
        boolean deleteSuccessful = false;

        try {
            open();
            deleteSuccessful = database.delete("dish", "dishID = " + dishID, null) > 0;
            close();
        } catch (Exception e) {
            // do nothing since delete successful is already false
            e.printStackTrace();
        }

        return deleteSuccessful;
    }
}
