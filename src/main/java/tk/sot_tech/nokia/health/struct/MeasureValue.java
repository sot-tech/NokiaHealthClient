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

/**
 *
 * @author sot
 */
public class MeasureValue {

	private int value, type, unit;

	public static final Map<Integer, String> TYPES;

	static {
		HashMap<Integer, String> tmp = new HashMap<>();
		tmp.put(1, "Weight (kg)");
		tmp.put(4, "Height (meter)");
		tmp.put(5, "Fat Free Mass (kg)");
		tmp.put(6, "Fat Ratio (%)");
		tmp.put(8, "Fat Mass Weight (kg)");
		tmp.put(9, "Diastolic Blood Pressure (mmHg)");
		tmp.put(10, "Systolic Blood Pressure (mmHg)");
		tmp.put(11, "Heart Pulse (bpm)");
		tmp.put(12, "Temperature");
		tmp.put(54, "SP02(%)");
		tmp.put(71, "Body Temperature");
		tmp.put(73, "Skin Temperature");
		tmp.put(76, "Muscle Mass");
		tmp.put(77, "Hydration");
		tmp.put(88, "Bone Mass");
		tmp.put(91, "Pulse Wave Velocity");
		TYPES = Collections.unmodifiableMap(tmp);
	}

	public float getValue() {
		return (float) (value * Math.pow(10.0, unit));
	}

	public String getType() {
		return TYPES.get(type);
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getUnit() {
		return unit;
	}

	public void setType(int type) {
		this.type = type;
	}

	public void setUnit(int unit) {
		this.unit = unit;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 89 * hash + this.value;
		hash = 89 * hash + this.type;
		hash = 89 * hash + this.unit;
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
		final MeasureValue other = (MeasureValue) obj;
		if (this.value != other.value) {
			return false;
		}
		if (this.type != other.type) {
			return false;
		}
		return this.unit == other.unit;
	}

	@Override
	public String toString() {
		return "MeasureValue{" + "value=" + getValue() + ", type=" + getType() + '}';
	}

}
