package com.deloittedigital.service.impl;

import com.deloittedigital.model.Activity;
import com.deloittedigital.model.TeamSchedule;
import com.deloittedigital.service.EventOrganizerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DefaultEventOrganizerService implements EventOrganizerService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultEventOrganizerService.class);

    public static int TOTAL_ACTIVITY_TIME_IN_A_DAY = 7 * 60;

    @Override
    public List<TeamSchedule> schedulesActivity(final List<Activity> activities) {
        final int totalDuration = activities.stream()
                                              .mapToInt(Activity::getDuration)
                                              .sum();

        activities.sort(Comparator.comparingInt(Activity::getDuration).reversed());

        final int noOfTeams = (int)Math.ceil(((double)totalDuration)/TOTAL_ACTIVITY_TIME_IN_A_DAY);

        List<TeamSchedule> teamSchedules = IntStream.range(0, noOfTeams)
                                                    .mapToObj(i -> new TeamSchedule())
                                                    .collect(Collectors.toList());

        int index = 0;

        for (Activity activity : activities) {
            while(!activity.isScheduled()) {
                final TeamSchedule teamSchedule = teamSchedules.get(index % noOfTeams);
                if (teamSchedule.addActivity(activity)) {
                    activity.setScheduled(true);
                }
                index++;
            }
        }

        final int presentationSlot = teamSchedules.stream().mapToInt(TeamSchedule::getAvailableSlot).max().orElse(-1);
        teamSchedules.forEach(eventSchedule -> eventSchedule.setPresentationSlot(presentationSlot));

        return teamSchedules;
    }

    /*@Override
    public List<EventSchedule> schedulesActivity(final List<Activity> activities) {
        List<EventSchedule> schedules = new ArrayList<>();

        EventSchedule schedule = new EventSchedule();

        boolean unscheduledActivityExist = true;
        while(unscheduledActivityExist) {
            for (Activity activity : activities) {
                if(activity.isScheduled()) {
                    unscheduledActivityExist = false;
                } else {
                    if(schedule.addActivity(activity)) {
                        activity.setScheduled(true);
                    }
                }
            }

            schedules.add(schedule);
            schedule = new EventSchedule();
        }

        int presentationSlot = schedules.stream().mapToInt(EventSchedule::getAvailableSlot).max().orElse(-1);
        schedules.forEach(eventSchedule -> eventSchedule.setPresentationSlot(presentationSlot));

        return schedules;
    }*/
}
