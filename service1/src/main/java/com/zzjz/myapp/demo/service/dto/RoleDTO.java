package com.zzjz.myapp.demo.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.zzjz.myapp.demo.domain.Role} entity.
 */
public class RoleDTO implements Serializable {
    
    private Long id;

    private String roleName;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoleDTO)) {
            return false;
        }

        return id != null && id.equals(((RoleDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleDTO{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            "}";
    }
}
