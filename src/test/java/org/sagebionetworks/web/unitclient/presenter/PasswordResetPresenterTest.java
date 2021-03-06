package org.sagebionetworks.web.unitclient.presenter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.sagebionetworks.repo.model.UserProfile;
import org.sagebionetworks.repo.model.UserSessionData;
import org.sagebionetworks.web.client.ClientProperties;
import org.sagebionetworks.web.client.DisplayConstants;
import org.sagebionetworks.web.client.GlobalApplicationState;
import org.sagebionetworks.web.client.IconsImageBundle;
import org.sagebionetworks.web.client.PlaceChanger;
import org.sagebionetworks.web.client.SageImageBundle;
import org.sagebionetworks.web.client.UserAccountServiceAsync;
import org.sagebionetworks.web.client.cookie.CookieProvider;
import org.sagebionetworks.web.client.place.Home;
import org.sagebionetworks.web.client.place.users.PasswordReset;
import org.sagebionetworks.web.client.presenter.users.PasswordResetPresenter;
import org.sagebionetworks.web.client.security.AuthenticationController;
import org.sagebionetworks.web.client.transform.NodeModelCreator;
import org.sagebionetworks.web.client.view.users.PasswordResetView;
import org.sagebionetworks.web.shared.exceptions.RestServiceException;
import org.sagebionetworks.web.test.helper.AsyncMockStubber;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;

public class PasswordResetPresenterTest {
	
	PasswordResetPresenter presenter;
	PasswordResetView mockView;
	CookieProvider mockCookieProvider;
	UserAccountServiceAsync mockUserService;
	GlobalApplicationState mockGlobalApplicationState;
	
	AuthenticationController mockAuthenticationController;
	SageImageBundle mockSageImageBundle;
	IconsImageBundle mockIconsImageBundle;
	NodeModelCreator mockNodeModelCreator;
	PlaceChanger mockPlaceChanger;
	PasswordReset place = Mockito.mock(PasswordReset.class);
	UserSessionData currentUserSessionData = new UserSessionData();

	
	@Before
	public void setup() {
		mockView = mock(PasswordResetView.class);
		mockCookieProvider = mock(CookieProvider.class);
		mockUserService = mock(UserAccountServiceAsync.class);
		mockGlobalApplicationState = mock(GlobalApplicationState.class);
		mockAuthenticationController = mock(AuthenticationController.class);
		mockSageImageBundle = mock(SageImageBundle.class);
		mockIconsImageBundle = mock(IconsImageBundle.class);
		mockNodeModelCreator = mock(NodeModelCreator.class);
		mockPlaceChanger = mock(PlaceChanger.class);
		mockAuthenticationController = Mockito.mock(AuthenticationController.class);
		
		presenter = new PasswordResetPresenter(mockView, mockCookieProvider,
				mockUserService, mockAuthenticationController,
				mockSageImageBundle, mockIconsImageBundle,
				mockGlobalApplicationState, mockNodeModelCreator);			
		verify(mockView).setPresenter(presenter);
		when(place.toToken()).thenReturn(ClientProperties.DEFAULT_PLACE_TOKEN);
		currentUserSessionData.setProfile(new UserProfile());
	}
	
	private void resetAll(){
		reset(mockView);
		reset(mockCookieProvider);
		reset(mockUserService);
		reset(mockGlobalApplicationState);
		reset(mockAuthenticationController);
		reset(mockSageImageBundle);
		reset(mockIconsImageBundle);
		reset(mockNodeModelCreator);

		when(mockGlobalApplicationState.getPlaceChanger()).thenReturn(mockPlaceChanger);
		when(mockAuthenticationController.isLoggedIn()).thenReturn(true);
	}
	
	@Test
	public void testStart() {
		resetAll();
		presenter.setPlace(place);

		AcceptsOneWidget panel = mock(AcceptsOneWidget.class);
		EventBus eventBus = mock(EventBus.class);		
		
		presenter.start(panel, eventBus);		
		verify(panel).setWidget(mockView);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testResetPassword() {
		//without the registration token set, mock a successful user service call
		resetAll();
		presenter.setPlace(place);
		AsyncMockStubber.callSuccessWith(null).when(mockUserService).changePassword(any(String.class), any(String.class), any(AsyncCallback.class));
		presenter.resetPassword("myPassword");
		//verify password reset text is shown in the view
		verify(mockView).showInfo(DisplayConstants.PASSWORD_RESET_TEXT);
		//verify that place is changed to Home
		verify(mockPlaceChanger).goTo(any(Home.class));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testServiceFailure() {
		//without the registration token set, mock a failed user service call
		resetAll();
		AsyncMockStubber.callFailureWith(new RestServiceException("unknown error")).when(mockUserService).changePassword(any(String.class), any(String.class), any(AsyncCallback.class));
		presenter.setPlace(place);
		presenter.resetPassword("myPassword");
		//verify password reset failed text is shown in the view
		verify(mockView).showErrorMessage(DisplayConstants.PASSWORD_RESET_FAILED_TEXT);
	}

}
