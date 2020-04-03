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

import java.util.*;

/**
 *
 * @author sot
 */
public class Body{
	private Date updatetime;
	private String timezone;
	private List<MeasureGroup> measuregrps;

	public Date getUpdateTime() {
		Calendar instance = Calendar.getInstance();
		instance.setTime(updatetime);
		instance.setTimeZone(TimeZone.getTimeZone(timezone));
		return instance.getTime();
	}

	public String getTimezone() {
		return timezone;
	}

	public List<MeasureGroup> getMeasuregrps() {
		return measuregrps;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public void setMeasuregrps(List<MeasureGroup> measuregrps) {
		this.measuregrps = measuregrps;
	}


	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17 * hash + Objects.hashCode(this.updatetime);
		hash = 17 * hash + Objects.hashCode(this.timezone);
		hash = 17 * hash + Objects.hashCode(this.measuregrps);
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
		final Body other = (Body) obj;
		if (!Objects.equals(this.timezone, other.timezone)) {
			return false;
		}
		if (!Objects.equals(this.updatetime, other.updatetime)) {
			return false;
		}
		return Objects.equals(this.measuregrps, other.measuregrps);
	}

	@Override
	public String toString() {
		return "Body{" + "updateTime=" + getUpdateTime() + ", timezone=" + timezone + ", measureGrps=" + measuregrps + '}';
	}	
}
