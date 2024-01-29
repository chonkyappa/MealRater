package sunny.mealrater;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class MainActivity extends AppCompatActivity implements RateMealDialog.SaveRatingListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        innitRateMealButton();
    }

    private void innitRateMealButton() {

        Button rateMealButton = findViewById(R.id.buttonRateMeal);

        rateMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.FragmentManager fm = getFragmentManager();
                RateMealDialog rateMealDialog = new RateMealDialog();
                rateMealDialog.show(fm, "Rate Your Meal");
            }
        });
    }

    public void didFinishRatingMeal(double rating) {
        TextView showRating = findViewById(R.id.textShowRating);
        showRating.setText(String.format("%.1f/5", rating));
    }
}