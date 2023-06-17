package com.example.mainproject;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;  // Intent 클래스를 임포트
import com.google.android.gms.maps.model.LatLngBounds;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

public class ParkingActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button backButton;
    private SupportMapFragment mapFragment; // mapFragment 변수를 클래스 수준으로 이동

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking);

        backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 뒤로 가기 버튼 클릭 시 MainActivity2로 이동
                startActivity(new Intent(ParkingActivity.this, MainActivity2.class));
                finish();
            }
        });

        // SupportMapFragment를 가져와서 지도가 준비되면 onMapReady 콜백 메서드를 호출합니다.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // 출발지와 도착지 좌표 설정
        LatLng origin = new LatLng(35.85652575, 129.2231078); // 출발지 좌표
        LatLng destination = new LatLng(35.86872, 129.21923); // 도착지 좌표

        // 경유지 좌표 추가
        LatLng[] waypoints = {
                new LatLng(35.85020774, 129.2052982),
                new LatLng(35.83818905, 129.203965),
                new LatLng(35.86810438, 129.2171738),
                new LatLng(35.85328552, 129.2066512),
                new LatLng(35.80465738, 129.5026226),
                new LatLng(36.00308431, 129.1977407),
                new LatLng(35.86468556, 129.2139104),
                new LatLng(35.85999001, 129.224545),
                new LatLng(35.87603689, 129.2294211),
                new LatLng(35.84755917, 129.2326893),
                new LatLng(35.8426479, 129.1897531),
                new LatLng(35.99230511, 129.222741),
                new LatLng(35.99168835, 129.2257702),
                new LatLng(35.71580404, 129.3238746),
                new LatLng(35.85674776, 129.2190038),
                new LatLng(35.84481501, 129.2118693),
                new LatLng(35.86883581, 129.2027348),
                new LatLng(35.84735712, 129.2180518),
                new LatLng(35.85563572, 129.2050704),
                new LatLng(35.84209434, 129.2170968),
                new LatLng(35.84295989, 129.2081439),
                new LatLng(35.87262627, 129.2031244),
                new LatLng(35.85577028, 129.2211809),
                new LatLng(35.78652047, 129.3277687),
                new LatLng(35.84791531, 129.2287207),
                new LatLng(35.84968113, 129.2172903),
                new LatLng(35.86183686, 129.216646),
                new LatLng(35.8331225, 129.2263562),
                new LatLng(35.86644489, 129.2119442),
                new LatLng(35.79889547, 129.1419038),
                new LatLng(35.8483832, 129.2270342),
                new LatLng(35.85017993, 129.2293646),
                new LatLng(35.84403698, 129.2103741),
                new LatLng(35.84136955, 129.2067582),
                new LatLng(35.8551876, 129.2066136),
                new LatLng(35.84429926, 129.2138539),
                new LatLng(35.84157276, 129.2098496),
                new LatLng(35.84222091, 129.2099259),
                new LatLng(35.84344624, 129.2099666),
                new LatLng(35.83905501, 129.202993),
                new LatLng(35.84505588, 129.2076444),
                new LatLng(35.7139649, 129.3247393),
                new LatLng(35.85620277, 129.2247303),
                new LatLng(35.86760114, 129.2027272),
                new LatLng(35.8657621, 129.214403),
                new LatLng(35.84874878, 129.2051875),
                new LatLng(35.87275978, 129.2032925),
                new LatLng(35.91959364, 129.2461616),
                new LatLng(35.84862003, 129.1042922),
                new LatLng(35.84993059, 129.1019303),
                new LatLng(35.84618685, 129.2170265),
                new LatLng(35.84474237, 129.2154879),
                new LatLng(35.86872483, 129.2128648),
                new LatLng(35.8642745, 129.2215551),
                new LatLng(35.88499451, 129.2242326),
                new LatLng(35.87678694, 129.2186245),
                new LatLng(35.86506919, 129.2205154),
                new LatLng(35.87271439, 129.2282292),
                new LatLng(35.88485689, 129.229757),
                new LatLng(35.88300393, 129.2289438),
                new LatLng(35.84396875, 129.2051421),
                new LatLng(35.844247, 129.21348),
                new LatLng(35.859165, 129.22526),
                new LatLng(35.843304, 129.2128),
                new LatLng(35.84313, 129.2134),
                new LatLng(35.83859, 129.20969),
                new LatLng(35.83635, 129.20965),
                new LatLng(35.83487, 129.20951),
                new LatLng(35.83312, 129.20952),
                new LatLng(35.86872, 129.21923) // 도착지 좌표
        };

        // Polyline 그리기
        PolylineOptions polylineOptions = new PolylineOptions()
                .add(origin) // 출발지 추가
                .color(Color.BLUE);

        // 경유지 추가
        for (LatLng waypoint : waypoints) {
            polylineOptions.add(waypoint);
        }

        polylineOptions.add(destination); // 도착지 추가

        // Polyline 지도에 추가
        mMap.addPolyline(polylineOptions);

        // 출발지와 도착지 중심으로 카메라 이동
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(origin);
        builder.include(destination);
        LatLngBounds bounds = builder.build();

        // 레이아웃이 완료된 후에 카메라 이동하도록 처리
        mapFragment.getView().post(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
            }
        });
    }
}
