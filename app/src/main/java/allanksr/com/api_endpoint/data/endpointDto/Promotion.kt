package allanksr.com.api_endpoint.data.endpointDto


import androidx.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Promotion(
    @SerializedName("promo")
    @Expose
    val promo: List<Promo>
)