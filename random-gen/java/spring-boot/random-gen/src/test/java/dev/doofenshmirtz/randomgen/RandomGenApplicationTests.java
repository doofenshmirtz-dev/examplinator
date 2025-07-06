package dev.doofenshmirtz.randomgen;

import dev.doofenshmirtz.randomgen.model.RandomRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RandomGenApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void testDefaultGet() {
		ResponseEntity<String> response = restTemplate.getForEntity("/getRandom", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void testPostRandom() {
		RandomRequest request = new RandomRequest();
		request.setMin("10");
		request.setMax("20");

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<RandomRequest> entity = new HttpEntity<>(request, headers);

		ResponseEntity<String> response = restTemplate.postForEntity("/getRandom", entity, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
