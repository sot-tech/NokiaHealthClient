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
package tk.sot_tech.nokia.health.oauth;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.builder.api.OAuth1SignatureType;
import com.github.scribejava.core.extractors.OAuth1AccessTokenExtractor;
import com.github.scribejava.core.extractors.TokenExtractor;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.services.TimestampService;
import com.github.scribejava.core.services.TimestampServiceImpl;
import java.util.Base64;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author sot
 */
public class NokiaOAuth extends DefaultApi10a {

	public static final String AUTHORIZATION_URL = "https://developer.health.nokia.com/account/authorize?oauth_token=",
			REQUEST_TOKEN_URL = "https://developer.health.nokia.com/account/request_token",
			ACCESS_TOKEN_URL = "https://developer.health.nokia.com/account/access_token";
	private final OAuth1AccessTokenExtractor EXTRACTOR = new CustomOAuth1AccessTokenExtractor();
	private final TimestampService TIMESTAMP_SERVICE = new CustomTimestampService();

	@Override
	public String getRequestTokenEndpoint() {
		return REQUEST_TOKEN_URL;
	}

	@Override
	public String getAccessTokenEndpoint() {
		return ACCESS_TOKEN_URL;
	}

	@Override
	public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
		return AUTHORIZATION_URL + requestToken.getToken();
	}

	@Override
	public OAuth1SignatureType getSignatureType() {
		return OAuth1SignatureType.QueryString;
	}

	@Override
	public TokenExtractor<OAuth1AccessToken> getAccessTokenExtractor() {
		return EXTRACTOR;
	}

	@Override
	public TimestampService getTimestampService() {
		return TIMESTAMP_SERVICE;
	}

	private class CustomOAuth1AccessTokenExtractor extends OAuth1AccessTokenExtractor {

		private final Pattern USER_REGEXP_PATTERN = Pattern.compile("userid=([^&]+)");

		@Override
		protected OAuth1AccessToken createToken(String token, String secret, String response) {
			final Matcher matcher = USER_REGEXP_PATTERN.matcher(response);
			int userId = -1;
			if (matcher.find() && matcher.groupCount() >= 1) {
				userId = Integer.decode(matcher.group(1));
			}
			return new CustomOAuth1AccessToken(token, secret, response, userId);
		}
	}

	private class CustomTimestampService extends TimestampServiceImpl {

		private final Random random = new Random();

		@Override
		public String getNonce() {
			byte[] bytes = new byte[32];
			for (int i = 0; i < bytes.length; ++i) {
				bytes[i] = (byte) random.nextInt();
			}
			return Base64.getEncoder().encodeToString(bytes);
		}

	};

	public class CustomOAuth1AccessToken extends OAuth1AccessToken {

		public final int userId;

		private CustomOAuth1AccessToken(String token, String secret, String response, int userId) {
			super(token, secret, response);
			this.userId = userId;
		}

	}

}
