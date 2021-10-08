/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2020 by McGill University.
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestInterfaceNode
{
	private InterfaceNode aNode1;
	
	@BeforeEach
	public void setup()
	{
		aNode1 = new InterfaceNode();
	}
	
	@Test
	public void testDefault()
	{
		assertEquals("", aNode1.getName());
		String methods = aNode1.getMethods();
		assertEquals("", methods);
		assertFalse(aNode1.hasParent());
	}
	
	@Test
	public void testSetName()
	{
		aNode1.setName("Foo");
		assertEquals("Foo", aNode1.getName());
	}
	
	@Test
	public void testSetParent()
	{
		PackageNode package1 = new PackageNode();
		PackageNode package2 = new PackageNode();
		aNode1.link(package1);
		assertTrue( aNode1.getParent() == package1 );
		aNode1.link(package2);
		assertTrue( aNode1.getParent() == package2 );
		aNode1.unlink();
		assertFalse( aNode1.hasParent() );
	}
	
	@Test
	public void testInterfaceNodeEqualsItself()
	{
		assertEquals(aNode1, aNode1);
		assertEquals(aNode1.hashCode(), aNode1.hashCode());
	}
	
	@Test
	public void testInterfaceNodeEqualsItsClone()
	{
		assertEquals(aNode1, aNode1.clone());
		assertEquals(aNode1.hashCode(), aNode1.clone().hashCode());
	}
	
	@Test
	public void testInterfaceNodeEqualsAnotherNode()
	{
		InterfaceNode node2 = new InterfaceNode();
		assertEquals(aNode1, node2);
		assertEquals(aNode1.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testInterfaceNodeNotEqualsNull()
	{
		assertNotEquals(aNode1, null);
	}
	
	@Test
	public void testInterfaceNodeNotEqualsString()
	{
		String anyString = "anystring";
		assertNotEquals(aNode1, anyString);
		assertNotEquals(aNode1.hashCode(), anyString.hashCode());
	}
	
	@Test
	public void testInterfaceNodeNotEqualsAnotherNodeTranslated()
	{
		InterfaceNode node2 = new InterfaceNode();
		node2.translate(200, 0);
		assertNotEquals(aNode1, node2);
		assertNotEquals(aNode1.hashCode(), node2.hashCode());
	}
	
	@Test
	public void testInterfaceNodeNotEqualsAnotherNodeWithDifferentName()
	{
		InterfaceNode node2 = new InterfaceNode();
		aNode1.setName("Class1");
		node2.setName("Class2");
		assertNotEquals(aNode1, node2);
		assertNotEquals(aNode1.hashCode(), node2.hashCode());
	}
	@Test
	public void testInterfaceNodeNotEqualsAnotherNodeWithDifferentMethods()
	{
		InterfaceNode node2 = new InterfaceNode();
		aNode1.setMethods("Method1");
		node2.setMethods("Method2");
		assertNotEquals(aNode1, node2);
		assertNotEquals(aNode1.hashCode(), node2.hashCode());
	}
}
