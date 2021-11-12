package ca.mcgill.cs.jetuml.viewers.edges;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import ca.mcgill.cs.jetuml.diagram.Diagram;
import ca.mcgill.cs.jetuml.diagram.DiagramType;
import ca.mcgill.cs.jetuml.diagram.edges.StateTransitionEdge;
import ca.mcgill.cs.jetuml.diagram.nodes.StateNode;

public class TestStateTransitionEdgeViewer 
{
	private StateNode aStateNode1;
	private StateNode aStateNode2;
	private StateTransitionEdge aTransitionEdge;
	private Diagram aDiagram;
	private StateTransitionEdgeViewer aStateTransitionEdgeViewer = new StateTransitionEdgeViewer();
	
	@BeforeEach
	void setup()
	{
		aDiagram = new Diagram(DiagramType.STATE);
		aStateNode1 = new StateNode();
		aTransitionEdge = new StateTransitionEdge();
	}
	
	@ParameterizedTest
	@CsvSource(value = {
			"apple banana orange kiwi peach grape raspberry, 1000, 100, 1", 
			"apple banana orange kiwi peach grape raspberry, 250, 100, 2",
			"apple banana orange kiwi peach grape raspberry, 200, 200, 3",
			"apple banana orange kiwi peach grape raspberry, 100, 0, 4"
	})
	public void testWrapLabelForEdgeBetweenTwoStates(String pString, int pDistanceInX, int pDistanceInY, int pExpectedNumberOfLines)
	{
		aStateNode2 = new StateNode();
		aStateNode2.translate(pDistanceInX, pDistanceInY);
		aTransitionEdge.setMiddleLabel(pString);
		aTransitionEdge.connect(aStateNode1, aStateNode2, aDiagram);
		String label = wrapLabel(aTransitionEdge);
		int numberOfLines = (int)label.chars().filter(c -> c == '\n').count() + 1;
		assertEquals(pExpectedNumberOfLines, numberOfLines);
	}

	private String wrapLabel(StateTransitionEdge pTransitionEdge) 
	{
		try 
		{
			Method method = StateTransitionEdgeViewer.class.getDeclaredMethod("wrapLabel", StateTransitionEdge.class);
			method.setAccessible(true);
			String label = (String)method.invoke(aStateTransitionEdgeViewer, pTransitionEdge);
			return label;
		} 
		catch (ReflectiveOperationException e)
		{
			assert false;
			fail();
			return "";
		}
	}
}
