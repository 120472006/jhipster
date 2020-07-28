package com.zzjz.myapp.demo.web.rest;

import com.zzjz.myapp.demo.Service1App;
import com.zzjz.myapp.demo.config.SecurityBeanOverrideConfiguration;
import com.zzjz.myapp.demo.domain.Role;
import com.zzjz.myapp.demo.repository.RoleRepository;
import com.zzjz.myapp.demo.service.RoleService;
import com.zzjz.myapp.demo.service.dto.RoleDTO;
import com.zzjz.myapp.demo.service.mapper.RoleMapper;
import com.zzjz.myapp.demo.service.dto.RoleCriteria;
import com.zzjz.myapp.demo.service.RoleQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link RoleResource} REST controller.
 */
@SpringBootTest(classes = { SecurityBeanOverrideConfiguration.class, Service1App.class })
@AutoConfigureMockMvc
@WithMockUser
public class RoleResourceIT {

    private static final String DEFAULT_ROLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ROLE_NAME = "BBBBBBBBBB";

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleQueryService roleQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restRoleMockMvc;

    private Role role;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createEntity(EntityManager em) {
        Role role = new Role()
            .roleName(DEFAULT_ROLE_NAME);
        return role;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Role createUpdatedEntity(EntityManager em) {
        Role role = new Role()
            .roleName(UPDATED_ROLE_NAME);
        return role;
    }

    @BeforeEach
    public void initTest() {
        role = createEntity(em);
    }

    @Test
    @Transactional
    public void getAllRoles() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)));
    }
    
    @Test
    @Transactional
    public void getRole() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", role.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(role.getId().intValue()))
            .andExpect(jsonPath("$.roleName").value(DEFAULT_ROLE_NAME));
    }


    @Test
    @Transactional
    public void getRolesByIdFiltering() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        Long id = role.getId();

        defaultRoleShouldBeFound("id.equals=" + id);
        defaultRoleShouldNotBeFound("id.notEquals=" + id);

        defaultRoleShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultRoleShouldNotBeFound("id.greaterThan=" + id);

        defaultRoleShouldBeFound("id.lessThanOrEqual=" + id);
        defaultRoleShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllRolesByRoleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName equals to DEFAULT_ROLE_NAME
        defaultRoleShouldBeFound("roleName.equals=" + DEFAULT_ROLE_NAME);

        // Get all the roleList where roleName equals to UPDATED_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.equals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName not equals to DEFAULT_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.notEquals=" + DEFAULT_ROLE_NAME);

        // Get all the roleList where roleName not equals to UPDATED_ROLE_NAME
        defaultRoleShouldBeFound("roleName.notEquals=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameIsInShouldWork() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName in DEFAULT_ROLE_NAME or UPDATED_ROLE_NAME
        defaultRoleShouldBeFound("roleName.in=" + DEFAULT_ROLE_NAME + "," + UPDATED_ROLE_NAME);

        // Get all the roleList where roleName equals to UPDATED_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.in=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName is not null
        defaultRoleShouldBeFound("roleName.specified=true");

        // Get all the roleList where roleName is null
        defaultRoleShouldNotBeFound("roleName.specified=false");
    }
                @Test
    @Transactional
    public void getAllRolesByRoleNameContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName contains DEFAULT_ROLE_NAME
        defaultRoleShouldBeFound("roleName.contains=" + DEFAULT_ROLE_NAME);

        // Get all the roleList where roleName contains UPDATED_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.contains=" + UPDATED_ROLE_NAME);
    }

    @Test
    @Transactional
    public void getAllRolesByRoleNameNotContainsSomething() throws Exception {
        // Initialize the database
        roleRepository.saveAndFlush(role);

        // Get all the roleList where roleName does not contain DEFAULT_ROLE_NAME
        defaultRoleShouldNotBeFound("roleName.doesNotContain=" + DEFAULT_ROLE_NAME);

        // Get all the roleList where roleName does not contain UPDATED_ROLE_NAME
        defaultRoleShouldBeFound("roleName.doesNotContain=" + UPDATED_ROLE_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultRoleShouldBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(role.getId().intValue())))
            .andExpect(jsonPath("$.[*].roleName").value(hasItem(DEFAULT_ROLE_NAME)));

        // Check, that the count call also returns 1
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultRoleShouldNotBeFound(String filter) throws Exception {
        restRoleMockMvc.perform(get("/api/roles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRoleMockMvc.perform(get("/api/roles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingRole() throws Exception {
        // Get the role
        restRoleMockMvc.perform(get("/api/roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }
}
