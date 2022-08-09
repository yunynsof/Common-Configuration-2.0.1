package hn.com.tigo.josm.common.configuration.entities;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import hn.com.tigo.josm.persistence.core.EntityBase;
import hn.com.tigo.josm.persistence.core.SessionBase;
import hn.com.tigo.josm.persistence.exception.PersistenceError;
import hn.com.tigo.josm.persistence.exception.PersistenceException;

/**
 * This class has DTO of the attributes of Execution Log Entity; 
 * contains the legacy filList method, which populates a list of entities in a result set.
 *
 * @author Gary Gonzalez Zepeda <mailto:ggzepeda@stefanini.com />
 * @version 2.0.1
 * @see
 * @since 28/05/2018 01:53:35 PM 2018
 */
public class ExecutionLogEntity extends EntityBase<ExecutionLogEntity> {

	/** Attribute that determine a Constant of LOGGER. */
	private static final transient Logger LOGGER = Logger.getLogger(ExecutionLogEntity.class);

	/** Attribute that determine id. */
	private String id;

	/** Attribute that determine adapter. */
	private String adapter;

	/** Attribute that determine logUser. */
	private String logUser;

	/** Attribute that determine request. */
	private String request;

	/** Attribute that determine ip. */
	private String ip;

	/**
	 * Instantiates a new execution log entity.
	 */
	public ExecutionLogEntity() {
	}

	/**
	 * Instantiates a new execution log entity.
	 *
	 * @param sessionBase
	 *            the session base
	 * @throws PersistenceException
	 *             the persistence exception
	 */
	public ExecutionLogEntity(SessionBase sessionBase) throws PersistenceException {
		super(sessionBase);
	}
	
	/* (non-Javadoc)
	 * @see hn.com.tigo.josm.persistence.core.EntityBase#fillList(java.sql.ResultSet)
	 */
	@Override
	protected List<ExecutionLogEntity> fillList(ResultSet resultSet) throws PersistenceException {
		final List<ExecutionLogEntity> list = new ArrayList<ExecutionLogEntity>();
		try {
			while (resultSet.next()) {
				final ExecutionLogEntity executionLogEntity = new ExecutionLogEntity();
				executionLogEntity.setId(resultSet.getString("Id"));
				executionLogEntity.setAdapter(resultSet.getString("Adapter"));
				executionLogEntity.setIp(resultSet.getString("Ip"));
				executionLogEntity.setLogUser(resultSet.getString("LogUser"));
				executionLogEntity.setRequest(resultSet.getString("Request"));

				list.add(executionLogEntity);
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
		return id;
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
	 * Gets the adapter.
	 *
	 * @return the adapter
	 */
	public String getAdapter() {
		return adapter;
	}

	/**
	 * Sets the adapter.
	 *
	 * @param adapter
	 *            the new adapter
	 */
	public void setAdapter(String adapter) {
		this.adapter = adapter;
	}

	/**
	 * Gets the log user.
	 *
	 * @return the log user
	 */
	public String getLogUser() {
		return logUser;
	}

	/**
	 * Sets the log user.
	 *
	 * @param logUser
	 *            the new log user
	 */
	public void setLogUser(String logUser) {
		this.logUser = logUser;
	}

	/**
	 * Gets the request.
	 *
	 * @return the request
	 */
	public String getRequest() {
		return request;
	}

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */
	public void setRequest(String request) {
		this.request = request;
	}

	/**
	 * Gets the ip.
	 *
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Sets the ip.
	 *
	 * @param ip
	 *            the new ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

}
