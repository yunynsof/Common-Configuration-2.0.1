package hn.com.tigo.josm.common.configuration.dto;

import java.io.Serializable;

/**
 * This class is a DTO of the server response attributes, 
 * and they are necessary for the validation of the use of the system.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 28/05/2018 09:24:26 AM 2018
 */
public class ConfigutationDto implements Serializable {

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine url. */
	private String url;

	/** Attribute that determine grant. */
	private String grant;

	/** Attribute that determine urlToken. */
	private String urlToken;

	/** Attribute that determine configExpiration. */
	private int configExpiration;

	/**
	 * Instantiates a new configutation dto.
	 */
	public ConfigutationDto() {
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

	/**
	 * Gets the url token.
	 *
	 * @return the url token
	 */
	public String getUrlToken() {
		return urlToken;
	}

	/**
	 * Sets the url token.
	 *
	 * @param urlToken
	 *            the new url token
	 */
	public void setUrlToken(String urlToken) {
		this.urlToken = urlToken;
	}

	/**
	 * Gets the config expiration.
	 *
	 * @return the config expiration
	 */
	public int getConfigExpiration() {
		return configExpiration;
	}

	/**
	 * Sets the config expiration.
	 *
	 * @param configExpiration
	 *            the new config expiration
	 */
	public void setConfigExpiration(int configExpiration) {
		this.configExpiration = configExpiration;
	}

}
