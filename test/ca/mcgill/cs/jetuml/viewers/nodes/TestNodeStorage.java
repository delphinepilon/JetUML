package ca.mcgill.cs.jetuml.viewers.nodes;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.nodes.NoteNode;
import ca.mcgill.cs.jetuml.geom.Rectangle;

/**
 * Tests the NodeStorage. 
 */
public class TestNodeStorage 
{
	private NodeStorage aStorage;
	
	@BeforeEach
	public void setup()
	{
		aStorage = new NodeStorage();
		NodeStorage.deactivate();
	}
	
	@Test
	public void testGetBoundsReturnsDifferentBoundsWhenNodeStorageIsNotActive()
	{
		Node node = new NoteNode();
		Rectangle boundsA = aStorage.getBounds(node, createDefaultBoundCalculator());
		Rectangle boundsB = aStorage.getBounds(node, createDefaultBoundCalculator());
		assertNotSame(boundsA, boundsB);
	}
	
	@Test
	public void testGetBoundsReturnsSameBoundsWhenNodeStorageIsActive()
	{
		NodeStorage.activate();
		Node node = new NoteNode();
		Rectangle boundsA = aStorage.getBounds(node, createDefaultBoundCalculator());
		Rectangle boundsB = aStorage.getBounds(node, createDefaultBoundCalculator());
		assertSame(boundsA, boundsB);
	}
	
	private Function<Node, Rectangle> createDefaultBoundCalculator()
	{
		return new Function<Node, Rectangle>()
		{
			@Override
			public Rectangle apply(Node pNode) 
			{
				return new Rectangle(pNode.position().getX(), pNode.position().getY(), 100, 100);
			};
			
		};
	}

}
