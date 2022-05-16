package com.devsuperior.dscatalog.services;


import com.devsuperior.dscatalog.dto.RoleDto;
import com.devsuperior.dscatalog.dto.UserDto;
import com.devsuperior.dscatalog.dto.UserInsertDto;
import com.devsuperior.dscatalog.entities.Role;
import com.devsuperior.dscatalog.entities.User;
import com.devsuperior.dscatalog.repositories.RoleRepository;
import com.devsuperior.dscatalog.repositories.UserRepository;
import com.devsuperior.dscatalog.services.exception.DataBaseExeception;
import com.devsuperior.dscatalog.services.exception.ResourceNotFoundExeception;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;



@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RoleRepository roleRepository;



    @Transactional() //readOnly = true
    public Page<UserDto> findAllPaged(PageRequest pageRequest) {
        Page<User> list = repository.findAll(pageRequest);

        return list.map(x -> new UserDto(x));
    }
    @Transactional
    public UserDto findById(Long id) {
        User user = repository.findById(id).orElseThrow(() -> new ResourceNotFoundExeception("Entity not found"));

        return new UserDto(user);
    }

    @Transactional
    public UserDto insert(UserInsertDto dto) {
        User entity = new User();
        copyDtoToEntity(dto, entity);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        entity = repository.save(entity);
        return new UserDto(entity);
    }



    @Transactional
    public UserDto update(Long id, UserDto dto) {
        try {
            User entity = repository.getOne(id); //findByID efetiva no banco de dados e o getOne nao toca no banco...instancia um objeto provisorio
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new UserDto(entity);

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
    private void copyDtoToEntity(UserDto dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.getRoles().clear();
        for (RoleDto roleDto : dto.getRoles()) {
            Role role = roleRepository.getOne(roleDto.getId());
            entity.getRoles().add(role);
        }
    }

}