//TODO: Implement the required methods and add JavaDoc for them.
//Remember: Do NOT add any additional instance or class variables (local variables are ok)
//and do NOT alter any provided methods or change any method signatures!

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *  Simulation of our coloring algorithm.
 *  
 */
class ThreeTenColor implements ThreeTenAlg {
	/**
	 *  The graph the algorithm will run on.
	 */
	Graph<GraphNode, GraphEdge> graph;
	
	/**
	 *  The priority queue of nodes for the algorithm.
	 */
	WeissPriorityQueue<GraphNode> queue;
	
	/**
	 *  The stack of nodes for the algorithm.
	 */
	LinkedList<GraphNode> stack;
	
	/**
	 *  Whether or not the algorithm has been started.
	 */
	private boolean started = false;
	
	/**
	 *  Whether or not the algorithm is in the coloring stage or not.
	 */	
	private boolean coloring = false;	

	/**
	 *  The color when a node has "no color".
	 */
	public static final Color COLOR_NONE_NODE = Color.WHITE;
	
	/**
	 *  The color when an edge has "no color".
	 */
	public static final Color COLOR_NONE_EDGE = Color.BLACK;
		
	/**
	 *  The color when a node is inactive.
	 */
	public static final Color COLOR_INACTIVE_NODE = Color.LIGHT_GRAY;

	/**
	 *  The color when an edge is inactive.
	 */
	public static final Color COLOR_INACTIVE_EDGE = Color.LIGHT_GRAY;
	
	/**
	 *  The color when a node is highlighted.
	 */
	public static final Color COLOR_HIGHLIGHT = new Color(255,204,51);
	
	/**
	 *  The color when a node is in warning.
	 */
	public static final Color COLOR_WARNING = new Color(255,51,51);

			
	/**
	 *  The colors used to assign to nodes.
	 */
	public static final Color[] COLORS = 
		{Color.PINK, Color.GREEN, Color.CYAN, Color.ORANGE, 
		Color.MAGENTA, Color.YELLOW, Color.DARK_GRAY, Color.BLUE};
	
	/**
	 *  {@inheritDoc}
	 */
	public EdgeType graphEdgeType() {
		return EdgeType.UNDIRECTED;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void reset(Graph<GraphNode, GraphEdge> graph) {
		this.graph = graph;
		started = false;
		coloring = false;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean isStarted() {
		return started;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void start() {
		this.started = true;
		
		//create an empty stack
		stack = new LinkedList<>();
		
		//create an empty priority queue
		queue = new WeissPriorityQueue<>();
		
		for(GraphNode v : graph.getVertices()) {
			
			//Set the cost of each node to be its degree
			v.setCost(graph.degree(v));
			
			//Set each node to be active
			//This enables the display of cost for the node
			v.setActive();
		
			//add node into queue
			queue.add(v);
		}
		
		//highlight the current node with max priority 
		highlightNextMax();
			
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void finish() {
	
		// Coloring completed. Set all edges back to "no color".
		for (GraphEdge e: graph.getEdges()){
			e.setColor(COLOR_NONE_EDGE);
		}
		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void cleanUpLastStep() {
		// Unused. Required by the interface.		
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public boolean setupNextStep() {
	
		// Whole algorithm done. 
		if (coloring && stack.size() == 0)
			return false;
						
		// First stage done when all nodes are pushed into stack.
		// Change the flag to start the coloring stage.
		if (!coloring && graph.getVertexCount() == stack.size()){
			coloring = true;
		}
		
		//Return true to indicate more steps to continue.
		return true;
	}
	
	/**
	 *  {@inheritDoc}
	 */
	public void doNextStep() {
	
		if (!coloring){
			//Stage 1: pushing nodes into stack one by one & update record
			
			// maxNode is the active node with the highest priority
			// Remove the maxNode from priority queue and push it into stack
			GraphNode maxNode = findMax();
			
			//Update the cost of all nodes that is a neighbor of the maxNode
			updateNeighborCost(maxNode);
			
			//Identify and highlight the next max node in the updated priority queue
			highlightNextMax();
			
								
		}
		else{
			//Stage 2: pop nodes from stack one by one and choose a color for each
			
			//Pop off stack top 
			GraphNode node = stack.pop();
			
			//For the node popped off, pick a color that is different from all
			//neighbors who has got assigned a color so far			
			Color newColor = chooseColor(node);
			
			//Inform all neighbors of this node the selected color
			updateColor(node, newColor);
			
		}
		
	}
	
	//----------------------------------------------------
	// TODO: Implement the methods below to complete the coloring algorithm.
	// - DO NOT change the signature of any required public method;
	// - Feel free to define additional method but they must be private.
	//
	// YOUR CODE HERE
	//----------------------------------------------------

	/**
	 * highlights next max node in the graph with color highlight.
	 */
	public void highlightNextMax(){
		//Stage 1 & start() operation: find out which node has the highest degree and highlight it
		LinkedList<GraphNode> nodes = (LinkedList<GraphNode>) graph.getVertices();
		Iterator<GraphNode> iter = queue.iterator();
		if(queue.isEmpty())
		{
			return;
		}
		GraphNode max = null;
		while(iter.hasNext())
		{
			GraphNode next = iter.next();
			if(max==null)
			{
				max=next;
			}
			if(next.compareTo(max)<0)
			{
				max = next;
			}
		}

		/*
		for(int i = 1;i < nodes.size();i++)
		{
			if(nodes.get(i).compareTo(max)<0)
			{
				max = nodes.get(i);
			}
		}


		*/
		max.color=COLOR_HIGHLIGHT;

		// and change the color of the node to be COLOR_HIGHLIGHT.
		// Note: do not dequeue the node.
		
	}

	/**
	 * finds the new max, if no nodes  are in graph return null.
	 * @return the max node
	 */
	public GraphNode findMax(){
		//Stage 1 operation: push node with highest degree to stack and remove it with its edges
		// - If queue is empty, return null.
		if(queue.isEmpty())
		{
			return null;
		}
		GraphNode max;
		// 1. Remove the node with the max priority from the priority queue;
		//

		max = queue.remove();
		max.color=COLOR_INACTIVE_NODE;
		/*


		Iterator<GraphNode> iter = queue.iterator();
		max = iter.next();
		// Note: the max node should be the one with the highest cost
		// (i.e max number of active neighbors).  If there is a tie in cost,
		// the one with the lowest ID should be selected.
		// Hint: if your priority queue has been implemented correctly, this
		// should be straightforward.
		//

		LinkedList<GraphNode> neighbors;
		int activeNbrs;
		LinkedList<GraphNode> nodes = (LinkedList<GraphNode>) graph.getVertices();
		HashMap<GraphNode,Integer> activeNeighborCounts = new HashMap<>();
		for(int i = 0 ; i< nodes.size();i++)
		{// go thru all nodes
			activeNeighborCounts.put(nodes.get(i),0);
			neighbors = (LinkedList<GraphNode>) graph.getNeighbors(nodes.get(i));// get list of neighbors
			// then, go thru all of nodes neighbors, and get amount of active ones
			activeNbrs=0;
			for(int j = 0 ; j < neighbors.size();j++)
			{
				if(neighbors.get(j).isActive())
				{
					activeNbrs++;
				}
			}
			activeNeighborCounts.put(nodes.get(i),activeNbrs);
		}
		// once we have all nodes and counts of active neighbors,go thru each node
		for(int i = 0;i < nodes.size();i++)
		{
			if(activeNeighborCounts.get(nodes.get(i))>activeNeighborCounts.get(max))
			{
				max = nodes.get(i);// if any node has a higher active neighborcount than max update max
			}
			if(activeNeighborCounts.get(nodes.get(i))==activeNeighborCounts.get(max))
			{
				//since both nodes have same number of active neighbors, go by ids
				if(max.id>nodes.get(i).id)// if possible new max has a lower id and same cost, change to new max, otherwise keep curr max
				{
					max=nodes.get(i);
				}
			}
		}




		queue.remove(max);

		 */



		this.stack.push(max);
		// 2. Push the max node into stack
		// Note: Take a look at the JavaDoc of LinkedList to determine which method
		// to use, especially the interface Deque.
		//
		// 3. Set max node to be inactive and change its color to be COLOR_INACTIVE_NODE;
		max.unsetActive();

		// 4. Set the color of all incident edges of max node to be COLOR_INACTIVE_EDGE.
		LinkedList<GraphEdge> edges = (LinkedList<GraphEdge>) graph.getIncidentEdges(max);
		for(int i = 0 ; i< edges.size();i++)
		{
			edges.get(i).color=COLOR_INACTIVE_EDGE;
		}
		//
		// Return the max node.  
		return max;
			

	}

	/**
	 * updates the neighborcost of node maxnode.
	 * @param maxNode the node to update the neighborcost of
	 */
	public void updateNeighborCost(GraphNode maxNode){
		//Stage 1 operation: lower the degree of the neighbors of the removed node
		int cost=0;
		LinkedList<GraphNode> neighbors = (LinkedList<GraphNode>) graph.getNeighbors(maxNode);
		for(int i = 0 ;i< neighbors.size();i++)
		{
			if(neighbors.get(i).isActive())
			{
				cost++;
			}
		}
		maxNode.setCost(cost);
		// Update the cost for all active neighbors of maxNode.
		// Note that the cost of a node is equal to the number of its *active* neighbors.


	}

	/**
	 * choose a new color for the node based on certain criteria, including if any of its neighbors have that color.
	 * @param node the node to choose a new color for
	 * @return the new color that was chosen for the node
	 */
	public Color chooseColor(GraphNode node){
		//Stage 2 operation: decide a color for a node such that no neighbor of the node is assigned the same color
		// - If node is null, return null.
		//
		if(node==null)
		{
			return null;
		}
		// Pick a color for node based on the following criteria:
		// 1. The color is one of the listed colors in ThreeTenColor.COLORS;
		Color color=null;
		HashMap<Color, Boolean> neighborColors = new HashMap<Color, Boolean>();
		for(int i = 0; i< COLORS.length;i++)
		{// put colors into hashmpa with true/false values for each
			neighborColors.put(COLORS[i],false);
		}
		// 2. The color has not been assigned to any of its neighbors, and
		LinkedList<GraphNode> neighbors = (LinkedList<GraphNode>) graph.getNeighbors(node);
		for(int i = 0;i< neighbors.size();i++)
		{// go thru all neighbors of node, if a neighbor has that color then swithc to true in neighborcolors hashmap
			if(neighborColors.containsKey(neighbors.get(i).color))
			{
				neighborColors.put(neighbors.get(i).color,true);
			}
		}
		// 3. The color has the lowest index in array ThreeTenColor.COLORS in all
		//    colors that satisfy condition 2.
		//
		int colorIndex=-1;
		for(int i =0 ; i< COLORS.length;i++)
		{// go thru colors again, starting from beginnign index0 see which one isnt taken by a neighbor
			if(neighborColors.get(COLORS[i])==false)
			{// if not taken by a neighbor
				color = COLORS[i];// choose the new approriate color
				colorIndex=i;// save index of color
			}
		}
		// - If we fail to find a color that satisfy all three conditions above,
		//  return COLOR_WARNING and no need to update the neighbors.
		// if all of the neighbors have taken the availible colors already
		if((color == null)||(colorIndex==-1))
		{
			return COLOR_WARNING;
		}


		// After a color is selected, inform all neighbors of node that this color
		// is in use.  Hint: You will need to update nbrColors flag for the neighbors.
		for(int i = 0 ; i < neighbors.size();i++)
		{
			neighbors.get(i).nbrHasColor(colorIndex);
		}

		// Return the selected color.


				
		return color;
	}

	/**
	 * updates the color of node node to newcolor.
	 * @param node the node to have its color updated to newcolor
	 * @param newColor the colorfor the node to have its color updated to
	 */
	public void updateColor(GraphNode node, Color newColor){
		//Stage 2 operation: update the color of node and its qualified incident edges
		// No change should be made if either node or newColor is null.
		if((node==null)||(newColor==null))
		{
			return;
		}

		int colorIndex=-1;
		for(int i = 0; i < COLORS.length;i++)
		{
			if(COLORS[i].equals(newColor))
			{// find index of newcolor in array colors for this
				colorIndex = i;
			}
		}
		LinkedList<GraphNode> neighbors = (LinkedList<GraphNode>) graph.getNeighbors(node);
		for(int i = 0 ;i< neighbors.size();i++)
		{// update neighbors that this node has a new color
			neighbors.get(i).nbrHasColor(colorIndex);
		}

		// As part of the 2nd stage, set the color of node to be newColor.
		node.color=newColor;
		LinkedList<GraphEdge> edges = (LinkedList<GraphEdge>) graph.getIncidentEdges(node);
		for(int i = 0 ;i<edges.size();i++)
		{// go thru edges
			if(edges.get(i).color.equals(COLOR_NONE_EDGE))
			{
				edges.get(i).color=newColor;
			}
		}
		// Also, for any edge incident to node, if the edge has not assigned a color 
		// in Stage 2, color it with newColor.
		//

	

	}

}
