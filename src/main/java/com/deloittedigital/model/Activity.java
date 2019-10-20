package com.deloittedigital.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class Activity {
    private static final Logger LOG = LoggerFactory.getLogger(Activity.class);

    public static final int SPRINT_TIME = 15;

    private static final String SPRINT_TEXT = "sprint";
    private static final String MINUTES = "min";

    private String name;
    private int duration;
    private boolean scheduled;

    private Activity(final String name, final int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(final boolean scheduled) {
        this.scheduled = scheduled;
    }

    public static Activity createActivity(final String line) {
        final int splitIndex = line.lastIndexOf(" ");
        String time = line.substring(splitIndex + 1);
        time = SPRINT_TEXT.equals(time) ? SPRINT_TIME + "" : time.replace(MINUTES, "");

        try {
            return new Activity(line.substring(0, splitIndex), Integer.parseInt(time));
        } catch (NumberFormatException e) {
            LOG.error("Error while parsing line: {} , {}", line, e.getMessage());
            throw e;
        }
    }

    public static Activity getLunchBreakActivity() {
        return createActivity("Lunch Break 60min");
    }

    public static Activity getPresentationActivity() {
        return createActivity("Staff Motivation Presentation 60min");
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Activity activity = (Activity) o;
        return name.equals(activity.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final String timeStringSuffix = duration == SPRINT_TIME ? SPRINT_TEXT : duration + MINUTES;
        return name + " " + timeStringSuffix;
    }
}
