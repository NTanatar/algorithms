package com.nata.advent.lanparty;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class LanParty {

    Set<String> triples = new HashSet<>();

    Map<String, Set<String>> connections = new HashMap<>();

    public void addConnection(String name1, String name2) {
        getConnectionsOf(name1).add(name2);
        getConnectionsOf(name2).add(name1);

        Set<String> commonConnections = new HashSet<>(getConnectionsOf(name2));
        commonConnections.retainAll(getConnectionsOf(name1));
        commonConnections.forEach(c -> {
            if (filter(name1, name2, c)) {
                addTriple(name1, name2, c);
            }
        });
    }

    private Set<String> getConnectionsOf(String name) {
        if (!connections.containsKey(name)) {
            connections.put(name, new HashSet<>());
        }
        return connections.get(name);
    }

    private boolean filter(String name1, String name2, String name3) {
        return Stream.of(name1, name2, name3)
            .anyMatch(s -> s.startsWith("t"));
    }

    private void addTriple(String name1, String name2, String name3) {
        triples.add(Stream.of(name1, name2, name3).sorted()
            .collect(joining("-")));
    }
}
