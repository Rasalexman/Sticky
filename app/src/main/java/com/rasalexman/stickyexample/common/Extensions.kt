package com.rasalexman.stickyexample.common

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment

const val YUI = "----->"

data class ScrollPosition(var index: Int = 0, var top: Int = 0) {
    fun drop() {
        index = 0
        top = 0
    }
}

inline fun <reified T, reified R> R.unsafeLazy(noinline init: () -> T): Lazy<T> =
    lazy(LazyThreadSafetyMode.NONE, init)

inline fun <reified T : Fragment> T.toast(message: String, duration: Int = Toast.LENGTH_SHORT) =
    Toast.makeText(this.context, message, duration).show()

fun TextView.clear() {
    this.text = null
    this.setOnClickListener(null)
}

fun ImageView.clear(isOnlyImage: Boolean = false) {
    this.setImageResource(0)
    this.setImageBitmap(null)
    this.setImageDrawable(null)
    if (isOnlyImage) this.setOnClickListener(null)
}

fun View.hide(gone: Boolean = true) {
    visibility = if (gone) View.GONE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun <T, R> ObservableField<T>.map(
    block: (T?) -> R
): ObservableField<R> {
    val current = this
    return object : ObservableField<R>(current) {
        override fun get(): R? {
            return block.invoke(current.get())
        }
    }
}

fun <T, R> ObservableField<T>.switchMap(
    block: (T?) -> ObservableField<R>
): ObservableField<R> {
    val current = this
    val fresh = block.invoke(current.get())
    val newBlock = ObservableField<R>(current, fresh)
    /*current.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            fresh.set(current.get())
        }
    })*/

    return newBlock
}

fun <T, R> ObservableField<T>.combine(
    vararg fields: ObservableField<T>,
    block: (List<T>) -> R
): ObservableField<R> {
    val fieldsCount = fields.size

    val combined: ObservableField<R> = when(fieldsCount) {
        1 -> object : ObservableField<R>(this, fields[0]) {
            override fun get(): R? {
                val fieldValues = this@combine.get()?.let { listOf(it) } ?: emptyList()
                val inputFieldsValues = fields.mapNotNull { it.get() }
                val allFieldsValues = fieldValues + inputFieldsValues
                return block.invoke(allFieldsValues)
            }
        }
        2 -> object : ObservableField<R>(this, fields[0], fields[1]) {
            override fun get(): R? {
                val fieldValues = this@combine.get()?.let { listOf(it) } ?: emptyList()
                val inputFieldsValues = fields.mapNotNull { it.get() }
                val allFieldsValues = fieldValues + inputFieldsValues
                return block.invoke(allFieldsValues)
            }
        }
        3 -> object : ObservableField<R>(this, fields[0], fields[1], fields[2]) {
            override fun get(): R? {
                val fieldValues = this@combine.get()?.let { listOf(it) } ?: emptyList()
                val inputFieldsValues = fields.mapNotNull { it.get() }
                val allFieldsValues = fieldValues + inputFieldsValues
                return block.invoke(allFieldsValues)
            }
        }
        4 -> object : ObservableField<R>(this, fields[0], fields[1], fields[2], fields[3]) {
            override fun get(): R? {
                val fieldValues = this@combine.get()?.let { listOf(it) } ?: emptyList()
                val inputFieldsValues = fields.mapNotNull { it.get() }
                val allFieldsValues = fieldValues + inputFieldsValues
                return block.invoke(allFieldsValues)
            }
        }

        else -> object : ObservableField<R>(this, fields[0]) {
            override fun get(): R? {
                val fieldValues = this@combine.get()?.let { listOf(it) } ?: emptyList()
                val inputFieldsValues = fields.mapNotNull { it.get() }
                val allFieldsValues = fieldValues + inputFieldsValues
                return block.invoke(allFieldsValues)
            }
        }
    }
    return combined
}