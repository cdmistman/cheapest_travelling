package Solution;

import java.io.*;
import java.text.*;
import java.math.*;
import java.util.Scanner;
import java.util.Iterator;

public class Solution {
	
	/* Enter your code here. Read input from STDIN. Print output to STDOUT. The classname 'Solution' is written above in the head. */
	/* DO NOT import and/or use any addtional libraries. */
	
	
	public static void main(String[] args) throws Exception {
		Scanner scan = new Scanner(System.in);
		
		// create nodes given the number of cities
		int nn = scan.nextInt();
		Node[] cities = new Node[nn];
		for (int ii = 0; ii < nn; ++ii) cities[ii] = new Node();
		
		// input edges into graph given them as inputs
		int mm = scan.nextInt();
		for (int ii = 0; ii < mm; ++ii) {
			Edge ee = new Edge();
			ee.from = cities[scan.nextInt() - 1];
			ee.to = cities[scan.nextInt() - 1];
			ee.cost = scan.nextInt();
			ee.from.out.add(ee);
			ee.to.in.add(ee);
		}
		
		// get source and destination for Alice and Bob
		int src = scan.nextInt() - 1;
		int dst = scan.nextInt() - 1;
		
		// run Dijkstra on the graph
		dijkstra(cities, src);
		
		// print the value at the destination city
		System.out.println(cities[dst].value);
	}
	
	private static void dijkstra(Node[] nodes, int src) {
		NodeQueue qq = new NodeQueue();
		
		Node curr = nodes[src];
		curr.value = 0;
		qq.enqueue(curr);
		while (qq.bot != null) {
			curr = qq.dequeue();
			curr.isVisited = true;
			EdgeList out = curr.out;
			for (int ii = 0; ii < out.size; ++ii) {
				Node dst = out.get(ii).to;
				if (dst.value == -1) dst.value = curr.value + out.get(ii).cost;
				else dst.value = Math.min(dst.value, curr.value + out.get(ii).cost);
				if (!dst.isVisited) {
					qq.enqueue(dst);
				}
			}
		}
	}
	
	/* Remember to include the following '}' to complete the definition of 'Solution'. */
}

class NodeQueue {
	NodeItem bot;
	NodeItem top;
	
	public NodeQueue() {
		this.bot = null;
		this.top = null;
	}
	
	public void enqueue(Node nn) {
		NodeItem toAdd = new NodeItem(nn);
		
		if (bot == null) {
			bot = toAdd;
			top = toAdd;
		} else {
			top.next = toAdd;
			top = toAdd;
		}
	}
	
	public Node dequeue() {
		if (bot == null) return null;
		
		Node res = bot.curr;
		bot = bot.next;
		return res;
	}
	
	private class NodeItem {
		Node curr;
		NodeItem next;
		
		NodeItem(Node nn) {
			this.curr = nn;
			this.next = null;
		}
	}
}

class Node {
	public EdgeList in, out;
	boolean isVisited;
	int value;
	
	public Node() {
		this.in = new EdgeList();
		this.out = new EdgeList();
		this.isVisited = false;
		this.value = -1;
	}
}

class Edge {
	Node from, to;
	int cost;
}

class EdgeList {
	Edge[] edges;
	public int size;
	
	public EdgeList() {
		this.size = 0;
		this.edges = new Edge[4];
	}
	
	private void ensureCapacity() {
		if (this.size == this.edges.length) {
			Edge[] tmp = new Edge[this.edges.length * 2];
			for (int ii = 0; ii < this.size; ++ii) {
				tmp[ii] = this.edges[ii];
			}
			this.edges = tmp;
		}
	}
	
	public void add(Edge ee) {
		this.add(ee, this.size);
	}
	
	public void add(Edge ee, int index) {
		this.ensureCapacity();
		for (int ii = index; ii < this.size; ++ii) {
			this.edges[ii + 1] = this.edges[ii];
		}
		this.edges[index] = ee;
		++this.size;
	}
	
	public Edge get(int ii) { return edges[ii]; }
	
	public void remove(int index) {
		for (int ii = index; ii < this.size - 1; ++ii) {
			this.edges[ii] = this.edges[ii + 1];
		}
		--this.size;
	}
}

/* You can also define other classes here. */

