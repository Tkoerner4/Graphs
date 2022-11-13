//TODO:
//  (1) Implement the graph!
//  (2) Update this code to meet the style and JavaDoc requirements.
//			Why? So that you get experience with the code for a graph!
//			Also, this happens a lot in industry (updating old code
//			to meet your new standards).

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.graph.util.Pair;
import org.apache.commons.collections15.Factory;

import java.util.Collection;
import java.util.LinkedList;

/**
 * class for threeten graph with all of the instances and methods.
 */
class ThreeTenGraph implements Graph<GraphNode,GraphEdge>, UndirectedGraph<GraphNode,GraphEdge> {
	//HINTS:
	//1 -- Learn about what methods and constructors are available in Java's the LinkedList
	//     class before trying this, a lot of them will come in handy...
	//2 -- You may want to become friendly with the ListIterator as well. This iterator can
	//     support things (like removal)
	
	
	//you may not have any other class variables, only this one
	//if you make more class variables your graph class will receive a 0,
	//no matter how well it works
	/**
	 * number for max number of nodes in the graph.
	 */
	private static final int MAX_NUMBER_OF_NODES = 200;
	
	//you may not have any other instance variables, only these two
	//if you make more instance variables your graph class will receive a 0,
	//no matter how well it works
	/**
	 * object for holding all the nodes in the graph.
	 */
	private LinkedList<GraphNode> nodeList = null;
	//nodelist is a linkedlist of all of the nodes in the graph
	/**
	 * list for holding all of the adjacent nodes for each node.
	 */
	private LinkedList<Destination>[] adjList = null;
	//adjlist is an adjacencylist, an array of linkedlists, each index corresponding to a node. each nodes index
	// has a linkedlist full of destinations in it, with the node being the destination node from the edge. the node
	//assocated with the index that the linkedlist is stored at has its edges, and the nodes that the edges lead to in the
	//linkedlist
	
	
	//a (destination,edge) to store in the adjacency list
	//note: source is indicated by the placement in the adjacency list

	/**
	 * class for destination, holds constructor and methods.
	 */
	private class Destination {
		//you may NOT add/remove/change any instance variables, you must used
		//the two below as-is
		/**
		 * node for the destination class.
		 */
		GraphNode node;
		/**
		 * edge for the destination class.
		 */
		GraphEdge edge;
		
		//do not remove/change this constructor either
		Destination(GraphNode n, GraphEdge e)
		{ this.node = n; this.edge = e; }

		/**
		 * gets the node of the destination.
		 * @return node for this destination.
		 */
		private GraphNode getNode()
		{
			return this.node;
		}

		/**
		 * gets the edge for this destination.
		 * @return the edge of this destination.
		 */
		private GraphEdge getEdge()
		{
			return this.edge;
		}

		/**
		 * checks if this destination has this node.
		 * @param node node to be checked for.
		 * @return if this destination contains this node.
		 */
		private boolean hasNode(GraphNode node)
		{
			if(this.node.equals(node))
			{
				return true;
			}
			return false;
		}
		//You may add *methods* to this class that you find helpful
	}
	
	//this is the only allowed constructor

	/**
	 * construcotr for threetengraph object.
	 */
	public ThreeTenGraph() {
		this.nodeList=new LinkedList<>();
		this.adjList = new LinkedList[MAX_NUMBER_OF_NODES];//array of linkedlists, size set to max num of nodes


		//reminder: you can NOT do this: ClassWithGeneric<T>[] items = (ClassWithGeneric<T>[]) new Object[10];
		//you must use this format this instead: ClassWithGeneric<T>[] items = (ClassWithGeneric<T>[]) new ClassWithGeneric[10];
	}

    /**
     * Returns a view of all edges in this graph. In general, this
     * obeys the Collection contract, and therefore makes no guarantees 
     * about the ordering of the vertices within the set.
     * @return a Collection view of all edges in this graph
     */
    public Collection<GraphEdge> getEdges() {
		LinkedList<GraphEdge> edgesOfGraph=new LinkedList<>();// store edges of graph

		for(int i =0;i< nodeList.size();i++)// go thru the adjacency list
		{//store the adjacent nodes and edges for this node in a copy
			//LinkedList<Destination> adjacent =  adjList[i];
			if(adjList[i]==null)
			{
				continue;
			}
			int size = adjList[i].size();// store size of copy of adjacent nodes and edges
			for(int j = 0;j < size;j++)// go thru list of adjacent edges and nodes
			{// for each destination object in index i adjacency list
				GraphEdge edge = adjList[i].get(j).edge;//remove the edge of destination object being parsed
				// add edge to list of edges
				if(edgesOfGraph.contains(edge)==false)
				{
					edgesOfGraph.add(edge);
				}

				// complete for every destination for this node
			}	//move onto next node

		}
		//O(n+e) where e is the number of edges in the graph and n is the number
		//of nodes in the graph (NOT the max number of nodes in the graph)
		
		//Big-O hint: O(n+e) with the above constraints means that you need to
		//be using the list of nodes that *are* in the graph for the 'n', the 'e'
		//will come naturally when you go through the edges
		
		//Hint: this method returns a Collection, look at the imports for
		//what collections you could return here.
		
		return edgesOfGraph;
	}
    
    /**
     * Returns a view of all vertices in this graph. In general, this
     * obeys the Collection contract, and therefore makes no guarantees 
     * about the ordering of the vertices within the set.
     * @return a Collection view of all vertices in this graph
     */
    public Collection<GraphNode> getVertices() {


		return  this.nodeList;
		//O(n) where n is the number of nodes in the graph
		//NOT the max number of nodes in the graph
		
		//Hint: this method returns a Collection, look at the imports for
		//what collections you could return here.
		

	}
    
    /**
     * Returns the number of edges in this graph.
     * @return the number of edges in this graph
     */
    public int getEdgeCount() {

		int numEdges=0; // counter for num edges in the craph
		LinkedList<GraphEdge> edges=new LinkedList<>();
		for(int i = 0; i < nodeList.size();i++)// go thru adjlist
		{

			if(adjList[i]==null)
			{

				continue;
			}
			//LinkedList<Destination> list = adjList[i];// store list of destinations for list
			for(int j =0; j< adjList[i].size();j++)// for each element of adjlist
			{
				Destination dest = adjList[i].get(j);
				if(edges.contains(dest.edge)==false)
				{
					edges.add(dest.edge);
				}

			}
		}
		//O(n) where n is the number of nodes in the graph
		//NOT the max number of nodes in the graph
		
		//note: this runtime is not a mistake, think about how
		//you could find out the number of edges *without*
		//looking at each one
		numEdges=edges.size();
		System.out.println("edge count = "+edges);
		return numEdges;
	}
    
    /**
     * Returns the number of vertices in this graph.
     * @return the number of vertices in this graph
     */
    public int getVertexCount() {
		//O(1)
		
		//note: this runtime is not a mistake, think about how
		//you could find out the number of nodes *without*
		//looking at each one
		System.out.println("vertex count = "+this.nodeList.size());
		return this.nodeList.size();
	}

    /**
     * Returns true if this graph's vertex collection contains vertex.
     * Equivalent to getVertices().contains(vertex).
     * @param vertex the vertex whose presence is being queried
     * @return true iff this graph contains a vertex vertex
     */
    public boolean containsVertex(GraphNode vertex) {
		//O(1) -- NOT O(n)!
		if(vertex.equals(null))
		{
			return false;
		}
		if(nodeList.contains(vertex))
		{
			if(nodeList.get(nodeList.indexOf(vertex)).id==vertex.id)
			{
				System.out.println("contains "+vertex);
				return true;
			}

		}
		//note: this runtime is not a mistake, there is a "quick" way
		//and a slow way to find out if a node is in the graph, look at
		//the storage overview in the project description for ideas
		
		return false;
	}
    
    /**
     * Returns the collection of vertices which are connected to vertex
     * via any edges in this graph.
     * If vertex is connected to itself with a self-loop, then 
     * it will be included in the collection returned.
     * 
     * @param vertex the vertex whose neighbors are to be returned
     * @return  the collection of vertices which are connected to vertex,
     */
    public Collection<GraphNode> getNeighbors(GraphNode vertex) {

		//O(n) where n is the number of nodes in the graph
		//get index of selected node
		LinkedList<GraphNode> neighbors = new LinkedList<>();
		if(this.containsVertex(vertex)==false)// if vertex not in graph
		{
			return null;
		}
		if(vertex==null)
		{
			return null;
		}

		// if the vertex is in the graph however


		int index= nodeList.indexOf(vertex);// grab index of node we want to examine


		while(adjList[index]==null)
		{
			index++;
		}
		for(int i = 0; i< adjList[index].size();i++)// exmining selected nodes destintion list
		{// go thru its destinations
			GraphNode neighbor = adjList[index].get(i).node;// store the neighbor node
			neighbors.add(neighbor);// add the neighbor into the list of neighbors for that node
		}


		System.out.println(vertex+" neighbors are "+ neighbors);
		return neighbors;// return list of neighboring nodes for this vertice
	}
    
    /**
     * Returns the number of vertices that are adjacent to vertex
     * (that is, the number of vertices that are incident to edges in vertex's
     * incident edge set).
     * 
     * <p>Equivalent to getNeighbors(vertex).size().
     * @param vertex the vertex whose neighbor count is to be returned
     * @return the number of neighboring vertices
     */
    public int getNeighborCount(GraphNode vertex) {
		//O(1) -- NOT O(n)!
		if(nodeList.contains(vertex)==false)
		{
			return 0;//if vertex not in
		}
		if(vertex==null)
		{
			return 0;
		}
		// if vertex is in
		int index = nodeList.indexOf(vertex);
		while(adjList[index]==null)
		{
			index++;
		}
		int neighborCount=this.adjList[index].size();
		//note: this runtime is not a mistake, think about how
		//you could find out the number of neighbors *without*
		//looking at each one
		System.out.println("node"+ vertex+ " neighbor count is"+ neighborCount);
		return neighborCount;
	}
    
    /**
     * Returns the collection of edges in this graph which are connected to vertex.
     * WORKING
     * @param vertex the vertex whose incident edges are to be returned
     * @return  the collection of edges which are connected to vertex,
     */
    public Collection<GraphEdge> getIncidentEdges(GraphNode vertex) {

		//O(n) where n is the number of nodes in the graph
		LinkedList<GraphEdge> edges = new LinkedList<>();// store incoming edges
		if(nodeList.contains(vertex)==false)
		{
			return null;//if vertex not present
		}
		if(vertex==null)
		{
			return null;
		}
		// if it is present

		for(int i =0; i< nodeList.size();i++)
		{
			if(i== nodeList.indexOf(vertex))
			{// if examining this vertexes adj list
				continue;// next iteration
			}//otherwise, if not this vertex adjlist
			//get copy of adjlist for curr node
			//LinkedList<Destination> destinations = adjList[i];
			if(adjList[i]==null)
			{
				continue;
			}
			for(int j = 0;j< adjList[i].size();j++)
			{
				//take out first elemtn of destinations
				Destination dest = adjList[i].get(j);
				if(dest.node==vertex)// if curr dest is leading to this one
				{
					edges.add(dest.edge);
				}
			}
		}
		System.out.println("node"+vertex+" is incident to +"+edges);
		return edges;
	}
    /**
	 * gets the endpoints of the graph.
     * @param edge the edge whose endpoints are to be returned
     * @return the endpoints (incident vertices) of edge
     */
    public Pair<GraphNode> getEndpoints(GraphEdge edge)
	{

		if(edge==null)
		{
			System.out.println("edge is null");
			return null;

		}

		GraphNode node1;
		GraphNode node2;
		Pair<GraphNode> nodes=null;
		/*
		LinkedList<GraphEdge> edges = (LinkedList<GraphEdge>) this.getEdges();
		for(int i = 0 ; i< nodeList.size();i++)
		{
			if(this.isIncident(nodeList.get(i),edge))
			{
				nodes.add(nodeList.get(i));
			}
		}
		*/

		for(int i = 0; i < nodeList.size();i++)
		{// go thru all nodes dest lists

			if(adjList[i]==null)
			{
				continue;
			}
			for(int j = 0;j< adjList[i].size();j++)
			{
				
				System.out.println("searching thru dest pair for node1: node " + adjList[i].get(j).node+ " and edge " + adjList[i].get(j).edge);
				Destination dest = adjList[i].get(j);// remove head dest
				System.out.println("got dest");

				if(dest.edge.equals(edge))// if we find our edge
				{
					System.out.println("first node found");
					node1 = dest.node;
					//time to search for the other one connected
					int index = nodeList.indexOf(node1);
					while(adjList[index]==null)
					{
						index++;
					}
					System.out.println("index found for node1");
					for(int k =0;k < adjList[index].size();k++)
					{
						System.out.println("searching thru dest pair for node2: node " + adjList[i].get(j).node+ " and edge " + adjList[i].get(j).edge);

						Destination dest2 = adjList[index].get(k);// serach the dest list of the other node
						if(dest2.edge.equals(edge))// if th find the edge conenction in the other dest list
						{
							System.out.println("node 2 found");
							node2=dest2.node;
							nodes = new Pair<>(node1,node2);
							System.out.println("edge "+ edge+ " endpoints :"+nodes);
							return nodes;
						}
					}
				}
			}
		}


		//O(n+e) where e is the number of edges in the graph and n is the number
		//of nodes in the graph (NOT the max number of nodes in the graph)
		return nodes;
		//return null;

	}

    /**
     * Returns an edge that connects v1 to v2.
     * If this edge is not uniquely
     * defined (that is, if the graph contains more than one edge connecting 
     * v1 to v2), any of these edges 
     * may be returned.  findEdgeSet(v1, v2) may be 
     * used to return all such edges.
     * Returns null if either of the following is true:
     * <ul>
     * <li/>v1 is not connected to v2
     * <li/>either v1 or v2 are not present in this graph
     * </ul>
     * v2 via a given <i>directed</i> edge e if
     * v1 == e.getSource() && v2 == e.getDest() evaluates to true.
     * (v1 and v2 are connected by an undirected edge u if 
     * u is incident to both v1 and v2.)
	 * @param v1 first node the find the edge for
	 * @param v2 second node the fund the edge for
     * @return  an edge that connects v1 to v2, or null if no such edge exists (or either vertex is not present)
     */
    public GraphEdge findEdge(GraphNode v1, GraphNode v2) {

		//O(n) where n is the number of nodes in the graph
		if(this.nodeList.contains(v1)==false)
		{
			return null;
		}
		if(this.nodeList.contains(v2)==false)
		{
			return null;
		}
		GraphEdge retEdge=null;
		int startingIndex = this.nodeList.indexOf(v1);
		while(adjList[startingIndex]==null)
		{
			startingIndex++;
		}
		int endIndex = this.nodeList.indexOf(v2);
		while(adjList[endIndex]==null)
		{
			endIndex++;
		}
		//LinkedList<Destination> destinations = adjList[startingIndex];
		for(int i = 0 ;i< adjList[startingIndex].size();i++)
		{
			Destination dest = adjList[startingIndex].get(i);// look at next dest obj
			GraphEdge edge = dest.edge;// edge to lookat for current destination connection
			if(dest.node.equals(v2))// if dest node found == v2
			{
				System.out.println("node "+ v1 + "and node"+v2+ "edge:" +edge);
				return edge;
			}
		}
		return null;// if no edge conencting node v1 and node v2 are found
	}

    /**
    * Returns true if vertex and edge
    * are incident to each other.
    * Equivalent to getIncidentEdges(vertex).contains(edge) and to
    * getIncidentVertices(edge).contains(vertex).
    * @param vertex the vertex to see if this edge is indicent to
    * @param edge the edge to check for if the node is indicent to it
    * @return true if vertex and edge are incident to each other
	*/
    public boolean isIncident(GraphNode vertex, GraphEdge edge) {
		boolean result = false;
		//O(n) where n is the number of nodes in the graph
		if((vertex==null)||(edge ==null))
		{
			return false;// if either parametet given is null
		}
		if(nodeList.contains(vertex)==false)
		{
			return false;
		}
		for(int i = 0 ; i< nodeList.size();i++)
		{// check all connections
			//LinkedList<Destination> destinations = adjList[i];
			if(adjList[i]==null)
			{
				continue;
			}
			for(int j = 0 ;j < adjList[i].size();i++)
			{
				Destination dest = adjList[i].get(j);
				if((dest.node.equals(vertex))&&(dest.edge.equals(edge)))// if desired edge found leading to desired node
				{
					System.out.println("node "+vertex+" is incident to edge "+edge);
					return true;
				}

			}
		}
		
		return false;
	}

    /**
     * Adds edge e to this graph such that it connects 
     * vertex v1 to v2.
     * If this graph does not contain v1, v2, 
     * or both, implementations may choose to either silently add 
     * the vertices to the graph or throw an IllegalArgumentException.
     * If this graph assigns edge types to its edges, the edge type of
     * e will be the default for this graph.
     * See Hypergraph.addEdge() for a listing of possible reasons
     * for failure.
     * @param e the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @return true if the add is successful, false otherwise
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2) {
		//System.out.println("adding edge"+ e + " between node"+v1+ " and node "+ v2);
		if((nodeList.contains(v1)==false)||(nodeList.contains(v2)==false))// if either not alr in
		{
			throw new IllegalArgumentException();
		}
		if(e==null)
		{
			return false;
		}
		if(v1==null)
		{
			return false;
		}
		if(v2==null)
		{
			return false;
		}
		//System.out.println("checked for nulls");
		int startingIndex = nodeList.indexOf(v1);// grab index of starting node
		while(adjList[startingIndex]==null)
		{
			startingIndex++;
		}
		//System.out.println("found correct index v1");
		int v2Index = nodeList.indexOf(v2);
		while(adjList[v2Index]==null)
		{
			v2Index++;
		}
		//System.out.println("found correct index v2");
		for(int i = 0 ; i < adjList[startingIndex].size();i++)// go thru starting nodes dest list
		{
			Destination dest = adjList[startingIndex].get(i);
			if((dest.edge.equals(e))&&(dest.node.equals(v2)))
			{// if a connection already exists
				return false;
			}
		}
		//System.out.println("found correct dest in v1 dest list");
		Destination dest = new Destination(v2,e);// create new destination with edge e leading to nodev2
		adjList[startingIndex].add(dest);// add to v1s dest list


		for(int i = 0 ; i < adjList[v2Index].size();i++)// go thru v2 dest list
		{
			Destination dest1 = adjList[v2Index].get(i);// exmine each dest in v2 dest list
			if((dest1.edge.equals(e))&&(dest1.node.equals(v2)))// if already in dont add
			{// if a connection already exists
				return false;
			}
		}//otherwise
		adjList[v2Index].add(new Destination(v1,e));// have v2 connecting to v1






		//O(n+e) where e is the number of edges in the graph and n is the number
		//of nodes in the graph (NOT the max number of nodes in the graph)
		
		//note: you need to make sure the edge isn't ANYWHERE in the graph
		//not just that it's not connecting v1 and v2, also that there isn't
		//a different edge connecting v1 and v2
		
		return true;
	}
    
    /**WORKING
     * Adds vertex to this graph.
     * Fails if vertex is null or already in the graph.
     * 
     * @param vertex    the vertex to add
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if vertex is null
     */
    public boolean addVertex(GraphNode vertex) {
		System.out.println("adding vertex"+vertex);
		if(vertex.equals(null))
		{
			throw new IllegalArgumentException();
		}
		if(nodeList.contains(vertex))
		{
			return false;
		}
		if(nodeList.size()==MAX_NUMBER_OF_NODES)
		{
			return false;
		}
		//O(1) -- NOT O(n)!

		LinkedList<Destination> destinations = new LinkedList<>();
		adjList[nodeList.size()]=destinations;
		nodeList.add(vertex);// add into nodelist
		return true;

		
		//note: pay close attention to the project description about how
		//the adjacency list works for nodes that have no edges vs. nodes
		//that aren't in the graph
		// if the grpah is full it would return false

	}

    /**WORKING
     * Removes edge from this graph.
     * Fails if edge is null, or is otherwise not an element of this graph.
     * 
     * @param edge the edge to remove
     * @return true if the removal is successful, false otherwise
     */
    public boolean removeEdge(GraphEdge edge) {
		if(edge.equals(null))
		{
			return false;
		}
		System.out.println("removing edge"+edge);
		GraphNode adj;
		int cost=0;
		for(int i = 0 ;i < nodeList.size();i++)
		{
			//LinkedList<Destination> destinations = adjList[i];
			// go thru nodes
			if(adjList[i]==null)
			{
				continue;
			}
			for(int j = 0; j < adjList[i].size();j++)
			{

				Destination dest = adjList[i].get(j);
				if(dest.edge.equals(edge))// going thru list of dests for node
				{// if edge found
					//store node edge is connecting to , to remove the alternate connection
					adj = dest.node;
					adjList[i].remove(dest);// remove dest node pair form this nodes dest list
					//cost = nodeList.get(i).getCost();
					//nodeList.get(i).setCost(cost-1);
					int adjIndex = nodeList.indexOf(adj);
					while(adjList[adjIndex]==null)
					{
						adjIndex++;
					}
					for(int k = 0; k <adjList[adjIndex].size();k++)// got to adj list of node connecting to one found
					{
						Destination dest2 = adjList[adjIndex].get(k);
						if(dest2.edge.equals(edge))
						{
							cost = nodeList.get(adjIndex).getCost();
							nodeList.get(adjIndex).setCost(cost-1);
							adjList[adjIndex].remove(dest2);
							return true;
						}
					}

				}
			}
		}
		//O(n+e) where e is the number of edges in the graph and n is the number
		//of nodes in the graph (NOT the max number of nodes in the graph)
		// if desired edge not found
		return false;
	}
    
    /**WORKING
    * Removes vertex from this graph.
    * As a side effect, removes any edges e incident to vertex if the
    * removal of vertex would cause e to be incident to an illegal
    * number of vertices.  (Thus, for example, incident hyperedges are not removed, but
    * incident edges--which must be connected to a vertex at both endpoints--are removed.)
    *
    * <p>Fails under the following circumstances:
    * <ul>
    * <li/>vertex is not an element of this graph
    * <li/>vertex is null
    * </ul>
    *
    * @param vertex the vertex to remove
    * @return true if the removal is successful, false otherwise
    */
    public boolean removeVertex(GraphNode vertex)
	{
		System.out.println("removing vertex"+vertex);
		if (vertex.equals(null)) {
			return false;
		}
		if (nodeList.contains(vertex) == false) {
			return false;
		}
		/*
		int pos = nodeList.indexOf(vertex);//save postion of vertex
		int encountered = 0;
		for (int i = 0; i < adjList.length; i++)// go thru adjacent lists
		{
			if (adjList[i] != null) {
				if (pos == encountered) {
					adjList[i] = null;
					break;
				} else {
					encountered++;
				}
			}
		}
		if(this.getNeighbors(vertex).size()==0)
		{
			nodeList.remove(vertex);
		}
		//remove edges connecting
		LinkedList<GraphEdge> edges = (LinkedList<GraphEdge>) this.getIncidentEdges(vertex);
		while (edges.isEmpty() == false)// get list of conencting edges, putnin linkedlist, remove until empty
		{
			this.removeEdge(edges.remove());//remove edge from edges list of vertex, then remove from graph
		}




		nodeList.remove(vertex);// remove vertex form nodelist
		return true;
		*/

		int index = nodeList.indexOf(vertex);
		while(adjList[index]==null)
		{
			index++;
		}
		LinkedList<GraphEdge> edges = (LinkedList<GraphEdge>) this.getIncidentEdges(vertex);
		for(int i = 0 ; i< edges.size();i++)
		{
			this.removeEdge(edges.get(i));
		}
		/*
		for(int i = 0;i < adjList.length;i++)
		{// go thru list of dests for all nodes
			if(adjList[i]==null)
			{
				continue;
			}
			int size;
			for(int j =0 ;j < adjList[i].size();j++)// go thru list of dests
			{

				if(adjList[i].get(j)==null)
				{
					break;
				}

				Destination dest = adjList[i].get(j); // examine first dest pair
				if(dest.node.equals(vertex))// if dest pair is leading to removed vertex
				{

					//cost = dest.node.getCost();//update cost of associated node to be one less than curr
					//dest.node.setCost(cost-1);
					adjList[i].remove(dest);// remove dest pair


				}
			}
		}

		 */
		nodeList.remove(vertex);// remove vertex form nodelist
		//LinkedList<Destination> destinations = new LinkedList<>();
		adjList[index]= null;// set adjlist entry to null

		return true;
		//O(n+e) where e is the number of edges in the graph and n is the number
		//of nodes in the graph (NOT the max number of nodes in the graph)
		
		//note: pay close attention to the project description about how
		//the adjacency list works for nodes that have no edges vs. nodes
		//that aren't in the graph


	}
	
	//********************************************************************************
	//   testing code goes here... edit this as much as you want!
	//********************************************************************************
	
	/**
	 *  {@inheritDoc}
	 */
	public String toString() {
		return super.toString();
	}

	/**
	 * main method to test all threetengraph construcotrs and methods with.
	 * @param args args to run main with
	 */
	public static void main(String[] args) {
		//create a set of nodes and edges to test with
		GraphNode[] nodes = {
			new GraphNode(0), 
			new GraphNode(1), 
			new GraphNode(2), 
			new GraphNode(3), 
			new GraphNode(4), 
			new GraphNode(5), 
			new GraphNode(6), 
			new GraphNode(7), 
			new GraphNode(8), 
			new GraphNode(9)
		};
		
		GraphEdge[] edges = {
			new GraphEdge(0), 
			new GraphEdge(1), 
			new GraphEdge(2),
			new GraphEdge(3), 
			new GraphEdge(4), 
			new GraphEdge(5),
			new GraphEdge(6),new GraphEdge(7),new GraphEdge(8),new GraphEdge(9),
			new GraphEdge(10)
		};
		ThreeTenGraph graph = new ThreeTenGraph();

		for(int i = 0;i<=7;i++)
		{
			graph.addVertex(nodes[i]);

		}
		graph.addEdge(edges[0],nodes[7],nodes[0]);
		graph.addEdge(edges[1],nodes[0],nodes[2]);
		graph.addEdge(edges[2],nodes[1],nodes[3]);
		graph.addEdge(edges[3],nodes[6],nodes[3]);
		graph.addEdge(edges[4],nodes[3],nodes[5]);
		graph.addEdge(edges[5],nodes[4],nodes[3]);
		graph.addEdge(edges[6],nodes[7],nodes[4]);
		graph.addEdge(edges[7],nodes[4],nodes[1]);
		graph.addEdge(edges[8],nodes[6],nodes[2]);
		graph.addEdge(edges[9],nodes[6],nodes[1]);
		graph.addEdge(edges[10],nodes[5],nodes[6]);

		LinkedList<GraphEdge> edges1 = new LinkedList<>();
		edges1.add(edges[2]);
		edges1.add(edges[1]);


		System.out.println(graph.removeVertex(nodes[3]));
		System.out.println(graph.getEdges());
		System.out.println(graph.getVertexCount());
		System.out.println(graph.getVertices());
		/*
		//constructs a graph

		for(GraphNode n : nodes) {
			graph.addVertex(n);
		}
		
		graph.addEdge(edges[0],nodes[0],nodes[1]);
		graph.addEdge(edges[1],nodes[1],nodes[2]);
		graph.addEdge(edges[2],nodes[3],nodes[2]);
		graph.addEdge(edges[3],nodes[6],nodes[7]);
		graph.addEdge(edges[4],nodes[8],nodes[9]);
		graph.addEdge(edges[5],nodes[9],nodes[0]);

		if(graph.getVertexCount() == 10 && graph.getEdgeCount() == 6) {
			System.out.println("Yay 1");
		}
		if(graph.containsVertex(new GraphNode(0)) && graph.containsEdge(new GraphEdge(3))) {
			System.out.println("Yay 2");
		}
		 */
		for(int i =0;i < nodes.length;i++)
		{
			if(graph.containsVertex(nodes[i]))
			{
				System.out.println(i+"nodes");
			}
		}

		System.out.println(graph.getIncidentEdges(nodes[2]));
		System.out.println(graph.getEdges());
		System.out.println(graph.getEdgeCount());
		System.out.println(graph.getEndpoints(edges[6]));
		for(int i =0;i < edges.length;i++)
		{
			if(graph.containsEdge(edges[i]))
			{
				System.out.println(i+"edges");
			}
		}







		//lot more testing here...
		
		//If your graph "looks funny" you probably want to check:
		//getVertexCount(), getVertices(), getInEdges(vertex),
		//and getIncidentVertices(incomingEdge) first. These are
		//used by the layout class.


	}

    //YOU MAY , BUT DONT NEED TO EDIT THINGS IN THIS SECTION.

    /**
    * Returns true if v1 and v2 share an incident edge.
    * Equivalent to getNeighbors(v1).contains(v2).
    *
    * @param v1 the first vertex to test
    * @param v2 the second vertex to test
    * @return true if v1 and v2 share an incident edge
    */
    public boolean isNeighbor(GraphNode v1, GraphNode v2) {
		return (findEdge(v1, v2) != null);
	}
    
    /**
     * Returns true if this graph's edge collection contains edge.
     * Equivalent to getEdges().contains(edge).
     * @param edge the edge whose presence is being queried
     * @return true iff this graph contains an edge edge
     */
    public boolean containsEdge(GraphEdge edge) {
		return (getEndpoints(edge) != null);
	}
    
    /**
    * Returns the collection of edges in this graph which are of type edge_type.
    * @param edgeType the type of edges to be returned
    * @return the collection of edges which are of type edge_type, or null if the graph does not accept edges of this type
    * @see EdgeType
    */
    public Collection<GraphEdge> getEdges(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdges();
		}
		return null;
	}

    /**
     * Returns the number of edges of type edge_type in this graph.
     * @param edgeType the type of edge for which the count is to be returned
     * @return the number of edges of type edge_type in this graph
     */
    public int getEdgeCount(EdgeType edgeType) {
		if(edgeType == EdgeType.UNDIRECTED) {
			return getEdgeCount();
		}
		return 0;
	}
    
    /**
     * Returns the number of edges incident to vertex.  
     * Special cases of interest:
     * <ul>
     * <li/> Incident self-loops are counted once.
     * <li> If there is only one edge that connects this vertex to
     * each of its neighbors (and vice versa), then the value returned 
     * will also be equal to the number of neighbors that this vertex has
     * (that is, the output of getNeighborCount).
     * <li> If the graph is directed, then the value returned will be 
     * the sum of this vertex's indegree (the number of edges whose 
     * destination is this vertex) and its outdegree (the number
     * of edges whose source is this vertex), minus the number of
     * incident self-loops (to avoid double-counting).
     * </ul>
     * 
     * @param vertex the vertex whose degree is to be returned
     * @return the degree of this node
     */
    public int degree(GraphNode vertex) {
		return getNeighborCount(vertex);
	}
	
    /**
     * Returns a Collection view of the predecessors of vertex 
     * in this graph.  A predecessor of vertex is defined as a vertex v 
     * which is connected to 
     * vertex by an edge e, where e is an outgoing edge of 
     * v and an incoming edge of vertex.
     * @param vertex    the vertex whose predecessors are to be returned
     * @return  a Collection view of the predecessors of vertex in this graph
     */
    public Collection<GraphNode> getPredecessors(GraphNode vertex) {
		return getNeighbors(vertex);
	}
    
    /**
     * Returns a Collection view of the successors of vertex 
     * in this graph.  A successor of vertex is defined as a vertex v 
     * which is connected to 
     * vertex by an edge e, where e is an incoming edge of 
     * v and an outgoing edge of vertex.
     * @param vertex    the vertex whose predecessors are to be returned
     * @return  a Collection view of the successors of vertex in this graph
     */
    public Collection<GraphNode> getSuccessors(GraphNode vertex) {
		return getNeighbors(vertex);
	}
    
    /**
     * Returns true if v1 is a predecessor of v2 in this graph.
     * Equivalent to v1.getPredecessors().contains(v2).
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return true if v1 is a predecessor of v2, and false otherwise.
     */
    public boolean isPredecessor(GraphNode v1, GraphNode v2) {
		return isNeighbor(v1, v2);
	}
    
    /**
     * Returns true if v1 is a successor of v2 in this graph.
     * Equivalent to v1.getSuccessors().contains(v2).
     * @param v1 the first vertex to be queried
     * @param v2 the second vertex to be queried
     * @return true if v1 is a successor of v2, and false otherwise.
     */
    public boolean isSuccessor(GraphNode v1, GraphNode v2) {
		return isNeighbor(v1, v2);
	}
    
    /**
     * If directed_edge is a directed edge in this graph, returns the source; 
     * otherwise returns null. 
     * The source of a directed edge d is defined to be the vertex for which  
     * d is an outgoing edge.
     * directed_edge is guaranteed to be a directed edge if 
     * its EdgeType is DIRECTED. 
     * @param directedEdge the directed edge to get the source for
     * @return  the source of directed_edge if it is a directed edge in this graph, or null otherwise
     */
    public GraphNode getSource(GraphEdge directedEdge) {
		return null;
	}

    /**
     * If directed_edge is a directed edge in this graph, returns the destination; 
     * otherwise returns null. 
     * The destination of a directed edge d is defined to be the vertex 
     * incident to d for which  
     * d is an incoming edge.
     * directed_edge is guaranteed to be a directed edge if 
     * its EdgeType is DIRECTED. 
     * @param directedEdge the directed edge to get the destination for
     * @return  the destination of directed_edge if it is a directed edge in this graph, or null otherwise
     */
    public GraphNode getDest(GraphEdge directedEdge) {
		return null;
	}
	
    /**
     * Returns a Collection view of the incoming edges incident to vertex
     * in this graph.
     * @param vertex    the vertex whose incoming edges are to be returned
     * @return  a Collection view of the incoming edges incident to vertex in this graph
     */
    public Collection<GraphEdge> getInEdges(GraphNode vertex) {
		return getIncidentEdges(vertex);
	}
    
    /**
     * Returns the collection of vertices in this graph which are connected to edge.
     * Note that for some graph types there are guarantees about the size of this collection
     * (i.e., some graphs contain edges that have exactly two endpoints, which may or may 
     * not be distinct).  Implementations for those graph types may provide alternate methods 
     * that provide more convenient access to the vertices.
     * 
     * @param edge the edge whose incident vertices are to be returned
     * @return  the collection of vertices which are connected to edge, or null if edge is not present
     */
    public Collection<GraphNode> getIncidentVertices(GraphEdge edge) {
		
		Pair<GraphNode> p = getEndpoints(edge);
		if(p == null) return null;
		
		LinkedList<GraphNode> ret = new LinkedList<>();
		ret.add(p.getFirst());
		ret.add(p.getSecond());
		return ret;
	}
    
    /**
     * Returns a Collection view of the outgoing edges incident to vertex
     * in this graph.
     * @param vertex    the vertex whose outgoing edges are to be returned
     * @return  a Collection view of the outgoing edges incident to vertex in this graph
     *
     */
    public Collection<GraphEdge> getOutEdges(GraphNode vertex) {
		return getIncidentEdges(vertex);
	}
    
    /**
     * Returns the number of incoming edges incident to vertex.
     * Equivalent to getInEdges(vertex).size().
     * @param vertex    the vertex whose indegree is to be calculated
     * @return  the number of incoming edges incident to vertex
     */
    public int inDegree(GraphNode vertex) {
		return degree(vertex);
	}
    
    /**
     * Returns the number of outgoing edges incident to vertex.
     * Equivalent to getOutEdges(vertex).size().
     * @param vertex    the vertex whose outdegree is to be calculated
     * @return  the number of outgoing edges incident to vertex
     */
    public int outDegree(GraphNode vertex) {
		return degree(vertex);
	}

    /**
     * Returns the number of predecessors that vertex has in this graph.
     * Equivalent to vertex.getPredecessors().size().
     * @param vertex the vertex whose predecessor count is to be returned
     * @return  the number of predecessors that vertex has in this graph
     */
    public int getPredecessorCount(GraphNode vertex) {
		return degree(vertex);
	}
    
    /**
     * Returns the number of successors that vertex has in this graph.
     * Equivalent to vertex.getSuccessors().size().
     * @param vertex the vertex whose successor count is to be returned
     * @return  the number of successors that vertex has in this graph
     */
    public int getSuccessorCount(GraphNode vertex) {
		return degree(vertex);
	}
    
    /**
     * Returns the vertex at the other end of edge from vertex.
     * (That is, returns the vertex incident to edge which is not vertex.)
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return the vertex at the other end of edge from vertex
     */
    public GraphNode getOpposite(GraphNode vertex, GraphEdge edge) {
		Pair<GraphNode> p = getEndpoints(edge);
		if(p.getFirst().equals(vertex)) {
			return p.getSecond();
		}
		else {
			return p.getFirst();
		}
	}
    
    /**
    * Returns all edges that connects v1 to v2.
    * If this edge is not uniquely
    * defined (that is, if the graph contains more than one edge connecting
    * v1 to v2), any of these edges
    * may be returned.  findEdgeSet(v1, v2) may be
    * used to return all such edges.
    * Returns null if v1 is not connected to v2.
    * <br/>Returns an empty collection if either v1 or v2 are not present in this graph.
    *
    * <p><b>Note</b>: for purposes of this method, v1 is only considered to be connected to
    * v2 via a given <i>directed</i> edge d if
    * v1 == d.getSource() && v2 == d.getDest() evaluates to true.
    * (v1 and v2 are connected by an undirected edge u if
    * u is incident to both v1 and v2.)
    * @param v1 first node the edge is connected to
	* @param v2 the second node to be connected to edge
    * @return  a collection containing all edges that connect v1 to v2,or null if either vertex is not present
    */
    public Collection<GraphEdge> findEdgeSet(GraphNode v1, GraphNode v2) {
		GraphEdge edge = findEdge(v1, v2);
		if(edge == null) {
			return null;
		}
		
		LinkedList<GraphEdge> ret = new LinkedList<>();
		ret.add(edge);
		return ret;
		
	}
	
    /**
     * Returns true if vertex is the source of edge.
     * Equivalent to getSource(edge).equals(vertex).
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return true iff vertex is the source of edge
     */
    public boolean isSource(GraphNode vertex, GraphEdge edge) {
		return getSource(edge).equals(vertex);
	}
    
    /**
     * Returns true if vertex is the destination of edge.
     * Equivalent to getDest(edge).equals(vertex).
     * @param vertex the vertex to be queried
     * @param edge the edge to be queried
     * @return true iff vertex is the destination of edge
     */
    public boolean isDest(GraphNode vertex, GraphEdge edge) {
		return getDest(edge).equals(vertex);
	}
    
    /**
     * Adds edge e to this graph such that it connects 
     * vertex v1 to v2.
     * If this graph does not contain v1, v2, 
     * or both, implementations may choose to either silently add 
     * the vertices to the graph or throw an IllegalArgumentException.
     * If edgeType is not legal for this graph, this method will
     * throw IllegalArgumentException.
     * See Hypergraph.addEdge() for a listing of possible reasons
     * for failure.
     * @param e the edge to be added
     * @param v1 the first vertex to be connected
     * @param v2 the second vertex to be connected
     * @param edgeType the type to be assigned to the edge
     * @return true if the add is successful, false otherwise
     */
    public boolean addEdge(GraphEdge e, GraphNode v1, GraphNode v2, EdgeType edgeType) {
		//NOTE: Only directed edges allowed
		
		if(edgeType == EdgeType.DIRECTED) {
			throw new IllegalArgumentException();
		}
		
		return addEdge(e, v1, v2);
	}

 /**
 * Adds edge to this graph.
 * Fails under the following circumstances:
 * <ul>
 * <li/>edge is already an element of the graph
 * <li/>either edge or vertices is null
 * <li/>vertices has the wrong number of vertices for the graph type
 * <li/>vertices are already connected by another edge in this graph,
 * and this graph does not accept parallel edges
 * </ul>
 *
 * @param edge the edge to be added into the graph
 * @param vertices vertices for the edge to be connected to
 * @return true if the add is successful, and false otherwise
 * @throws IllegalArgumentException if edge or vertices is null,
 */
	@SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1]);
	}
	/**
     * Adds edge to this graph with type edge_type.
     * Fails under the following circumstances:
     * <ul>
     * <li/>edge is already an element of the graph 
     * <li/>either edge or vertices is null
     * <li/>vertices has the wrong number of vertices for the graph type
     * <li/>vertices are already connected by another edge in this graph,
     * and this graph does not accept parallel edges
     * <li/>edge_type is not legal for this graph
     * </ul>
     * 
     * @param edge the edge connecting the vertices
     * @param vertices the vertices in the graph
	 * @param edgeType the type of edge being used
     * @return true if the add is successful, and false otherwise
     * @throws IllegalArgumentException if edge or vertices is null,
     */
	@SuppressWarnings("unchecked")
    public boolean addEdge(GraphEdge edge, Collection<? extends GraphNode> vertices, EdgeType edgeType) {
		if(edge == null || vertices == null || vertices.size() != 2) {
			return false;
		}
		
		GraphNode[] vs = (GraphNode[])vertices.toArray();
		return addEdge(edge, vs[0], vs[1], edgeType);
	}
	
	//********************************************************************************
	//   DO NOT EDIT ANYTHING BELOW THIS LINE
	//********************************************************************************
	
	/**
     * Returns a {@code Factory} that creates an instance of this graph type.
     * @param <GraphNode> the vertex type for the graph factory
     * @param <GraphEdge> the edge type for the graph factory
	 * @return returns a grpah factory with selected types
     */
	//the cs310code checker is giving me an error for this methid, saying that GraphNode and GraphEdge arent valid names
	//becuase they arent satsifying the variable naming conventions
	public static <GraphNode,GraphEdge> Factory<UndirectedGraph<GraphNode,GraphEdge>> getFactory() { 
		return new Factory<UndirectedGraph<GraphNode,GraphEdge>> () {
			@SuppressWarnings("unchecked")
			public UndirectedGraph<GraphNode,GraphEdge> create() {
				return (UndirectedGraph<GraphNode,GraphEdge>) new ThreeTenGraph();
			}
		};
	}
    
    /**
     * Returns the edge type of edge in this graph.
     * @param edge gets the type of edge in the graph
     * @return the EdgeType of edge, or null if edge has no defined type
     */
    public EdgeType getEdgeType(GraphEdge edge) {
		return EdgeType.UNDIRECTED;
	}
    
    /**
     * Returns the default edge type for this graph.
     * 
     * @return the default edge type for this graph
     */
    public EdgeType getDefaultEdgeType() {
		return EdgeType.UNDIRECTED;
	}
    
    /**
     * Returns the number of vertices that are incident to edge.
     * For hyperedges, this can be any nonnegative integer; for edges this
     * must be 2 (or 1 if self-loops are permitted). 
     * 
     * <p>Equivalent to getIncidentVertices(edge).size().
     * @param edge the edge whose incident vertex count is to be returned
     * @return the number of vertices that are incident to edge.
     */
    public int getIncidentCount(GraphEdge edge) {
		return 2;
	}
}
