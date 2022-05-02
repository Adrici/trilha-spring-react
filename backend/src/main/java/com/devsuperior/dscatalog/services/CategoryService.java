package com.devsuperior.dscatalog.services;

import com.devsuperior.dscatalog.dto.CategoryDto;
import com.devsuperior.dscatalog.entities.Category;
import com.devsuperior.dscatalog.repositories.CategoryRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseExeception;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Optional;


@Service //registra a classe como um componente que participa de sistema de injeção de dependencia automatizada do spring
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional() //readOnly = true
    public Page<CategoryDto> findAllPaged(PageRequest pageRequest) {
        Page<Category> list = repository.findAll(pageRequest);

        return list.map(x -> new CategoryDto(x));
    }

    @Transactional
    public CategoryDto findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundExeception("Entity not found"));
        return new CategoryDto(entity);
    }

    @Transactional
    public CategoryDto insert(CategoryDto dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);

        return new CategoryDto(entity);
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        try {
            Category entity = repository.getOne(id); //findByID efetiva no banco de dados e o getOne nao toca no banco...instancia um objeto provisorio
            entity.setName(dto.getName());
            entity = repository.save(entity);
            return new CategoryDto(entity);

        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundExeception("id not found" + id);
        }

    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);

        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundExeception("id not found" + id);
        }
        catch (DataIntegrityViolationException e){
            throw new DataBaseExeception("Integrity Violetion");

        }
    }


}