package com.devsuperior.dscatalog.resources;


import com.devsuperior.dscatalog.dto.UserDto;
import com.devsuperior.dscatalog.dto.UserInsertDto;
import com.devsuperior.dscatalog.dto.UserUpdateDto;
import com.devsuperior.dscatalog.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;

    //busca paginada
    @GetMapping
    public ResponseEntity<Page<UserDto>> findAll(Pageable pageable) {
        Page<UserDto> list = service.findAllPaged(pageable);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDto> findById (@PathVariable Long id) {
        UserDto dto = service.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping
    public ResponseEntity<UserDto> insert ( @Valid @RequestBody UserInsertDto dto){
        UserDto newDto = service.insert(dto);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getId()).toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserDto> update( @Valid @PathVariable Long id, @RequestBody UserUpdateDto dto){
        UserDto newDto = service.update(id, dto);

        return ResponseEntity.ok().body(newDto);
    }
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UserDto> delete( @PathVariable Long id){
         service.delete(id);

        return ResponseEntity.noContent().build(); //204 - corpo da resp vazio
    }
}
