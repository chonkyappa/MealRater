package sunny.mealrater;

import android.os.Bundle;
import android.app.DialogFragment; // android.support.v4.app.DialogFragment does not work
import java.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RatingBar;

public class RateMealDialog extends DialogFragment {

    float userRating;

    public RateMealDialog() {
        // empty constructor is required to make dialogs
    }

    // created an inner class that requires implementation to pass the info from dialog back to the activity
    public interface SaveRatingListener {
        void didFinishRatingMeal(double rating);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.rate_stars, container);

        getDialog().setTitle("Rate Meal");

        RatingBar stars = (RatingBar) view.findViewById(R.id.ratingBar);
        userRating = stars.getRating();

        stars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                userRating = rating;
            }
        });

        Button saveB = (Button) view.findViewById(R.id.buttonSave);
        saveB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                saveRating(userRating);
            }
        });
        return view;

    }

    private void saveRating(double rating) {
        SaveRatingListener activity = (SaveRatingListener)getActivity();
        activity.didFinishRatingMeal(rating);
        getDialog().dismiss();
    }
}
