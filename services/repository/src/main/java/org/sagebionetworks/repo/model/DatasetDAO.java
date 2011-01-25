package org.sagebionetworks.repo.model;

import java.util.Collection;

/**
 * This interface defines the methods to be supported by any DAO for Datasets.
 * 
 * @author bhoff
 *
 */
public interface DatasetDAO extends BaseDAO<Dataset>, AnnotatableDAO<Dataset>,
		RevisableDAO<Dataset> {
	
	/**
	 * 
	 * @param datasetId the ID of the dataset to which the layer is to be added
	 * @param layerId ID of the layer to add
	 */
	public void addLayer(String datasetId, String layerId);

	/**
	 * 
	 * @param datasetId the ID of the dataset from which the layer is to be removed
	 * @param layerId ID of the layer to remove
	 */
	public void removeLayer(String datasetId, String layerId);

	/**
	 * 
	 * @param datasetId the ID of the dataset of interest
	 * @return IDs of all the layers in the dataset
	 */
	public Collection<String> getLayers(String datasetId);
}
