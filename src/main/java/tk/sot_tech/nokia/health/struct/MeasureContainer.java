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
package tk.sot_tech.nokia.health.struct;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author sot
 */
public class MeasureContainer {

	private int status = 2556;
	private Body body;

	public static final Map<Integer, String> STATUS;

	static {
		HashMap<Integer, String> tmp = new HashMap<>();
		tmp.put(0, "Operation was successful");
		tmp.put(247, "The userid provided is absent, or incorrect");
		tmp.put(250, "The provided userid and/or Oauth credentials do not match");
		tmp.put(283, "Token is invalid or doesn't exist");
		tmp.put(286, "No such subscription was found");
		tmp.put(293, "The callback URL is either absent or incorrect");
		tmp.put(294, "No such subscription could be deleted");
		tmp.put(304, "The comment is either absent or incorrect");
		tmp.put(305, "Too many notifications are already set");
		tmp.put(328, "The user is deactivated");
		tmp.put(342, "The signature (using Oauth) is invalid");
		tmp.put(343, "Wrong Notification Callback Url don't exist");
		tmp.put(601, "Too Many Request");
		tmp.put(2554, "Wrong action or wrong webservice");
		tmp.put(2555, "An unknown error occurred ");
		tmp.put(2556, "Service is not defined");
		STATUS = Collections.unmodifiableMap(tmp);
	}

	public String getStatus() {
		return STATUS.get(status);
	}

	public Body getBody() {
		return body;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 41 * hash + this.status;
		hash = 41 * hash + Objects.hashCode(this.body);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final MeasureContainer other = (MeasureContainer) obj;
		if (this.status != other.status) {
			return false;
		}
		return Objects.equals(this.body, other.body);
	}

	@Override
	public String toString() {
		return "MeasureContainer{" + "status=" + getStatus() + ", body=" + body + '}';
	}

}
