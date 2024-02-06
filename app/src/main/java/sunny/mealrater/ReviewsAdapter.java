package sunny.mealrater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter {

    private ArrayList<Dish> reviewData; // this variable holds the data to be displayed
    private Context parentContext;
    private View.OnClickListener mOnItemClickListener;

    private boolean isDeleting;


    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        public TextView tvReviewName;
        public TextView tvReviewType;
        public TextView tvReviewRating;

        public Button buttonDelete;


        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewName = itemView.findViewById(R.id.textReviewName);
            tvReviewType = itemView.findViewById(R.id.textReviewType);
            tvReviewRating = itemView.findViewById(R.id.textReviewRating);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteReview);
            itemView.setTag(this); // identifies which item was clicked

            // set the onclicklistener from the one passed in activity
            itemView.setOnClickListener(mOnItemClickListener);

        }

        public TextView getTvReviewName() {
            return tvReviewName;
        }
        public TextView getTvReviewType() {
            return tvReviewType;
        }
        public TextView getTvReviewRating() {
            return tvReviewRating;
        }
        public Button getButtonDelete() { return buttonDelete; }

    }

    public ReviewsAdapter(ArrayList<Dish> arrayList, Context context) {
        reviewData = arrayList;
        parentContext = context;
    }
    @NonNull
    @Override // view holder is now owned by this Adapter
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, parent, false);
        return new ReviewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReviewsViewHolder rvh = (ReviewsViewHolder) holder;
        rvh.getTvReviewName().setText(reviewData.get(position).getName());
        rvh.getTvReviewType().setText(reviewData.get(position).getType());
        rvh.getTvReviewRating().setText(reviewData.get(position).getRating());

        if (isDeleting) {
            rvh.getButtonDelete().setVisibility(View.VISIBLE);
            rvh.getButtonDelete().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                }
            });
        } else {
            rvh.getButtonDelete().setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return reviewData.size();
    }

    public void setOnItemClickListener(View.OnClickListener listener) {
        mOnItemClickListener = listener;
    }

    public void changeReviews(ArrayList<Dish> newReviews) {
        reviewData = newReviews;
        notifyDataSetChanged();
    }

    private void deleteItem(int position) {
        int dishID = reviewData.get(position).getDishID();
        boolean didDelete;
        try {
            DishDataSource ds = new DishDataSource(parentContext);
            didDelete = ds.deleteReview(dishID);

            if (didDelete) {
                reviewData.remove(position);
                notifyDataSetChanged();
            } else {
                Toast.makeText(parentContext, "Delete Failed!", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(parentContext, "Delete Failed", Toast.LENGTH_LONG).show();
        }
    }

    public void setDeleting(boolean b) {
        isDeleting = b;
    }

}
