# QuickReturn

Android ListView that implements the QuickReturn UI pattern. Written from scratch with focus on performance.

### Usage

In your ``build.gradle`` file:

```groovy
repositories {
    maven { url 'https://raw.github.com/felipecsl/m2repository/master' }
    // ...
}

dependencies {
    // ...
    compile 'com.felipecsl:quickreturn:1.0.+'
}
```

In your layout xml:

```xml
<com.felipecsl.quickreturn.library.widget.QuickReturnListView
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/listView"
    android:layout_width="match_parent"
    android:layout_height="match_parent"/>
```

In your activity class:

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    quickReturnListView = (QuickReturnListView) findViewById(R.id.listView);

    // the quick return target view to be hidden/displayed
    TextView quickReturnTarget = (TextView) findViewById(R.id.quickReturnTarget);

    // your inner adapter
    adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);

    quickReturnAdapter = new QuickReturnAdapter(adapter);
    quickReturnListView.setAdapter(quickReturnAdapter);
    quickReturnListView.setQuickReturnView(quickReturnTarget);
}
```

Supports dynamic adapter. That means you can add and remove items from your adapter and it will still work nicely.

Works with Android 2.3.x and above.

### Caveats

* None yet.

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