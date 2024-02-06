package sunny.mealrater;

import android.content.Intent;
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
        innitHomeButton();
        innitRestaurantButton();
        innitTypeDropdown();
        innitRestaurantDropdown();
        innitTextChange();
        innitSaveRatingButton();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            innitDishReview(extras.getInt("dishID"), extras.getInt("restaurantID"));
            setForEditing(false);
        } else {
            currentDish = new Dish();
        }


    }

    private void innitHomeButton() {
        ImageButton ibHome = findViewById(R.id.imageButtonHome);
        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RateDishActivity.this, ReviewListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    private void innitRestaurantButton() {
        ImageButton ibHome = findViewById(R.id.imageButtonRestaurant);
        ibHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RateDishActivity.this, AddRestaurantActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
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
                    if (!ds.isDuplicateDish(currentDish)) {
                        wasSuccessful = ds.insertRating(currentDish);

                    } else {
                        // update rating here
                        wasSuccessful = ds.updateRating(currentDish);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (wasSuccessful) {
                    // display error msg
                    displayCompletion.setTextColor(getResources().getColor(R.color.success));
                    displayCompletion.setText("Rating Saved Successfully!");
                    displayCompletion.setVisibility(View.VISIBLE);

                } else {
                    displayCompletion.setTextColor(getResources().getColor(R.color.error));
                    displayCompletion.setText("Save Failed.");
                    displayCompletion.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setForEditing(boolean b) {
        Spinner restaurantDropdown = findViewById(R.id.spinnerRestaurant);
        Spinner dishTypeDropdown = findViewById(R.id.spinnerDishType);
        EditText editName = findViewById(R.id.editDishName);

        restaurantDropdown.setEnabled(b);
        dishTypeDropdown.setEnabled(b);
        editName.setEnabled(b);

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

    private void innitDishReview(int dishID, int restaurantID) {
        TextView displayRestaurant = findViewById(R.id.textDisplayRestaurant);
        TextView displayType = findViewById(R.id.textDisplayType);
        Spinner restaurantDropDown = findViewById(R.id.spinnerRestaurant);
        Spinner typeDropDown = findViewById(R.id.spinnerDishType);
        EditText editDishName = findViewById(R.id.editDishName);
        RatingBar rbDish = findViewById(R.id.ratingBar2);

        DishDataSource ds = new DishDataSource(RateDishActivity.this);

        currentDish = ds.getSpecificDish(dishID, restaurantID);

        displayRestaurant.setText(ds.getSpecificRestaurant(currentDish.getRestaurantID()));
        displayType.setText(currentDish.getType());
        displayRestaurant.setVisibility(View.VISIBLE);
        displayType.setVisibility(View.VISIBLE);

        editDishName.setText(currentDish.getName());
        rbDish.setRating(Float.valueOf(currentDish.getRating()));

        restaurantDropDown.setVisibility(View.INVISIBLE);
        typeDropDown.setVisibility(View.INVISIBLE);

    }
}

