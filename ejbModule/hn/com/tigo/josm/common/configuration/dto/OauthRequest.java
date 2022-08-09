package hn.com.tigo.josm.common.configuration.dto;

import java.io.Serializable;

/**
 * This class is a DTO of the Oauth request, which refers to the attributes token, grant, path.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 25/05/2018 01:43:41 PM 2018
 */
public class OauthRequest implements Serializable {

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine token. */
	private String token;

	/** Attribute that determine path. */
	private String path;

	/** Attribute that determine grant. */
	private String grant;

	/**
	 * Instantiates a new oauth request.
	 */
	public OauthRequest() {
	}

	/**
	 * Gets the token.
	 *
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * Sets the token.
	 *
	 * @param token
	 *            the new token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * Sets the path.
	 *
	 * @param path
	 *            the new path
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * Gets the grant.
	 *
	 * @return the grant
	 */
	public String getGrant() {
		return grant;
	}

	/**
	 * Sets the grant.
	 *
	 * @param grant
	 *            the new grant
	 */
	public void setGrant(String grant) {
		this.grant = grant;
	}

}
