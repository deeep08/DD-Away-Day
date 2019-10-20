package com.deloittedigital;

import com.deloittedigital.model.Activity;
import com.deloittedigital.model.TeamSchedule;
import com.deloittedigital.service.EventOrganizerService;
import com.deloittedigital.service.impl.DefaultEventOrganizerService;
import com.deloittedigital.util.InputOutputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;
import java.util.Optional;

/**
 * Starting point for Day Away application
 */
public class DeloitteDigitalDayAway {
    private static final Logger LOG = LoggerFactory.getLogger(DeloitteDigitalDayAway.class);

    public static void main( String[] args ) {
        final Optional<String> filename = getValidatedFilename(args);

        if(!filename.isPresent()) {
            LOG.info("Please provide the filename as first parameter command-line argument");
            return;
        }

        final File inputFile = new File(filename.get());

        final List<Activity> activities = InputOutputUtil.readAllActivities(inputFile);

        if(activities != null && !activities.isEmpty()) {
            final EventOrganizerService eventOrganizerService = new DefaultEventOrganizerService();
            List<TeamSchedule> teamSchedules = eventOrganizerService.schedulesActivity(activities);

            InputOutputUtil.displayEventSchedule(teamSchedules);
        } else {
            LOG.warn("No activities present or Invalid input file.");
        }

    }

    private static Optional<String> getValidatedFilename(final String[] args) {
        return Optional.ofNullable(args)
                       .filter(input -> input.length >= 1)
                       .map(input -> input[0]);
    }
}
