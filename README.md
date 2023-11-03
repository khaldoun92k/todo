# ToDoList 
The purpose of this project a backend service allowing basic management of a simple to-do list.

# Tech-stack
| Requirement | Version | Purpose of Choice                          |
|-------------|---------|--------------------------------------------|
| JDK         | 21      | Latest LTS Java version                    |
| Maven       | 3+      | backend building tool                      |



# Services
List of RESTful APIs

| URL                     | Type | usage                                                                     |
|-------------------------|------|---------------------------------------------------------------------------|
| /items                  | POST | add an item.                                                              |
| /items/description/{id} | PUT  | change description of an item.                                            |
| /items/done/{id}        | PUT  | mark an item as "done".                                                   |
| /items/not_done/{id}    | PUT  | mark an item as "not done".                                               |
| /items                  | GET  | get all items that are "not done" (with an option to retrieve all items). |
| /items                  | GET  | get details of a specific item.                                           |
# User instruction
TODO...
# Notes
TODO...

