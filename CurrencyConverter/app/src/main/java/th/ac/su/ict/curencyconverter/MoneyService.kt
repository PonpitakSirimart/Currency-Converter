package th.ac.su.ict.curencyconverter

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoneyService {
    @GET("latest?")
    fun getCurrentMoneyData(@Query("base")exUnit:String
    ): Call<MoneyResponse>

}