package com.nata.advent.lanparty;

import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Group {
    private int id;
    private Set<String> names;

    public Group(int id, String name1, String name2) {
        this.id = id;
        this.names = new HashSet<>();
        names.add(name1);
        names.add(name2);
    }
}
