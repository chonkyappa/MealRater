package sunny.mealrater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DishRatingDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dishrating.db";
    private static final int DATABASE_VERSION = 1;

    private static final String CREATE_TABLE_RESTAURANT =
            "create table restaurant (restaurantID integer primary key autoincrement, " +
                    "name text not null," +
                    "streetaddress text," +
                    "city text," +
                    "state text," +
                    "zipcode text);";

    private static final String CREATE_TABLE_DISH =
            "create table dish (dishID integer primary key autoincrement," +
                    "name text not null," +
                    "type text," +
                    "rating text," +
                    "restaurantID integer, FOREIGN KEY (restaurantID) references restaurant(restaurantid));";

    public DishRatingDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANT);
        db.execSQL(CREATE_TABLE_DISH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(DishRatingDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion +
                "to version " + newVersion + ". Deleting all old data.");
        db.execSQL("DROP TABLE IF EXISTS restaurants");
        db.execSQL("DROP TABLE IF EXISTS dish");
        onCreate(db);
    }
}
