package info.dagrut.www.a04_geoloc;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    LocationManager mLocationManager;
    LocationListener mLocationListener;
    TextView mGeolocText;
    static final private int REQUEST_LOCATION = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGeolocText = (TextView) findViewById(R.id.geoloc_text);

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                Log.i("Geoloc", "Location changed");
                mGeolocText.setText(getString(R.string.geoloc, location.getLatitude(), location.getLongitude()));
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("Geoloc", "Location status changed to "+status);
                Toast.makeText(MainActivity.this, "Status changed to : "+status, Toast.LENGTH_LONG).show();
            }

            public void onProviderEnabled(String provider) {
                Log.i("Geoloc", "Provider enabled");
                Toast.makeText(MainActivity.this, "Provider "+provider+" enabled", Toast.LENGTH_LONG).show();
            }

            public void onProviderDisabled(String provider) {
                Log.i("Geoloc", "Provider disabled");
                Toast.makeText(MainActivity.this, "Provider "+provider+" disabled", Toast.LENGTH_LONG).show();
            }
        };

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }
        else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
            }
            else {
                Toast.makeText(this, "GPS permission was denied!", Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
