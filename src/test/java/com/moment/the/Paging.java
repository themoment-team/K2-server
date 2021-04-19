package com.moment.the;

import com.moment.the.repository.TableRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;

@RequiredArgsConstructor
@SpringBootTest
public class Paging {
    private final TableRepository tableRepository;
}
