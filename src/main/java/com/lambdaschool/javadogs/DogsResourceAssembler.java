package com.lambdaschool.javadogs;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

// This is helper code that will help include data and the links the data is from.
// Resource points to the object class we are going to be working with (e.g. Dogs).
// Assembler is part of Hateoas library which helps combine the data and links into a nice looking format.

@Component // Makes this class manageable by the spring network
public class DogsResourceAssembler implements ResourceAssembler<Dogs, Resource<Dogs>> {
    @Override
    public Resource<Dogs> toResource(Dogs dog) {
        return new Resource<Dogs>(dog,
                // linkTo(methodOn(DogsController.class).findOne(dog.getId())).withSelfRel(),
                linkTo(methodOn(DogsController.class).orderByBreeds()).withRel("dogs/breeds"),
                linkTo(methodOn(DogsController.class).getBreed(dog.getBreed())).withSelfRel(),
                linkTo(methodOn(DogsController.class).isSuitable(dog.isSuitable())).withSelfRel());
    }
}
