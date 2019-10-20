package com.deloittedigital.model;

public class SprintSlot {

    private Activity activity;

    public SprintSlot(final Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

    public boolean isEmpty() {
        return activity == null;
    }
}
