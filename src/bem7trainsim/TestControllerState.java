package bem7trainsim;

import javafx.util.Pair;

import java.awt.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

/**
 * Created by Csuto on 4/18/2017.
 */
public class TestControllerState extends PlayControllerState {
    /**
     * The expected end result of the test case
     */
    private String expectedOutput;

    /**
     * The description of the current test case
     */
    protected String testDescription;

    /**
     * The commands of test cases when running a test
     */
    protected List<String> testCommands;

    /**
     * A printstream for the outputs
     */
    PrintStream out;

    /**
     * @param map The name of the map in string format. Available names in the documentation.
     * @throws IOException thrown when the input is not correct
     */
    public TestControllerState(String map) throws IOException  {
        super(map); // loads the map
    }

    /**
     * Runs the test
     */
    @Override
    public void start() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        out = new PrintStream(baos);

        try {
            moveTrains();
            for(String cmd: testCommands) {
                handleCommand(cmd);
            }
        } catch (CollisionException e) {
            out.println("CollisionException");
        } catch (TableLeftException e) {
            out.println("TableLeftException");
        }
        // TODO: check if succeeded, maybe out should be put to the super
        String content = table.getDrawData();
        content += new String(baos.toByteArray(), StandardCharsets.UTF_8);
        out = System.out;
        boolean success = Objects.equals(content, expectedOutput);
        if(success) {
            out.print("[OK]   ");
        } else {
            out.print("[FAIL] ");
        }
        out.println(testDescription);

        if(!success) {
            out.println("expected:");
            out.println(expectedOutput);
            out.println("got:");
            out.println(content);
        }
    }

    /**
     * Handles the commands but not throwing exceptions.
     * @param command The command in string format. Available commands list is in the documentation.
     * @return The controller state which the controller steps into
     */
    @Override
    public ControllerState handleCommand(String command) {
        try{
            return handleCommandWithoutException(command);
        } catch(CannotSwitchException e){
            out.println("CannotSwitchException");
        }
        catch(CannotBuildException e){
            out.println("CannotBuildException");
        }
        return this;
    }

    /**
     * Tries moving the trains and increments time.
     * @return The controller state which the controller steps into
     */
    @Override
    protected ControllerState tick() {
        currentTime++;
        try {
            moveTrains();
        } catch (CollisionException e) {
            out.println("CollisionException");
            return new MainMenuControllerState();
        } catch (TableLeftException e){
            out.println("TableLeftException");
            return new MainMenuControllerState();
        }
        if(isWin()) {
            return new MainMenuControllerState();
        }
        return this;
    }

    /**
     * @param mapFileName the name of the map in string format. Available names in the documentation.
     * @throws IOException thrown when the input is not correct.
     */
    @Override
    protected void loadMap(String mapFileName) throws IOException {
        TestTableLoader tl = new TestTableLoader();
        table = tl.LoadTable(mapFileName);
        trains = tl.trains;
        upstations = tl.upstations;
        startRail = tl.startRail;
        trainData = tl.trainData;
        testCommands = tl.testCommands;
        testDescription = tl.testDescription;
        expectedOutput = tl.expectedOutput;
    }
}
