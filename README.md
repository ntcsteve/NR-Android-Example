## NR-Android-Example

New Relic Mobile allows you to monitor and manage the performance of your iOS and Android applications by providing end-to-end details, errors, and throughput from every angle in real time.

You can also examine HTTP and other network performance for unexpected lag, which will in turn help you collaborate more efficiently with your backend teams.

## Security for mobile apps

To protect your mobile application's security and your users' data privacy, New Relic Mobile only records performance data. The agent does not collect any data used or stored by the monitored app.

The New Relic Mobile product is part of your iOS or Android app and lives within the application's "sandbox," so it cannot access anything other than performance data from your mobile app. We do not collect performance data about the device itself, such as battery level.

## Instrumentation added to your code

The Mobile SDK agent injects code into certain method calls within your application in order to collect performance data. This allows us to time and monitor the inputs and outputs of various APIs.

**By default, New Relic Mobile collects automatically many important performance Interactions, Breadcrumbs and Events from your mobile app.**

This Android example is great if you like to learn more about setting up custom instrumentation to measure specific items in your mobile app.

Instructions:

1. Go to NR-Android-Example/app/src/main/java/com/example/hands_onlab/

2. Look for NewRelic.withApplicationToken(
   "REPLACE THIS WITH YOUR NEW RELIC MOBILE LICENSE KEY"
   ).start(this.getApplication());

3. This application token is similar to a New Relic license key and is required to authenticate your app with New Relic.

4. Once you insert your Mobile application token, just scroll down and you should see a few comments in MainActivity.kt with some examples.

5. Build the mobile app in your Android Studio with an emulator. You can play around with the Android sample app to gain a better understanding in using the Android SDK API.

Reference Links:

-   [Viewing Application Token](https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile/maintenance/viewing-your-application-token)

-   [Android SDK API Guide](https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile-android/api-guides/android-sdk-api-guide)

-   [iOS SDK API Guide](https://docs.newrelic.com/docs/mobile-monitoring/new-relic-mobile-ios/api-guides/ios-sdk-api-guide)
