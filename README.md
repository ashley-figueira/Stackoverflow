# Simple Stackoverflow app

Stackoverflow app that fetches a list of stackoverflow users and displays it in a screen. 
It also includes a detail screen with the possability to follow a user and block a user.

![](/sample/Screenshot_1563037062.png)
![](/sample/Screenshot_1563037044.png)
![](/sample/Screenshot_1563037108.png)

# Goal

- When the app is launched, the user should be able to see a list of the top 20 StackOverflow users
- Each list item should contain user's profile image, name and reputation
- If the server is unavailable (e.g. offline), the user should see a list empty state with an error message
- When a server‑unavailable message is displayed, as soon as connectivity is restored, the application should refresh
 the list from the network and display it to the user (this scenario can be simulated switching on and off airplane mode
 on the device)
- The user should be able to tap on a list item and open a details view screen
- The details view should display user's name, id, profile image, reputation, location and creation date
- Have cells with additional options to 'follow' and 'block' a user
- Users that are followed should show an indicator in the main part of the list item and in the details view
- Users that are blocked should show in a disabled greyed‑out list item; tapping on the item should not open the details
view
- User details and following/blocking states should be persisted across screen configuration changes
- Include 'unfollow' and 'unblock' options in the view

# Architecture 

The app follows a clean architecture pattern with 3 layers (data, domain and ui (app)).  
The domain layer is where the entities and use cases are, ie, GetUsersUseCase or UserEntity.  
The data layer is where all the logic behind fetching and persisting is included.  
The data layer uses the repository pattern and takes advantages of Retrofit and Room.  
In the UI layer view models are being used to abstract the presentation logic away from the actual fragments.  
For the UI layer a sealed class is used to describe the state of the screen, called ScreenState.  

Uses cases, repositories and view models are unit tested.  

The project uses heavily RxJava and the observable pattern to automatically updated users when something has changed. 
For example, when user starts following someone the list is automatically updated.

# Libraries and tools

- Kotlin
- AndroidX
- Support libraries
- Android Architecture components (LiveData, ViewModel, Room & Navigation)
- [RxJava](https://github.com/ReactiveX/RxJava) and [RxAndroid](https://github.com/ReactiveX/RxAndroid) 
- [Retrofit 2](http://square.github.io/retrofit/)
- [Dagger 2](http://google.github.io/dagger/)
- [Timber](https://github.com/JakeWharton/timber)

Testing Libraries

- [jUnit](http://junit.org/junit5/)
- [Mockito](https://github.com/mockito/mockito)
