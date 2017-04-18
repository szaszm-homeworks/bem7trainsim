package bem7trainsim;

import javafx.util.Pair;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Csuto on 4/18/2017.
 */
public class PlayControllerState extends ControllerState {

    /**
     * The game table
     */
    protected Table table;

    /**
     * The list of the trains which have already started
     */
    protected List<Train> trains;

    /**
     * The list of UpStations
     */
    protected List<UpStation> upstations;

    /**
     * The starting rail of the trains. Needed for train construction.
     */
    protected Rail startRail;

    /**
     * The start time and wagons of the trains which have not yet started
     */
    protected List<Pair<Integer, List<Wagon>>> trainData;

    /**
     * The current timestamp
     */
    protected int currentTime = 0;

    /**
     * @param map The name of the map in string format. Available names in the documentation.
     * @throws IOException thrown when the input is not correct.
     */
    public PlayControllerState(String map) throws IOException {
        loadMap(map);
    }

    /**
     * Starts the movement of the trains
     * @throws CollisionException thrown when trains collide
     * @throws TableLeftException thrown when a not empty train leaves the table
     */
    public void start() throws CollisionException, TableLeftException {
        moveTrains();
        System.out.println(table.getDrawData());
    }

    /**
     * Handles the commands but not throwing exceptions.
     * @param command The command in string format. Available commands list is in the documentation.
     * @return The controller state which the controller steps into
     */
    @Override
    public ControllerState handleCommand(String command) {
        try{
            ControllerState newState = handleCommandWithoutException(command);
            System.out.println(table.getDrawData()); // Draws the table if there was no exception
            return newState;
        } catch(CannotSwitchException | CannotBuildException e){
            System.out.println(e.getMessage());
        }
        return this;
    }

    /**
     * Handles the command and throws exceptions while doing so.
     * @param command The command in string format. Available commands list is in the documentation.
     * @return The controller state which the controller steps into
     * @throws CannotSwitchException thrown when the switch cannot switch
     * @throws CannotBuildException thrown when a tunnel entrance cannot be built
     */
    protected ControllerState handleCommandWithoutException(String command) throws CannotSwitchException, CannotBuildException {
        String[] s = command.split(" ");
        switch (s[0]) {
            //switch x y
            case "switch":
            {
                int x = Integer.parseInt(s[1]);
                int y = Integer.parseInt(s[2]);
                table.switchAt(x - 1, y - 1);
            }
            break;
            // build x y
            case "build":
            {
                int x = Integer.parseInt(s[1]);
                int y = Integer.parseInt(s[2]);
                table.buildAt(x - 1, y - 1);
            }
            break;
            //enter 10
            case "enter":
                if (s.length > 1) {
                    int moveTimes = Integer.parseInt(s[1]);
                    for (int i = 0; i < moveTimes; i++) {
                        ControllerState newState = tick();
                        if (newState != this) {
                            return newState;
                        }
                    }
                }
                //default = enter 1
            default:
                return tick();
        }
        return this;
    }

    /**
     * Moves the trains by one step
     * @throws CollisionException thrown when trains collide
     * @throws TableLeftException thrown when a not empty train leaves the table
     */
    protected void moveTrains() throws CollisionException, TableLeftException {
        // Firstly move existing trains
        for (Train train: trains) {
            train.move();
        }

        // Secondly add new trains as their constructor already moves them to the starting rail
        for(Iterator<Pair<Integer, List<Wagon>>> iterator = trainData.iterator(); iterator.hasNext();) {
            Pair<Integer, List<Wagon>> pair = iterator.next();
            int start = pair.getKey();
            if(start == currentTime + 1) {
                List<Wagon> wagons = pair.getValue();
                trains.add(new Train(startRail, table, wagons));
                iterator.remove();
            }
        }
    }

    /**
     * Tries moving the trains and increments time.
     * @return The controller state which the controller steps into
     */
    protected ControllerState tick() {
        currentTime++;
        try {
            moveTrains();
        } catch (CollisionException e) {
            System.out.println("Utkozes, jatek vege. Ido: "+Integer.toString(currentTime));
            return new MainMenuControllerState();
        } catch (TableLeftException e){
            System.out.println("Nem ures vonat elhagyta a palyat, jatek vege. Ido: "+Integer.toString(currentTime));
            return new MainMenuControllerState();
        }
        System.out.println(table.getDrawData());
        if(isWin()) {
            System.out.println("Pálya sikeresen teljesítve. Ido: " + Integer.toString(currentTime));
            return new MainMenuControllerState();
        }
        return this;
    }

    /**
     * @param mapFileName the name of the map in string format. Available names in the documentation.
     * @throws IOException thrown when the input is not correct.
     */
    protected void loadMap(String mapFileName) throws IOException{
        TableLoader tl = new TableLoader();
        table = tl.LoadTable(mapFileName);
        trains = tl.trains;
        upstations = tl.upstations;
        startRail = tl.startRail;
        trainData = tl.trainData;
    }

    /**
     * @return if the winning condition is fulfilled returns true, else false.
     */
    protected boolean isWin(){
        boolean win = true;
        for (Train train: trains) {
            if (!train.isEmpty())
                win = false;
        }
        for(UpStation upstation: upstations){
            if(!upstation.isGone())
                win = false;
        }
        if(!trainData.isEmpty())
            win = false;
        return  win;
    }
    @Override
    public void changedTo() {
    }
}
