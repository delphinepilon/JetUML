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
	
	private Map<Node, Rectangle> aNodeBounds = new IdentityHashMap<Node, Rectangle>();
	private static boolean aIsNodeStorageActivated = false;

	/**
	 * Returns the bounds of the current node either from the storage or from the calculator.
	 * @param pNode the node of interest.
	 * @param pBoundCalculator the bound calculator.
	 * @return the bounds of pNode. 
	 */
	public Rectangle getBounds(Node pNode, Function<Node, Rectangle> pBoundCalculator)
	{
		if (!aIsNodeStorageActivated)
		{
			return pBoundCalculator.apply(pNode);
		}
		else if (aIsNodeStorageActivated && aNodeBounds.containsKey(pNode))
		{
			return aNodeBounds.get(pNode);
		}
		else
		{
			Rectangle newlyComputedBounds = pBoundCalculator.apply(pNode);
			aNodeBounds.put(pNode, newlyComputedBounds);
			return newlyComputedBounds;
		}
	}

	/**
	 * Activates the NodeStorage.
	 */
	public static void activate() 
	{
		aIsNodeStorageActivated = true;
	}
	
	/**
	 * Deactivates the NodeStorage.
	 */
	public static void deactivate() 
	{
		aIsNodeStorageActivated = false;
	}
	
	public void clear()
	{
		aNodeBounds.clear();
	}
}