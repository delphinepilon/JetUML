package ca.mcgill.cs.jetuml.viewers.nodes;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Function;

import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.geom.Rectangle;

/**
 * Stores the bounds of nodes. 
 */
public class NodeStorage 
{
	
	private static Map<Node, Rectangle> aNodeBounds = new IdentityHashMap<Node, Rectangle>();
	private static boolean aIsActivated = false;

	/**
	 * Returns the bounds of the current node either from the storage or from the calculator.
	 * @param pNode the node of interest.
	 * @param pBoundCalculator the bound calculator.
	 * @return the bounds of pNode. 
	 */
	public static Rectangle getBounds(Node pNode, Function<Node, Rectangle> pBoundCalculator)
	{
		if (!aIsActivated)
		{
			return pBoundCalculator.apply(pNode);
		}
		else if (aIsActivated && aNodeBounds.containsKey(pNode))
		{
			return aNodeBounds.get(pNode);
		}
		else
		{
			Rectangle computedBounds = pBoundCalculator.apply(pNode);
			aNodeBounds.put(pNode, computedBounds);
			return computedBounds;
		}
	}

	/**
	 * Activates the NodeStorage.
	 */
	public static void activate() 
	{
		aIsActivated = true;
	}
	
	/**
	 * Deactivates the NodeStorage.
	 */
	public static void deactivate() 
	{
		aIsActivated = false;
		aNodeBounds.clear();
	}
}