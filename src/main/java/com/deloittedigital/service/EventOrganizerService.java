package com.deloittedigital.service;

import com.deloittedigital.model.Activity;
import com.deloittedigital.model.TeamSchedule;

import java.util.List;

/**
 * EventOrganizerService
 */
public interface EventOrganizerService {

    /**
     * Based on the duration and number of activities, it creates a schedule for various teams in the event.
     *
     * @param activities
     * @return list of team schedule
     */
    List<TeamSchedule> schedulesActivity(List<Activity> activities);

}
