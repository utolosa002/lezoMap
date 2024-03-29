package utolotu.lezo.eraikinmapa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class BaserriActivity  extends FragmentActivity {
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
	but_eraikinak.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(getApplicationContext(),
					MainActivity.class);
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
	but_baserriak.setOnClickListener(new OnClickListener() {
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
	
				Toast.makeText(BaserriActivity.this, "Idioma: Español",
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
			kargatuBaserriak();
        

        // Move the camera instantly to hamburg with a zoom of 15.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Lezo, 15));

        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17), 2000, null);
        // Initialize map options. For example:
        // mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
	private void kargatuBaserriak() {
		// TODO Auto-generated method stub
		Gunea guneMiura 	= new Gunea("Miura","miura. Eraikina ....","Miura baserria",R.drawable.lezo,R.drawable.sanjuan,43.32103703,-1.89884573, "baserria");
	    Marker Miura = mMap.addMarker(new MarkerOptions().position(guneMiura.LatLng).title("Miura").snippet("Lezoko udaletxea")
	              .icon(BitmapDescriptorFactory.fromResource(guneMiura.image)));
	}
}