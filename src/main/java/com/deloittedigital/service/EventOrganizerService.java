package com.deloittedigital.service;

import com.deloittedigital.model.Activity;
import com.deloittedigital.model.TeamSchedule;

import java.util.List;

public interface EventOrganizerService {

    List<TeamSchedule> schedulesActivity(List<Activity> activities);

}
