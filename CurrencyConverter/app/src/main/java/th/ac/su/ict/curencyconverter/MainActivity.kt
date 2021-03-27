package th.ac.su.ict.curencyconverter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var edtUSA:EditText
    lateinit var CurrencyConverter:TextView
    lateinit var btnCoverter:Button
    lateinit var showRate:TextView

    var BASE_URL = "https://api.exchangeratesapi.io/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtUSA = findViewById<EditText>(R.id.inputUSA)
        CurrencyConverter = findViewById<TextView>(R.id.CurrencyConverter)
        showRate = findViewById<TextView>(R.id.showRate)
        val btnCoverter = findViewById<Button>(R.id.btn_coverter)

        btnCoverter.setOnClickListener {
            getCurrentMoneyData()
        }

    } //override
    fun getCurrentMoneyData() {

        val retrofit =Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(MoneyService::class.java)
        val call = service.getCurrentMoneyData("USD")

        call.enqueue(object : Callback<MoneyResponse>{



            override fun onFailure(call: Call<MoneyResponse>?, t: Throwable?) {
                TODO("Not yet implemented")
            }
            override fun onResponse(
                call: Call<MoneyResponse>?,
                response: Response<MoneyResponse>?

            ) {

                if (response!=null){
                    if (response.code() == 200){

                        val moneyResponse = response.body()
//                        CurrencyConverter.text = moneyResponse.base.toString()
                        val calculate = moneyResponse.rates.THB.toDouble()
                        val exUnit:Double = edtUSA.text.toString().toDouble()
                        val sum = (exUnit*calculate)
                        CurrencyConverter.text = "THB = ${sum.toString()}"

//                        showRate.text = moneyResponse.rates.THB.toString()
                        val showMoneyTHB = moneyResponse.rates.THB.toString()
                        showRate.text = "1 USD = ${showMoneyTHB.toString()}"

                    }
                }
            }
        })
    }
}