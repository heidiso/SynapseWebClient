package org.sagebionetworks.web.client.view;

import org.sagebionetworks.web.client.PlaceChanger;

import com.google.gwt.user.client.ui.IsWidget;

public interface PhenoEditView extends IsWidget{
	
	public void setEditorDetails(String layerId, String layerName, String layerLink, String datasetLink);
	
	/**
	 * Set this view's presenter
	 * @param presenter
	 */
	public void setPresenter(Presenter presenter);
		
	/**
	 * The view pops-up an error dialog.
	 * @param message
	 */
	public void showErrorMessage(String message);
	
	
	public interface Presenter {

		PlaceChanger getPlaceChanger();
	}

}