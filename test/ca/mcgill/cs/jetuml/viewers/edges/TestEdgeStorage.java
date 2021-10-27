package ca.mcgill.cs.jetuml.viewers.edges;

import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.function.Function;

import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.Edge;
import ca.mcgill.cs.jetuml.diagram.edges.NoteEdge;
import ca.mcgill.cs.jetuml.geom.Rectangle;

public class TestEdgeStorage 
{
	@Test
	public void testGetBoundsReturnsDifferentBoundsWhenEdgeStorageIsNotActive()
	{
		EdgeStorage.deactivate();
		Edge edge = new NoteEdge();
		Rectangle boundsA = EdgeStorage.getBounds(edge, createDefaultBoundCalculator());
		Rectangle boundsB = EdgeStorage.getBounds(edge, createDefaultBoundCalculator());
		assertNotSame(boundsA, boundsB);
	}
	
	@Test
	public void testGetBoundsReturnsDifferentBoundsForDifferentEdgesWhenEdgeStorageIsActive()
	{
		EdgeStorage.activate();
		Edge edge1 = new NoteEdge();
		Edge edge2 = new NoteEdge();
		Rectangle boundsA = EdgeStorage.getBounds(edge1, createDefaultBoundCalculator());
		Rectangle boundsB = EdgeStorage.getBounds(edge2, createDefaultBoundCalculator());
		assertNotSame(boundsA, boundsB);
		EdgeStorage.deactivate();
	}
	
	@Test
	public void testGetBoundsReturnsSameBoundsForSameEdgeWhenEdgeStorageIsActive()
	{
		EdgeStorage.activate();
		Edge edge = new NoteEdge();
		Rectangle boundsA = EdgeStorage.getBounds(edge, createDefaultBoundCalculator());
		Rectangle boundsB = EdgeStorage.getBounds(edge, createDefaultBoundCalculator());
		assertSame(boundsA, boundsB);
		EdgeStorage.deactivate();
	}

	private Function<Edge, Rectangle> createDefaultBoundCalculator() 
	{
		return new Function<Edge, Rectangle>()
		{
			@Override
			public Rectangle apply(Edge pEdge) 
			{
				return new Rectangle(0, 0, 0, 0);
			}
		};
	}

}
