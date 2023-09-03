# CanYouHearMe
App consists of three screens:

| **Home** | **Results** | **TEST** |
|---|---|---|
|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/d2ba7b60-6df4-4dec-89a6-808e04c71063" width="300"/>|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/95221bee-ac87-4ba4-9573-36fc98afb6e1" width="300"/>|<img src="https://github.com/E5c11/CanYouHearMe/assets/38525610/6b7f04e5-d034-4850-8556-8e332d75f647" width="300"/>|
|  |  |  |

The test screen utilises a countDownTimer to maintain breaks between digits and triplets. The softInput shows and hides according to state of the round. Users are limited to one entry per Digit and moves to the next upon entry.
Once the test is complete the user is navigated to the results screen.

The Result screen detects whether there is a new input in which case it uploads the input then inserts it to the db and observes the db. Once complete the user is informed of there test score.
If navigated from the home screen only the test results are shown. They are arranged from best desc also showing the date on which the test was taken

The Home screen has two buttons navigating to the Test screen which immediately starts the test and to the results screen
