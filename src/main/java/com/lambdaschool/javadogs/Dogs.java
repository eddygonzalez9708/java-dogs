package com.lambdaschool.javadogs;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data // Creates getters, setters and toString
@Entity // Gets object ready for JPA storage

public class Dogs {
    private @Id @GeneratedValue Long id; // primary key automatically populated
    private String breed;
    private int weight;
    private boolean suitable;

    // needed for JPA
    public Dogs() {
        // default constructor
    }

    public Dogs(String breed, int weight, boolean suitable) {
        this.breed = breed;
        this.weight = weight;
        this.suitable = suitable;
    }
}
