package uk.co.pete_b.advent.aoc2023;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.BiconnectivityInspector;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

public class Day25 {
    public static int findProductOfSeparateGroups(final List<String> wiringDiagram, boolean bruteForce) {
        final Set<Set<String>> connectionsSet = new HashSet<>();
        final Set<String> nodeList = new HashSet<>();

        for (String wiringLine : wiringDiagram) {
            final String[] parts = wiringLine.split(": ");
            final String partName = parts[0];
            final String[] connectedParts = parts[1].split(" ");
            nodeList.add(partName);

            for (String connectedPart : connectedParts) {
                nodeList.add(connectedPart);
                connectionsSet.add(new HashSet<>(Arrays.asList(partName, connectedPart)));
            }
        }

        final List<String> nodeNames = new ArrayList<>(nodeList);
        final List<List<String>> connections = connectionsSet.stream().map(x -> (List<String>) new ArrayList<>(x)).toList();
        final Graph<String, DefaultEdge> graph = buildGraph(connections);

        if (bruteForce) {
            return bruteForce(connections, graph);
        } else {
            // Traverse the graph 1000 times between two random nodes and log the edges we travel over
            final DijkstraShortestPath<String, DefaultEdge> dijkstraAlg = new DijkstraShortestPath<>(graph);

            final Map<DefaultEdge, Integer> commonEdges = new HashMap<>();

            for (int i = 0; i < 1000; i++) {
                final int startNodeIndex = ThreadLocalRandom.current().nextInt(0, nodeNames.size());
                final int endNodeIndex = ThreadLocalRandom.current().nextInt(0, nodeNames.size());
                if (startNodeIndex == endNodeIndex) {
                    // Try again if the start & end are the same
                    i--;
                    continue;
                }

                final String startNode = nodeNames.get(startNodeIndex);
                final String endNode = nodeNames.get(endNodeIndex);
                final ShortestPathAlgorithm.SingleSourcePaths<String, DefaultEdge> iPaths = dijkstraAlg.getPaths(startNode);
                final GraphPath<String, DefaultEdge> route = iPaths.getPath(endNode);

                for (DefaultEdge node : route.getEdgeList()) {
                    commonEdges.compute(node, (s, count) -> {
                        if (count == null) {
                            count = 0;
                        }
                        count++;

                        return count;
                    });
                }
            }

            // Sort the edges by how common they are
            final List<DefaultEdge> edges = commonEdges.entrySet().stream().sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue())).map(Map.Entry::getKey).toList();

            // Remove the three most common
            graph.removeEdge(edges.get(0));
            graph.removeEdge(edges.get(1));
            graph.removeEdge(edges.get(2));

            // The graph should now have two subgraphs as we've split it in two
            final BiconnectivityInspector<String, DefaultEdge> inspector = new BiconnectivityInspector<>(graph);
            final List<Graph<String, DefaultEdge>> subGraphs = new ArrayList<>(inspector.getConnectedComponents());

            return subGraphs.get(0).vertexSet().size() * subGraphs.get(1).vertexSet().size();
        }
    }

    private static int bruteForce(final List<List<String>> connections, final Graph<String, DefaultEdge> solutionGraph) {
        final List<List<String>> edgesToRemove = getAnswerViaBruteForce(connections);

        for (List<String> edge : edgesToRemove) {
            solutionGraph.removeEdge(edge.get(0), edge.get(1));
        }

        final BiconnectivityInspector<String, DefaultEdge> inspector = new BiconnectivityInspector<>(solutionGraph);
        final List<Graph<String, DefaultEdge>> subGraphs = new ArrayList<>(inspector.getConnectedComponents());
        return subGraphs.get(0).vertexSet().size() * subGraphs.get(1).vertexSet().size();
    }

    private static List<List<String>> getAnswerViaBruteForce(final List<List<String>> connections) {
        final IGenerator<List<Integer>> combination = Generator.combination(IntStream.range(0, connections.size()).boxed().toList()).simple(2);

        return combination.stream().parallel().map(connects -> {
            final Graph<String, DefaultEdge> graph = buildGraph(connections);

            final List<List<String>> removedEdges = new ArrayList<>();
            for (Integer connect : connects) {
                removedEdges.add(connections.get(connect));
                final String nodeOne = connections.get(connect).get(0);
                final String nodeTwo = connections.get(connect).get(1);
                graph.removeEdge(nodeOne, nodeTwo);
            }

            final BiconnectivityInspector<String, DefaultEdge> inspector = new BiconnectivityInspector<>(graph);
            if (!inspector.getCutpoints().isEmpty()) {
                final List<String> lastEdgeToRemove = new ArrayList<>(inspector.getCutpoints());
                removedEdges.add(lastEdgeToRemove);
                return removedEdges;
            }
            for (Integer connect : connects) {
                final String nodeOne = connections.get(connect).get(0);
                final String nodeTwo = connections.get(connect).get(1);

                graph.addEdge(nodeOne, nodeTwo);
            }

            return null;
        }).filter(Objects::nonNull).findFirst().orElseThrow();
    }

    private static Graph<String, DefaultEdge> buildGraph(final List<List<String>> connections) {
        final Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        for (List<String> connection : connections) {
            graph.addVertex(connection.get(0));
            graph.addVertex(connection.get(1));
            graph.addEdge(connection.get(0), connection.get(1));
        }

        return graph;
    }
}
