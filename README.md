# QuickReturn

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-felipecsl%2FQuickReturn-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/697)

Android ListView that implements the QuickReturn UI pattern. Written from scratch with focus on performance.

### Demo

[![video thumbnail](http://img.youtube.com/vi/BwLjMMIWNQU/hqdefault.jpg)](https://www.youtube.com/watch?v=BwLjMMIWNQU)

### Usage

In your ``build.gradle`` file:

```groovy
dependencies {
    // ...
    compile 'com.felipecsl.quickreturn:library:1.5.1'
}
```

In your activity class:

```java
private ListView listView;
private ArrayAdapter<String> adapter;
private QuickReturnAttacher quickReturnAttacher;
private TextView quickReturnTarget;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_main);

    // your listView :)
    listView = (ListView) findViewById(R.id.listView);

    // the quick return target view to be hidden/displayed
    quickReturnTarget = (TextView) findViewById(R.id.quickReturnTarget);

    // your inner adapter
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

    // Wrap your adapter with QuickReturnAdapter
    listView.setAdapter(new QuickReturnAdapter(adapter));

    // Attach a QuickReturnAttacher, which takes care of all of the hide/show functionality.
    quickReturnAttacher = QuickReturnAttacher.forView(listView);

    // Add a quick return targetView to the attacher.
    // You can pass a position argument (POSITION_TOP or POSITION_BOTTOM).
    // You can also optionally pass the size of the target view, which will be used to 
    // offset the list height, preventing it from hiding content behind the target view.
    quickReturnAttacher.addTargetView(quickReturnTarget, QuickReturnTargetView.POSITION_TOP, 50);

    // If you need to add an OnScrollListener to the listView, this is the correct
    // way to do so.
    // You have to add it on the QuickReturnAttacher instead
    // of adding on the listView directly.
    quickReturnAttacher.addOnScrollListener(this);
}
```

Check the [sample app](https://github.com/felipecsl/QuickReturn/blob/master/app/src/main/java/com/felipecsl/quickreturn/app/MainActivity.java) for an example of usage.

### Features

* Supports dynamic adapters. That means you can add and remove items from your adapter and it will still work nicely.
* You don't have to subclass ``QuickReturnAdapter`` in order to use it. Just pass your own adapter to the constructor and you're done.
* Animated transitions via ``QuickReturnAttacher.setAnimatedTransition(true)``
* Supports bottom (footer) quick return position via ``QuickReturnAttacher.setPosition(QuickReturnListView.POSITION_BOTTOM).``
* You can use it with any subclass of ``AbsListView``, including ``ListView`` and ``GridView``.
* If you're using a ``GridView``, you have to tell ``QuickReturnAdapter`` how many columns it has, via its constructor:
* Automatically adjusts the ListView/GridView to prevent it from being hidden behind the target view, when it is placed at the top of the list.
* Supports ScrollView as well!

```java
public QuickReturnAdapter(final ListAdapter wrappedAdapter, final int numColumns)
```


Works with API Level 10 and above.

### Known issues/Caveats

* The Animated Transition will sometimes hide/show randomly when scrolling down very slowly.

### Changelog

Please see the [Changelog](https://github.com/felipecsl/QuickReturn/blob/master/CHANGELOG.md) to check what's recently changed.

### Credits

Heavily inspired/influenced by the [nice work of Roman Nurik's and Nick Butcher's](https://plus.google.com/+RomanNurik/posts/1Sb549FvpJt) and Lars Werkman's [QuickReturnListView](https://github.com/LarsWerkman/QuickReturnListView)

### Contributing

* Check out the latest master to make sure the feature hasn't been implemented or the bug hasn't been fixed yet
* Check out the issue tracker to make sure someone already hasn't requested it and/or contributed it
* Fork the project
* Start a feature/bugfix branch
* Commit and push until you are happy with your contribution
* Make sure to add tests for it. This is important so I don't break it in a future version unintentionally.

### Copyright and license

Code and documentation copyright 2011-2014 Felipe Lima.
Code released under the [MIT license](https://github.com/felipecsl/QuickReturn/blob/master/LICENSE.txt).
