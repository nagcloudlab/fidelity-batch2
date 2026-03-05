package com.example.model;



/*


    {
        "id": 1,
        "title": "Buy groceries",
        "description": "Milk, Bread, Eggs",
        "completed": false,
        "userId": 1,
        "type": PERSONAL || WORK || SOCIAL,
    }

 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Todo {

    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private Long userId;
    private TodoType type;


}
