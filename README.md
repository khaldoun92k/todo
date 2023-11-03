# ToDoList 
The purpose of this project a backend service allowing basic management of a simple to-do list.

# Tech-stack
| Requirement | Version | Purpose of Choice                          |
|-------------|---------|--------------------------------------------|
| JDK         | 21      | Latest LTS Java version                    |
| Maven       | 3+      | backend building tool                      |



# Services
List of RESTful APIs

| URL                        | Type | usage                                                                     |
|----------------------------|------|---------------------------------------------------------------------------|
| /api/item                  | POST | Add an item.                                                              |
| /api/item/description/{id} | PUT  | Change description of an item.                                            |
| /api/item/done/{id}        | PUT  | Mark an item as "done".                                                   |
| /api/item/not_done/{id}    | PUT  | Mark an item as "not done".                                               |
| /api/items                 | GET  | Get all items that are "not done" (with an option to retrieve all items). |
| /api/item                  | GET  | Get details of a specific item.                                           |
# User instruction
TODO...
# Notes
TODO...

