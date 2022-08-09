package hn.com.tigo.josm.common.configuration.dto;

import java.io.Serializable;

/**
 * This class is a DTO of the Oauth model, 
 * which references the attributes httpStatus, status, message, url, userName.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 25/05/2018 11:56:52 AM 2018
 */
public class OauthResponse implements Serializable {

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine httpStatus. */
	private String httpStatus;

	/** Attribute that determine status. */
	private int status;

	/** Attribute that determine message. */
	private String message;

	/** Attribute that determine token. */
	private String url;

	/** Attribute that determine data. */
	private String userName;

	/**
	 * Instantiates a new oauth model.
	 */
	public OauthResponse() {
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public String getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus
	 *            the new http status
	 */
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message
	 *            the new message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Gets the url.
	 *
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * Sets the url.
	 *
	 * @param url
	 *            the new url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Gets the user name.
	 *
	 * @return the user name
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Sets the user name.
	 *
	 * @param userName
	 *            the new user name
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
