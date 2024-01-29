package sunny.mealrater;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;

public class AddRestaurantActivity extends AppCompatActivity {

    Restaurant currentRestaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_restaurant);
        //Button addRestaurant = findViewById(R.id.buttonAdd);
        //addRestaurant.setEnabled(false); implement this later if you have time
        innitTextChange();
        innitAddButton();

        currentRestaurant = new Restaurant();
    }

    private void innitAddButton() {
        Button buttonAdd = findViewById(R.id.buttonAdd);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantDataSource ds = new RestaurantDataSource(AddRestaurantActivity.this);
                boolean wasSuccessful = false;
                try {
                    ds.open();

                    if (currentRestaurant.getRestaurantID() == -1) {
                        wasSuccessful = ds.insertRestaurant(currentRestaurant);
                        int newID = ds.getLastRestaurantID();
                        currentRestaurant.setRestaurantID(newID);
                    }

                    ds.close();
                } catch (Exception e) {
                    // wasSuccessful stays false
                }

                if (wasSuccessful) {
                    // method to clear entry fields?
                }
            }
        });
    }
    private void innitTextChange() {
        EditText etRName = findViewById(R.id.editRName);
        etRName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setName(etRName.getText().toString());
            }
        });

        EditText etStreetAddress = findViewById(R.id.editStreetAddress);
        etStreetAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setStreetAddress(etStreetAddress.getText().toString());
            }
        });

        EditText etCity = findViewById(R.id.editCity);
        etCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setCity(etCity.getText().toString());
            }
        });

        EditText etState = findViewById(R.id.editState);
        etState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setState(etState.getText().toString());
            }
        });

        EditText etZipcode = findViewById(R.id.editZipcode);
        etZipcode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentRestaurant.setZipcode(etZipcode.getText().toString());
            }
        });

    }
}
