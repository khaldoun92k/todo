package com.task.todo;


import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

// Added a model assembler with hypertext links to make it properly Restful
@Component
public class ItemModelAssembler implements RepresentationModelAssembler<Item, EntityModel<Item>> {
    // added hypertext links to make it properly Restful
    @Override
    public EntityModel<Item> toModel(Item item) {
        return EntityModel.of(item, //
                linkTo(methodOn(ItemController.class).getDetails(item.getId())).withSelfRel(),
                linkTo(methodOn(ItemController.class).allNotDone(true)).withRel("items"));
    }
}