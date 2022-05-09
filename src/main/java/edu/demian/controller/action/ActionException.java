package edu.demian.controller.action;

public class ActionException extends RuntimeException {

	private static final long serialVersionUID = 3146460392009050712L;

	public ActionException(String message) {
		super(message);
	}
	
	public ActionException(String message, Throwable cause) {
        super(message, cause);
    }

}
