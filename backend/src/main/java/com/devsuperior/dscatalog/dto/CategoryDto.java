package com.devsuperior.dscatalog.dto;

import com.devsuperior.dscatalog.entities.Category;

import java.io.Serializable;
import java.util.Locale;

public class CategoryDto implements Serializable {
    private static final long serialVersionUIDO = 1L;


    private Long id;
    private String name;

    public CategoryDto(){

    }

    public CategoryDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDto (Category entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

    //nao entendi essa parte
    public CategoryDto(Locale.Category x) {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
