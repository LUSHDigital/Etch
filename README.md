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

Setup
--------

**1.** Create a `TypeParser`. This will be used to determine which view should be rendered. You need to provide this because the library does not handle JSON parsing itself.

```kotlin
class ObjectTypeParser: TypeParser() {
    override fun parse(json: String): String { Klaxon().parse<ObjectType>(json)?.type ?: "" }
}
```

```kotlin
data class ObjectType(val type: String)
```

**2.** Initialise using the created `TypeParser` subclass. In this case it is `ObjectTypeParser`

```kotlin
Etch.initialise(ObjectTypeParser())
```

**3.** Create `Etcher` classes for each view type you wish to render. Below is an example for image rendering

```kotlin
class ImageEtcher: Etcher<Image>() {
    override fun parse(json: String): Image? = Klaxon().parse<Image>(json)
    override fun provideLayout(): Int = R.layout.item_picture
    override fun bindView(view: View, model: Image) {
        Picasso.get().load(model.url).into(view.etch_picture)
    }
}
```
```kotlin
data class Image(val url: String)
```

**4.** Register the mapping between view types and `Etcher` rendering classes

```kotlin
Etch.register("image", ImageEtcher())
Etch.register("text", TextEtcher())
```

Usage
--------

```kotlin
Etch(json).into(view)
```

Download
--------

**1.** Add the [JitPack](https://jitpack.io) repository to your build file

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```

**2.** Add the dependency  

```groovy
dependencies {
    implementation 'com.github.JamieCruwys:Etch:master-SNAPSHOT'
}
```