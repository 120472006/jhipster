package com.zzjz.myapp.demo.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.zzjz.myapp.demo.web.rest.TestUtil;

public class RoleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoleDTO.class);
        RoleDTO roleDTO1 = new RoleDTO();
        roleDTO1.setId(1L);
        RoleDTO roleDTO2 = new RoleDTO();
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO2.setId(roleDTO1.getId());
        assertThat(roleDTO1).isEqualTo(roleDTO2);
        roleDTO2.setId(2L);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
        roleDTO1.setId(null);
        assertThat(roleDTO1).isNotEqualTo(roleDTO2);
    }
}
