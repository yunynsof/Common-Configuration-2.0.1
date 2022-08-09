package hn.com.tigo.josm.common.configuration.oauth;

import java.util.Map;

import hn.com.tigo.josm.common.exceptions.HttpClientException;
import hn.com.tigo.josm.common.http.Buildable;
import hn.com.tigo.josm.common.http.PoolingBuilder;
import hn.com.tigo.josm.common.http.PostMethod;

/**
 * This class makes use of the PostMethod class, 
 * which makes http connection with the server, 
 * owns the legacy method getResponse that gets a response from the server.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 2/03/2018 09:19:22 AM 2018
 */
public class HttpConnector extends PostMethod<String> {

	/** The instance of a {@link Buildable} object. */
	private static Buildable buildable = PoolingBuilder.getInstance("CommonConfiguration");

	/**
	 * Instantiates a new http connector.
	 *
	 * @param url            the url
	 * @param properties the properties
	 */
	public HttpConnector(final String url, final Map<String, String> properties) {
		super(url, url, buildable, properties);
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.common.http.ServiceInvoker#getResponse(java.lang.String, int, java.lang.String)
	 */
	@Override
	protected String getResponse(String response, int responseCode, String responseMessage) throws HttpClientException {
		return response;
	}

}
