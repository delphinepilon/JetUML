/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2020, 2021 by McGill University.
 *     
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 *******************************************************************************/
package ca.mcgill.cs.jetuml.diagram;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class TestProperty
{
	class Stub { String aValue; Stub(String pValue) { aValue = pValue; } }
	
	@Test
	public void testProperty()
	{
		Stub stub = new Stub("value");
		Property property = new Property(PropertyName.NAME, () -> stub.aValue, newval -> stub.aValue = (String) newval);
		assertEquals(PropertyName.NAME, property.name());
		assertEquals("value", property.get());
		
		property.set("foo");
		assertEquals("foo", property.get());
	}
	
	@Test
	public void testPropertyEqualsItself()
	{
		Stub stub = new Stub("value");
		Property property = new Property(PropertyName.NAME, () -> stub.aValue, newval -> stub.aValue = (String) newval);
		assertEquals(property, property);
		assertEquals(property.hashCode(), property.hashCode());
	}
	
	@Test
	public void testPropertyEqualsOtherPropertyWithSameValues()
	{
		Stub stub1 = new Stub("value1");
		Stub stub2 = new Stub("value1");
		Property property1 = new Property(PropertyName.NAME, () -> stub1.aValue, newval -> stub1.aValue = (String) newval);
		Property property2 = new Property(PropertyName.NAME, () -> stub2.aValue, newval -> stub2.aValue = (String) newval);
		assertEquals(property1, property2);
		assertEquals(property1.hashCode(), property2.hashCode());
	}
	
	@Test
	public void testPropertyNotEqualsOtherPropertyWithDifferentValue()
	{
		Stub stub1 = new Stub("value1");
		Stub stub2 = new Stub("value2");
		Property property1 = new Property(PropertyName.NAME, () -> stub1.aValue, newval -> stub1.aValue = (String) newval);
		Property property2 = new Property(PropertyName.NAME, () -> stub2.aValue, newval -> stub2.aValue = (String) newval);
		assertNotEquals(property1, property2);
		assertNotEquals(property1.hashCode(), property2.hashCode());
	}
	
	@Test
	public void testPropertyNotEqualToNull()
	{
		Stub stub = new Stub("value");
		Property property = new Property(PropertyName.NAME, () -> stub.aValue, newval -> stub.aValue = (String) newval);
		assertNotEquals(null, property);
	}
	
	@Test
	public void testPropertyNotEqualString()
	{
		String anyString = "anyString";
		Stub stub = new Stub("value");
		Property property = new Property(PropertyName.NAME, () -> stub.aValue, newval -> stub.aValue = (String) newval);
		assertNotEquals(property, anyString);
		assertNotEquals(property.hashCode(), anyString.hashCode());
	}
}
