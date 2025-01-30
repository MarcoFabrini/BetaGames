package com.betagames.dto;
/**
 *
 * @author Simone Checco
 */
public class RolesDTO {
    private Integer id;

    private String name;

    private List<UsersDTO> listUsersDTO;

    public RolesDTO(Integer id, String name, List<UsersDTO> listUsersDTO) {
        this.id = id;
        this.name = name;
        this.listUsersDTO = listUsersDTO;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UsersDTO> getListUsersDTO() {
        return listUsersDTO;
    }

    public void setListUsersDTO(List<UsersDTO> listUsersDTO) {
        this.listUsersDTO = listUsersDTO;
    }

    @Override
    public String toString() {
        return "RolesDTO [id=" + id + ", name=" + name + ", listUsersDTO=" + listUsersDTO + "]";
    }

    
}

    
