package com.example.hands_onlab

// In your default Activity, import the NewRelic class.
import com.newrelic.agent.android.NewRelic;

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import kotlin.coroutines.CoroutineContext


class MainActivity : AppCompatActivity(), CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
    get() = Dispatchers.Main + job

    val service: HttpBinAPI by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://httpbin.org/")
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(HttpBinAPI::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpUi()

        // Add this call to initialize New Relic.
        NewRelic.withApplicationToken(
            "REPLACE THIS WITH YOUR NEW RELIC MOBILE LICENSE KEY"
        ).start(this.getApplication());
    }

    private fun setUpUi() {
        //set scrolling
        responseText.movementMethod = ScrollingMovementMethod()
        // Button methods
        getButton.setOnClickListener { launch { getButton() } }
        postButton.setOnClickListener { launch { postButton() } }
        putButton.setOnClickListener { launch { putButton() } }
        deleteButton.setOnClickListener { launch { deleteButton() } }
        downloadButton.setOnClickListener { launch { downloadButton() } }
        errorButton.setOnClickListener { launch { errorButton() } }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private suspend fun getButton(){
        val response = service.getButton().await()
        responseText.text = "Get Response : \n${response.body()}"
        Toast.makeText(this, "Get Button clicked", Toast.LENGTH_SHORT).show()

        // Set a session attribute which will be added to all events created during the session.
        // A session is when the app is in the foreground. When the app is sent to background for any reason the session ends.
        NewRelic.setAttribute("vehicleId", "SampleVehicleId-001")

        // Create an interaction to instrument a method in your Android app code.
        NewRelic.startInteraction("getButton")
    }

    private suspend fun postButton(){
        val response = service.postButton(body = PostBody()).await()
        responseText.text = "Post Response :\n${response.body()}"
        Toast.makeText(this, "Post Button clicked", Toast.LENGTH_SHORT).show()


        // Creates and records a custom event, for use in New Relic Dashboards.
        // The event includes a list of attributes, specified as a map.
        val attributes = mutableMapOf<String, Any?>()
        attributes["make"] = "Toyota"
        attributes["model"] = "Camry"
        attributes["color"] = "Black"
        attributes["VIN"] = "123XYZ"
        attributes["maxSpeed"] = 20
        NewRelic.recordCustomEvent("Car", attributes)

        // Create an interaction to instrument a method in your Android app code.
        NewRelic.startInteraction("postButton")
    }

    private suspend fun putButton(){
        val response = service.putButton(body = PostBody(name = "larry",job = "bobo")).await()
        responseText.text = "Put Response :\n${response.body()}"
        Toast.makeText(this, "Put Button clicked", Toast.LENGTH_SHORT).show()

        // This is a specialized API to create a session attribute called userId.
        // Since this is a session attribute, userId will appear on all events created during a given session.
        NewRelic.setUserId("SampleUserName")

        // Create an interaction to instrument a method in your Android app code.
        NewRelic.startInteraction("putButton")
    }

    private suspend fun deleteButton(){
        val response = service.deleteButton().await()
        responseText.text = "Delete Response :\n${response.body()}"
        Toast.makeText(this, "Delete Button clicked", Toast.LENGTH_SHORT).show()

        // Create events under the MobileBreadcrumb event type with a name and additional attributes.
        // Similar to setUserId this is a specialized API to create custom events in an event type called MobileBreadcrumb.
        // You can place this call in various parts of an app to monitor certain actions or collect additional details.
        // This is commonly used to track activity leading up to a crash in the Mobile Crash Event Trail.
        val attributes = mutableMapOf<String, Any?>()
        attributes["button"] = "delete"
        attributes["location"] = "MainActivity"
        NewRelic.recordBreadcrumb("user tapped delete button", attributes)

        // Create an interaction to instrument a method in your Android app code.
        NewRelic.startInteraction("deleteButton")
    }

    private suspend fun downloadButton(){
        val response = service.downloadButton().await()
        responseText.text = "Download Response :\n${response.body()}"
        Toast.makeText(this, "Download Button clicked", Toast.LENGTH_SHORT).show()

        // Throws a demo run-time exception named java.lang.RuntimeException to test New Relic crash reporting.
        // This will cause the app to crash along with a custom message. This is useful for looking at how a crash will appear in Crash Analysis UI.
        NewRelic.crashNow("This is my CRASH message")
    }

    private suspend fun errorButton(){
        val response = service.errorButton().await()
        responseText.text = "Error Response : \n${response.errorBody()?.string() ?: "there is no errorBody string"}"
        Toast.makeText(this, "Error Button clicked", Toast.LENGTH_SHORT).show()

        val num1 = 0
        val num2 = 42

        try {
            val result = num2 / num1

        } catch (e: Exception) {
            val attributesMap = mapOf<String, Any>(Pair("num1",num1),Pair("num2",num2))
            Toast.makeText(this, "num1: " + num1 + " num2: " + num2, Toast.LENGTH_SHORT).show()

            // Record a handled exception event. Additional attributes can be added for context.
            NewRelic.recordHandledException(e, attributesMap)
        }
    }
}
