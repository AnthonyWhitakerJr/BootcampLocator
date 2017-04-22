package whitaker.anthony.bootcamplocator.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import whitaker.anthony.bootcamplocator.R;
import whitaker.anthony.bootcamplocator.fragment.MainFragment;

public class MapsActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    public static final int PERMISSION_LOCATION_REQUEST_CODE = 111;
    public static final String LOG_TAG = MapsActivity.class.getSimpleName();

    private GoogleApiClient googleApiClient;
    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.container_main);
        if(mainFragment == null) {
            mainFragment = MainFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.container_main, mainFragment).commit();
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v(LOG_TAG, "Lat:" + location.getLatitude() + "Long:" + location.getLongitude());
        mainFragment.setUserMarker(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION_REQUEST_CODE);
            Log.v(LOG_TAG, "Requesting permissions.");
        } else {
            Log.v(LOG_TAG, "Permissions already granted.");
            startLocationServices();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_LOCATION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(LOG_TAG, "Permissions granted. Starting services.");
                    startLocationServices();
                } else {
                    Log.v(LOG_TAG, "Permission not granted.");
                    Toast.makeText(this, "No permission = app no workie. You broked da codez!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    public void startLocationServices() {
        Log.v(LOG_TAG, "Starting location services.");

        try {
            LocationRequest request = LocationRequest.create().setPriority(LocationRequest.PRIORITY_LOW_POWER);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, request, this);
            Log.v(LOG_TAG, "Location updates requested.");
        } catch (SecurityException exception) {
            Toast.makeText(this, "No permission = app no workie. You broked da codez!", Toast.LENGTH_SHORT).show();
            Log.v(LOG_TAG, exception.toString());
        }
    }
}
