import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	
	// 간선
	private static class Edge {
		int to, capacity, flow, cost;
		Edge reverse;
		
		Edge(int to, int capacity, int cost) {
			this.to = to;
			this.capacity = capacity;
			this.cost = cost;
			this.flow = 0;
		}
	}
	
	// 페어
	private static class Pair implements Comparable<Pair> {
		int index, value;
		
		Pair(int index) {
			this.index = index;
			this.value = distance[index];
		}

		@Override
		public int compareTo(Pair other) {
			return this.value - other.value;
		}
	}
	
	private static final int INF = Integer.MAX_VALUE;
	private static int[] prev, distance;
	private static Edge[] path;
	private static ArrayList<ArrayList<Edge>> adj;
	
	// 간선 추가
	private static void addEdge(int from, int to, int capacity, int cost) {
		Edge forward, backward;
		forward = new Edge(to, capacity, cost);
		backward = new Edge(from, 0, -cost);
		forward.reverse = backward;
		backward.reverse = forward;
		adj.get(from).add(forward);
		adj.get(to).add(backward);
	}
	
	// 다익스트라
	private static boolean dijkstra(int source, int sink) {
		int curr;
		PriorityQueue<Pair> queue = new PriorityQueue<>();
		Arrays.fill(distance, INF);
		distance[source] = 0;
		queue.add(new Pair(source));
		while (!queue.isEmpty()) {
			curr = queue.poll().index;
			if (curr == sink) {
				return true;
			}
			for (Edge edge : adj.get(curr)) {
				if (edge.capacity - edge.flow > 0 && distance[curr] + edge.cost < distance[edge.to]) {
					distance[edge.to] = distance[curr] + edge.cost;
					prev[edge.to] = curr;
					path[edge.to] = edge;
					queue.add(new Pair(edge.to));
				}
			}
		}
		return false;
	}

	
	// MCMF
	private static int[] mcmf(int source, int sink) {
		int totalFlow = 0, totalCost = 0, minFlow, i;
		while (dijkstra(source, sink)) {
			minFlow = INF;
			for (i = sink; i != source; i = prev[i]) {
				minFlow = Math.min(minFlow, path[i].capacity - path[i].flow);
			}
			for (i = sink; i != source; i = prev[i]) {
				path[i].flow += minFlow;
				path[i].reverse.flow -= minFlow;
			}
			totalFlow += minFlow;
			totalCost += minFlow * distance[sink];
		}
		return new int[] {totalFlow, totalCost};
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int n, m, source, sink, cost, i, j;
		int[] ans;
		int[][] capacity;
		
		// Source, sink
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		source = 0;
		sink = n + m + 1;
		
		// 간선 추가
		adj = new ArrayList<>();
		for (i = 0; i <= sink; i++) {
			adj.add(new ArrayList<>());
		}
		st = new StringTokenizer(br.readLine());
		for (i = 1; i <= n; i++) {
			addEdge(0, i, Integer.parseInt(st.nextToken()), 0);
		}
		st = new StringTokenizer(br.readLine());
		for (i = n + 1; i <= n + m; i++) {
			addEdge(i, sink, Integer.parseInt(st.nextToken()), 0);
		}
		capacity = new int[n + 1][m + 1];
		for (i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			for (j = 1; j <= n; j++) {
				capacity[j][i] = Integer.parseInt(st.nextToken());
			}
		}
		for (i = 1; i <= m; i++) {
			st = new StringTokenizer(br.readLine());
			for (j = 1; j <= n; j++) {
				cost = Integer.parseInt(st.nextToken());
				if (capacity[j][i] > 0) {
					addEdge(j, n + i, capacity[j][i], cost);
				}
			}
		}
		
		// 출력
		prev = new int[sink + 1];
		path = new Edge[sink + 1];
		distance = new int[sink + 1];
		ans = mcmf(source, sink);
		sb.append(ans[0]).append("\n").append(ans[1]);

		bw.write(sb.toString());
		bw.flush();
		bw.close();
	}

}