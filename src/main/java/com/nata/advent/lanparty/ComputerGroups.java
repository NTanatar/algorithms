package com.nata.advent.lanparty;

import static java.util.stream.Collectors.joining;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public class ComputerGroups {

    Set<String> triples = new HashSet<>();

    Map<String, Set<String>> connections = new HashMap<>();

    public void addConnection(String name1, String name2) {
        System.out.println("------ " + name1 + " " + name2);
        System.out.println("1: " + getConnectionsOf(name1));
        System.out.println("2: " + getConnectionsOf(name2));
        getConnectionsOf(name1).forEach(c -> addTriple(name1, c, name2));
        getConnectionsOf(name2).forEach(c -> addTriple(name2, c, name1));
        getConnectionsOf(name1).add(name2);
        getConnectionsOf(name2).add(name1);
    }

    private Set<String> getConnectionsOf(String name) {
        if (!connections.containsKey(name)) {
            connections.put(name, new HashSet<>());
        }
        return connections.get(name);
    }

    private void addTriple(String name1, String name2, String name3) {
        String code = Stream.of(name1, name2, name3).sorted()
            .collect(joining("-"));
        triples.add(code);
        System.out.println("---------- > " + code);
    }
}
