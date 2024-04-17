package com.von.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import com.von.api.user.service.UserService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
    private UserService userService;


    @Test
    public void count() {
        long totalUsers = userService.count();
		System.out.println(totalUsers);
        assertThat(totalUsers).isEqualTo(14L);
	}


}
