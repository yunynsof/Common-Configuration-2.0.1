package hn.com.tigo.josm.common.configuration.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.persistence.core.EntityBase;
import hn.com.tigo.josm.persistence.core.SessionBase;
import hn.com.tigo.josm.persistence.exception.PersistenceError;
import hn.com.tigo.josm.persistence.exception.PersistenceException;

/**
 * The class ConfigurationEntity contains the <Usage of this class> for
 * Common-Configuration project.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com/>
 * @version 1.0.0
 * @since Oct 25, 2017
 */
public class ConfigurationEntity extends EntityBase<ConfigurationEntity> {
	
	/** Attribute that determine a Constant of LOGGER. */
	private static final transient Logger LOGGER = Logger.getLogger(ConfigurationEntity.class);

	/** The id. */
	private String id;

	/** The file name. */
	private String fileName;

	/** The file configuration. */
	private String fileConfiguration;

	/** The creation date. */
	private Date creationDate;

	/**
	 * Instantiates a new configuration entity.
	 */
	public ConfigurationEntity() {
	}

	/**
	 * Instantiates a new configuration entity.
	 *
	 * @param sessionBase
	 *            the session base
	 * @throws PersistenceException
	 *             the persistence exception
	 */
	public ConfigurationEntity(SessionBase sessionBase) throws PersistenceException {
		super(sessionBase);
	}

	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.persistence.core.EntityBase#fillList(java.sql.ResultSet)
	 */
	protected List<ConfigurationEntity> fillList(ResultSet resultSet) throws PersistenceException {
		final List<ConfigurationEntity> list = new ArrayList<ConfigurationEntity>();
		try {
			while (resultSet.next()) {
				final ConfigurationEntity configurationEntity = new ConfigurationEntity();
				configurationEntity.setId(resultSet.getString("ID"));
				configurationEntity.setFileName(resultSet.getString("NAME"));
				configurationEntity.setFileConfiguration(resultSet.getString("CONTENT"));
				configurationEntity.setCreationDate(resultSet.getDate("CREATED"));

				list.add(configurationEntity);
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage(), e);
			throw new PersistenceException(PersistenceError.SQL, e);
		}
		return list;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the file name.
	 *
	 * @return the file name
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Sets the file name.
	 *
	 * @param fileName
	 *            the new file name
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * Gets the file configuration.
	 *
	 * @return the file configuration
	 */
	public String getFileConfiguration() {
		return this.fileConfiguration;
	}

	/**
	 * Sets the file configuration.
	 *
	 * @param fileConfiguration
	 *            the new file configuration
	 */
	public void setFileConfiguration(String fileConfiguration) {
		this.fileConfiguration = fileConfiguration;
	}

	/**
	 * Gets the creation date.
	 *
	 * @return the creation date
	 */
	public Date getCreationDate() {
		return new Date(this.creationDate.getTime());
	}

	/**
	 * Sets the creation date.
	 *
	 * @param creationDate
	 *            the new creation date
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = new Date(creationDate.getTime());
	}
}
