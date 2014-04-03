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
	public Gunea(String name,String description, String snippet, int marker, int image, Double lat, Double lon, String type) {
		// TODO Auto-generated constructor stub
		this.name= name;
		this.description=description;
		this.snippet=snippet;
		this.marker=marker;
		this.image=image;
		this.LatLng= new LatLng(lat, lon);
		this.type=type;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the snippet
	 */
	public String getSnippet() {
		return snippet;
	}
	/**
	 * @param snippet the snippet to set
	 */
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	/**
	 * @return the marker
	 */
	public int getMarker() {
		return marker;
	}
	/**
	 * @param marker the marker to set
	 */
	public void setMarker(int marker) {
		this.marker = marker;
	}
	/**
	 * @return the image
	 */
	public int getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(int image) {
		this.image = image;
	}
	/**
	 * @return the latLng
	 */
	public LatLng getLatLng() {
		return LatLng;
	}
	/**
	 * @param latLng the latLng to set
	 */
	public void setLatLng(LatLng latLng) {
		LatLng = latLng;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

}
