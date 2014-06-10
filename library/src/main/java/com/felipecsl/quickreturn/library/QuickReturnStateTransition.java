package com.felipecsl.quickreturn.library;

public interface QuickReturnStateTransition {
    public int determineState(final int rawY, int quickReturnHeight);
}
