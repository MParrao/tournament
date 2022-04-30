# Tournament Project

This project is in charged to calculate the results of preformatted tournament matches

### Expected input

The file that will be processed by the program needs to follow the below format:

```
Lions 3, Snakes 3
Tarantulas 1, FC Awesome 0
Lions 1, FC Awesome 1
Tarantulas 3, Snakes 1
Lions 4, Grouches 0
```

The expected output from above input is the following:

```
1. Tarantulas, 6 pts
2. Lions, 5 pts
3. FC Awesome, 1 pt
3. Snakes, 1 pt
5. Grouches, 0 pts
```

### How to run the program?

1. Download/Clone this repository and run `mvn clean install`.
2. Once the process is done run `java -classpath .\target\classes org.tournament.Tournament {file_path}`.
   Being `{file_path}` the absolute path of the file containing the tournament information