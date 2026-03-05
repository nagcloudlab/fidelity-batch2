package com.example.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EditTodoDto {
    private String newTitle;
    private String newDescription;
    private String newType;
    private boolean newCompleted;
}
