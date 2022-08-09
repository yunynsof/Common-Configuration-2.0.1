package hn.com.tigo.josm.common.configuration.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * This class is a DTO of the File Name List configuration, where it refers to name, updated.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 28/05/2018 05:12:38 PM 2018
 */
public class ConfigurationFileNameList implements Serializable {

	/** Attribute that determine a Constant of serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Attribute that determine name. */
	private String name;

	/** Attribute that determine updated. */
	private Date updated;

	/**
	 * Instantiates a new configuration file name list.
	 */
	public ConfigurationFileNameList() {
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the updated.
	 *
	 * @return the updated
	 */
	public Date getUpdated() {
		return new Date(this.updated.getTime());
	}

	/**
	 * Sets the updated.
	 *
	 * @param updated
	 *            the new updated
	 */
	public void setUpdated(Date updated) {
		this.updated = new Date(updated.getTime());
	}

}
