package ca.mcgill.cs.jetuml.viewers.nodes;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import ca.mcgill.cs.jetuml.diagram.Node;
import ca.mcgill.cs.jetuml.geom.Point;
import ca.mcgill.cs.jetuml.geom.Rectangle;

/**
 * Stores the bounds of nodes. 
 */
public class NodeStorage 
{
	private final int NUMBER_OF_CALLS_BETWEEN_CLEANS = 1000;
	
	private Map<Node, Rectangle> aNodeBounds = new IdentityHashMap<Node, Rectangle>();
	private Map<Node, Point> aNodePositions = new IdentityHashMap<Node, Point>();
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
		if (aNodeBounds.containsKey(pNode) && !aNodePositions.get(pNode).equals(pNode.position()))
		{
			Rectangle newlyComputedBounds = pBoundCalculator.apply(pNode);
			aNodeBounds.put(pNode, newlyComputedBounds);
			aNodePositions.put(pNode, pNode.position());
			updateClonePosition(pNode, pNode.position());
			return newlyComputedBounds;
		}
		else if (aNodeBounds.containsKey(pNode) && aSavedNodeClones.get(pNode).equals(pNode))
		{
			return aNodeBounds.get(pNode);
		}
		else
		{
			Rectangle newlyComputedBounds = pBoundCalculator.apply(pNode);
			aNodeBounds.put(pNode, newlyComputedBounds);
			aNodePositions.put(pNode, pNode.position());
			aSavedNodeClones.put(pNode, pNode.clone());
			return newlyComputedBounds;
		}
	}

	/**
	 * Updates pNode's clone's position without calling Node.clone(). 
	 */
	private void updateClonePosition(Node pNode, Point pPosition) 
	{
		Node clone = aSavedNodeClones.get(pNode);
		int cloneX = clone.position().getX();
		int cloneY = clone.position().getY();
		clone.translate(-cloneX+pPosition.getX(), -cloneY+pPosition.getY());
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
				aNodePositions.remove(node);
			}
			aCallCounter = 0;
		}
		aCallCounter++;
		
	}
}
