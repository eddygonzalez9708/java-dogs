package com.lambdaschool.javadogs;

// Use to add your own runtime exception messages

public class DogNotFoundException extends RuntimeException {
    public DogNotFoundException(Long id) {
        super ("Could not find dog " + id);
    }
}
