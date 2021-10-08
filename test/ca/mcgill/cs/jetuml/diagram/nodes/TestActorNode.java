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
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramType;
import ca.mcgill.cs.jetuml.diagram.Properties;
import ca.mcgill.cs.jetuml.diagram.PropertyName;
import ca.mcgill.cs.jetuml.geom.Point;

public class TestActorNode
{
	private ActorNode aNode;
	
	@BeforeEach
	public void setup()
	{
		aNode = new ActorNode();
	}
	
	@Test
	public void testGetProperties()
	{
		Properties properties = aNode.properties();
		
		assertEquals("Actor", properties.get(PropertyName.NAME).get());
		
		aNode.setName("Foo");
		aNode.translate(10, 20);
		properties = aNode.properties();
		assertEquals("Foo", properties.get(PropertyName.NAME).get());
	}
	
	@Test
	public void testProperty_Name()
	{
		aNode.setName("Foo");
		assertEquals("Foo", aNode.getName());
	}
	
	@Test
	public void testInitialPosition()
	{
		assertEquals(new Point(0,0), aNode.position());
	}
	
	@Test
	public void testMoveTo()
	{
		aNode.moveTo(new Point(10, 20));
		assertEquals(new Point(10,20), aNode.position());
	}
	
	/*
	 * The position should be a clone
	 */
	@Test
	public void testClone_OfPosition()
	{
		ActorNode clone = (ActorNode) aNode.clone();
		assertNotSame(aNode.position(), clone.position());
		clone.properties().get(PropertyName.NAME).set("Foo");
		assertNotEquals("Foo", aNode.properties().get(PropertyName.NAME));
	}
	
	/*
	 * The name should be the same object
	 */
	@Test
	public void testClone_OfName()
	{
		aNode.setName("Foo");
		ActorNode clone = (ActorNode) aNode.clone();
		assertSame(aNode.getName(), clone.getName());
	}
	
	/*
	 * The name should be the same object
	 */
	@Test
	public void testClone_OfProperties()
	{
		ActorNode clone = (ActorNode) aNode.clone();
		assertNotSame(aNode.properties(), clone.properties());
	}
	
	@Test
	public void testClone_OfDiagram()
	{
		Diagram diagram = new Diagram(DiagramType.USECASE);
		aNode.attach(diagram);
		assertSame(diagram, aNode.getDiagram().get());
	}
	
	@Test
	public void testAttachDetach()
	{
		assertFalse(aNode.getDiagram().isPresent());
		Diagram diagram = new Diagram(DiagramType.USECASE);
		aNode.attach(diagram);
		assertSame(diagram, aNode.getDiagram().get());
		aNode.detach();
		assertFalse(aNode.getDiagram().isPresent());
	}
	
	@Test
	public void testHasParent()
	{
		assertFalse(aNode.hasParent());
	}
	
	@Test
	public void testRequiresParent()
	{
		assertFalse(aNode.requiresParent());
	}
	
	@Test
	public void testGetChildren()
	{
		assertTrue(aNode.getChildren().isEmpty());
	}
	
	@Test
	public void allowsChildren()
	{
		assertFalse(aNode.allowsChildren());
	}
	
	@Test
	public void testActorNodeEqualsItself()
	{
		assertEquals(aNode, aNode);
		assertEquals(aNode.hashCode(), aNode.hashCode());
	}
	
	@Test
	public void testActorNodeEqualsItsClone()
	{
		assertEquals(aNode, aNode.clone());
		assertEquals(aNode.hashCode(), aNode.clone().hashCode());
	}
	
	@Test
	public void testActorNodeEqualsAnotherActorNode()
	{
		ActorNode node2 = new ActorNode();
		assertEquals(aNode, node2);
		assertEquals(aNode.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testActorNodeNotEqualsNull()
	{
		assertNotEquals(aNode, null);
	}
	
	@Test
	public void testActorNodeNotEqualsString()
	{
		String anyString = "anystring";
		assertNotEquals(aNode, anyString);
		assertNotEquals(aNode.hashCode(), anyString.hashCode());
	}
	
	@Test
	public void testActorNodeNotEqualsAnotherNodeTranslated()
	{
		ActorNode node2 = new ActorNode();
		node2.translate(200, 0);
		assertNotEquals(aNode, node2);
		assertNotEquals(aNode.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testActorNodeNotEqualsAnotherNodeWithDifferentName()
	{
		ActorNode node2 = new ActorNode();
		aNode.setName("Mario");
		node2.setName("Luigi");
		assertNotEquals(aNode, node2);
		assertNotEquals(aNode.hashCode(), node2.hashCode());
	}
}
