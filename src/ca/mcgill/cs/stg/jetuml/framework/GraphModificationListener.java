package ca.mcgill.cs.stg.jetuml.framework;

import java.awt.geom.Rectangle2D;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ca.mcgill.cs.stg.jetuml.graph.GraphElement;
import ca.mcgill.cs.stg.jetuml.graph.Node;
import ca.mcgill.cs.stg.jetuml.graph.Edge;
import ca.mcgill.cs.stg.jetuml.commands.AddDeleteEdgeCommand;
import ca.mcgill.cs.stg.jetuml.commands.AddDeleteNodeCommand;
import ca.mcgill.cs.stg.jetuml.commands.CompoundCommand;
import ca.mcgill.cs.stg.jetuml.commands.MoveCommand;
import ca.mcgill.cs.stg.jetuml.commands.PropertyChangeCommand;

/**
 * @author EJBQ
 *
 */
public class GraphModificationListener 
{
	private UndoManager aUndoManager;
	private Node[] aSelectionNodes;
	private Rectangle2D[] aSelectionBounds;
	private Object[] aPropertyValues;

	/**
	 * Creates a new GraphModificationListener with an UndoManager.
	 * @param pUndo the UndoManager to be accessed
	 */
	public GraphModificationListener(UndoManager pUndo)
	{
		aUndoManager = pUndo;
	}

	/**
	 * Keeps track of the addition of a node.
	 * @param pGraphPanel The Panel to add the node to
	 * @param pNode The node to be added
	 */
	public void nodeAdded(GraphPanel pGraphPanel, Node pNode)
	{
		AddDeleteNodeCommand ac = new AddDeleteNodeCommand(pGraphPanel, pNode, true);
		aUndoManager.add(ac);
	}

	/**
	 * Keeps track of the removal of a node.
	 * @param pGraphPanel The Panel to remove the node from
	 * @param pNode The node to be removed
	 */
	public void nodeRemoved(GraphPanel pGraphPanel, Node pNode)
	{
		AddDeleteNodeCommand dc = new AddDeleteNodeCommand(pGraphPanel, pNode, false);
		aUndoManager.add(dc);
	}

	/**
	 * Keeps track of the moving of a node.
	 * @param pGraphPanel The panel that the nodes were moved on
	 * @param pNode The node that was moved
	 * @param pDX The amount moved in the horizontal direction
	 * @param pDY The amount moved in the vertical direction
	 */
	public void nodeMoved(GraphPanel pGraphPanel, Node pNode, double pDX, double pDY)
	{
		MoveCommand mc = new MoveCommand(pGraphPanel, pNode, pDX, pDY);
		aUndoManager.add(mc);
	}

	/**
	 * Keeps track of the addition of a child.
	 * @param pGraphPanel The panel the child was added in
	 * @param pIndex The index of the parent node that the child was added in
	 * @param pParent The parent node
	 * @param pChild The child node
	 */
	public void childAttached(GraphPanel pGraphPanel, int pIndex, Node pParent, Node pChild)
	{

	}

	/**
	 * Keeps track of the removal of a child.
	 * @param pGraphPanel The panel the child was added in
	 * @param pIndex The index of the parent node that the child was added in
	 * @param pParent The parent node
	 * @param pChild The child node
	 */
	public void childDetached(GraphPanel pGraphPanel, int pIndex, Node pParent, Node pChild)
	{

	}

	/**
	 * Tracks the elements in pSelectedElements and records their positions.
	 * @param pGraphPanel The panel to be moved on
	 * @param pSelectedElements The elements that are being moved
	 */
	public void startTrackingMove(GraphPanel pGraphPanel, SelectionList pSelectedElements)
	{
		aSelectionNodes = new Node[pSelectedElements.size()];
		aSelectionBounds = new Rectangle2D[pSelectedElements.size()];
		int i = 0;
		for(GraphElement e : pSelectedElements)
		{
			if(e instanceof Node)
			{
				aSelectionNodes[i] = (Node) e;
				aSelectionBounds[i] = aSelectionNodes[i].getBounds();
				i++;
			}
		}
	}

	/**
	 * Creates a compound command with each node move and adds it to the stack.
	 * @param pGraphPanel The panel to be moved on
	 * @param pSelectedElements The elements that are being moved
	 */
	public void endTrackingMove(GraphPanel pGraphPanel, SelectionList pSelectedElements)
	{
		CompoundCommand cc = new CompoundCommand();
		Rectangle2D[] selectionBounds2 = new Rectangle2D[pSelectedElements.size()];
		int i = 0;
		for(GraphElement e : pSelectedElements)
		{
			if(e instanceof Node)
			{
				selectionBounds2[i] = ((Node) e).getBounds();
				i++;
			}
		}
		for(i = 0; i<aSelectionNodes.length && aSelectionNodes[i] != null; i++)
		{
			double dY = selectionBounds2[i].getY() - aSelectionBounds[i].getY();
			double dX = selectionBounds2[i].getX() - aSelectionBounds[i].getX();
			if (dX > 0 || dY > 0)
			{
				cc.add(new MoveCommand(pGraphPanel, aSelectionNodes[i], dX, dY));
			}
		}
		if (cc.size() > 0) 
		{
			aUndoManager.add(cc);
		}
	}

	/**
	 * Stores the properties of the edited object until the change is finished.
	 * @param pGraphPanel The panel of the object being edited
	 * @param pEdited The object to be edited
	 */
	public void trackPropertyChange(GraphPanel pGraphPanel, Object pEdited)
	{
		BeanInfo info;
		try 
		{
			info = Introspector.getBeanInfo(pEdited.getClass());
			PropertyDescriptor[] oldDescriptors = (PropertyDescriptor[])info.getPropertyDescriptors().clone();
			aPropertyValues = new Object[oldDescriptors.length];
			for(int i = 0; i< aPropertyValues.length; i++)
			{
				final Method getter = oldDescriptors[i].getReadMethod();
				System.out.println(getter.getName());
				aPropertyValues[i] = getter.invoke(pEdited, new Object[] {});
				aPropertyValues[i] = propertyClone(aPropertyValues[i]);
			}
		} 
		catch (IntrospectionException e) 
		{
			e.printStackTrace();
			return;
		} 
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
			return;
		}

	}

	/**
	 * Compares the properties of the edited object and creates a command if needed.
	 * @param pGraphPanel The panel of the object being edited
	 * @param pEdited The object to be edited
	 */
	public void finishPropertyChange(GraphPanel pGraphPanel, Object pEdited)
	{
		BeanInfo info;
		CompoundCommand cc = new CompoundCommand();
		try 
		{
			info = Introspector.getBeanInfo(pEdited.getClass());
			PropertyDescriptor[] descriptors = (PropertyDescriptor[])info.getPropertyDescriptors().clone();  
			for(int i = 0; i<descriptors.length; i++)
			{
				final Method getter = descriptors[i].getReadMethod();
				System.out.println(getter.getName());
				Object propVal = getter.invoke(pEdited, new Object[] {});
				if (!propVal.equals(aPropertyValues[i]))
				{
					Object oldPropValue = aPropertyValues[i];
					Object propValue;
					propValue = propertyClone(propVal);
					cc.add(new PropertyChangeCommand(pGraphPanel, pEdited, oldPropValue, propValue, i));
				}
			}
		}
		catch (IntrospectionException e) 
		{
			e.printStackTrace();
			return;
		}
		catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) 
		{
			e.printStackTrace();
			return;
		}
		finally
		{
			if (cc.size() > 0)
			{
				aUndoManager.add(cc);
			}
		}
	}

	/**
	 * Tracks the addition of an edge to the graph.
	 * @param pGraphPanel The panel to be edited
	 * @param pEdge The edge being added
	 */
	public void edgeAdded(GraphPanel pGraphPanel, Edge pEdge)
	{
		AddDeleteEdgeCommand ac = new AddDeleteEdgeCommand(pGraphPanel, pEdge, true);
		aUndoManager.add(ac);
	}

	/**
	 * Tracks the removal of an edge to the graph.
	 * @param pGraphPanel The panel to be edited
	 * @param pEdge The edge being removed
	 */
	public void edgeRemoved(GraphPanel pGraphPanel, Edge pEdge)
	{
		AddDeleteEdgeCommand dc = new AddDeleteEdgeCommand(pGraphPanel, pEdge, false);
		aUndoManager.add(dc);
	}

	/**
	 * Clones an undefined object.
	 * This should only be done in cases where the original object is edited, not the reference
	 * TODO: Make this a cleaner idea
	 * @param pObject The object to be cloned
	 * @return The new object if it is an unrecognized type and the same object otherwise.
	 */
	private Object propertyClone(Object pObject)
	{
		Object temp = null;
		if (pObject instanceof MultiLineString)
		{
			temp = (MultiLineString) ((MultiLineString) pObject).clone();
		}
		
		//Node is purposely not cloned
		
		if(temp != null)
		{
			System.out.println(temp.equals(pObject));
			return temp;
		}
		else
		{
			return pObject;
		}
	}
	
//	void propertyChangedOnNodeOrEdge(GraphPanel pGraphPanel, PropertyChangeEvent pEvent)
//	{
//
//	}

}
