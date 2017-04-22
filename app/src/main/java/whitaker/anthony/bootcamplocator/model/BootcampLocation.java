package whitaker.anthony.bootcamplocator.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Anthony Whitaker.
 */
public class BootcampLocation {

    private LatLng latLng;
    private String title;
    private String address;
    private String imageUrl;

    public BootcampLocation(LatLng latLng, String title, String address, String imageUrl) {
        this.latLng = latLng;
        this.title = title;
        this.address = address;
        this.imageUrl = imageUrl;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
