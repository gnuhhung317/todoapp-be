package vdt.example.demo.dto;

import lombok.Data;

@Data
public class TodoRequest {

    private String title;

    private String description;

    private Boolean completed = false;
}