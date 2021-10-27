package ca.mcgill.cs.jetuml.viewers.nodes;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.nodes.NoteNode;
import ca.mcgill.cs.jetuml.geom.Rectangle;

/**
 * Tests the NodeStorage. 
 */
public class TestNodeStorage 
{	
	@Test
	public void testGetBoundsReturnsDifferentBoundsWhenNodeStorageIsNotActive()
	{
		NodeStorage.deactivate();
		Node node = new NoteNode();
		Rectangle boundsA = NodeStorage.getBounds(node, createDefaultBoundCalculator());
		Rectangle boundsB = NodeStorage.getBounds(node, createDefaultBoundCalculator());
		assertNotSame(boundsA, boundsB);
	}
	
	@Test
	public void testGetBoundsReturnsSameBoundsWhenNodeStorageIsActive()
	{
		NodeStorage.activate();
		Node node = new NoteNode();
		Rectangle boundsA = NodeStorage.getBounds(node, createDefaultBoundCalculator());
		Rectangle boundsB = NodeStorage.getBounds(node, createDefaultBoundCalculator());
		assertSame(boundsA, boundsB);
		NodeStorage.deactivate();
	}
	
	@Test
	public void testGetBoundsReturnsDifferentBoundsForDifferentNodesWhenNodeStorageIsActive()
	{
		NodeStorage.activate();
		Node node1 = new NoteNode();
		Node node2 = new NoteNode();
		Rectangle boundsA = NodeStorage.getBounds(node1, createDefaultBoundCalculator());
		Rectangle boundsB = NodeStorage.getBounds(node2, createDefaultBoundCalculator());
		assertNotSame(boundsA, boundsB);
		NodeStorage.deactivate();
	}
	
	private Function<Node, Rectangle> createDefaultBoundCalculator()
	{
		return new Function<Node, Rectangle>()
		{
			@Override
			public Rectangle apply(Node pNode) 
			{
				return new Rectangle(pNode.position().getX(), pNode.position().getY(), 100, 100);
			}
		};
	}

}