package uk.co.jamiecruwys.etch

import android.util.NoSuchPropertyException
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import org.json.JSONException
import java.util.NoSuchElementException

class Etch(private val json: String) {
    companion object {
        private val etchers = LinkedHashMap<String, Etcher<Any>>()
        private var typeParser: TypeParser? = null
        fun initialise(typeParser: TypeParser) { Companion.typeParser = typeParser }
        fun register(type: String, etcher: Etcher<Any>) { etchers[type] = etcher }
    }

    fun into(root: LinearLayout) {
        typeParser ?: throw NullPointerException("Type parser is not set")
        val type = typeParser?.parse(json) ?: throw NoSuchPropertyException("No type property found for model")
        val etcher = etchers[type] ?: throw NoSuchElementException("No matching element for type")
        val models = etcher.parse(json) ?: throw JSONException("Failed to parse json")
        for (model in models) {
            val view = LayoutInflater.from(root.context).inflate(etcher.provideLayout(), root, false)
            root.addView(view)
            etcher.bindView(view, model)
        }
    }
}

abstract class Etcher<out T> {
    abstract fun parse(json: String): List<T>?
    abstract fun provideLayout(): Int
    // TODO: Fix in/out generic issue so model is of type T instead of Any
    abstract fun bindView(view: View, model: Any)
}

abstract class TypeParser {
    abstract fun parse(json: String): String
}