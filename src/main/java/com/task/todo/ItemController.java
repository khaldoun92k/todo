package com.task.todo;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
public class ItemController {
    @Autowired
    private ItemRepository repository;
    @Autowired
    private ItemModelAssembler assembler;


    /** Service to add an item **/
    @PostMapping("/item")
    public ResponseEntity<?> addItem(@Valid @RequestBody Item newItem) {
        EntityModel<Item> entityModel=assembler.toModel( repository.save(newItem));
        return  ResponseEntity //Additionally, return the model-based version of the saved object.
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }
    /** Service change description of an item **/
    @PutMapping("/item/description/{id}")
    public ResponseEntity<?> changeDescription(@PathVariable Long id, @RequestParam String description) {
        Item updateditem = repository.findById(id)
                .map(item -> {
                    item.setDescription(description);
                    return repository.save(item);
                }).orElseThrow(() -> new ItemNotFoundException(id));
        EntityModel<Item> entityModel = assembler.toModel(updateditem);
        return ResponseEntity
                .ok()
                .body(entityModel);
    }
    /** Service mark an item as "done" **/
    @PutMapping("/item/done/{id}")
    public ResponseEntity<?> markAsDone(@PathVariable Long id) {
        Item updateditem = repository.findById(id)
                .map(item -> {
                    item.setStatus(TaskStatus.DONE);
                    return repository.save(item);
                }).orElseThrow(() -> new ItemNotFoundException(id));
        EntityModel<Item> entityModel = assembler.toModel(updateditem);
        return ResponseEntity
                .ok()
                .body(entityModel);
    }
    /** Service mark an item as "not done" **/
    @PutMapping("/item/not_done/{id}")
    public ResponseEntity<?> markAsNotDone(@PathVariable Long id) {
        Item updatedItem = repository.findById(id)
                .map(item -> {
                    item.setStatus(TaskStatus.NOT_DONE);
                    return repository.save(item);
                }).orElseThrow(() -> new ItemNotFoundException(id));
        EntityModel<Item> entityModel = assembler.toModel(updatedItem);
        return ResponseEntity
                .ok()
                .body(entityModel);
    }

    /** Service to get all items that are "not done" (with an option to retrieve all items) **/
    @GetMapping("/items")
    public CollectionModel<EntityModel<Item>> allNotDone(@RequestParam(required = false) Boolean showAll) {
        List<Item> allItems = repository.findAll();

        // Apply filter and map to EntityModel in one stream
        List<EntityModel<Item>> items = allItems.stream()
                .filter(i -> Boolean.TRUE.equals(showAll) || i.getStatus() == TaskStatus.NOT_DONE)
                .map(assembler::toModel)
                .collect(Collectors.toList());

        //wrapping model
        return CollectionModel.of(items, linkTo(methodOn(ItemController.class).allNotDone(showAll)).withSelfRel());
    }
    /**Service to get details of a specific item.**/
    @GetMapping("/item/{id}")
    public	EntityModel<Item> getDetails(@PathVariable Long id) {
        Item item = repository.findById(id)//
                .orElseThrow(() -> new ItemNotFoundException(id));
        return assembler.toModel(item);

    }
}
