# Version 1.2.0

* Fixes problem with using ``ListView.setOnScrollListener()`` outside of ``QuickReturnAttacher`` would cause QuickReturn to stop working. Scroll listeners should be added via ``QuickReturnAttacher.addOnScrollListener()``.
* Prevents crash in ``QuickReturnAttacher.setAnimatedTransition(true)`` on Gingerbread. Ignores call and logs warning instead.
* Prevents crash with empty adapter in ``QuickReturnAdapter.getMaxVerticalOffset()``

# Version 1.1

* Added new ``QuickReturnAttacher`` and removed ``QuickReturnListView``. This way, users are not required to subclass a custom listView in order to user the lib.
* Added position argument to ``QuickReturnAttacher``, so now you can choose whether to use top or bottom quick return. **Note: Don't forget to set the view's gravity correctly according to the position chosen!**

# Version 1.0

Initial release.