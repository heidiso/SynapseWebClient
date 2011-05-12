package org.sagebionetworks.repo.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.sagebionetworks.repo.model.query.BasicQuery;
import org.sagebionetworks.repo.model.query.FieldType;

/**
 * Used to query for Node IDs.
 * 
 * @author jmhill
 *
 */
public interface NodeQueryDao {
	
	/**
	 * Execute a query, and return a paginated list of node ids.
	 * @param query
	 * @return
	 * @throws DatastoreException 
	 */
	public NodeQueryResults executeQuery(BasicQuery query) throws DatastoreException;

	/**
	 * Execute a given SQL query using the JDO template.
	 * @param <T>
	 * @param clazz
	 * @param sql
	 * @param parameters
	 * @return
	 */
	public List executeQuery(final String sql, final Map<String, Object> parameters);
}