package Place;

/**
 * @author HP
 *
 */
public class PlaceModel {
    int id ;
    String name ;
	double rate ;
	int numOfCheckIns ;
	int numOfReq ;
	String desc ;
	double longitude ;
	double latitude ;

	
	public PlaceModel( String name, String desc, double longitude, double latitude) {
		super();
		this.name = name;
		this.desc = desc;
		this.longitude = longitude;
		this.latitude = latitude;
	}
	public PlaceModel() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public int getNumOfCheckIns() {
		return numOfCheckIns;
	}
	public void setNumOfCheckIns(int numOfCheckIns) {
		this.numOfCheckIns = numOfCheckIns;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	

	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public int getNumOfReq() {
		return numOfReq;
	}
	public void setNumOfReq(int numOfReq) {
		this.numOfReq = numOfReq;
	}
	
	
}
