package com.ber.router.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void routeServer(String orig, String dest, AsyncCallback<String> callback)
			throws IllegalArgumentException;
}
