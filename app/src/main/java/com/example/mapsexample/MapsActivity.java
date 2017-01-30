package com.example.mapsexample;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ToggleButton drawRouteButton;
    private boolean drawRouteButtonState;
    private Polyline newLine;
    private Button saveButton;

    private FirebaseDatabase routeDB;
    private DatabaseReference routeReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        drawRouteButton = (ToggleButton)findViewById(R.id.drawRouteButton);
        drawRouteButtonState = false;
        saveButton = (Button)findViewById(R.id.saveButton);

        routeDB = FirebaseDatabase.getInstance();
        routeReference = routeDB.getReference().child("routes");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
      //  int mapTypeHybrid = GoogleMap.MAP_TYPE_HYBRID;

        // Add a marker in Sydney and move the camera
        LatLng moscow = new LatLng(55.748517,37.4923125);    // coordinates
        LatLng dushanbe = new LatLng(38.5526315, 68.7760564);
        LatLng khujand = new LatLng(40.2843962, 69.5667148);
        LatLng hisor = new LatLng(38.4810988, 68.5829256);
        LatLng vahdat = new LatLng(38.5630121, 68.9802483);
        MarkerOptions markerOptions=new MarkerOptions().position(moscow).title("Marker in Moscow");
        MarkerOptions markerOptions1=new MarkerOptions().position(dushanbe).title("Marker in Dushanbe");
        MarkerOptions markerOptions2=new MarkerOptions().position(khujand).title("Marker in Khujand");
        MarkerOptions markerOptions3=new MarkerOptions().position(hisor).title("Marker in Hisor");
        MarkerOptions markerOptions4=new MarkerOptions().position(vahdat).title("Marker in Vahdat");
       // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));  //точка на карте
     //   mMap.addMarker((markerOptions));
        mMap.addMarker((markerOptions1));
        mMap.addMarker((markerOptions2));
        mMap.addMarker((markerOptions3));
        mMap.addMarker((markerOptions4));
      //  mMap.moveCamera(CameraUpdateFactory.newLatLng(moscow));   //камера показывает Душанбе издалека
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dushanbe, 11));  //камера показывает Душанбе поближе

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (drawRouteButtonState) {
                    List<LatLng> pointsList = newLine.getPoints();
                    pointsList.add(latLng);
                    newLine.setPoints(pointsList);
                    mMap.addMarker(new MarkerOptions().position(latLng));
                }
            }
        });

        Polyline line = googleMap.addPolyline((new PolylineOptions()
                .add(new LatLng(38.557933, 68.799927),
                        new LatLng(38.574494,68.786564),
                        new LatLng(38.608577,68.786876),
                        new LatLng(38.626426,68.778931)))
                .width(5)
                .color(Color.RED));

        drawRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // кнопка on/off
                if (drawRouteButtonState) {
                    drawRouteButtonState= false;
                }
                else {
                    drawRouteButtonState = true;
                    newLine = mMap.addPolyline(new PolylineOptions()
                            .width(5)
                    .color(Color.RED));
                }
            }
        });

    /*    mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                if (drawRouteButtonState) newLine.getPoints().add(latLng);
            }
        });  */

   /*     saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Route route = new Route(newLine.getPoints().toString()  );
                //чтобы публиковать в базу
                routeReference.push().setValue(route);


            }
        });*/

     /*   routeReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Route route = dataSnapshot.getValue(Route.class);
                // append добавляет к тексту
                // set меняет текст
                tableTView.append(route.getNewLine() + "\n");

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });  */

    }
}
