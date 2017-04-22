package whitaker.anthony.bootcamplocator.model;

import com.google.android.gms.maps.model.LatLng;

/**
 * @author Anthony Whitaker.
 */
public class BootcampLocation {

    private LatLng latLng;
    private String title;
    private String address;
    private int imageId;

    public BootcampLocation(LatLng latLng, String title, String address, int imageId) {
        this.latLng = latLng;
        this.title = title;
        this.address = address;
        this.imageId = imageId;
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

    public int getImageId() {
        return imageId;
    }
}
