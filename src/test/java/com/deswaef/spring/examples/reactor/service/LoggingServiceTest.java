package com.deswaef.spring.examples.reactor.service;

import com.deswaef.spring.examples.reactor.model.LogCategory;
import com.deswaef.spring.examples.reactor.model.LogMessage;
import com.deswaef.spring.examples.reactor.repository.LogMessageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import reactor.bus.EventBus;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class LoggingServiceTest {

    private static final LogCategory TEST_CATEGORY = LogCategory.DEBUG;
    private static final String TEST_LOG_MESSAGE = "this is the log message";

    @InjectMocks
    private LoggingService loggingService;

    @Mock
    private LogMessageRepository repository;


    @Mock
    private EventBus reactor;

    @Test
    public void logCallsLogRepository() {
        loggingService.log(TEST_CATEGORY, TEST_LOG_MESSAGE);
        ArgumentCaptor<LogMessage> argCaptor = ArgumentCaptor.forClass(LogMessage.class);
        verify(repository).save(argCaptor.capture());

        LogMessage value = argCaptor.getValue();
        assertThat(value.getCategory()).isEqualTo(TEST_CATEGORY);
        assertThat(value.getText()).isEqualTo(TEST_LOG_MESSAGE);
    }

}