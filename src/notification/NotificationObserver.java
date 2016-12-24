package notification;

public abstract class NotificationObserver {
	
	public abstract boolean AddNotification ( NotificationModel obj  ) ;
	public abstract boolean Action (int checkinid) throws Exception ;

	

}
