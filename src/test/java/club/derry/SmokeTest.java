/*
 * Copyright (c) 2022
 * derry.club
 */

package club.derry;

import club.derry.config.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private Config config;

    @Test
    public void contextLoad() {
        assertNotNull(config);
    }
}
