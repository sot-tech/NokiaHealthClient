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
public class MeasureGroup {

	private int grpid, attrib, category;
	private Date date;
	private String comment;
	private List<MeasureValue> measures;

	public static final List<String> ATTRIB, CATEGORY;

	static {
		ArrayList<String> tmp = new ArrayList<>(7);
		tmp.add("The measuregroup has been captured by a device and is known to belong to this user (and is not ambiguous)");
		tmp.add("The measuregroup has been captured by a device but may belong to other users as well as this one (it is ambiguous)");
		tmp.add("The measuregroup has been entered manually for this particular user");
		tmp.add("The measuregroup has been entered manually during user creation (and may not be accurate)r");
		tmp.add("Measure auto, it's only for the Blood Pressure Monitor. This device can make many measures and computed the best value");
		tmp.add("Measure confirmed. You can get this value if the user confirmed a detected activity");
		tmp.add("The measuregroup has been captured by a device and is known to belong to this user (and is not ambiguous)");
		ATTRIB = Collections.unmodifiableList(tmp);
		tmp = new ArrayList<>(2);
		tmp.add("real measurement");
		tmp.add("user objective");
		CATEGORY = Collections.unmodifiableList(tmp);
	}

	public int getGrpId() {
		return grpid;
	}

	public String getAttrib() {
		return ATTRIB.get(attrib);
	}

	public String getCategory() {
		return CATEGORY.get(category);
	}

	public Date getDate() {
		return date;
	}

	public String getComment() {
		return comment;
	}

	public List<MeasureValue> getMeasures() {
		return measures;
	}

	public void setGrpid(int grpid) {
		this.grpid = grpid;
	}

	public void setAttrib(int attrib) {
		this.attrib = attrib;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setMeasures(List<MeasureValue> measures) {
		this.measures = measures;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 97 * hash + this.grpid;
		hash = 97 * hash + this.attrib;
		hash = 97 * hash + this.category;
		hash = 97 * hash + Objects.hashCode(this.date);
		hash = 97 * hash + Objects.hashCode(this.comment);
		hash = 97 * hash + Objects.hashCode(this.measures);
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
		final MeasureGroup other = (MeasureGroup) obj;
		if (this.grpid != other.grpid) {
			return false;
		}
		if (this.attrib != other.attrib) {
			return false;
		}
		if (this.category != other.category) {
			return false;
		}
		if (!Objects.equals(this.comment, other.comment)) {
			return false;
		}
		if (!Objects.equals(this.date, other.date)) {
			return false;
		}
		return Objects.equals(this.measures, other.measures);
	}

	@Override
	public String toString() {
		return "MeasureGroup{" + "grpId=" + grpid + ", attrib=" + getAttrib() + ", category=" + getCategory() 
			   + ", date=" + date + ", comment=" + comment + ", measures=" + measures + '}';
	}

}
