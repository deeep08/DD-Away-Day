package com.deloittedigital;

import static org.junit.Assert.*;

import com.deloittedigital.model.TeamSchedule;
import com.deloittedigital.service.EventOrganizerService;
import com.deloittedigital.service.impl.DefaultEventOrganizerService;
import com.deloittedigital.util.InputOutputUtil;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for Away day App.
 */
public class DeloitteDigitalDayAwayTest {

    private EventOrganizerService eventOrganizerService;

    @Before
    public void setUp() {
        eventOrganizerService = new DefaultEventOrganizerService();
    }

    /**
     * Empty input file
     */
    @Test
    public void emptyInputFile() {
        List<TeamSchedule> schedule = eventOrganizerService.schedulesActivity(new ArrayList<>());
        assertTrue("Team schedule is not empty.", schedule.isEmpty());
    }

    /**
     * Invalid format input file
     */
    @Test
    public void invalidFormatInputFile() {
        assertNull(InputOutputUtil.readAllActivities(new File("./src/main/resources/input/invalid-format-activities.txt")));
    }

    /**
     * Invalid format input file
     */
    @Test
    public void noActivitiesInputFile() {
        assertTrue("Input file is not empty",
                   InputOutputUtil.readAllActivities(new File("./src/main/resources/input/no-activities.txt")).isEmpty());
    }
}
