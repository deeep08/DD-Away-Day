package com.deloittedigital.model;

public class Team {

    private String name;
    private TeamSchedule teamSchedule;

    public Team(final String name, final TeamSchedule teamSchedule) {
        this.name = name;
        this.teamSchedule = teamSchedule;
    }

    public String getName() {
        return name;
    }

    public TeamSchedule getTeamSchedule() {
        return teamSchedule;
    }

    @Override
    public String toString() {
        return name + "\n" + teamSchedule;
    }
}
