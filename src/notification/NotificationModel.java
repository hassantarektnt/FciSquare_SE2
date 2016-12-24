package notification;

public class NotificationModel {
	
	int id ;
	String desc ;
	String name ; 
	int userid ;
	int userid1 ;
	int checkinid ;
	int type ;
	int actionid ;
	
	public NotificationModel(int id, int userid, int userid1,
			int checkinid , String name , int type , int actionid ) {
		super();
		this.id = id;
		this.userid = userid;
		this.userid1 = userid1;
		this.checkinid = checkinid;
		this.name = name ;
		this.type = type ;
		this.actionid = actionid ;
	}

	public NotificationModel() {
		// TODO Auto-generated constructor stub
	}

	
	
	public int getActionid() {
		return actionid;
	}

	public void setActionid(int actionid) {
		this.actionid = actionid;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getUserid1() {
		return userid1;
	}

	public void setUserid1(int userid1) {
		this.userid1 = userid1;
	}

	public int getCheckinid() {
		return checkinid;
	}

	public void setCheckinid(int checkinid) {
		this.checkinid = checkinid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	
	

}
