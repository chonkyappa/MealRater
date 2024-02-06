package sunny.mealrater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    ReviewsAdapter reviewsAdapter;
    ArrayList<Dish> allReviews;
    String selectedRestaurant;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int dishID = allReviews.get(position).getDishID();
            int restaurantID = allReviews.get(position).getRestaurantID();
            Intent intent = new Intent(ReviewListActivity.this, RateDishActivity.class);
            intent.putExtra("dishID", dishID);
            intent.putExtra("restaurantID", restaurantID);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_list);
        innitDishButton();
        innitRestaurantButton();
        innitAddReviewButton();
        innitRestaurantDropdown();
        innitDeleteSwitch();

    }

    @Override
    public void onResume() {
        super.onResume();
        DishDataSource ds = new DishDataSource(ReviewListActivity.this);
        int restaurantID = 1;
        allReviews = ds.getReviews(restaurantID);

        try {
            if (allReviews.size() > 0) {
                RecyclerView rvReviews = findViewById(R.id.rvReviewList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReviewListActivity.this);
                rvReviews.setLayoutManager(layoutManager);
                reviewsAdapter = new ReviewsAdapter(allReviews, this);
                reviewsAdapter.setOnItemClickListener(onItemClickListener);
                rvReviews.setAdapter(reviewsAdapter);
                innitDropdownListener();
            } else {
                Intent intent = new Intent(ReviewListActivity.this, MainMenuActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error retrieving reviews.", Toast.LENGTH_LONG).show();
        }
    }
    private void innitDishButton() {
        ImageButton dishImageButton = findViewById(R.id.imageButtonDish);
        dishImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewListActivity.this, RateDishActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void innitRestaurantButton() {
        ImageButton restaurantImageButton = findViewById(R.id.imageButtonRestaurant);
        restaurantImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewListActivity.this, AddRestaurantActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void innitAddReviewButton() {
        Button addReviewB = findViewById(R.id.buttonAddReview);
        addReviewB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewListActivity.this, RateDishActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void innitRestaurantDropdown() {
        Spinner restaurantDropdown = findViewById(R.id.spinnerSelectedRestaurant);
        DishDataSource ds = new DishDataSource(ReviewListActivity.this);
        String[] restaurants = ds.selectRestaurants();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(ReviewListActivity.this, android.R.layout.simple_spinner_dropdown_item, restaurants);
        restaurantDropdown.setAdapter(adapter);
    }

    private void innitDropdownListener() {
        Spinner restaurantDropdown = findViewById(R.id.spinnerSelectedRestaurant);
        restaurantDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRestaurant = restaurantDropdown.getItemAtPosition(position).toString();
                DishDataSource ds = new DishDataSource(ReviewListActivity.this);
                allReviews = ds.getReviews(ds.selectRestaurantID(selectedRestaurant));
                reviewsAdapter.changeReviews(allReviews);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void innitDeleteSwitch() {
        Switch deleteReviewSwitch = findViewById(R.id.switchDelete);
        deleteReviewSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                reviewsAdapter.setDeleting(isChecked);
                reviewsAdapter.notifyDataSetChanged();
            }
        });
    }

}
