package allanksr.com.api_endpoint.common

import android.content.Context
import android.widget.Toast


fun Context.toastShort(message: String){
    Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
}
fun Context.toastLong(message: String){
    Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
}