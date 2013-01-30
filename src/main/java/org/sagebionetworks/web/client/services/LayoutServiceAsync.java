package org.sagebionetworks.web.client.services;

import org.sagebionetworks.web.shared.provenance.ProvGraph;
import org.sagebionetworks.web.shared.provenance.ProvTreeNode;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LayoutServiceAsync {

	void layoutProvTree(ProvTreeNode root, AsyncCallback<ProvTreeNode> callback);

	void dagLayout(ProvGraph provGraph, AsyncCallback<ProvGraph> callback);

}
