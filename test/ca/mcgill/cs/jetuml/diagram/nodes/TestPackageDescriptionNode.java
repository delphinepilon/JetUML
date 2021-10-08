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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestPackageDescriptionNode
{
	private PackageDescriptionNode aPackageDescriptionNode;
	private PackageNode aPackageNode;
	
	@BeforeEach
	public void setup()
	{
		aPackageDescriptionNode = new PackageDescriptionNode();
		aPackageNode = new PackageNode();
	}
	
	@Test
	public void testDefault()
	{
		assertEquals(0,aPackageDescriptionNode.getChildren().size());
		assertEquals("", aPackageDescriptionNode.getName().toString());
		assertFalse(aPackageDescriptionNode.hasParent());
	}
	
	@Test
	public void testLink()
	{
		aPackageDescriptionNode.link(aPackageNode);
		assertTrue(aPackageDescriptionNode.hasParent());
		assertSame(aPackageNode, aPackageDescriptionNode.getParent());
		
		aPackageDescriptionNode.unlink();
		assertFalse(aPackageDescriptionNode.hasParent());
	}
	
	@Test
	public void testRequiresParent()
	{
		assertFalse(aPackageDescriptionNode.requiresParent());
	}
	
	@Test
	public void testAllowsChildren()
	{
		assertFalse(aPackageDescriptionNode.allowsChildren());
	}
	
	@Test
	public void testSetName()
	{
		aPackageDescriptionNode.setName("Foo");
		assertEquals("Foo", aPackageDescriptionNode.getName());
	}
	
	@Test
	public void testSetContents()
	{
		aPackageDescriptionNode.setContents("Foo");
		assertEquals("Foo", aPackageDescriptionNode.getContents());
	}
	
	@Test
	public void testClone()
	{
		aPackageDescriptionNode.setName("Name");
		aPackageDescriptionNode.setContents("Contents");
		aPackageDescriptionNode.link(aPackageNode);
		PackageDescriptionNode clone = aPackageDescriptionNode.clone();
		assertEquals("Name", clone.getName());
		assertEquals("Contents", clone.getContents());
		assertSame(aPackageNode, clone.getParent());
	}
	
	@Test
	public void testPackageDescriptionNodeEqualsItself()
	{
		assertEquals(aPackageDescriptionNode, aPackageDescriptionNode);
		assertEquals(aPackageDescriptionNode.hashCode(), aPackageDescriptionNode.hashCode());
	}
	
	@Test
	public void testPackageDescriptionNodeEqualsItsClone()
	{
		assertEquals(aPackageDescriptionNode, aPackageDescriptionNode.clone());
		assertEquals(aPackageDescriptionNode.hashCode(), aPackageDescriptionNode.clone().hashCode());
	}
	
	@Test
	public void testPackageDescriptionNodeEqualsAnotherNode()
	{
		PackageDescriptionNode packageDescriptionNode2 = new PackageDescriptionNode();
		assertEquals(aPackageDescriptionNode, packageDescriptionNode2);
		assertEquals(aPackageDescriptionNode.hashCode(), packageDescriptionNode2.hashCode());
	}
	
	@Test
	public void testPackageDescriptionNodeNotEqualsNull()
	{
		assertNotEquals(aPackageDescriptionNode, null);
	}
	
	@Test
	public void testPackageDescriptionNodeNotEqualsString()
	{
		String anyString = "anystring";
		assertNotEquals(aPackageDescriptionNode, anyString);
		assertNotEquals(aPackageDescriptionNode.hashCode(), anyString.hashCode());
	}
	
	@Test
	public void testPackageDescriptionNodeNotEqualsAnotherNodeTranslated()
	{
		PackageDescriptionNode packageDescriptionNode2 = new PackageDescriptionNode();
		packageDescriptionNode2.translate(200, 0);
		assertNotEquals(aPackageDescriptionNode, packageDescriptionNode2);
		assertNotEquals(aPackageDescriptionNode.hashCode(), packageDescriptionNode2.hashCode());
	}
	
	@Test
	public void testPackageDescriptionNodeNotEqualsAnotherNodeWithDifferentName()
	{
		PackageDescriptionNode packageDescriptionNode2 = new PackageDescriptionNode();
		aPackageDescriptionNode.setName("Name1");
		packageDescriptionNode2.setName("Name2");
		assertNotEquals(aPackageDescriptionNode, packageDescriptionNode2);
		assertNotEquals(aPackageDescriptionNode.hashCode(), packageDescriptionNode2.hashCode());
	}
	
	@Test
	public void testPackageDescriptionNodeNotEqualsAnotherNodeWithDifferentContent()
	{
		PackageDescriptionNode packageDescriptionNode2 = new PackageDescriptionNode();
		aPackageDescriptionNode.setContents("Content1");
		packageDescriptionNode2.setContents("Content2");
		assertNotEquals(aPackageDescriptionNode, packageDescriptionNode2);
		assertNotEquals(aPackageDescriptionNode.hashCode(), packageDescriptionNode2.hashCode());
	}
}
