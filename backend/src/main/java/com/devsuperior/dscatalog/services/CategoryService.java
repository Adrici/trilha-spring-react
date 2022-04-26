package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDto;

import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service //registra a classe como um componente que participa de sistema de injeção de dependencia automatizada do spring
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional
    public List<CategoryDto> findAll(){
        List<Category> list = repository.findAll();

        return list.stream().map(x -> new CategoryDto(x)).collect(Collectors.toList());
    }

}
