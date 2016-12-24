package Comment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.models.UserModel;

public class Comment {
	private int comment_id;
	private int comment_check_in_id;
	private int comment_user_id;
	private String comment_date;
	private String comment_description;
	private UserModel comment_user;

	public Comment(int comment_check_in_id, int comment_user_id,
			String comment_description) {
		super();
		this.comment_check_in_id = comment_check_in_id;
		this.comment_user_id = comment_user_id;
		this.comment_description = comment_description;
		this.comment_id = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		this.comment_date = dateFormat.format(date);
		this.comment_user = new UserModel();
	}

	public UserModel getComment_user() {
		return comment_user;
	}

	public void setComment_user(UserModel comment_user) {
		this.comment_user = comment_user;
	}

	public int getComment_id() {
		return comment_id;
	}

	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}

	public int getComment_check_in_id() {
		return comment_check_in_id;
	}

	public void setComment_check_in_id(int comment_check_in_id) {
		this.comment_check_in_id = comment_check_in_id;
	}

	public int getComment_user_id() {
		return comment_user_id;
	}

	public void setComment_user_id(int comment_user_id) {
		this.comment_user_id = comment_user_id;
	}

	public String getComment_date() {
		return comment_date;
	}

	public void setComment_date(String comment_date) {
		this.comment_date = comment_date;
	}

	public String getComment_description() {
		return comment_description;
	}

	public void setComment_description(String comment_description) {
		this.comment_description = comment_description;
	}

}
