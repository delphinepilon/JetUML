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
package ca.mcgill.cs.jetuml.diagram.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramType;
import ca.mcgill.cs.jetuml.diagram.Properties;
import ca.mcgill.cs.jetuml.diagram.PropertyName;

public class TestCallNode
{
	private CallNode aNode;
	
	@BeforeEach
	public void setup()
	{
		aNode = new CallNode();
	}
	
	@Test
	public void testGetProperties()
	{
		Properties properties = aNode.properties();
		
		assertEquals(false, properties.get(PropertyName.OPEN_BOTTOM).get());
		
		aNode.setOpenBottom(true);
		aNode.translate(10, 20);
		properties = aNode.properties();
		assertEquals(true, properties.get(PropertyName.OPEN_BOTTOM).get());
	}
	
	@Test
	public void testOpenBottom()
	{
		assertFalse(aNode.isOpenBottom());
		aNode.setOpenBottom(true);
		assertTrue(aNode.isOpenBottom());
	}
	
	@Test
	public void testClone_OpenBottom()
	{
		assertFalse(aNode.clone().isOpenBottom());
		aNode.setOpenBottom(true);
		assertTrue(aNode.clone().isOpenBottom());
	}
	
	@Test
	public void testClone_Parent()
	{
		ImplicitParameterNode parent = new ImplicitParameterNode();
		aNode.link(parent);
		assertSame(parent, aNode.clone().getParent());
	}
	
	@Test
	public void testClone_Diagram()
	{
		Diagram diagram = new Diagram(DiagramType.SEQUENCE);
		aNode.attach(diagram);
		assertSame(diagram, aNode.clone().getDiagram().get());
	}
	
	@Test
	public void testRequiresParent()
	{
		assertTrue(aNode.requiresParent());
	}
	
	@Test
	public void testParent()
	{
		ImplicitParameterNode parent = new ImplicitParameterNode();
		assertFalse(aNode.hasParent());
		aNode.link(parent);
		assertTrue(aNode.hasParent());
		assertSame(parent, aNode.getParent());
		aNode.unlink();
		assertFalse(aNode.hasParent());
	}
	
	@Test
	public void testAllowsChildren()
	{
		assertFalse(aNode.allowsChildren());
	}
	
	@Test
	public void testCallNodeEqualsItself()
	{
		assertEquals(aNode, aNode);
		assertEquals(aNode.hashCode(), aNode.hashCode());
	}
	
	@Test
	public void testCallNodeEqualsItsClone()
	{
		assertEquals(aNode, aNode.clone());
		assertEquals(aNode.hashCode(), aNode.clone().hashCode());
	}
	
	@Test
	public void testCallNodeEqualsAnotherCallNode()
	{
		CallNode node2 = new CallNode();
		assertEquals(aNode, node2);
		assertEquals(aNode.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testCallNodeNotEqualsNull()
	{
		assertNotEquals(aNode, null);
	}
	
	@Test
	public void testCallNodeNotEqualsString()
	{
		String anyString = "anystring";
		assertNotEquals(aNode, anyString);
		assertNotEquals(aNode.hashCode(), anyString.hashCode());
	}
	
	@Test
	public void testCallNodeNotEqualsAnotherNodeTranslated()
	{
		CallNode node2 = new CallNode();
		node2.translate(200, 0);
		assertNotEquals(aNode, node2);
		assertNotEquals(aNode.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testCallNodeNotEqualsAnotherNodeWithOpenBottom()
	{
		CallNode node2 = new CallNode();
		node2.setOpenBottom(true);
		assertNotEquals(aNode, node2);
		assertNotEquals(aNode.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testCallNodeEqualsAnotherNodeWithSameParent()
	{
		ImplicitParameterNode implicitParameterNode = new ImplicitParameterNode();
		CallNode node2 = new CallNode();
		aNode.link(implicitParameterNode);
		node2.link(implicitParameterNode);
		assertEquals(aNode, node2);
		assertEquals(aNode.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testCallNodeNotEqualsAnotherNodeWithDifferentParent()
	{
		ImplicitParameterNode implicitParameterNode1 = new ImplicitParameterNode();
		ImplicitParameterNode implicitParameterNode2 = new ImplicitParameterNode();
		CallNode node2 = new CallNode();
		implicitParameterNode1.setName("IPN1");
		implicitParameterNode2.setName("IPN2");
		aNode.link(implicitParameterNode1);
		node2.link(implicitParameterNode2);
		assertEquals(aNode, node2);
		assertEquals(aNode.hashCode(), node2.hashCode());
	}
}
