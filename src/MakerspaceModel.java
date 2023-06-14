import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.Map;
import java.util.HashMap;
import desmoj.core.statistic.Tally;
import java.util.concurrent.TimeUnit;

public class MakerspaceModel extends Model {

    protected Map<String, Queue<Machine>> machineQueues;
    protected Queue<Member> memberQueue;
    protected ContDistExponential memberArrivalTime;
    protected ContDistExponential machineServiceTime;
    protected ContDistExponential memberServiceTime;
    public Tally waitingTimeTally;
    public Tally queueLengthTally; // Neue Statistik für die Länge der Warteschlange

    public MakerspaceModel(Model owner, String name, boolean showInTrace, boolean showInReport) {
        super(owner, name, showInTrace, showInReport);
    }

    public String description() {
        return "Makerspace Model";
    }

    public void doInitialSchedules() {
        String[] machineTypes = {"Lasercutter", "3D-Drucker", "CNC-Maschine","Schneidplotter", "Nähmaschinen","Overlock","Stickmaschine","Metallaser","Elektrosäge","Schweißgerät","Transferpresse"};
        double serviceMean = 5.0; // Define serviceMean here
        for (String machineType : machineTypes) {
            for (int i = 0; i < 1; i++) { // Initialize with 10 machines of each type
                Machine machine = new Machine(this, "Machine " + i + " " + machineType, machineType, serviceMean, true);
                machineQueues.get(machineType).insert(machine);
            }
        }

        new MemberGenerator(this, "Member Generator", true).activate();
    }


    public void init() {
        machineQueues = new HashMap<>();
        String[] machineTypes = {"Lasercutter", "3D-Drucker", "CNC-Maschine","Schneidplotter", "Nähmaschinen","Overlock","Stickmaschine","Metallaser","Elektrosäge","Schweißgerät","Transferpresse"};
        for (String machineType : machineTypes) {
            machineQueues.put(machineType, new Queue<Machine>(this, "Machine Queue " + machineType, true, true));
        }
        memberQueue = new Queue<Member>(this, "Member Queue", true, true);
        waitingTimeTally = new Tally(this, "Waiting Time Tally", true, true);
        queueLengthTally = new Tally(this, "Queue Length Tally", true, true); // Neue Statistik für die Länge der Warteschlange
        memberArrivalTime = new ContDistExponential(this, "Member Arrival Time", 0.5, true, true);
        machineServiceTime = new ContDistExponential(this, "Machine Service Time", 20, true, true);
        memberServiceTime = new ContDistExponential(this, "MemberServiceTimeStream", 0.5, true, true);
    }
}