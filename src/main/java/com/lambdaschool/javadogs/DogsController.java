package com.lambdaschool.javadogs;

import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class DogsController {
    private final DogsRepository dogsrepos;
    private final DogsResourceAssembler assembler;

    public DogsController(DogsRepository dogsrepos, DogsResourceAssembler assembler) {
        this.dogsrepos = dogsrepos;
        this.assembler = assembler;
    }

    // Post -> Create
    // Get -> Read
    // Put -> Update / Replace
    // Patch -> Update / Modify for collections
    // Delete -> Destroy

    // RequestMapping(method = RequestMethod.GET)
    @GetMapping("/dogs/breeds")
    public Resources<Resource<Dogs>> orderByBreeds() {
        List<Resource<Dogs>> dogs = dogsrepos
                .findAll(new Sort(Sort.Direction.ASC, "breed"))
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).orderByBreeds()).withSelfRel());
    }

    @GetMapping("/dogs/breeds/{breed}")
    public Resources<Resource<Dogs>> getBreed(@PathVariable String breed) {
        List<Resource<Dogs>> dogs = dogsrepos
                .findByBreed(breed)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).getBreed(breed)).withSelfRel());
    }

    @GetMapping("/dogs/weight")
    public Resources<Resource<Dogs>> orderByWeight() {
        List<Resource<Dogs>> dogs = dogsrepos
                .findAll(new Sort(Sort.Direction.ASC, "weight", "breed"))
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).orderByWeight()).withSelfRel());
    }

    @GetMapping("/dogs/apartment/{suitable}")
    public Resources<Resource<Dogs>> isSuitable(@PathVariable boolean suitable) {
        List<Resource<Dogs>> dogs = dogsrepos
                .findBySuitable(suitable)
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(dogs, linkTo(methodOn(DogsController.class).isSuitable(suitable)).withSelfRel());
    }
}
