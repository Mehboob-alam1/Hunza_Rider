package com.mehboob.hunzarider.activities;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.mapbox.core.constants.Constants.PRECISION_6;
import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.fillOpacity;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconOffset;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineCap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineColor;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineJoin;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.lineWidth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.MapboxDirections;
import com.mapbox.api.directions.v5.models.DirectionsResponse;
import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.core.exceptions.ServicesException;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.LineString;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.ActivityUserTrackBinding;
import com.mehboob.hunzarider.models.ActiveRides;
import com.mehboob.hunzarider.utils.LocationTrack;
import com.mehboob.hunzarider.utils.SharedPref;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserTrackActivity extends AppCompatActivity implements PermissionsListener {
    private ActivityUserTrackBinding binding;


    private MapboxMap mapboxMap;

    private PermissionsManager permissionsManager;


    private String searchedLocation;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    //
    private BottomSheetDialog dialog;

    private static float ZOOM_LEVEL = 16f;

    private static final String TAG = "MapsActivity";

    private SharedPref sharedPref;
    //  Location
    private String distanceTotal;

    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    private Point origin, destination;
    MarkerOptions markerOptions;


// User location



    double latitude, longitude;
    String firstResultPoint;
    DirectionsRoute drivingRoute;


    private static final LatLngBounds RESTRICTED_BOUNDS_AREA = new LatLngBounds.Builder()
            .include(Constants.BOUND_CORNER_NW)
            .include(Constants.BOUND_CORNER_NW1)
            .include(Constants.BOUND_CORNER_NW2)
            .include(Constants.BOUND_CORNER_NW3)
            .include(Constants.BOUND_CORNER_NW4)
            .include(Constants.BOUND_CORNER_NW5)
            .include(Constants.BOUND_CORNER_NW6)
            .include(Constants.BOUND_CORNER_NW7)

            .include(Constants.BOUND_CORNER_SE)
            .build();

    private final List<List<Point>> points = new ArrayList<>();
    private final List<Point> outerPoints = new ArrayList<>();

    CarmenFeature feature;
private String intentString;
private ActiveRides activeRides;
private double lon,lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getResources().getString(R.string.mapbox_access_token));
        binding = ActivityUserTrackBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedPref = new SharedPref(this);
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        intentString= getIntent().getStringExtra("data");
        lat= Double.parseDouble(getIntent().getStringExtra("lat"));
        lon= Double.parseDouble(getIntent().getStringExtra("lon"));



        Gson gson = new Gson();
        Type type = new TypeToken<ActiveRides>() {
        }.getType();
        activeRides = gson.fromJson(intentString, type);

//        mapView.onCreate(savedInstanceState);
        permissionsToRequest = findUnAskedPermissions(permissions);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
        if (isLocationEnabled()) {
            binding.mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull com.mapbox.mapboxsdk.maps.MapboxMap mapboxMap) {
                    UserTrackActivity.this.mapboxMap = mapboxMap;
                    mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
//                        mapboxMap.getUiSettings().setAttributionEnabled(false);
                            //     enableLocationComponent(style);


                            enableLocations();
//updateLocation();

                            showBoundsArea(style);


                            style.addImage("red-pin-icon-id", BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(UserTrackActivity.this, R.drawable.ic_baseline_place_24)));
                            style.addLayer(new SymbolLayer("icon-layer-id", "icon-source-id").withProperties(
                                    iconImage("red-pin-icon-id"),
                                    iconIgnorePlacement(true),
                                    iconAllowOverlap(true),
                                    iconOffset(new Float[]{0f, -0f})
                            ));
                            style.addSource(new GeoJsonSource("route-source-id"));
                            LineLayer routeLayer = new LineLayer("route-layer-id", "route-source-id");

                            routeLayer.setProperties(
                                    lineCap(Property.LINE_CAP_ROUND),
                                    lineJoin(Property.LINE_JOIN_ROUND),
                                    lineWidth(3f),
                                    lineColor(Color.parseColor("#14CA15"))
                            );
                            style.addLayer(routeLayer);

                            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lon), ZOOM_LEVEL));
                            Point userDest = Point.fromLngLat(Double.parseDouble(activeRides.getUserDestLongitude()), Double.parseDouble(activeRides.getUserDestLatitude()));
                            Point userPoint = Point.fromLngLat(Double.parseDouble(activeRides.getUserOriginLongitude()), Double.parseDouble(activeRides.getUserOriginLatitude()));
                            getRoute(mapboxMap, userPoint, userDest);


                        }
                    });
                }
            });
        } else {
            enableLocations();
        }

    }



    private void enableLocations() {

        locationTrack = new LocationTrack(UserTrackActivity.this);


        if (locationTrack.canGetLocation()) {


            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();


            markerOptions = new MarkerOptions().setIcon(IconFactory.getInstance(this).defaultMarker());
            markerOptions.title("My location");

            markerOptions.position(new LatLng(latitude, longitude));


            mapboxMap.addMarker(markerOptions);




            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), ZOOM_LEVEL));


        } else {
            UserTrackActivity.this.DialogShow();
            //  locationTrack.showSettingsAlert();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList wanted) {
        ArrayList result = new ArrayList();

        for (Object perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission((String) permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @SuppressLint("Lifecycle")
    @Override
    protected void onStart() {
        super.onStart();
        binding.mapView.onStart();

    }

    @SuppressLint("Lifecycle")
    @Override
    protected void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @SuppressLint("Lifecycle")
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @SuppressLint("Lifecycle")
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
        locationTrack.stopListener();
    }






    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

    }

    private void enableLocationComponent(Style style) {

        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(this)) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
// Activate with options

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(this, style).build());

// Enable to make component visible
            if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                locationComponent.setLocationComponentEnabled(true);

// Set the component's camera mode
                locationComponent.setCameraMode(CameraMode.TRACKING);

// Set the component's render mode
                locationComponent.setRenderMode(RenderMode.COMPASS);

            } else {
                ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 100);
            }

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            mapboxMap.getStyle(new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(style);
                }
            });
        } else {
            Toast.makeText(this, R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void showBoundsArea(@NonNull Style loadedMapStyle) {
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthWest().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthEast().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthEast().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getSouthEast().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getSouthEast().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getSouthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getSouthWest().getLatitude()));
        outerPoints.add(Point.fromLngLat(RESTRICTED_BOUNDS_AREA.getNorthWest().getLongitude(),
                RESTRICTED_BOUNDS_AREA.getNorthWest().getLatitude()));
        points.add(outerPoints);


        // serviceAreaPolygons.add(Polygon.fromLngLats(Collections.singletonList(points)));


        loadedMapStyle.addSource(new GeoJsonSource("source-id",
                Polygon.fromLngLats(points)));

        loadedMapStyle.addLayer(new FillLayer("layer-id", "source-id").withProperties(fillOpacity(.24f),
                fillColor(Color.RED)));
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (Object perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale((String) permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions((String[]) permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(UserTrackActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void getRoute(MapboxMap mapboxMap, Point origin, Point destination) {


        MapboxDirections client = MapboxDirections.builder()

                .origin(origin)
                .destination(destination)
                .overview(DirectionsCriteria.OVERVIEW_FULL)
                .profile(DirectionsCriteria.PROFILE_DRIVING)
                .accessToken(getString(R.string.mapbox_access_token))
                .build();

        client.enqueueCall(new Callback<DirectionsResponse>() {
            @Override
            public void onResponse(Call<DirectionsResponse> call, Response<DirectionsResponse> response) {
                if (response == null) {
                    Log.d(TAG, "No routes found make sure you have correct access token");
                    return;
                } else {
                    assert response.body() != null;
                    if (response.body().routes().size() < 1) {
                        Log.d(TAG, "No routes found");
                        return;
                    }
                }


                if (response.body() != null) {
                    try {
                        drivingRoute = response.body().routes().get(0);
                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }

                    double distance = drivingRoute.distance() / 1000;
                    distanceTotal = String.format("%2f KM", distance);
                    Toast.makeText(UserTrackActivity.this, "" + distanceTotal, Toast.LENGTH_SHORT).show();
                    if (mapboxMap != null) {
                        mapboxMap.getStyle(new Style.OnStyleLoaded() {
                            @Override
                            public void onStyleLoaded(@NonNull Style style) {
                                GeoJsonSource routeLineSource = style.getSourceAs("route-source-id");
                                GeoJsonSource iconGeoJsonSource = style.getSourceAs("icon-source-id");

                                if (routeLineSource != null) {
                                    routeLineSource.setGeoJson(LineString.fromPolyline(drivingRoute.geometry(), PRECISION_6));

                                    if (iconGeoJsonSource == null) {
                                        iconGeoJsonSource = new GeoJsonSource("icon-source-id", Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude())));

                                        style.addSource(iconGeoJsonSource);

                                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destination.latitude(), destination.longitude()), ZOOM_LEVEL));

                                    } else {
                                        iconGeoJsonSource.setGeoJson(Feature.fromGeometry(Point.fromLngLat(destination.longitude(), destination.latitude())));

                                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(destination.latitude(), destination.longitude()), ZOOM_LEVEL));
                                    }


                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<DirectionsResponse> call, Throwable t) {

            }
        });
    }



    @SuppressLint("MissingInflatedId")


    protected boolean isLocationEnabled() {

        LocationManager lm = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gps_enabled && !network_enabled) {


            DialogShow();
            return false;
        } else
            return true;
    }
    @SuppressLint("MissingInflatedId")
    private void showDriverDialog(ActiveRides order) {

        ImageView userImage, call, sms;
        TextView txtUserName, txtPrice, txtDistance, txtPickUpLocation, txtDropLocation;
        TextView btnComplete;

        BottomSheetDialog dialogB = new BottomSheetDialog(this
                , R.style.AppBottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_order, findViewById(R.id.bottom_request_order));

        dialogB.setContentView(bottomSheetView);
        dialogB.show();


        userImage = bottomSheetView.findViewById(R.id.userImageBo);
        txtUserName = bottomSheetView.findViewById(R.id.txtUserNameBo);
        txtPrice = bottomSheetView.findViewById(R.id.txtPriceBo);
        txtDistance = bottomSheetView.findViewById(R.id.txtDistanceBo);
        txtPickUpLocation = bottomSheetView.findViewById(R.id.txtPickUpLocationBo);
        txtDropLocation = bottomSheetView.findViewById(R.id.txtDropLocationBo);
        call=bottomSheetView.findViewById(R.id.btnCallBo);
        sms=bottomSheetView.findViewById(R.id.btnChatBo);
        btnComplete=bottomSheetView.findViewById(R.id.btnComplete);



        call.setOnClickListener(v -> {
            onCallBtnClick(order.getRiderPhone());
        });


        sms.setOnClickListener(v -> {
            sendSMS(order.getRiderPhone());
        });

        btnComplete.setOnClickListener(v -> {




        });

        txtUserName.setText(order.getRiderName());
        txtPrice.setText(order.getFare());
        txtDistance.setText(order.getTotalDistance());
        txtPickUpLocation.setText(order.getUserOriginLatitude() + " , " + order.getUserOriginLongitude());
        txtDropLocation.setText(order.getUserDestLatitude() + " , " + order.getUserDestLatitude());
    }
    public void DialogShow() {

        dialog = new BottomSheetDialog(UserTrackActivity.this, R.style.AppBottomSheetDialogTheme);

        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.permission_dialog, (LinearLayout) findViewById(R.id.permissionDialog));
        dialog.setContentView(bottomsheetView);
        dialog.show();
        dialog.setCancelable(false);


        AppCompatButton btnYes = bottomsheetView.findViewById(R.id.btnEnableLocation);
        AppCompatButton btnNot = bottomsheetView.findViewById(R.id.btnNotNow);

        btnYes.setOnClickListener(v -> {
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        });
        btnNot.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.show();
    }


    @Override
    public void onBackPressed() {

    }
    private void onCallBtnClick(String phonenumber)

    {
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall(phonenumber);
        } else {

            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall(phonenumber);
            } else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    private void phoneCall(String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + phoneNumber));
            this.startActivity(callIntent);
        } else {
            Toast.makeText(this, "You don't assign permission.", Toast.LENGTH_SHORT).show();
        }
    }


    private void sendSMS(String phoneNumber) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, "text");

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        } else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", phoneNumber);
            smsIntent.putExtra("sms_body", "message");
            startActivity(smsIntent);
        }
    }
}