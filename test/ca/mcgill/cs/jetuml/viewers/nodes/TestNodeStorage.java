package ca.mcgill.cs.jetuml.viewers.nodes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramType;
import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.diagram.nodes.ClassNode;
import ca.mcgill.cs.jetuml.geom.Rectangle;

/**
 * Ensures that the NodeStorage functions properly. 
 */
public class TestNodeStorage 
{
	private NodeStorage aNodeStorage;
	
	@BeforeEach
	void setup()
	{
		aNodeStorage = new NodeStorage();
	}
	
	@Test
	public void testNodeStorageReturnsCalculatedBounds()
	{
		ClassNode classNode = new ClassNode();
		Rectangle bounds = aNodeStorage.getBounds(classNode, createBoundCalculatorForTests());
		assertEquals(new Rectangle(0, 0, 10, 10), bounds);
	}
	
	@Test
	public void testNodeStorageReturnsCalculatedBoundsAfterNodeTranslated()
	{
		ClassNode classNode = new ClassNode();
		classNode.translate(20, 20);
		Rectangle bounds = aNodeStorage.getBounds(classNode, createBoundCalculatorForTests());
		assertEquals(new Rectangle(20, 20, 10, 10), bounds);
	}
	
	@Test
	public void testNodeStorageReturnsDifferentBoundsAfterNodeTranslatedAndThenRenamed()
	{
		ClassNode classNode = new ClassNode();
		classNode.translate(20, 20);
		Rectangle boundsA = aNodeStorage.getBounds(classNode, createBoundCalculatorForTests());
		classNode.setName("TrialName");
		Rectangle boundsB = aNodeStorage.getBounds(classNode, createBoundCalculatorForTests());
		assertEquals(new Rectangle(20, 20, 10, 10), boundsA);
		assertEquals(new Rectangle(20, 20, 10, 10), boundsB);
		assertNotSame(boundsA, boundsB); 
	}
	
	@Test
	public void testNodeStorageCleanedAfter1000CallsToGetBounds()
	{
		ClassNode classNodeA = new ClassNode();
		ClassNode classNodeB = new ClassNode();
		ClassNode classNodeC = new ClassNode();
		Diagram diagram = new Diagram(DiagramType.CLASS);
		classNodeA.attach(diagram);
		classNodeB.attach(diagram);
		// classNodeC is not attached to the diagram
		Rectangle initialBoundsA = aNodeStorage.getBounds(classNodeA, createBoundCalculatorForTests());
		Rectangle initialBoundsB = aNodeStorage.getBounds(classNodeB, createBoundCalculatorForTests());
		Rectangle initialBoundsC = aNodeStorage.getBounds(classNodeC, createBoundCalculatorForTests());
		for (int i = 0; i < 1002; i++)
		{
			aNodeStorage.getBounds(classNodeA, createBoundCalculatorForTests());
		}
		Rectangle finalBoundsA = aNodeStorage.getBounds(classNodeA, createBoundCalculatorForTests());
		Rectangle finalBoundsB = aNodeStorage.getBounds(classNodeB, createBoundCalculatorForTests());
		Rectangle finalBoundsC = aNodeStorage.getBounds(classNodeC, createBoundCalculatorForTests());
		assertSame(initialBoundsA, finalBoundsA);
		assertSame(initialBoundsB, finalBoundsB);
		assertNotSame(initialBoundsC, finalBoundsC); // the bounds were removed from the storage and recomputed. 
	}
	
	private Function<Node, Rectangle> createBoundCalculatorForTests()
	{
		return new Function<Node, Rectangle>()
		{
			@Override
			public Rectangle apply(Node pNode) 
			{
				return new Rectangle(pNode.position().getX(), pNode.position().getY(), 10, 10);
			}
		};
	}
}
