package com.mehboob.hunzarider.fragments;


import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

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

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
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
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.FillLayer;
import com.mapbox.mapboxsdk.style.layers.LineLayer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;
import com.mapbox.mapboxsdk.utils.BitmapUtils;
import com.mehboob.hunzarider.R;
import com.mehboob.hunzarider.activities.DashboardActivity;
import com.mehboob.hunzarider.constants.Constants;
import com.mehboob.hunzarider.databinding.FragmentHomeBinding;
import com.mehboob.hunzarider.models.LocationObject;
import com.mehboob.hunzarider.utils.LocationTrack;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements PermissionsListener {
    private ArrayList permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private PermissionsManager permissionsManager;
    LocationTrack locationTrack;
    private BottomSheetDialog dialog;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private MapboxMap mapboxMap;
    private Point origin, destination;
    private static float ZOOM_LEVEL = 16f;
    double latitude, longitude;
    private DatabaseReference mRef;

    FragmentHomeBinding binding;
    MarkerOptions markerOptions;
    private final List<List<Point>> points = new ArrayList<>();
    private final List<Point> outerPoints = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 101;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        mRef = FirebaseDatabase.getInstance().getReference(Constants.RIDER).child(Constants.LOCATION);
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);


//        mapView.onCreate(savedInstanceState);
        permissionsToRequest = findUnAskedPermissions(permissions);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }

        if (isLocationEnabled()) {
            binding.mapView.getMapAsync(new com.mapbox.mapboxsdk.maps.OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull com.mapbox.mapboxsdk.maps.MapboxMap mMapboxMap) {
                    mapboxMap = mMapboxMap;
                    mapboxMap.setStyle(Style.OUTDOORS, new Style.OnStyleLoaded() {
                        @Override
                        public void onStyleLoaded(@NonNull Style style) {
//                        mapboxMap.getUiSettings().setAttributionEnabled(false);
                            //     enableLocationComponent(style);


                            enableLocations();
//updateLocation();

                            showBoundsArea(style);


                            style.addImage("red-pin-icon-id", BitmapUtils.getBitmapFromDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_place_24)));
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

                            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(origin.latitude(), origin.longitude()), ZOOM_LEVEL));
                            Point userDest = Point.fromLngLat(107.6848254, -6.9218571);
                            Point userPoint = Point.fromLngLat(105.6848254, -9.9218571);
                            //  getRoute(mapboxMap, userPoint, userDest);


                        }
                    });
                }
            });
        } else {
            enableLocations();
        }


        binding.btnMyLocation.setOnClickListener(v -> {

            if (isLocationEnabled()) {


                enableLocations();
            } else {
                new AlertDialog.Builder(getContext())
                        .setTitle("Please activate location")
                        .setMessage("Click ok to goto settings else exit.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        })
                        .show();
            }

        });


        return binding.getRoot();
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

    protected boolean isLocationEnabled() {

        LocationManager lm = (LocationManager)
                requireContext().getSystemService(Context.LOCATION_SERVICE);

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

    public void DialogShow() {

        dialog = new BottomSheetDialog(requireContext(), R.style.AppBottomSheetDialogTheme);

        View bottomsheetView = LayoutInflater.from(getApplicationContext()).
                inflate(R.layout.permission_dialog, (LinearLayout) binding.getRoot().findViewById(R.id.permissionDialog));
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
            // DashboardActivity.finish();
        });

        dialog.show();
    }

    private void enableLocations() {

        locationTrack = new LocationTrack(requireContext());


        if (locationTrack.canGetLocation()) {


            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();

            mRef.child(Constants.USER_ID).setValue(new LocationObject(String.valueOf(latitude), String.valueOf(longitude), Constants.USER_ID))
                    .addOnCompleteListener(task -> {
                        //showSnackBar();
                        if (task.isComplete() && task.isSuccessful()) {
                            Log.d("LocationTrack", "Location object added");
                        } else {
                            Log.d("LocationTrack", "Something went wrong");
                        }
                    }).addOnFailureListener(e -> {
                        Log.d("LocationTrack", "Location not added " + e.getLocalizedMessage());
                    });
            markerOptions = new MarkerOptions().setIcon(IconFactory.getInstance(requireContext()).defaultMarker());
            markerOptions.title("My location");

            markerOptions.position(new LatLng(latitude, longitude));


            mapboxMap.addMarker(markerOptions);


//            binding.txtOrigin.setText("latlng://" + latitude + " , " + longitude + "\n" + reverseGeocode(Point.fromLngLat(longitude, latitude)));
//// The result of this reverse geocode will give you "Pennsylvania Ave NW"

            origin = Point.fromLngLat(longitude, latitude);
//            sharedPref.saveLatitude(String.valueOf(latitude));
//
//            sharedPref.saveLongitude(String.valueOf(longitude));


            mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), ZOOM_LEVEL));


        } else {
            DialogShow();
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

    private void showSnackBar() {

        Snackbar snackbar = Snackbar.make(binding.getRoot().findViewById(android.R.id.content), "Adding Location to cloud", Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("Close", view -> {
            snackbar.dismiss();
        });

        snackbar.show();

    }

    private boolean hasPermission(Object permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (getContext().checkSelfPermission((String) permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

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
            Toast.makeText(requireContext(), R.string.user_location_permission_not_granted, Toast.LENGTH_LONG).show();
            //    getContext().finish();
        }
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
        new AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void enableLocationComponent(Style style) {

        // Check if permissions are enabled and if not request
        if (PermissionsManager.areLocationPermissionsGranted(getContext())) {

// Get an instance of the component
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
// Activate with options

// Activate with options
            locationComponent.activateLocationComponent(
                    LocationComponentActivationOptions.builder(getContext(), style).build());

// Enable to make component visible
            if (ActivityCompat.checkSelfPermission(getContext(), ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), ACCESS_COARSE_LOCATION)
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
                ActivityCompat.requestPermissions(requireActivity(), new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 100);
            }

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(requireActivity());
        }
    }
}