package whitaker.anthony.bootcamplocator.service;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import whitaker.anthony.bootcamplocator.R;
import whitaker.anthony.bootcamplocator.model.BootcampLocation;

/**
 * @author Anthony Whitaker.
 */
public class DataService {
    private static final DataService sharedInstance = new DataService();

    public static DataService getSharedInstance() {
        return sharedInstance;
    }

    private DataService() {
    }

    /**
     * Download bootcamp locations from server within 10 miles of given zip code.
     * @param zipcode Zip code to search around.
     * @return List of bootcamp location within 10 miles.
     */
    public List<BootcampLocation> getBootcampLocationsWithin10Miles(int zipcode) {
        ArrayList<BootcampLocation> list = new ArrayList<>();
        list.add(new BootcampLocation(new LatLng(35.279f,-120.663f),"Downtown", "762 Higuera Street, San Luis Obispo, CA 93401", R.drawable.slo));
        list.add(new BootcampLocation(new LatLng(35.302f,-120.658f),"On The Campus", "1 Grand Ave, San Luis Obispo, CA 93401", R.drawable.slo));
        list.add(new BootcampLocation(new LatLng(35.267f,-120.652f),"East Side Tower", "2494 Victoria Ave, San Luis Obispo, CA 93401", R.drawable.slo));
        return list;
    }
}
