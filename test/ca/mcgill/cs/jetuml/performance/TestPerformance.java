package ca.mcgill.cs.jetuml.performance;

import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.Test;

import ca.mcgill.cs.jetuml.diagram.nodes.ClassNode;
import ca.mcgill.cs.jetuml.diagram.nodes.NoteNode;
import ca.mcgill.cs.jetuml.diagram.nodes.ObjectNode;
import ca.mcgill.cs.jetuml.diagram.nodes.PackageNode;
import ca.mcgill.cs.jetuml.viewers.nodes.NodeViewerRegistry;

/**
 * Tests the performance of certain components of JetUML. 
 */
public class TestPerformance 
{
	private final int numberOfRounds = 10;
	private final int numberOfTranslationsPerRound = 10000;
	
	@Test
	public void runTest()
	{
		// To run the performance test, simply uncomment the following lines and run the test class. 
		/*
		testGetBoundsPerformanceWithTenNodesContinuouslyBeingTranslated();
		testGetBoundsPerformanceWithThreeNodesContinuouslyBeingTranslated();
		testGetBoundsPerformanceWithClassNodeContinuouslyBeingTranslated();
		*/
	}
	
	/**
	 * Tests NodeViewerRegistry.getBounds(Node) as a ClassNode is continuously being translated. 
	 */
	public void testGetBoundsPerformanceWithClassNodeContinuouslyBeingTranslated()
	{
		double averageTime = 0.0;
		
		for (int i = 0; i < numberOfRounds; i++)
		{
			ClassNode classNode = new ClassNode();
			
			Instant start = Instant.now();
			for (int j = 0; j < numberOfTranslationsPerRound; j++)
			{
				classNode.translate(i, i);
				NodeViewerRegistry.getBounds(classNode);
			}
			Instant end = Instant.now();
			
			averageTime += Duration.between(start, end).getNano()*0.000001;
		}
		averageTime = averageTime / numberOfRounds;
		System.out.println("(1 node) Average duration of " + numberOfRounds + " trials in milliseconds : " + averageTime);
	}
	
	/**
	 * Tests NodeViewerRegistry.getBounds(Node) as 3 nodes of different types keep being translated. 
	 */
	public void testGetBoundsPerformanceWithThreeNodesContinuouslyBeingTranslated()
	{
		double averageTime = 0.0;
		
		for (int i = 0; i < numberOfRounds; i++)
		{
			PackageNode packageNode = new PackageNode();
			ClassNode classNode = new ClassNode();
			NoteNode noteNode = new NoteNode();
			
			Instant start = Instant.now();
			for (int j = 0; j < numberOfTranslationsPerRound; j++)
			{
				packageNode.translate(i, i);
				classNode.translate(i, i);
				noteNode.translate(i, i);
				NodeViewerRegistry.getBounds(classNode);
				NodeViewerRegistry.getBounds(packageNode);
				NodeViewerRegistry.getBounds(noteNode);
			}
			Instant end = Instant.now();
			
			averageTime += Duration.between(start, end).getNano()*0.000001;
		}
		averageTime = averageTime / numberOfRounds;
		System.out.println("(3 nodes) Average duration of " + numberOfRounds + " trials in milliseconds : " + averageTime);
	}
	
	/**
	 * Tests NodeViewerRegistry.getBounds(Node) as 10 nodes of different types keep being translated. 
	 */
	public void testGetBoundsPerformanceWithTenNodesContinuouslyBeingTranslated()
	{
		double averageTime = 0.0;
		
		for (int i = 0; i < numberOfRounds; i++)
		{
			PackageNode packageNode = new PackageNode();
			ClassNode classNode = new ClassNode();
			NoteNode noteNode = new NoteNode();
			ObjectNode objectNode1 = new ObjectNode();
			ObjectNode objectNode2 = new ObjectNode();
			ObjectNode objectNode3 = new ObjectNode();
			ObjectNode objectNode4 = new ObjectNode();
			ObjectNode objectNode5 = new ObjectNode();
			ObjectNode objectNode6 = new ObjectNode();
			ObjectNode objectNode7 = new ObjectNode();
			
			Instant start = Instant.now();
			for (int j = 0; j < numberOfTranslationsPerRound; j++)
			{
				packageNode.translate(i, i);
				classNode.translate(i, i);
				noteNode.translate(i, i);
				objectNode1.translate(i, i);
				objectNode2.translate(i, i);
				objectNode3.translate(i, i);
				objectNode4.translate(i, i);
				objectNode5.translate(i, i);
				objectNode6.translate(i, i);
				objectNode7.translate(i, i);
				NodeViewerRegistry.getBounds(classNode);
				NodeViewerRegistry.getBounds(packageNode);
				NodeViewerRegistry.getBounds(noteNode);
				NodeViewerRegistry.getBounds(objectNode1);
				NodeViewerRegistry.getBounds(objectNode2);
				NodeViewerRegistry.getBounds(objectNode3);
				NodeViewerRegistry.getBounds(objectNode4);
				NodeViewerRegistry.getBounds(objectNode5);
				NodeViewerRegistry.getBounds(objectNode6);
				NodeViewerRegistry.getBounds(objectNode7);
			}
			Instant end = Instant.now();
			
			averageTime += Duration.between(start, end).getNano()*0.000001;
		}
		averageTime = averageTime / numberOfRounds;
		System.out.println("(10 nodes) Average duration of " + numberOfRounds + " trials in milliseconds : " + averageTime);
	}
}
