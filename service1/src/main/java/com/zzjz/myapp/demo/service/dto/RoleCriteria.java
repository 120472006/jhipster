package com.zzjz.myapp.demo.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.zzjz.myapp.demo.domain.Role} entity. This class is used
 * in {@link com.zzjz.myapp.demo.web.rest.RoleResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /roles?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoleCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter roleName;

    public RoleCriteria() {
    }

    public RoleCriteria(RoleCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roleName = other.roleName == null ? null : other.roleName.copy();
    }

    @Override
    public RoleCriteria copy() {
        return new RoleCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getRoleName() {
        return roleName;
    }

    public void setRoleName(StringFilter roleName) {
        this.roleName = roleName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoleCriteria that = (RoleCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(roleName, that.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        roleName
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (roleName != null ? "roleName=" + roleName + ", " : "") +
            "}";
    }

}
