package com.nata.advent.lanparty;

import static java.util.stream.Collectors.toSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ComputerGroups {

    private final Map<String, Integer> groupIdByComputerName = new HashMap<>();
    private final Map<Integer, Group> groupById = new HashMap<>();

    private int nextGroupId = 1;

    public void addConnection(String name1, String name2) {
        boolean firstGroupExists = groupIdByComputerName.containsKey(name1);
        boolean secondGroupExists = groupIdByComputerName.containsKey(name2);

        if (firstGroupExists && !secondGroupExists) {
            int groupId = groupIdByComputerName.get(name1);
            groupById.get(groupId).getNames().add(name2);
            groupIdByComputerName.put(name2, groupId);

        } else if (secondGroupExists && !firstGroupExists) {
            int groupId = groupIdByComputerName.get(name2);
            groupById.get(groupId).getNames().add(name1);
            groupIdByComputerName.put(name1, groupId);

        } else if (firstGroupExists && secondGroupExists) {
            int first = groupIdByComputerName.get(name1);
            int second = groupIdByComputerName.get(name2);
            if (first != second) {
                groupById.get(second).getNames().forEach(name -> groupIdByComputerName.put(name, first));
                groupById.get(first).getNames().addAll(groupById.get(second).getNames());
                groupById.remove(second);
            }
        } else {
            int id = nextGroupId++;
            Group g = new Group(id, name1, name2);
            groupById.put(id, g);
            groupIdByComputerName.put(name1, id);
            groupIdByComputerName.put(name2, id);
        }
    }

    public Set<Group> getGroups(int minSize) {
        return groupById.values().stream()
            .filter(group -> group.getNames().size() >= minSize)
            .collect(toSet());
    }
}
