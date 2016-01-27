package com.felipecsl.quickreturn.library;

public interface QuickReturnStateTransition {
  int determineState(int rawY, int quickReturnHeight);
}
