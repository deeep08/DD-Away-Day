package com.deloittedigital.util;

import com.deloittedigital.DeloitteDigitalDayAway;
import com.deloittedigital.model.Activity;
import com.deloittedigital.model.Team;
import com.deloittedigital.model.TeamSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class InputOutputUtil {

    private static final Logger LOG = LoggerFactory.getLogger(InputOutputUtil.class);

    public static void displayEventSchedule(final List<TeamSchedule> teamSchedules) {
        final AtomicInteger counter = new AtomicInteger(0);
        try(final PrintWriter writer = new PrintWriter("output.txt")) {
            teamSchedules.stream()
                         .map(teamSchedule -> new Team("Team " + counter.incrementAndGet(), teamSchedule))
                         .map(Team::toString)
                         .forEach(writer::println);

            LOG.info("Schedule generated successfully...");
        } catch (FileNotFoundException e) {
            LOG.error("Exception while printing file: {}", e.getMessage());
        }
    }

    public static List<Activity> readAllActivities(final File inputFile) {
        try {
            return Files.readAllLines(inputFile.toPath(), StandardCharsets.UTF_8)
                        .stream()
                        .map(Activity::createActivity)
                        .collect(Collectors.toList());
        } catch (IOException e) {
            LOG.error("Exception while reading file: {}", e.getMessage());
        } catch (NumberFormatException e) {
            LOG.error("Input file in invalid format...");
        }
        return null;
    }

}
