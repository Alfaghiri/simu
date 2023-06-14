import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.concurrent.TimeUnit;

public class MakerspaceExperiment {
    public static class Main {
        public static void main(String[] args) {
            MakerspaceModel model = new MakerspaceModel(null, "Makerspace Model", true, false);
            Experiment exp = new Experiment("Makerspace Experiment", TimeUnit.SECONDS, TimeUnit.MINUTES, null);
            model.connectToExperiment(exp);
            exp.setShowProgressBar(false);
            exp.stop(new TimeInstant(480, TimeUnit.MINUTES)); // Run for 8 hours
            exp.start();
            exp.report();
            exp.finish();
        }
    }
}