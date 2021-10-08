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
package ca.mcgill.cs.jetuml.diagram.edges;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.PropertyName;
import ca.mcgill.cs.jetuml.diagram.nodes.CallNode;

public class TestCallEdge
{
	@Test
	public void testGetWithProperty()
	{
		CallEdge edge = new CallEdge();
		assertFalse(edge.isSignal());
		edge.setSignal(true);
		assertTrue(edge.isSignal());
		assertTrue((boolean) edge.properties().get(PropertyName.SIGNAL).get());
		edge.properties().get(PropertyName.SIGNAL).set(false);
		assertFalse((boolean) edge.properties().get(PropertyName.SIGNAL).get());
		assertFalse(edge.isSignal());
		
		edge.properties().get(PropertyName.MIDDLE_LABEL).set("Foo");
		assertEquals("Foo", edge.getMiddleLabel());
	}
	
	@Test
	public void testGetWithPropertyAndClone()
	{
		CallEdge edge = new CallEdge();
		CallEdge clone = (CallEdge) edge.clone();
		
		edge.properties().get(PropertyName.MIDDLE_LABEL).set("Foo");
		
		assertEquals("Foo", edge.properties().get(PropertyName.MIDDLE_LABEL).get());
		assertEquals("", clone.properties().get(PropertyName.MIDDLE_LABEL).get());
	}
	
	@Test
	public void testCallEdgeEqualsItself()
	{
		CallEdge callEdge = new CallEdge();
		assertEquals(callEdge, callEdge);
		assertEquals(callEdge.hashCode(), callEdge.hashCode());
	}
	
	@Test
	public void testCallEdgeEqualsItsClone()
	{
		CallEdge callEdge = new CallEdge();
		assertEquals(callEdge, callEdge.clone());
		assertEquals(callEdge.hashCode(), callEdge.clone().hashCode());
	}
	
	@Test
	public void testCallEdgeNotEqualsNull()
	{
		CallEdge callEdge = new CallEdge();
		assertNotEquals(callEdge, null);
	}
	
	@Test
	public void testCallEdgeNotEqualsNoteEdge()
	{
		CallEdge callEdge = new CallEdge();
		NoteEdge noteEdge = new NoteEdge();
		assertNotEquals(callEdge, noteEdge);
		assertNotEquals(callEdge.hashCode(), noteEdge.hashCode());
	}
	
	@Test
	public void testCallEdgeEqualsAnotherCallEdge()
	{
		CallEdge callEdge1 = new CallEdge();
		CallEdge callEdge2 = new CallEdge();
		assertEquals(callEdge1, callEdge2);
		assertEquals(callEdge1.hashCode(), callEdge2.hashCode());
	}
	
	@Test
	public void testCallEdgeEqualsAnotherCallEdgeWithSameTerminals()
	{
		CallEdge callEdge1 = new CallEdge();
		CallEdge callEdge2 = new CallEdge();
		CallNode callNode1 = new CallNode();
		CallNode callNode2 = new CallNode();
		callEdge1.connect(callNode1, callNode2, null);
		callEdge2.connect(callNode1, callNode2, null);
		assertEquals(callEdge1, callEdge2);
		assertEquals(callEdge1.hashCode(), callEdge2.hashCode());
	}
	
	@Test
	public void testCallEdgeNotEqualsAnotherCallEdgeWithDifferentTerminals()
	{
		CallEdge callEdge1 = new CallEdge();
		CallEdge callEdge2 = new CallEdge();
		CallNode callNode1 = new CallNode();
		CallNode callNode2 = new CallNode();
		CallNode callNode3 = new CallNode();
		CallNode callNode4 = new CallNode();
		callNode3.setOpenBottom(true);
		callNode4.setOpenBottom(true);
		callEdge1.connect(callNode1, callNode2, null);
		callEdge2.connect(callNode3, callNode4, null);
		assertNotEquals(callEdge1, callEdge2);
		assertNotEquals(callEdge1.hashCode(), callEdge2.hashCode());
	}
	
	@Test
	public void testCallEdgeNotEqualsAnotherCallEdgeWithOneDifferentTerminal()
	{
		CallEdge callEdge1 = new CallEdge();
		CallEdge callEdge2 = new CallEdge();
		CallNode callNode1 = new CallNode();
		CallNode callNode2 = new CallNode();
		CallNode callNode3 = new CallNode();
		callNode3.setOpenBottom(true);
		callEdge1.connect(callNode1, callNode2, null);
		callEdge2.connect(callNode1, callNode3, null);
		assertNotEquals(callEdge1, callEdge2);
		assertNotEquals(callEdge1.hashCode(), callEdge2.hashCode());
	}
}
