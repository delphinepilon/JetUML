package ca.mcgill.cs.jetuml.viewers.edges;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

import ca.mcgill.cs.jetuml.diagram.Edge;
import ca.mcgill.cs.jetuml.geom.Line;
import ca.mcgill.cs.jetuml.geom.Rectangle;

public class EdgeStorage 
{
	private static Map<Edge, Rectangle> aEdgeBounds = new IdentityHashMap<Edge, Rectangle>();
	private static Map<Edge, Line> aEdgeConnectionPoints = new IdentityHashMap<Edge, Line>();
	private static boolean aIsActivated = false;

	/**
	 * @param pEdge the edge for which we want to obtain the bounds.
	 * @param pBoundCalculator provides the way to compute the bounds of the edge.
	 * @return the bounds of the edge either computed or from the storage. 
	 */
	public static Rectangle getBounds(Edge pEdge, Function<Edge, Rectangle> pBoundCalculator)
	{
		if (!aIsActivated)
		{
			return pBoundCalculator.apply(pEdge);
		}
		else if (aIsActivated && aEdgeBounds.containsKey(pEdge))
		{
			return aEdgeBounds.get(pEdge);
		}
		else
		{
			Rectangle computedBounds = pBoundCalculator.apply(pEdge);
			aEdgeBounds.put(pEdge, computedBounds);
			return computedBounds;
		}
	}
	
	public static Line getConnectionPoints(Edge pEdge, Function<Edge, Line> pConnectionPointsCalculator)
	{
		if (!aIsActivated)
		{
			return pConnectionPointsCalculator.apply(pEdge);
		}
		else if (aIsActivated && aEdgeConnectionPoints.containsKey(pEdge))
		{
			return aEdgeConnectionPoints.get(pEdge);
		}
		else
		{
			Line computedPoints = pConnectionPointsCalculator.apply(pEdge);
			aEdgeConnectionPoints.put(pEdge, computedPoints);
			return computedPoints;
		}
	}
	
	/**
	 * Activates the EdgeStorage.
	 */
	public static void activate() 
	{
		aIsActivated = true;
	}
	
	/**
	 * Deactivates the EdgeStorage.
	 */
	public static void deactivate() 
	{
		aIsActivated = false;
		aEdgeBounds.clear();
	}
}
