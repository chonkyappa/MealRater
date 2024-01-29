package sunny.mealrater;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class RateDishActivity extends AppCompatActivity {

    Dish currentDish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_dish);
        innitTypeDropdown();
        innitRestaurantDropdown();
        currentDish = new Dish();
    }

    private void innitRestaurantDropdown() {
        Spinner dropdownRestaurants = findViewById(R.id.spinnerRestaurant);
        HashMap<Integer, String> restaurants;

        DishDataSource ds = new DishDataSource(RateDishActivity.this);
        restaurants = ds.selectRestaurants();
        String[] restaurantList = new String[restaurants.size()];
        int i = 0;
        for (String place: restaurants.values()) {
            restaurantList[i++] = place;
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(RateDishActivity.this, android.R.layout.simple_spinner_dropdown_item, restaurantList);
        dropdownRestaurants.setAdapter(adapter);
    }

    private void innitTypeDropdown() {
        Spinner dropdownType = findViewById(R.id.spinnerDishType);

        // array of different dish types
        String[] dishTypes = {"Entree", "Appetizer", "Dessert"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(RateDishActivity.this, android.R.layout.simple_spinner_dropdown_item, dishTypes);
        // first arg: context of the activity (can either put "this" or "[class name].this
        //  what activity this takes place in?
        // second arg: used to define how the drop down looks?
        //  in this case, we use a defined, simple layout
        // third arg: stuff in the drop down
        //  in this case, it's an array of strings
        dropdownType.setAdapter(adapter);
    }

    private void innitTextChange() {
        EditText etDName = findViewById(R.id.editDishName);
        etDName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentDish.setName(etDName.getText().toString());
            }
        });

        Spinner dropdownType = findViewById(R.id.spinnerDishType);
        dropdownType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentDish.setType(dropdownType.getItemAtPosition(position).toString());
                System.out.println(currentDish.getType());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Spinner dropdownRestaurant = findViewById(R.id.spinnerRestaurant);
        dropdownRestaurant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        RatingBar stars = findViewById(R.id.ratingBar2);
        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                currentDish.setRating(rating);
            }
        });

    }
}
