package edu.demian.web.controller.action;

public class ActionException extends RuntimeException {

	public ActionException(final String message) {
		super(message);
	}
	
	public ActionException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
