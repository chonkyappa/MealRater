package sunny.mealrater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements RateMealDialog.SaveRatingListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        innitAddRestaurantButton();
        innitRateDishButton();
    }

    private void innitAddRestaurantButton() {

        Button addRestaurant = findViewById(R.id.buttonAddRestaurant);

        addRestaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRestaurantActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void innitRateDishButton() {
        Button rateDish = findViewById(R.id.buttonRateDish);
        rateDish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RateDishActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    public void didFinishRatingMeal(double rating) {
        TextView showRating = findViewById(R.id.textShowRating);
        showRating.setText(String.format("%.1f/5", rating));
    }
}