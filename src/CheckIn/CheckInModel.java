package CheckIn;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import com.models.UserModel;
import Comment.Comment;
import Place.PlaceModel;

public class CheckInModel implements Comparable<CheckInModel> {
	private int check_in_id;
	private int check_in_user_id;
	private int check_in_place_id;
	private int numberOfLikes;
	private int numberOfComments;
	private String date;
	private String description;
	private ArrayList<Comment> comments;
	private PlaceModel myPlace;
	private UserModel check_in_user;

	// checkin constructor
	public CheckInModel(String description, int check_in_user_id,
			int check_in_place_id) {
		super();
		this.check_in_place_id = check_in_place_id;
		this.check_in_user_id = check_in_user_id;
		this.description = description;
		this.numberOfLikes = 0;
		this.numberOfComments = 0;
		this.check_in_id = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.date = dateFormat.format(date);
		this.comments = new ArrayList<Comment>();
		this.myPlace = new PlaceModel();
		this.check_in_user = new UserModel();
	}

	public UserModel getCheck_in_user() {
		return check_in_user;
	}

	public void setCheck_in_user(UserModel check_in_user) {
		this.check_in_user = check_in_user;
	}

	public PlaceModel getMyPlace() {
		return myPlace;
	}

	public void setMyPlace(PlaceModel myPlace) {
		this.myPlace = myPlace;
	}

	public ArrayList<Comment> getComments() {
		return comments;
	}

	public void setComments(ArrayList<Comment> comments) {
		this.comments = comments;
	}

	public CheckInModel() {
		// TODO Auto-generated constructor stub
	}

	public int getCheck_in_id() {
		return check_in_id;
	}

	public void setCheck_in_id(int check_in_id) {
		this.check_in_id = check_in_id;
	}

	public int getCheck_in_user_id() {
		return check_in_user_id;
	}

	public void setCheck_in_user_id(int check_in_user_id) {
		this.check_in_user_id = check_in_user_id;
	}

	public int getCheck_in_place_id() {
		return check_in_place_id;
	}

	public void setCheck_in_place_id(int check_in_place_id) {
		this.check_in_place_id = check_in_place_id;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberOfLikes() {
		return numberOfLikes;
	}

	public void setNumberOfLikes(int numberOfLikes) {
		this.numberOfLikes = numberOfLikes;
	}

	@Override
	public int compareTo(CheckInModel obj) {
		int comparelike = ((CheckInModel) obj).getNumberOfLikes();
		return obj.getNumberOfLikes() - comparelike;
	}

}
