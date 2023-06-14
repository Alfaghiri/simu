import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // Create a new experiment
        Experiment exp = new Experiment("Makerspace Experiment");

        // Create a new makerspace model
        Makerspace makerspace = new Makerspace(null, "Makerspace Model", true);

        // Connect the model with the experiment
        makerspace.connectToExperiment(exp);

        // Set experiment parameters
        exp.setShowProgressBar(false);  // not necessary, but possible
        exp.stop(new TimeInstant(600, TimeUnit.MINUTES));
        exp.tracePeriod(new TimeInstant(0), new TimeInstant(500, TimeUnit.MINUTES));
        exp.debugPeriod(new TimeInstant(0), new TimeInstant(250, TimeUnit.MINUTES));

        // Start the experiment at simulation time 0.0
        exp.start();

        // --> now the simulation is running until it reaches its end criterion
        // ...
        // ...
        // <-- afterwards, the main thread returns here

        // Generate the report(s)
        exp.report();

        // Finish the experiment
        exp.finish();
    }
}
