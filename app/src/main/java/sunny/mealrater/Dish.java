package sunny.mealrater;

public class Dish {

    private int dishID;
    private int restaurantID;
    private String name;
    private String type;
    private String rating;

    public Dish() {
        dishID = -1;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(double rating) {
        // convert double into string
        this.rating = String.valueOf(rating);
    }
}
