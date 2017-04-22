package whitaker.anthony.bootcamplocator.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import whitaker.anthony.bootcamplocator.R;
import whitaker.anthony.bootcamplocator.model.BootcampLocation;

/**
 * @author Anthony Whitaker.
 */
public class LocationViewHolder extends RecyclerView.ViewHolder {

    private ImageView locationImageView;
    private TextView titleTextView;
    private TextView addressTextView;

    public LocationViewHolder(View itemView) {
        super(itemView);

        locationImageView = (ImageView)itemView.findViewById(R.id.location_image);
        titleTextView = (TextView)itemView.findViewById(R.id.location_title);
        addressTextView = (TextView)itemView.findViewById(R.id.location_address);
    }

    public void updateUI(BootcampLocation location) {
        locationImageView.setImageResource(location.getImageId());
        titleTextView.setText(location.getTitle());
        addressTextView.setText(location.getAddress());
    }
}
