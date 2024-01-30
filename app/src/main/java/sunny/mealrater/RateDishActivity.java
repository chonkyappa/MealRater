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
        innitTextChange();
        innitSaveRatingButton();
        currentDish = new Dish();
    }

    private void innitSaveRatingButton() {
        Button saveRatingButton = findViewById(R.id.buttonSaveRating);
        TextView displayCompletion = findViewById(R.id.textRatingError);
        saveRatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DishDataSource ds = new DishDataSource(RateDishActivity.this);
                boolean wasSuccessful = false;

                try {
                    if (!ds.isDuplicateRating(currentDish.getName(), currentDish.getRestaurantID())) {
                        wasSuccessful = ds.insertRating(currentDish);
                    }
                } catch (Exception e) {

                }

                if (wasSuccessful) {
                    // display success message
                    displayCompletion.setTextColor(getResources().getColor(R.color.success));
                    displayCompletion.setText("Success!");
                    displayCompletion.setVisibility(View.VISIBLE);
                } else {
                    // display error message, duplicate dish rating
                    displayCompletion.setTextColor(getResources().getColor(R.color.error));
                    displayCompletion.setText("You already rated this dish! Please rate a different one.");
                    displayCompletion.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void innitRestaurantDropdown() {
        Spinner dropdownRestaurants = findViewById(R.id.spinnerRestaurant);
        DishDataSource ds = new DishDataSource(RateDishActivity.this);
        String[] restaurants = ds.selectRestaurants();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(RateDishActivity.this, android.R.layout.simple_spinner_dropdown_item, restaurants);
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
        DishDataSource ds = new DishDataSource(RateDishActivity.this);
        EditText etDName = findViewById(R.id.editDishName);
        etDName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                TextView displayCompletion = findViewById(R.id.textRatingError);
                displayCompletion.setVisibility(View.INVISIBLE);
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

        Spinner dropdownRestaurants = findViewById(R.id.spinnerRestaurant);
        dropdownRestaurants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentDish.setRestaurantID(ds.selectRestaurantID(dropdownRestaurants.getItemAtPosition(position).toString()));
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
