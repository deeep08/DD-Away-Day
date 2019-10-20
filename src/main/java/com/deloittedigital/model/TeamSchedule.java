package com.deloittedigital.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TeamSchedule {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final int LUNCH_TIME_SLOT = 42;
    private static final int EVENT_START_TIME_IN_HOUR = 9;

    private static final int SLOT_DURATION = 5;
    private SprintSlot[] slots = new SprintSlot[96];

    private int firstAvailableSlot = 0;
    private int presentationSlot = 84; // Must be after 4:00 PM i.e. 84th sprint slot

    public boolean addActivity(final Activity activity) {
        final int requiredSlots = getRequiredSlots(activity);
        if (firstAvailableSlot + requiredSlots > 96) {
            return false;
        }

        if(firstAvailableSlot <= LUNCH_TIME_SLOT && firstAvailableSlot + requiredSlots > LUNCH_TIME_SLOT) {
            updateScheduleSlots(Activity.getLunchBreakActivity());
        }

        updateScheduleSlots(activity);

        return true;
    }

    private int getRequiredSlots(final Activity activity) {
        return activity.getDuration() / SLOT_DURATION;
    }

    public void setPresentationSlot(final int presentationSlot) {
        if(this.presentationSlot < presentationSlot) {
            this.presentationSlot = presentationSlot;
        }
    }

    public int getAvailableSlot() {
        return firstAvailableSlot;
    }

    private void updateScheduleSlots(final Activity activity) {
        SprintSlot sprintSlot = new SprintSlot(activity);
        final int requiredSlots = getRequiredSlots(activity);

        for(int i = firstAvailableSlot; i < firstAvailableSlot + requiredSlots; i++) {
            slots[i] = sprintSlot;
        }

        firstAvailableSlot = firstAvailableSlot + requiredSlots;
    }

    private String getSlotStartTime(int index) {
        LocalTime localTime = LocalTime.MIN.plusMinutes(EVENT_START_TIME_IN_HOUR * 60L);
        localTime = localTime.plusMinutes(index * SLOT_DURATION);
        return localTime.format(DateTimeFormatter.ISO_TIME);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Schedule:-");

        builder.append(LINE_SEPARATOR)
               .append("Time     -------- Activity")
               .append(LINE_SEPARATOR);

        for(int i=0; i < slots.length; i++) {
            if(i==0 || (slots[i] != null && !slots[i].getActivity().equals(slots[i-1].getActivity()))) {
                builder.append(getSlotStartTime(i))
                       .append(" -------- ")
                       .append(slots[i].getActivity())
                       .append(LINE_SEPARATOR);
            }
        }

        builder.append(getSlotStartTime(presentationSlot))
               .append(" -------- ")
               .append(Activity.getPresentationActivity())
               .append(LINE_SEPARATOR);

        return builder.toString();
    }
}
