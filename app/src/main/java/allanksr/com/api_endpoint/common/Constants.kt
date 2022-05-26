package allanksr.com.api_endpoint.common

import allanksr.com.api_endpoint.BuildConfig

object Constants {

    const val maxPromoLength = 15

    const val endPointUrl = "https://script.google.com/macros/s/${BuildConfig.API_KEY}"
    const val localEndPointUrl = "http://192.168.0.18:3000/"
    const val dataBaseName = "dataBaseName_db"
}