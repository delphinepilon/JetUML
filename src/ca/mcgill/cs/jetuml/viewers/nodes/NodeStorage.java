package ca.mcgill.cs.jetuml.viewers.nodes;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.geom.Rectangle;

/**
 * Stores the bounds of nodes. 
 */
public class NodeStorage 
{
	private final int NUMBER_OF_CALLS_BETWEEN_CLEANS = 1000;
	
	private Map<Node, Rectangle> aNodeBounds = new IdentityHashMap<Node, Rectangle>();
	private Map<Node, Node> aSavedNodeClones = new IdentityHashMap<Node, Node>();
	private int aCallCounter;
	
	/**
	 * Upon creation, the call counter is initialized to 0. 
	 */
	public NodeStorage()
	{
		aCallCounter = 0;
	}
	
	/**
	 * Returns the bounds of the current node either from the storage or from the calculator.
	 * @param pNode the node of interest.
	 * @param pBoundCalculator the bound calculator.
	 * @return the bounds of pNode. 
	 */
	public Rectangle getBounds(Node pNode, Function<Node, Rectangle> pBoundCalculator)
	{
		cleanBoundStorage();
		if (aSavedNodeClones.containsKey(pNode) && aSavedNodeClones.get(pNode).equals(pNode))
		{
			return aNodeBounds.get(pNode);
		}
		else
		{
			Rectangle newlyComputedBounds = pBoundCalculator.apply(pNode);
			aNodeBounds.put(pNode, newlyComputedBounds);
			aSavedNodeClones.put(pNode, pNode.clone());
			return newlyComputedBounds;
		}
	}

	/**
	 * After a certain number of calls have been made to getBounds(...),
	 * this method removes from the bound storage all the nodes that are not attached to a diagram.
	 */
	private void cleanBoundStorage() 
	{
		if (aCallCounter == NUMBER_OF_CALLS_BETWEEN_CLEANS)
		{
			List<Node> nodesToBeRemovedFromStorage = new ArrayList<>();
			for (Node node : aSavedNodeClones.keySet())
			{
				if (node.getDiagram().isEmpty())
				{
					nodesToBeRemovedFromStorage.add(node);
				}
			}
			for (Node node : nodesToBeRemovedFromStorage)
			{
				aNodeBounds.remove(node);
				aSavedNodeClones.remove(node);
			}
			aCallCounter = 0;
		}
		aCallCounter++;
		
	}
}
