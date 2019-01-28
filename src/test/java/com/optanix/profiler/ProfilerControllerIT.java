package com.optanix.profiler;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.stringContainsInOrder;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProfilerControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;
    private String mobyDick;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");

        mobyDick = IOUtils.toString(getClass().getResourceAsStream("/MobyDick.txt"), "UTF-8");
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.postForEntity(new URL(base, "/api/analyze").toString(), mobyDick, String.class);
        assertThat(response.getBody(), containsString("\"insights\""));
        assertThat(response.getBody(), containsString("\"wordsPerSentence\""));
        assertThat(response.getBody(), containsString("\"countPerLetter\""));
    }
}
