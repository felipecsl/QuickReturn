# Version 1.5.0

* Fixed onItemLongClick position offset issue

# Version 1.4.0

* Changed QuickReturnAttacher interface. ``QuickReturnAttacher.forView()`` should be used now.
* Implemented preliminar support for ``ScrollView``. ``ObservableScrollView`` should be used.
* Fixed jumping quick return view when listView had a positive divider height.
* Fixed incorrect position for ``AdapterView.OnItemClickListener`` ``onItemClick()`` callback.
* Fixed jump on scroll issue.

# Version 1.3.2

* Fixes bug with incorrectly calculated ``QuickReturnAdapter.getViewTypeCount()``

# Version 1.3.1

* Prevents overlap of listView elements with targetView: Automatically adjusts the ListView/GridView to prevent it from being hidden behind the target view, when it is placed at the top of the list.\

# Version 1.3.0

* ``QuickReturnAttacher`` API has changed. You can now assign multiple QuickReturn targets using ``QuickReturnAttacher.addTargetView()``.
* Fixes weird transition when using a GridView.
* Minor performance optimizations.

# Version 1.2.4

* Fixes quickreturn view jumping sometimes when scrolling the list

# Version 1.2.3

* Fixes messed up package names

# Version 1.2.2

* Fixes incorrect quick return view position when listview adapter has not enough items to fill the screen.
* QuickReturnAttacher constructor now takes AbsListView instead of ListView, so you can use any concrete implementation with it.

# Version 1.2.1

* Allows the listView adapter to be an instance of WrapperListAdapter.

# Version 1.2.0

* Fixes problem with using ``ListView.setOnScrollListener()`` outside of ``QuickReturnAttacher`` would cause QuickReturn to stop working. Scroll listeners should be added via ``QuickReturnAttacher.addOnScrollListener()``.
* Prevents crash in ``QuickReturnAttacher.setAnimatedTransition(true)`` on Gingerbread. Ignores call and logs warning instead.
* Prevents crash with empty adapter in ``QuickReturnAdapter.getMaxVerticalOffset()``

# Version 1.1

* Added new ``QuickReturnAttacher`` and removed ``QuickReturnListView``. This way, users are not required to subclass a custom listView in order to user the lib.
* Added position argument to ``QuickReturnAttacher``, so now you can choose whether to use top or bottom quick return. **Note: Don't forget to set the view's gravity correctly according to the position chosen!**

# Version 1.0

Initial release.