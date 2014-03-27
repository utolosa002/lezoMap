package utolotu.lezo.eraikinmapa;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

public class MainActivity extends FragmentActivity{
    private GoogleMap mMap;
	private LinearLayout slidingPanel;
	private boolean isExpanded;
	private DisplayMetrics metrics;
	private RelativeLayout headerPanel;
	private RelativeLayout menuPanel;
	private int panelWidth;
	private ImageView menuViewButton;
    static final LatLng Lezo = new LatLng(43.32108386, -1.89889401);
	Button but_baserriak;
	Button but_eraikinak;
	Button but_iturriak;
	Button but_denak;
	Button but_hizkuntza;
	FrameLayout.LayoutParams menuPanelParameters;
	FrameLayout.LayoutParams slidingPanelParameters;
	LinearLayout.LayoutParams headerPanelParameters;
	LinearLayout.LayoutParams listViewParameters;
	static public List<Gunea> guneLista = new ArrayList<Gunea>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setUpMapIfNeeded();
		// //////////////////////////////////
		metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		panelWidth = (int) ((metrics.widthPixels) * 0.75);

		headerPanel = (RelativeLayout) findViewById(R.id.header);
		headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
				.getLayoutParams();
		headerPanelParameters.width = metrics.widthPixels;
		headerPanel.setLayoutParams(headerPanelParameters);

		menuPanel = (RelativeLayout) findViewById(R.id.menuPanel);
		menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
				.getLayoutParams();
		menuPanelParameters.width = panelWidth;
		menuPanel.setLayoutParams(menuPanelParameters);

		slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
		slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
				.getLayoutParams();
		slidingPanelParameters.width = metrics.widthPixels;
		slidingPanel.setLayoutParams(slidingPanelParameters);

		setUpMapIfNeeded();
		
		menuViewButton = (ImageView) findViewById(R.id.menuViewButton);
		but_eraikinak = (Button) findViewById(R.id.menu_eraikinak);
		but_iturriak = (Button) findViewById(R.id.menu_iturriak);
		but_baserriak = (Button) findViewById(R.id.menu_baserriak);
		but_denak = (Button) findViewById(R.id.menu_denak);
		but_hizkuntza = (Button) findViewById(R.id.menu_hizkuntza);
		
		
		
    
	but_iturriak.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(),
					IturriActivity.class);
			startActivity(i);
			finish();
		}
	});
	but_baserriak.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(),
					BaserriActivity.class);
			startActivity(i);
			finish();
		}
	});	
	but_denak.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(),
					DenakActivity.class);
			startActivity(i);
			finish();
		}
	});
	but_eraikinak.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			isExpanded = false;
			// Collapse
			new CollapseAnimation(slidingPanel, panelWidth,
					TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
					TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0,
					0.0f);
		}
	});
	but_hizkuntza.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			System.out.println("clik lang=  locale: "
					+ getResources().getConfiguration().locale);
	
				Toast.makeText(MainActivity.this, "Idioma: Español",
						Toast.LENGTH_SHORT).show();
		
		}
		
	});
	menuViewButton.setOnClickListener(new OnClickListener() {
		public void onClick(View v) {
			if (!isExpanded) {
				isExpanded = true;
				menuViewButton.setImageResource(R.drawable.fletxaalderantziz);
				// Expand
				new ExpandAnimation(slidingPanel, panelWidth,
						Animation.RELATIVE_TO_SELF, 0.0f,
						Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
			} else {
				isExpanded = false;
				menuViewButton.setImageResource(R.drawable.fletxa);
				// Collapse
				new CollapseAnimation(slidingPanel, panelWidth,
						TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
						TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
						0, 0.0f);
			}
		}
	});
}
    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        if (mMap != null) {
            return;
        }
       
        mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
      //   mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); 
         if (mMap == null) {
            return;
        }
         
         guneLista=kargatzailea.kargatu(mMap,"denak");
        

        // Move the camera instantly to hamburg with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lezo, 16));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
        // Initialize map options. For example:
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
           
    			Intent i = new Intent(getApplicationContext(),
    					ExpandingCells.class);
    			i.putExtra("titulua", marker.getTitle());
    			i.putExtra("Snippet", marker.getSnippet());
    			startActivity(i);
            }
        });
    }
}