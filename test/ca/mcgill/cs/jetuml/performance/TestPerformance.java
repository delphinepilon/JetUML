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
	private static final int NUMBER_OF_ROUNDS = 10;
	private static final int NUMBER_OF_ITERATIONS_PER_ROUND = 10000;
	
	public static void main(String[] pArgs)
	{
		testGetBoundsPerformanceWithTenNodesContinuouslyBeingTranslated();
		testGetBoundsPerformanceWithThreeNodesContinuouslyBeingTranslated();
		testGetBoundsPerformanceWithClassNodeContinuouslyBeingTranslated();
		
	}
	
	/**
	 * Tests NodeViewerRegistry.getBounds(Node) as a ClassNode is continuously being translated. 
	 */
	public static void testGetBoundsPerformanceWithClassNodeContinuouslyBeingTranslated()
	{
		double averageTime = 0.0;
		
		for (int i = 0; i < NUMBER_OF_ROUNDS; i++)
		{
			ClassNode classNode = new ClassNode();
			
			Instant start = Instant.now();
			for (int j = 0; j < NUMBER_OF_ITERATIONS_PER_ROUND; j++)
			{
				classNode.translate(i, i);
				NodeViewerRegistry.getBounds(classNode);
			}
			Instant end = Instant.now();
			
			averageTime += Duration.between(start, end).getNano()*0.000001;
		}
		averageTime = averageTime / NUMBER_OF_ROUNDS;
		System.out.println("(1 node) Average duration of " + NUMBER_OF_ROUNDS + " trials in milliseconds : " + averageTime);
	}
	
	/**
	 * Tests NodeViewerRegistry.getBounds(Node) as 3 nodes of different types keep being translated. 
	 */
	public static void testGetBoundsPerformanceWithThreeNodesContinuouslyBeingTranslated()
	{
		double averageTime = 0.0;
		
		for (int i = 0; i < NUMBER_OF_ROUNDS; i++)
		{
			PackageNode packageNode = new PackageNode();
			ClassNode classNode = new ClassNode();
			NoteNode noteNode = new NoteNode();
			
			Instant start = Instant.now();
			for (int j = 0; j < NUMBER_OF_ITERATIONS_PER_ROUND; j++)
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
		averageTime = averageTime / NUMBER_OF_ROUNDS;
		System.out.println("(3 nodes) Average duration of " + NUMBER_OF_ROUNDS + " trials in milliseconds : " + averageTime);
	}
	
	/**
	 * Tests NodeViewerRegistry.getBounds(Node) as 10 nodes of different types keep being translated. 
	 */
	public static void testGetBoundsPerformanceWithTenNodesContinuouslyBeingTranslated()
	{
		double averageTime = 0.0;
		
		for (int i = 0; i < NUMBER_OF_ROUNDS; i++)
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
			for (int j = 0; j < NUMBER_OF_ITERATIONS_PER_ROUND; j++)
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
		averageTime = averageTime / NUMBER_OF_ROUNDS;
		System.out.println("(10 nodes) Average duration of " + NUMBER_OF_ROUNDS + " trials in milliseconds : " + averageTime);
	}
}
