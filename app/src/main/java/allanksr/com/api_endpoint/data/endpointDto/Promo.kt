package allanksr.com.api_endpoint.data.endpointDto


import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Promo(
    @SerializedName("promo_data")
    @Expose
    val promoData: Int,
    @SerializedName("promo_found")
    @Expose
    val promoFound: Boolean = false,
    @SerializedName("promo_type")
    @Expose
    val promoType: String
)