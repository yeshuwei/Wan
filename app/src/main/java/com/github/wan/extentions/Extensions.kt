package com.github.wan.extentions

import android.app.ProgressDialog.show
import android.content.Context
import android.net.ConnectivityManager
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.github.wan.R
import com.squareup.picasso.Picasso


fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, @IdRes frameId: Int) {
    supportFragmentManager.transact {
        replace(frameId, fragment)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, tag: String) {
    supportFragmentManager.transact {
        add(fragment, tag)
    }
}

fun AppCompatActivity.addFragmentToActivity(fragment: Fragment, @IdRes containerViewId: Int, tag: String? = null) {
    supportFragmentManager.transact {
        add(containerViewId, fragment, tag)
    }
}

/**
 * show a fragment
 */
fun AppCompatActivity.showFragmentToActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        if (fragment.isAdded) {
            hide(fragment)
        }
        //显示一个[被隐藏!!]的fragment
        show(fragment)
    }
}

/**
 * hide a fragment
 */
fun AppCompatActivity.hideFragmentFromActivity(fragment: Fragment) {
    supportFragmentManager.transact {
        hide(fragment)
    }
}


/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}

/**
 * load image by picasso
 */
fun ImageView.loadingImage(imaUrl: String) {
    if (TextUtils.isEmpty(imaUrl)) {
        Picasso.get().load(R.mipmap.ic_launcher).into(this)
    } else {
        Picasso.get().load(imaUrl).into(this)
    }
}

/**
 * show snackBar
 */
fun Fragment.showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun Fragment.showSnackbar(view: View, @StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun AppCompatActivity.showSnackbar(view: View, message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

fun AppCompatActivity.showSnackbar(view: View, @StringRes message: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(view, message, duration).show()
}

/**
 * show toast
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

/**
 * check net work
 */
fun networkConnected(context: Context): Boolean {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = manager.activeNetworkInfo
    if (info != null) {
        return info.isAvailable
    }
    return false
}

/**
 * 添加数据到list之前 是否清空
 */

fun <T> addDataToList(dataList: MutableList<T>?, originData: List<T>?, clear: Boolean = false): Boolean {
    if (dataList == null) {
        return false
    }
    if (clear) {
        dataList.clear()
    }
    return if (originData != null) dataList.addAll(originData) else false
}

/**
 * 避免每次都要写xx::class.java
 */
inline fun <reified T> genericClass() = T::class.java
