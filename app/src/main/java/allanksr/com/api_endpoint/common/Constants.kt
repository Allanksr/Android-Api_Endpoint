package allanksr.com.api_endpoint.common

import allanksr.com.api_endpoint.BuildConfig

object Constants {

    const val maxPromoLength = 15

    const val endPointUrl = "https://script.google.com/macros/s/${BuildConfig.API_KEY}"
    //ipconfig in ms-dos to find your IPv4 address, something like -> 192.168.0.10
    const val localEndPointUrl = "http://IPv4Address:3000/"
    const val dataBaseName = "dataBaseName_db"
}