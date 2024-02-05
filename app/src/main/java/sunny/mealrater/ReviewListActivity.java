package sunny.mealrater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewListActivity extends AppCompatActivity {

    ReviewsAdapter reviewsAdapter;
    ArrayList<Dish> allReviews;

    private View.OnClickListener onItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
            int position = viewHolder.getAdapterPosition();
            int dishID = allReviews.get(position).getDishID();
            Intent intent = new Intent(ReviewListActivity.this, RateDishActivity.class);
            intent.putExtra("dishID", dishID);
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
}
