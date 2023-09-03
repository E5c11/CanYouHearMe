# CanYouHearMe
App consists of three screens:

| **Home** | **Results** | **TEST** |
|---|---|---|
|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/eb354b82-0db4-49dd-b9a5-59625fb94a2a" width="300"/>|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/a72682d3-b954-4afa-8d4c-4c1931709872" width="300"/>|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/d894a472-bbe5-4f68-af43-c6b387888da8" width="300"/>|
|  |  |  |

The test screen utilises a countDownTimer to maintain breaks between digits and triplets. The softInput shows and hides according to state of the round. Users are limited to one entry per Digit and moves to the next upon entry.
Once the test is complete the user is navigated to the results screen.

The Result screen detects whether there is a new input in which case it uploads the input then inserts it to the db and observes the db. Once complete the user is informed of there test score.
If navigated from the home screen only the test results are shown. They are arranged from best desc also showing the date on which the test was taken

The Home screen has two buttons navigating to the Test screen which immediately starts the test and to the results screen

I have gone with an MVVM architecture
This allows a clean model for separation of concerns. 
I am using Kotlin flow to keep state and communicate between the layers.
Dependency Injection is used throughout, keeping to viewmodel scoping thereby conserving memory.

|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/1f45c872-e541-4c0c-8454-7238c1afcfc1" width="1200"/>|
