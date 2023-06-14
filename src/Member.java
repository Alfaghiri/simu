import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

public class Member extends SimProcess {
    private String name;
    private Machine machineToUse;
    private int usageTime;
    private int numberOfVisits;

    public Member(Model owner, String name, boolean showInTrace, Machine machineToUse, int usageTime, int numberOfVisits) {
        super(owner, name, showInTrace);
        this.name = name;
        this.machineToUse = machineToUse;
        this.usageTime = usageTime;
        this.numberOfVisits = numberOfVisits;
    }

    public void lifeCycle() {
        for (int i = 0; i < numberOfVisits; i++) {
            try {
                if (!machineToUse.isFree()) {
                    machineToUse.getQueue().insert(this);
                    passivate();
                } else {
                    machineToUse.activate();
                    hold(new TimeSpan(usageTime, TimeUnit.MINUTES));
                    sendTraceNote("leaves the makerspace");
                    break;
                }

            }

            catch (co.paralleluniverse.fibers.SuspendExecution e) {
            e.printStackTrace();
        }


        }
    }

    public String getName() {
        return name;
    }

    public Machine getMachineToUse() {
        return machineToUse;
    }

    public int getUsageTime() {
        return usageTime;
    }
}
