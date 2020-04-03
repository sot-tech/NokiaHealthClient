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

import com.github.scribejava.core.model.Response;
import java.util.Date;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Before;
import org.junit.Test;
import tk.sot_tech.nokia.health.struct.MeasureContainer;
import static tk.sot_tech.nokia.health.struct.MeasureContainer.STATUS;
import tk.sot_tech.nokia.health.struct.MeasureGroup;
import tk.sot_tech.nokia.health.utility.CustomJsonCodec;

/**
 *
 * @author sot
 */
public class NokiaHttpClientTest {

	private static final String API_KEY = "PLACE_YOUR_KEY_HERE";
	private static final String API_SECRET = "PLACE_YOUR_SECRET_HERE";
	private static final String COOKIE = "session_key=PLACE_YOUR_COOKIE_HERE";
	private static final String URL = "https://api.health.nokia.com/measure?action=getmeas"
									  + "&lastupdate="
									  + (new Date().getTime() / 1000 - 120);

	private NokiaHttpClient instance;

	@Before
	public void testInit() {
		System.out.println("init");
		instance = new NokiaHttpClient(API_KEY, API_SECRET)
				/*.setAuthCallbackUrlHandler(new NokiaCookieVerifier(COOKIE))*/;
		assertNotNull(instance);
	}

	@Test
	public void testGet() throws Exception {

		System.out.println("get");
		Response response = instance.get(URL);
		assertNotNull(response);
		String data = response.getBody();
		assertNotNull(data);
		MeasureContainer entityContainer = CustomJsonCodec.getService().fromJson(data, MeasureContainer.class);
		assertNotNull(entityContainer);
		assertEquals(STATUS.get(0), entityContainer.getStatus());
		assertNotNull(entityContainer.getBody());
		List<MeasureGroup> measuregrps = entityContainer.getBody().getMeasuregrps();
		assertNotNull(measuregrps);
		measuregrps.stream().filter((group) -> {
			assertNotNull(group);
			return true;
		})
		.sorted((MeasureGroup o1, MeasureGroup o2) -> o1.getDate().compareTo(o2.getDate()))
		.map((group) -> {
			System.out.println(group.getDate());
			return group.getMeasures();
		}).filter((measure) -> {
			assertNotNull(measure);
			return true;
		}).forEach((measure) -> {
			measure.stream().forEach(System.out::println);
		});
	}

}
