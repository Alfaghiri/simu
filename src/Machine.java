import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import desmoj.core.statistic.Tally;

import java.util.concurrent.TimeUnit;

public class Machine extends SimProcess {

    private MakerspaceModel model;
    private String machineType;
    private ContDistExponential serviceTime;
    public Tally machineUsageTally; // Neue Statistik für die Maschinennutzung
    public Machine(Model owner, String name, String machineType,double serviceMean, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.model = (MakerspaceModel)owner;
        this.machineType = machineType;
        this.serviceTime = new ContDistExponential(this.model, "Service Time for " + machineType, serviceMean, true, true);

    }


    public void lifeCycle() {
        try {
            this.machineUsageTally = new Tally(this.model, "Machine Usage for " + machineType, true, true); // Initialisieren Sie die Tally-Statistik hier
            while (true) {
                Member m = model.memberQueue.first();
                if (m != null) {
                    model.memberQueue.remove(m); // remove the member from the queue
                    m.activate(); // activate the member
                    double usageStartTime = presentTime().getTimeAsDouble(TimeUnit.MINUTES);
                    hold(new TimeSpan(serviceTime.sample()));
                    double usageEndTime = presentTime().getTimeAsDouble(TimeUnit.MINUTES);
                    machineUsageTally.update(usageEndTime - usageStartTime);
                    // Once service is done, the machine is passivated until a new member requires service
                    passivate();
                } else {
                    // if no members are waiting, insert the machine back into the machine queue and passivate the machine
                    model.machineQueues.get(machineType).insert(this);
                    passivate();
                    while (model.memberQueue.isEmpty()) {
                        hold(new TimeSpan(1)); // check every minute
                    }
                    continue; // start over and fetch the next member
                }
            }
        } catch (co.paralleluniverse.fibers.SuspendExecution e) {
            e.printStackTrace();
        }
    }






}