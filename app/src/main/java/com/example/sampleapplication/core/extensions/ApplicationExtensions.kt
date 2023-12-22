package com.example.sampleapplication.core.extensions

import android.app.Activity
import android.app.Application
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Rect
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.example.sampleapplication.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.Serializable
import java.util.concurrent.TimeUnit

/**
 * Returns text by string expression of resource id
 * f.e. resId = "message_no_app_for_action"
 */
fun Application.getStringByResIdName(resId: String): String {
    val id = resources.getIdentifier(resId, "string", packageName)
    return if (id != 0) getString(id) else ""
}

/**
 * Returns drawable resource id by string expression of resource id
 */
fun Application.getDrawableResId(resId: String): Int? {
    val id = resources.getIdentifier(resId, "drawable", packageName)
    return if (id != 0) id else null
}

/**
 * Returns color resource id by string expression of resource id
 */
fun Application.getColorResId(resId: String): Int? {
    val id = resources.getIdentifier(resId, "color", packageName)
    return if (id != 0) id else null
}

fun Context.dpToPx(value: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resources.displayMetrics)
}

fun Context.isTablet(): Boolean = resources.getBoolean(R.bool.is_tablet)

/**
 * Method allows make subscribing easy.
 * You need just set the LiveData and get the data from lambda
 *
 *
 * @param liveData the source, which will provide data
 * @param observer The observer that will receive the events
 */
inline fun <T> Fragment.observe(liveData: LiveData<T>, crossinline observer: (T) -> Unit) {
    liveData.observe(
        viewLifecycleOwner
    ) {
        observer(it)
    }
}

/**
 * Method allows make [LiveData] subscribing easy.
 * Observes last value only in [Lifecycle.State.RESUMED] state
 * You need just set the LiveData and get the data from lambda
 *
 * @param liveData the source, which will provide data
 * @param observer The observer that will receive the events
 */
inline fun <T> Fragment.observeResumeState(
    liveData: LiveData<T>,
    crossinline observer: (T) -> Unit
) {
    liveData.observe(
        viewLifecycleOwner,
        ResumedEventObserver(viewLifecycleOwner) {
            observer.invoke(it)
        }
    )
}

inline fun <T> AppCompatActivity.observeResumeState(
    liveData: LiveData<T>,
    crossinline observer: (T) -> Unit
) {
    liveData.observe(
        this,
        ResumedEventObserver(this) {
            observer.invoke(it)
        }
    )
}

class ResumedEventObserver<T, F : LifecycleOwner>(
    private val lifecycleOwner: F,
    private val observer: (T) -> Unit
) : Observer<T> {
    private var doOnResumeJob: Job? = null

    override fun onChanged(event: T) {
        doOnResumeJob?.cancel()
        doOnResumeJob = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.withResumed {
                observer.invoke(event)
            }
        }
    }
}

/**
 * Method allows make subscribing easy.
 * You need just set the LiveData and get the data from lambda
 *
 *
 * @param liveData the source, which will provide data
 * @param observer The observer that will receive the events
 */
inline fun <T> AppCompatActivity.observe(liveData: LiveData<T>, crossinline observer: (T) -> Unit) {
    liveData.observe(
        this
    ) {
        observer(it)
    }
}

fun Context.isPermissionGranted(permission: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Context.shareLink(chooserHeader: String, link: String, description: String = "") {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        putExtra(Intent.EXTRA_TEXT, link)
        putExtra(Intent.EXTRA_SUBJECT, description)
        type = "text/plain"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    try {
        startActivity(
            Intent.createChooser(
                shareIntent,
                chooserHeader
            )
        )
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
    } catch (t: Throwable) {
        t.printStackTrace()
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

/**
 * Call this method (in onOnViewCreated f.e.) to set
 * the width of the dialog to a percentage of the current
 * screen width.
 */
fun DialogFragment.setWidthPercent(percentage: Int) {
    val percent = percentage.toFloat() / 100
    val dm = Resources.getSystem().displayMetrics
    val rect = dm.run { Rect(0, 0, widthPixels, heightPixels) }
    val percentWidth = rect.width() * percent
    dialog?.window?.setLayout(percentWidth.toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.isDarkMode(): Boolean {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}

fun interval(time: Long, timeUnit: TimeUnit, emitImmediately: Boolean = true): Flow<Long> = flow {
    var counter: Long = 0
    var initialEmission = false

    val delayTime = when (timeUnit) {
        TimeUnit.MICROSECONDS -> time / 1000
        TimeUnit.NANOSECONDS -> time / 1_000_000
        TimeUnit.SECONDS -> time * 1000
        TimeUnit.MINUTES -> 60 * time * 1000
        TimeUnit.HOURS -> 60 * 60 * time * 1000
        TimeUnit.DAYS -> 24 * 60 * 60 * time * 1000
        else -> time
    }

    if (emitImmediately) {
        emit(counter++)
        initialEmission = true
    }

    while (true) {
        if (initialEmission) {
            delay(delayTime)
        } else {
            initialEmission = true
        }
        emit(counter++)
    }
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Intent.parcelableList(key: String): List<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra(key)
}

inline fun <reified T : Parcelable> Intent.requireParcelable(key: String): T = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java) as T
    else -> @Suppress("DEPRECATION") getParcelableExtra<T>(key) as T
}

inline fun <reified T : Parcelable> Intent.requireParcelableList(key: String): List<T> = when {
    SDK_INT >= 33 -> getParcelableArrayListExtra(key, T::class.java) as List<T>
    else -> @Suppress("DEPRECATION") getParcelableArrayListExtra<T>(key) as List<T>
}

inline fun <reified T : Serializable> Intent.serializable(key: String): T? = when {
    SDK_INT >= 33 -> getSerializableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as? T
}


inline fun <reified T : Serializable> Intent.requireSerializable(key: String): T = when {
    SDK_INT >= 33 -> getSerializableExtra(key, T::class.java) as T
    else -> @Suppress("DEPRECATION") getSerializableExtra(key) as T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelableList(key: String): List<T>? = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableArrayList(key)
}

inline fun <reified T : Parcelable> Bundle.requireParcelable(key: String): T = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java) as T
    else -> @Suppress("DEPRECATION") getParcelable<T>(key) as T
}

inline fun <reified T : Parcelable> Bundle.requireParcelableList(key: String): List<T> = when {
    SDK_INT >= 33 -> getParcelableArrayList(key, T::class.java) as List<T>
    else -> @Suppress("DEPRECATION") getParcelableArrayList<T>(key) as List<T>
}

inline fun <reified T : Serializable> Bundle.serializable(key: String): T? = when {
    SDK_INT >= 33 -> getSerializable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getSerializable(key) as? T
}

inline fun <reified T : Serializable> Bundle.requireSerializable(key: String): T = when {
    SDK_INT >= 33 -> getSerializable(key, T::class.java) as T
    else -> @Suppress("DEPRECATION") getSerializable(key) as T
}