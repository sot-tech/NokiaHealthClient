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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import tk.sot_tech.nokia.health.NokiaHttpClient;

/**
 *
 * @author sot
 */
public class NokiaCookieVerifier implements NokiaHttpClient.AccessTokenVerifier {
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.121";
	private static final Pattern LINK_PATTERN = Pattern.compile("\\'(.*)\\'"), 
		KEY_PATTERN = Pattern.compile("oauth_verifier=([a-zA-Z0-9]{4,64})");
	static {
		CookieHandler.setDefault(new CookieManager());
	}
	
	private final String cookies;

	public NokiaCookieVerifier(String cookies) {
		this.cookies = cookies;
	}

	@Override
	public String verify(String url) {
		String tmp = getHtmlFormString(url, (String s) -> s.contains("acceptDelegation=true"));
		Matcher matcher = LINK_PATTERN.matcher(tmp);
		if (matcher.find() && matcher.groupCount() >= 1) {
			tmp = matcher.group(1);
		}
		else {
			throw new IllegalStateException("Unable to parse correct link");
		}
		tmp = getHtmlFormString(tmp, (String s) -> s.contains("oauth_verifier="));
		matcher = KEY_PATTERN.matcher(tmp);
		if (matcher.find() && matcher.groupCount() >= 1) {
			tmp = matcher.group(1);
		}
		else {
			throw new IllegalStateException("Unable to parse correct link");
		}
		return tmp;
	}
	
	private String getHtmlFormString(String url, Predicate<String> filter) {
		HttpURLConnection conn = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setUseCaches(false);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("User-Agent", USER_AGENT);
			conn.setRequestProperty("Accept",
									"text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			conn.setRequestProperty("Host", conn.getURL().getHost());
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Cookie", cookies);
			conn.setDoOutput(true);
			int responseCode = conn.getResponseCode();
			if (responseCode >= HttpURLConnection.HTTP_BAD_REQUEST) {
				throw new IllegalStateException("HTTP Error " + responseCode);
			}
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
				Optional<String> urlJsString = reader.lines().filter(filter).findFirst();
				if (!urlJsString.isPresent()) {
					throw new IllegalStateException("Unable to find correct link");
				}
				return urlJsString.get();
			}
		}
		catch (IOException | IllegalStateException ex) {
			Logger.getLogger(NokiaCookieVerifier.class.getName()).log(Level.SEVERE, null, ex);
		}
		finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		return null;
	}

}
