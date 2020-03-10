import java.util.List;
import java.util.LinkedList;

class Graph {
	private Node[] nodes;
	protected int[] components;
	public int bComponent=1;

	public Graph(Node[] nodes) {
		this.nodes = nodes;
	}


	public void printNeighbors() {
		for (Node n1 : nodes) {
			String s = n1.toString() + ": ";
			for (Node n2 : n1.getNeighbors()) {
				s += n2.toString() + " ";
			}
			System.out.println(s.substring(0, s.length() - 1));
		}
	}

	private static Graph buildExampleGraph() {
	    // ukeoppgave
		Node[] nodes = new Node[7];
		for (int i = 0; i < 7; i++) {
			nodes[i] = new Node(i);
		}
		nodes[0].addNeighbor(nodes[1]);
		nodes[0].addNeighbor(nodes[2]);
		nodes[1].addNeighbor(nodes[2]);
		nodes[2].addNeighbor(nodes[3]);
		nodes[2].addNeighbor(nodes[5]);
		nodes[3].addNeighbor(nodes[4]);
		nodes[4].addNeighbor(nodes[5]);
		nodes[5].addNeighbor(nodes[6]);
		return new Graph(nodes);
	}

	private static Graph buildRandomSparseGraph(int numberofV, long seed) {
		// seed brukes av java.util.Random for å generere samme sekvens for samme frø
		// (seed) og numberofV
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	private static Graph buildRandomDenseGraph(int numberofV, long seed) {
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < numberofV * numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addNeighbor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	private static Graph buildRandomDirGraph(int numberofV, long seed) {
		java.util.Random tilf = new java.util.Random(seed);
		int tilfeldig1 = 0, tilfeldig2 = 0;
		Node[] nodes = new Node[numberofV];

		for (int i = 0; i < numberofV; i++) {
			nodes[i] = new Node(i);
		}

		for (int i = 0; i < 2 * numberofV; i++) {
			tilfeldig1 = tilf.nextInt(numberofV);
			tilfeldig2 = tilf.nextInt(numberofV);
			if (tilfeldig1 != tilfeldig2)
				nodes[tilfeldig1].addSuccessor(nodes[tilfeldig2]);
		}
		return new Graph(nodes);
	}

	public void DFS(Node s,int nrOfComponet,int nrOfNodes) {
		s.visit();
		for (Node n : s.getNeighbors() ) {
			if (!n.isVisited()) {
				nrOfNodes++;
				DFS(n,nrOfComponet,nrOfNodes);
			}
		}
		components[nrOfComponet]=nrOfNodes;
	}

	public int DFSFull() {
		int komponent =0;
		for (Node s : nodes) {
			if (!s.isVisited()) {
			  DFS(s,komponent,1);
				komponent++;
			}
		}
		return komponent;
	}

  public int numberOfComponents() {
	    int nrComponents = 0;
			nrComponents = DFSFull();
			components = new int[nrComponents];
 		return nrComponents; // for at prekoden skal kompilere
	}

  public Graph transformDirToUndir() {
	    // Oppgave 1B
			int count=0;
			Node[] urettaGraph = new Node[nodes.length];
			for (Node node : nodes) {
				for (Node utgrad : node.getNeighbors()) {
					utgrad.addNeighbor(utgrad);
				}
				urettaGraph[count]=node;
				count++;
			}
		return new Graph(urettaGraph); // returner en NY graf
	}

  public boolean isConnected(){
		boolean weakConnected = true;
		Graph urettetGraph = this.transformDirToUndir();
		if (urettetGraph.numberOfComponents() > 1) {
			weakConnected = false;
		}
 		return weakConnected; // for at prekoden skal kompilere
	}

  public Graph biggestComponent() {
		int biggestComponent = components[0];
		for (int i=1;i<components.length;i++ ) {
			if (biggestComponent<components[i]) {
				biggestComponent = components[i];
			}
		}
	 	return biggestComponent; // for at prekoden skal kompilere
	}

	public int[][] buildAdjacencyMatrix() {
		// Oppgave 1E
	    return null; // for at koden skal kompilere
	}

	public static void main(String[] args) {
		Graph graph = buildExampleGraph();
		System.out.println(graph.numberOfComponents());
		Graph urettetGraph = graph.transformDirToUndir();
		System.out.println(graph.isConnected());
		System.out.println("Urettet Graph");
		urettetGraph.printNeighbors();
		System.out.println("Directed Graph");
		graph.printNeighbors();


		//graph = buildRandomSparseGraph(11, 201909202359L);
		//graph.printNeighbors();
		//System.out.println("");
		//graph = buildRandomDenseGraph(15, 201909202359L);*/
		//graph.printNeighbors();
	}
}
