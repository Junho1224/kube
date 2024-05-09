package com.von.api.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    
    @Test
    public void count() {
        long totalUsers = userService.count();
        assertThat(totalUsers).isEqualTo(15L);


    }
    @Test
    void testDeleteById() {

    }

    @Test
    void testExistById() {

    }

    @Test
    void testFindAll() {

    }

    @Test
    void testFindById() {

    }

    @Test
    void testFindUserByUsername() {

    }

    @Test
    void testFindUsersByJob() {

    }

    @Test
    void testFindUsersByName() {

    }

    @Test
    void testLogin() {

    }

    @Test
    void testModify() {

    }

    @Test
    void testSave() {

    }

    @Test
    void testUpdatePassword() {

    }
}
