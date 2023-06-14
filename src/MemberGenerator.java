import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MemberGenerator extends SimProcess {

    private MakerspaceModel model;
    private Random random;

    public MemberGenerator(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.model = (MakerspaceModel)owner;
        this.random = new Random();
    }

    public void lifeCycle() {
        int count = 0;
        while (count < 2000) {
            try {
                // Auswahl eines Maschinentyps auf zufälliger Basis
                String[] machineTypes = {"Lasercutter", "3D-Drucker", "CNC-Maschine","Schneidplotter", "Nähmaschinen","Overlock","Stickmaschine","Metallaser","Elektrosäge","Schweißgerät","Transferpresse"};
                String machineType = machineTypes[random.nextInt(machineTypes.length)];

                new Member(model, "Member", machineType, true).activate();
                double arrivalTimeSample = model.memberArrivalTime.sample()/2;
                if (arrivalTimeSample <= 0 || Double.isInfinite(arrivalTimeSample)) {
                    System.out.println("Invalid arrival time sample: " + arrivalTimeSample);
                    continue;
                }
                hold(new TimeSpan(arrivalTimeSample));
                count++;
            } catch (co.paralleluniverse.fibers.SuspendExecution e) {
                e.printStackTrace();
            }
        }
    }
}