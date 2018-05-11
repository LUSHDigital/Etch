package uk.co.jamiecruwys.etch.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.beust.klaxon.Klaxon
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_picture.view.*
import kotlinx.android.synthetic.main.item_text.view.*
import uk.co.jamiecruwys.etch.Etch
import uk.co.jamiecruwys.etch.Etcher
import uk.co.jamiecruwys.etch.TypeParser

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Picasso.setSingletonInstance(Picasso.Builder(etch_container.context).loggingEnabled(true).build())

        Etch.initialise(ObjectTypeParser())
        Etch.register("image", ImageEtcher())
        Etch.register("text", TextEtcher())
        Etch("{ \"type\": \"text\", \"title\": \"Android P\", \"description\": \"Developer preview now available\" }").into(etch_container)
        Etch("{ \"type\": \"image\", \"url\": \"https://cdn.vivotech.com.br/vivo-tech/wp-content/uploads/2018/04/27064324/io-google-android-p.jpg\" }").into(etch_container)
        Etch("{ \"type\": \"text\", \"title\": \"Picasso by Square\", \"description\": \"A powerful image downloading and caching library for Android\" }").into(etch_container)
        Etch("{ \"type\": \"image\", \"url\": \"https://square.github.io/picasso/static/sample.png\" }").into(etch_container)
        Etch("{ \"type\": \"image\", \"url\": \"https://384uqqh5pka2ma24ild282mv-wpengine.netdna-ssl.com/wp-content/uploads/2015/05/JSON-1.png\" }").into(etch_container)
        Etch("{ \"type\": \"text\", \"title\": \"Title only item\" }").into(etch_container)
        Etch("{ \"type\": \"text\", \"description\": \"Description only item\" }").into(etch_container)
        Etch("{ \"type\": \"image\", \"url\": \"https://mobirank.pl/wp-content/uploads/2018/05/top-10-nowosci-google-io-2018.jpg\" }").into(etch_container)
    }
}

data class ObjectType(val type: String)
class ObjectTypeParser: TypeParser() {
    override fun parse(json: String): String = Klaxon().parse<ObjectType>(json)?.type ?: ""
}

data class Image(val url: String)
class ImageEtcher: Etcher<Image>() {
    override fun parse(json: String): List<Image>? = listOfNotNull(Klaxon().parse<Image>(json))
    override fun provideLayout(): Int = R.layout.item_picture

    // TODO: Fix in/out generic issue so model is of type T instead of Any
    override fun bindView(view: View, model: Any) {
        Picasso.get().load((model as Image).url).into(view.etch_picture)
    }
}

data class Text(val title: String = "", val description: String = "")
class TextEtcher: Etcher<Text>() {
    override fun parse(json: String): List<Text>? = listOfNotNull(Klaxon().parse<Text>(json))
    override fun provideLayout(): Int = R.layout.item_text

    // TODO: Fix in/out generic issue so model is of type T instead of Any
    override fun bindView(view: View, model: Any) {
        val text = (model as Text)

        if (text.title.isBlank()) view.etch_text_title.visibility = View.GONE else view.etch_text_title.visibility = View.VISIBLE
        view.etch_text_title.text = text.title

        if (text.description.isBlank()) view.etch_text_description.visibility = View.GONE else view.etch_text_description.visibility = View.VISIBLE
        view.etch_text_description.text = text.description
    }
}