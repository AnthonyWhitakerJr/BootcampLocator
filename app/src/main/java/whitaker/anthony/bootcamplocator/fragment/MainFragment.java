package whitaker.anthony.bootcamplocator.fragment;


import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import whitaker.anthony.bootcamplocator.R;
import whitaker.anthony.bootcamplocator.activity.MapsActivity;
import whitaker.anthony.bootcamplocator.model.BootcampLocation;
import whitaker.anthony.bootcamplocator.service.DataService;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment implements OnMapReadyCallback {

    public static final String LOG_TAG = MainFragment.class.getSimpleName();

    /** Standard zip code length for user location. Use constant for Development only. */
    public static final int ZIP_CODE_LENGTH = 5;


    private GoogleMap map;
    private MarkerOptions userMarker;

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MainFragment.
     */
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        final EditText zipCodeText = (EditText)view.findViewById(R.id.zipCodeText);
        zipCodeText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(KeyEvent.ACTION_DOWN == event.getAction() && KeyEvent.KEYCODE_ENTER == keyCode) {
                    String text = zipCodeText.getText().toString();

                    int zipCode;
                    try {
                        zipCode = parseZipCode(text);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getContext(), "Invalid Zip Code", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                    dismissKeyboard(zipCodeText.getWindowToken());

                    updateMapForZipCode(zipCode);
                    return true;
                }
                return false;
            }
        });

        return view;
    }

    private void dismissKeyboard(IBinder windowToken) {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(windowToken, 0);
    }

    private int parseZipCode(String text) throws IllegalArgumentException {
        String trimmedText = text.trim();
        if(trimmedText.length() != ZIP_CODE_LENGTH) {
            throw new IllegalArgumentException("Invalid zip code: " + text);
        }

        try {
            return Integer.parseInt(trimmedText);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid zip code: " + text);
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
    }

    public void setUserMarker(LatLng latLng) {
        if(userMarker == null) {
            userMarker = new MarkerOptions().position(latLng).title("Current Location");
            map.addMarker(userMarker);
            Log.v(LOG_TAG, "Current location: " + latLng);
        }

        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            int zipCode = parseZipCode(addresses.get(0).getPostalCode());
            updateMapForZipCode(zipCode);
        } catch (IOException e) {
            Log.d(LOG_TAG, e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            Toast.makeText(getContext(), "Invalid Zip Code", Toast.LENGTH_SHORT).show();
            Log.e(LOG_TAG, "Faulty zip code.", e);
        }

        updateMapForZipCode(92284);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
    }

    private void updateMapForZipCode(int zipcode) {
        List<BootcampLocation> locations = DataService.getSharedInstance().getBootcampLocationsWithin10Miles(zipcode);

        for(BootcampLocation location : locations) {
            MarkerOptions marker = new MarkerOptions()
                    .position(location.getLatLng())
                    .title(location.getTitle())
                    .snippet(location.getAddress())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_pin));
            map.addMarker(marker);
        }
    }

}
