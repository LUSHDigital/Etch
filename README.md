Etch
=======

Create Android views from JSON with no dependencies.

<img src="github/etch.jpg" width="350" height="277" />

Overview
--------

Minimal library to create android views from JSON. No dependencies are used so that:
- You implement all JSON parsing, using your preferred library
- You don't have someone else's preferred JSON parser in your build!
- You can easily swap out parsing and view implementations
- You avoid version conflicts via transitive dependencies e.g. Gson, Android Support Library


Usage
--------

**Step 1.** Initialise the type parser, which works out which type it should render. This is necessary because this library deliberately does not parse any Gson, leaving it to the calling code to use the appropriate library.

```kotlin
Etch.initialise(ObjectTypeParser())
```

**Step 2.** Register to map types an instance of `Etcher`, which builds the view

```kotlin
Etch.register("image", ImageEtcher())
Etch.register("text", TextEtcher())
```

**Step 3.** Etch JSON straight into a view
```kotlin
Etch(json).into(view)
```

Download
--------

**Step 1.** Add the [JitPack](https://jitpack.io) repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {

        maven { url 'https://jitpack.io' }
    }
}
```

**Step 2.** Add the dependency  

```groovy
dependencies {
    implementation 'com.github.JamieCruwys:Etch:master-SNAPSHOT'
}
```