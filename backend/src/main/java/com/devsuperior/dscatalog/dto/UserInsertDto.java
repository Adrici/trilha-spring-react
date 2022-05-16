package com.devsuperior.dscatalog.dto;


import com.devsuperior.dscatalog.entities.User;

public class UserInsertDto extends UserDto {

    private static final long serialVersionUID = 1L;

    private String password;

    UserInsertDto(){
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
