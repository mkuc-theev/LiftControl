# LiftControl
A lift system simulator with a browser-based control panel, written in Java with the use of Spring Boot and Thymeleaf.

## Notable features
- Concurrent support of up to 16 lift carriages (can be changed in the application.yml file)
- Persistence of system status between sessions - you won't lose your IDs and names on reboot
- All user interaction is done through the webpage

# Installation
Run `./gradlew bootJar` on Unix-based systems or `./gradlew.bat bootJar` on Windows to build the executable, which can be accessed under `./build/libs/`

# Running the program
The program requires a Docker container to be running MongoDB on port 27017 Install Docker on your system and run:
`docker run -d --name liftctl-mongo -p 27017:27017 mongo`

Next, start the executable with `java -jar EXECUTABLE_PATH`

Finally, access the control panel by going to `localhost:8080` in your browser.

## Usage
Start by creating a new lift carriage under "Add a carriage". Give it a name and click Create.

You can either simulate pressing a call button on a floor under "Call carriage to floor" or simulate pressing a floor button inside the elevator by specifying the floor and clicking the "Send" button on the newly created carriage.

Click the "Next step" button to advance the simulation forward and watch the carriage move to the target floor. If you send the carriage to more floors, they'll appear in its current queue status.

The queue is a sequential list of the floors the carriage will head to next.

If you press the call button while no carriage is in a state where accepting that call would induce a long wait, it'll appear in the call queue, which is evaluated on every step.

The "Initialize" button fetches all the stored carriages from the database and sets all the carriages to floor 0, with an empty queue.

# How it works

The call button is a request rather than a command - the carriage checks where it is, the direction it's traveling in and the direction in which the caller wants to travel in order to determine whether it'll take the call or not.

The send button always inserts the specified floor into the queue as long as it's not already in the queue. This works as follows:

- If the elevator is idle, the floor is added to the queue
- If the elevator has already moved past the floor, it'll be added to the end of the queue.
- Otherwise, the floor is inserted into the queue depending on the current status of the queue and direction of travel
