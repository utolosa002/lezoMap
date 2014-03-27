package utolotu.lezo.eraikinmapa;
import com.google.android.gms.maps.model.LatLng;

//gunea klasea sortuko dugu eta honek izena mota deskripzioa eta irudia izango du
public class Gunea{
	/**
	 * 
	 */
	String name;
	String description;
	String snippet;
	int marker;
	int image;
	LatLng LatLng;
	String type;
	

	public Gunea() {
		// TODO Auto-generated constructor stub
		this.name= "izena";
		this.description="desk";
		this.snippet="desk txiki";
		this.marker=R.drawable.marker;
		this.image=R.drawable.lezo;
		this.LatLng= new LatLng(43.32108386, -1.89889401);
		this.type="mota";
	}	
	public Gunea(String name, String description,String snippet, int image,String type) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.description=description;
		this.snippet=snippet;
		this.marker=R.drawable.marker;
		this.image=image;
		this.LatLng= new LatLng(43.32108386, -1.89889401);
		this.type=type;
	}
	public Gunea(String name,String description,String snippet, int marker,LatLng lat, String type) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.description=description;
		this.snippet=snippet;
		this.marker=marker;
		this.image=R.drawable.lezo;
		this.LatLng= lat;
		this.type=type;
		
	}
	public Gunea(String name,String description, String snippet, int marker, int image, LatLng lat, String type) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.description=description;
		this.snippet=snippet;
		this.marker=marker;
		this.image=image;
		this.LatLng= lat;
		this.type=type;
	}

}
