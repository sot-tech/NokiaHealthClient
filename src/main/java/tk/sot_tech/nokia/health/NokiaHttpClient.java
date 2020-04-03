/*
 * Copyright (c) 2018, sot
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package tk.sot_tech.nokia.health;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tk.sot_tech.nokia.health.oauth.NokiaOAuth;
import tk.sot_tech.nokia.health.oauth.NokiaOAuth.CustomOAuth1AccessToken;

/**
 *
 * @author sot
 */
public class NokiaHttpClient {

	private final OAuth10aService service;
	private AccessTokenVerifier cmdAuthCallBack = (String url) -> {
		System.out.println("" + url + "\nBrowse URL and enter oauth_verifier here: ");
		String ret = "";
		try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
			ret = bufferedReader.readLine();
		}
		catch (IOException ex) {
			Logger.getLogger(NokiaHttpClient.class.getName()).log(Level.SEVERE, null, ex);
		}
		return ret;
	};

	public NokiaHttpClient(String apiKey, String apiSecret) {
		service = new ServiceBuilder(apiKey)
				.apiSecret(apiSecret)
				.callback("")
				.build(new NokiaOAuth());
	}

	public NokiaHttpClient setAuthCallbackUrlHandler(AccessTokenVerifier callback) {
		cmdAuthCallBack = callback;
		return this;
	}

	private CustomOAuth1AccessToken getAccessToken() throws IOException, InterruptedException, ExecutionException {
		OAuth1RequestToken requestToken = service.getRequestToken();
		String authUrl = service.getAuthorizationUrl(requestToken);
		return (CustomOAuth1AccessToken) service.getAccessToken(requestToken, cmdAuthCallBack.verify(authUrl));
	}

	public Response get(String url) throws IOException, InterruptedException, ExecutionException {
		CustomOAuth1AccessToken token = getAccessToken();
		String modUrl = token.userId < 0 ? url : url + "&userid=" + token.userId;
		OAuthRequest request = new OAuthRequest(Verb.GET, modUrl);
		service.signRequest(token, request);

		return service.execute(request);
	}

	@FunctionalInterface
	public static interface AccessTokenVerifier {
		public String verify(String url);
	}

}
