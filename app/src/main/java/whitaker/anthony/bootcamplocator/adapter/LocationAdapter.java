package whitaker.anthony.bootcamplocator.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import whitaker.anthony.bootcamplocator.R;
import whitaker.anthony.bootcamplocator.holder.LocationViewHolder;
import whitaker.anthony.bootcamplocator.model.BootcampLocation;

/**
 * @author Anthony Whitaker.
 */
public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {

    List<BootcampLocation> locations;

    public LocationAdapter(List<BootcampLocation> locations) {
        this.locations = locations;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View card = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_location, parent, false);
        return new LocationViewHolder(card);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        final BootcampLocation location = locations.get(position);
        holder.updateUI(location);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load details page
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
