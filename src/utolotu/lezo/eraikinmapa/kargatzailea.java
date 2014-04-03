package utolotu.lezo.eraikinmapa;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class kargatzailea extends SQLiteOpenHelper{
	@SuppressLint("SdCardPath")
	private static String DB_PATH = "/data/data/utolotu.lezo.eraikinmapa/databases/";
    private static String DB_NAME = "db.db";
    private SQLiteDatabase myDataBase;
    private final Context myContext;
	// gune taulak
	private static final String TABLE_EU = "euskara";
	private static final String TABLE_ES = "espanol";
    private static final int DB_VERSION = 1;
	private static final String KEY_NAME = "izena";

    public kargatzailea(Context context) {
    	super(context, DB_NAME, null, DB_VERSION);
        this.myContext = context;
    }	
 
    public void createDataBase() throws IOException{
    	boolean dbExist = checkDataBase();
    	if(dbExist){
    		System.out.println("ez egin ezer, dagoeneko existitzen da");
    	}else{
    		this.getReadableDatabase();
        	System.out.println("ez da existitzen");
        	try {
    			copyDataBase();
    		} catch (IOException e) {
        		throw new Error("Error copying database");
        	}
    	}
    }
 
    public boolean checkDataBase(){
    	SQLiteDatabase checkDB = null;
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		System.out.println("checking "+myPath);
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
    		System.out.println("datu basea ez da existitzen oraindik");
    	}
    	if(checkDB != null){
    		checkDB.close();
    	}
    	return checkDB != null ? true : false;
    }
 
    private void copyDataBase() throws IOException{
    	InputStream myInput = myContext.getAssets().open("hondakinak.db");
    	String outFileName = DB_PATH + DB_NAME;
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    public void openDataBase() throws SQLException{
        String myPath = DB_PATH + DB_NAME;
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }
 
    @Override
	public synchronized void close() {
    	    if(myDataBase != null)
    		    myDataBase.close();
    	    super.close();
	}
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if ( newVersion > oldVersion)
        {
        	 System.out.println("New database version exists for upgrade.");         
            try {
               System.out.println("droping upgrade database..."+newVersion+" old:"+oldVersion); 
               dropDB();
               System.out.println("Copying upgrade database...");
               copyDataBase();
            } catch (IOException e) {
                // TODO Auto-generated catch block
            	System.out.println("upgrade database catch..."); 
                e.printStackTrace();
            }       
        }
    }
	public ArrayList<Gunea> getGuneak(String str,String zutabe)  throws IOException, SQLiteException {
		ArrayList<Gunea> guneList = new ArrayList<Gunea>();
		SQLiteDatabase db = this.getReadableDatabase();
		String pattern = "[a-zA-Z]*[- ()]*[a-zA-Z]*";
		if (!str.matches(pattern)&& (zutabe!="non")) {
			if(str==""||str==null){
		}else{str = " ";}
		}
		String selectQuery = "SELECT  * FROM " + TABLE_EU;
		System.out.println(selectQuery);
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				String[] latlngStr = cursor.getString(5).split(",",2);
				double lati = Double.parseDouble(latlngStr[0]);
				double lng = Double.parseDouble(latlngStr[1]);
				LatLng lat = new LatLng(lati, lng);
				int marker =R.drawable.marker;
				int image = R.drawable.lezo;
				Gunea g = new Gunea(cursor.getString(0),cursor.getString(1),cursor.getString(2),marker, image,lat,cursor.getString(6));
				// Adding gunea to list
				guneList.add(g);
			} while (cursor.moveToNext());
		}
		
		// return gune list
		return guneList;
	}
//	public ArrayList<Gunea> getGuneMota(String str,String zutabe)  throws IOException, SQLiteException {
//		ArrayList<Gunea> guneList = new ArrayList<Gunea>();
//		SQLiteDatabase db = this.getReadableDatabase();
//		String pattern = "[a-zA-Z]*[- ()]*[a-zA-Z]*";
//		if (!str.matches(pattern)&& (zutabe!="non")) {
//			if(str==""||str==null){
//		}else{str = " ";}
//		}
//		String selectQuery = "SELECT  * FROM " + TABLE_EU
//				+ " WHERE "+zutabe+" LIKE '" + str + "%' ORDER BY "+ KEY_NAME +"";
//		System.out.println(selectQuery);
//		Cursor cursor = db.rawQuery(selectQuery, null);
//		if (cursor.moveToFirst()) {
//			do {
//				Gunea g = new Gunea(cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(6));
//				g.setIzena(Integer.parseInt(cursor.getString(0)));
//				g.setName(cursor.getString(1));
//				g.setNon(cursor.getString(2));
//				g.setInfo(cursor.getString(3));
//				// Adding hondakin to list
//				guneList.add(g);
//			} while (cursor.moveToNext());
//		}
//		
//		// return hondakin list
//		return guneList;
//	}
	public void dropDB() {
		File dbFile = new File(DB_PATH + DB_NAME);
		if( dbFile.exists()){
			dbFile.delete();
		}
	}

	public static List<Gunea> kargatu(GoogleMap mMap, String mota) {
		// TODO Auto-generated method stub
		List<Gunea> guneLista = new ArrayList<Gunea>();

		Gunea gUdala 	= new Gunea("Udaletxea","lezoko udaletxea santokristo plazan dago. Eraikina ....","Lezoko udaletxea",R.drawable.lezo,R.drawable.sanjuan,43.32103703,-1.89884573, "eraikina");
		guneLista.add(gUdala);
		Gunea gPlazeta 	= new Gunea("Plazeta", "lezoko futbol zelaia....","Plazeta futbol zelaia",R.drawable.foot,R.drawable.sanjuan,43.32316398,-1.8944791,"kirol instalazioa");
		guneLista.add(gPlazeta);
		Gunea gElias 	= new Gunea("Elias", "lezoko kale nagusian. Eraikina ....","Armarridun eraikina",R.drawable.armarria,R.drawable.sanjuan,43.3218566,-1.89949483,"eraikina");
		guneLista.add(gElias);
		Gunea gSKeliza 	= new Gunea("santo kristo eliza", "lezoko udaletxea santokristo plazan dago. Eraikina ....","eraikina",R.drawable.eliza,R.drawable.skristo,43.32123216,-1.89911127,"eraikina");
		guneLista.add(gSKeliza);
//		Gunea gSJBeliza = new Gunea("santo juan batailatzailea", "eraikina","lezoko udaletxea santokristo plazan dago. Eraikina ....",R.drawable.eliza,R.drawable.sanjuan,43.32081848,-1.89917028);
//		guneLista.add(gSJBeliza);
//		Gunea gGezala 	= new Gunea("Gezala auditoriuma", "eraikina","200X urtean inaguratu zen",R.drawable.arts,43.31978814,-1.8981564);
//		guneLista.add(gGezala);
//		Gunea gKN	= new Gunea("Kale Nagusikoa", "iturria","Kale nagusian dago...",R.drawable.iturria,43.32151202,-1.89927757);
//		guneLista.add(gKN);
		Marker udala = mMap.addMarker(new MarkerOptions().position(gUdala.LatLng)
				.title(gUdala.name).snippet("Lezoko udaletxea")
	              .icon(BitmapDescriptorFactory.fromResource(gUdala.marker)));
		Marker plazeta = mMap.addMarker(new MarkerOptions().position(gPlazeta.LatLng)
				.title(gPlazeta.name).snippet("kirol instalazioa")
	              .icon(BitmapDescriptorFactory.fromResource(gPlazeta.marker)));
		Marker Elias = mMap.addMarker(new MarkerOptions().position(gElias.LatLng)
				.title(gElias.name).snippet("Armarridun etxea")
	              .icon(BitmapDescriptorFactory.fromResource(gElias.marker)));
		Marker SKeliza = mMap.addMarker(new MarkerOptions().position(gSKeliza.LatLng)
				.title(gSKeliza.name).snippet("Eliza")
	              .icon(BitmapDescriptorFactory.fromResource(gSKeliza.marker)));
//		Marker SJBeliza = mMap.addMarker(new MarkerOptions().position(gSJBeliza.LatLng)
//				.title(gSJBeliza.name).snippet("Eliza")
//	              .icon(BitmapDescriptorFactory.fromResource(gSJBeliza.marker)));
//		Marker Gezala = mMap.addMarker(new MarkerOptions().position(gGezala.LatLng)
//				.title(gGezala.name).snippet("Auditoriuma")
//	              .icon(BitmapDescriptorFactory.fromResource(gGezala.marker)));
//
//	    Marker kaleNagusikoa = mMap.addMarker(new MarkerOptions().position(gKN.LatLng).title("Kale Nagusian").snippet("KaleNagusikoa")
//	              .icon(BitmapDescriptorFactory.fromResource(gKN.marker)));
		return guneLista;
	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}
}
