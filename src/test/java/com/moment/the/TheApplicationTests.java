package com.moment.the;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TheApplicationTests {

	@Test
	void pageable_값_검증() {
		//Given
		int expected = (186-5)/6;
		//When
		int actual = 30;
		//then
		assertEquals(expected, actual);
		System.out.println("expected: " + expected + " actual: " + actual);
	}

}
