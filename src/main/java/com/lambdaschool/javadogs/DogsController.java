package com.lambdaschool.javadogs;

import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
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

    @PutMapping("/dogs/{id}")
    public ResponseEntity<?> replaceDog(
            @RequestBody Dogs newDog,
            @PathVariable Long id)
            throws URISyntaxException {
        Dogs updatedDog = dogsrepos
                .findById(id)
                .map(dog -> {
                    dog.setBreed(newDog.getBreed());
                    dog.setWeight(newDog.getWeight());
                    dog.setSuitable(newDog.isSuitable());
                    return dogsrepos.save(dog);
                })
                .orElseGet(() -> {
                    newDog.setId(id);
                    return dogsrepos.save(newDog);
                });

        Resource<Dogs> resource = assembler.toResource(updatedDog);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PostMapping("/dogs")
    public ResponseEntity<?> createDog(@RequestBody Dogs newDog)
            throws URISyntaxException {
        Dogs createdDog = dogsrepos.save(newDog);
        Resource<Dogs> resource = assembler.toResource(createdDog);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


}
