/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *	 http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pig.data;

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;

/**
 * Indicates an float datum.
 */

public class DataFloat extends AtomicDatum {

public DataFloat() { mVal = 0; }

public DataFloat(float val) { mVal = val; }

public DataType getType() { return Datum.DataType.FLOAT; }

public long size() { return 4L; }

/**
 * Get the value of the datum, as a float.
 * @return float value
 */
public final float get() { return mVal; }

/**
 * Set the value of the datum.
 * @param val Value to be set.
 */
public final void set(float val) { mVal = val; }

public String toString()
{
	return String.valueOf(mVal);
}

public int hashCode()
{
	return (int)Double.doubleToLongBits(mVal);
}

// Don't make this use compareTo.  These functions are used in things like hashs
// and we want them to be as fast as possible.
public boolean equals(Object other)
{
	if (!(other instanceof DataFloat)) return false;
	DataFloat o = (DataFloat)other;
	return mVal == o.mVal;
}

public int compareTo(Object other)
{
	if (!(other instanceof Datum)) return -1;

	Datum od = (Datum)other;

	if (od.getType() != Datum.DataType.FLOAT) return crossTypeCompare(od);

	DataFloat f = (DataFloat)od;

	if (mVal < f.mVal) return -1;
	else if (mVal > f.mVal) return 1;
	else return 0;
}

public void write(DataOutput out) throws IOException 
{
	out.write(Datum.DataType.FLOAT.getMarker());
	out.writeFloat(mVal);	
}

static DataFloat read(DataInput in) throws IOException
{
	// Assumes that the float indicator has already been read in order to
	// select his function.
	return new DataFloat(in.readFloat());
}
 
private float mVal;

}

