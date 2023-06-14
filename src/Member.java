import desmoj.core.simulator.*;
import desmoj.core.dist.*;
import java.util.concurrent.TimeUnit;

public class Member extends SimProcess {

    private MakerspaceModel model;
    private String machineType;
    private TimeInstant arrivalTime;

    public Member(Model owner, String name, String machineType, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.model = (MakerspaceModel)owner;
        this.machineType = machineType;
        this.machineType = machineType;
        this.arrivalTime = presentTime();
    }

    public void lifeCycle() {
        try {
            Queue<Machine> machineQueue = model.machineQueues.get(machineType);
            Machine m = machineQueue.first(); // Get the first available machine
            if (m != null) {
                machineQueue.remove(m); // remove the machine from the queue
                m.activate(); // If machine is available, activate it
                model.waitingTimeTally.update(presentTime().getTimeAsDouble() - arrivalTime.getTimeAsDouble()); // Update waiting time tally
                model.queueLengthTally.update(model.memberQueue.length()); // Warteschlangenlänge aktualisieren
                passivate(); // and passivate the member
                if(model.memberQueue.contains(this)){
                    model.memberQueue.remove(this);
                    model.queueLengthTally.update(model.memberQueue.length());
                    model.waitingTimeTally.update(presentTime().getTimeAsDouble() - arrivalTime.getTimeAsDouble());
                }
            } else {
                model.memberQueue.insert(this); // If machine is not available, add the member to the waiting queue
                model.queueLengthTally.update(model.memberQueue.length());
                passivate(); // and passivate the member
            }
        } catch (co.paralleluniverse.fibers.SuspendExecution e) {
            e.printStackTrace();
        }
    }




}