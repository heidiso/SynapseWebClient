package org.sagebionetworks.web.client.widget.entity.editor;

import java.util.Map;

import org.sagebionetworks.web.client.DisplayUtils;
import org.sagebionetworks.web.client.widget.WidgetEditorPresenter;
import org.sagebionetworks.web.client.widget.entity.registration.WidgetConstants;
import org.sagebionetworks.web.shared.WikiPageKey;

import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.extjs.gxt.ui.client.widget.Dialog;
public class YouTubeConfigEditor implements YouTubeConfigView.Presenter, WidgetEditorPresenter {
	
	private YouTubeConfigView view;
	private Map<String, String> descriptor;
	@Inject
	public YouTubeConfigEditor(YouTubeConfigView view) {
		this.view = view;
		view.setPresenter(this);
		view.initView();
	}
	@Override
	public void configure(WikiPageKey wikiKey, Map<String, String> widgetDescriptor, Dialog window) {
		descriptor = widgetDescriptor;
		String videoId = descriptor.get(WidgetConstants.YOUTUBE_WIDGET_VIDEO_ID_KEY);
		if (videoId != null)
			view.setVideoUrl(DisplayUtils.getYouTubeVideoUrl(videoId));
	}
	
	@SuppressWarnings("unchecked")
	public void clearState() {
		view.clear();
	}

	@Override
	public Widget asWidget() {
		return view.asWidget();
	}

	@Override
	public void updateDescriptorFromView() {
		//update widget descriptor from the view
		view.checkParams();
		descriptor.put(WidgetConstants.YOUTUBE_WIDGET_VIDEO_ID_KEY, DisplayUtils.getYouTubeVideoId(view.getVideoUrl()));
	}
	
	@Override
	public int getDisplayHeight() {
		return view.getDisplayHeight();
	}
	
	@Override
	public int getAdditionalWidth() {
		return view.getAdditionalWidth();
	}
	
	@Override
	public String getTextToInsert() {
		return null;
	}
	
	/*
	 * Private Methods
	 */
}
