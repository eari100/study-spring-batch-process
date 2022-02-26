package com.example.studyspringbatchprocess;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void afterJob(JobExecution jobExecution) {
        log.info("!!! JOB FINISHED! Time to verify the results");

        jdbcTemplate.query("SELECT first_name, last_name FROM people",
            (rs, row) -> new Person(
                rs.getString(1),
                rs.getString(2))
            ).forEach(person -> log.info("Found <" + person + "> in the database."));
    }
}
