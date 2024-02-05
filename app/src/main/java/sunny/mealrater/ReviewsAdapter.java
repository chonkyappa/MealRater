package sunny.mealrater;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter {

    private ArrayList<Dish> reviewData; // this variable holds the data to be displayed

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {

        public TextView tvReviewName;
        public TextView tvReviewType;
        public TextView tvReviewRating;

        public ReviewsViewHolder(@NonNull View itemView) {
            super(itemView);
            tvReviewName = itemView.findViewById(R.id.textReviewName);
            tvReviewType = itemView.findViewById(R.id.textReviewType);
            tvReviewRating = itemView.findViewById(R.id.textReviewRating);
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
    }

    public ReviewsAdapter(ArrayList<Dish> arrayList) {
        reviewData = arrayList;
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

    }

    @Override
    public int getItemCount() {
        return reviewData.size();
    }
}
