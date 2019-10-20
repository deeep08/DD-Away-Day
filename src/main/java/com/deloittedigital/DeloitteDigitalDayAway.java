package com.deloittedigital;

import com.deloittedigital.model.Activity;
import com.deloittedigital.model.TeamSchedule;
import com.deloittedigital.model.Team;
import com.deloittedigital.service.EventOrganizerService;
import com.deloittedigital.service.impl.DefaultEventOrganizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Starting point for Day Away application
 */
public class DeloitteDigitalDayAway {
    private static final Logger LOG = LoggerFactory.getLogger(DeloitteDigitalDayAway.class);

    public static void main( String[] args ) {
        final Optional<String> filename = getValidatedFilename(args);

        if(!filename.isPresent()) {
            throw new IllegalArgumentException("Filename not present in command-line arguments");
        }

        final File inputFile = new File(filename.get());

        final List<Activity> activities = readAllActivities(inputFile);

        final EventOrganizerService eventOrganizerService = new DefaultEventOrganizerService();
        List<TeamSchedule> teamSchedules = eventOrganizerService.schedulesActivity(activities);

        displayEventSchedule(teamSchedules);
    }

    private static void displayEventSchedule(final List<TeamSchedule> teamSchedules) {
        final AtomicInteger counter = new AtomicInteger(0);
        try(final PrintWriter writer = new PrintWriter("output.txt")) {
            teamSchedules.stream()
                         .map(teamSchedule -> new Team("Team " + counter.incrementAndGet(), teamSchedule))
                         .map(Team::toString)
                         .forEach(writer::println);
        } catch (FileNotFoundException e) {
            LOG.error("Exception while printing file: {}", e.getMessage());
        }
    }

    private static List<Activity> readAllActivities(final File inputFile) {
        final List<Activity> activities = new ArrayList<>();
        try {
            Files.readAllLines(inputFile.toPath(), StandardCharsets.UTF_8)
                 .stream()
                 .map(Activity::createActivity)
                 .forEach(activities::add);
        } catch (IOException e) {
            LOG.error("Exception while reading file: {}", e.getMessage());
        }
        return activities;
    }

    private static Optional<String> getValidatedFilename(final String[] args) {
        return Optional.ofNullable(args)
                       .filter(input -> input.length >= 1)
                       .map(input -> input[0]);
    }
}
