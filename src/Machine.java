import desmoj.core.simulator.*;

import java.util.concurrent.TimeUnit;

public class Machine extends SimProcess {
    private String name;
    private boolean isFree;
    private Queue<Member> queue;
    private boolean isBroken;
    private boolean isRunning;

    public Machine(Model owner, String name, boolean showInTrace) {
        super(owner, name, showInTrace);
        this.name = name;
        this.isFree = true;
        this.queue = new Queue<Member>(owner, "Queue for " + name, true, true);
        this.isBroken = false;
        this.isRunning = false;
    }

    public void lifeCycle() {
        while (true) {
            isRunning = true;
            try {
                if (queue.isEmpty()) {
                    isFree = true;
                    passivate();
                } else {
                    isFree = false;
                    Member member = queue.first();
                    queue.remove(member);
                    hold(new TimeSpan(member.getUsageTime(), TimeUnit.MINUTES));
                    if (isBroken) {
                        hold(new TimeSpan(getRepairTime(), TimeUnit.MINUTES));
                        isBroken = false;
                    }
                    if (!queue.isEmpty()) {
                        this.activate();
                    }
                }
                isRunning = false;
            }
            catch (co.paralleluniverse.fibers.SuspendExecution e) {
                e.printStackTrace();
            }

            }

        }

    public boolean isRunning() {
        return isRunning;
    }
    public String getName() {
        return name;
    }

    public boolean isFree() {
        return isFree;
    }

    public Queue<Member> getQueue() {
        return queue;
    }

    public boolean isBroken() {
        return isBroken;
    }

    public void setBroken(boolean isBroken) {
        this.isBroken = isBroken;
    }

    private int getRepairTime() {
        // Return the time it takes to repair the machine.
        // This could be a fixed value or a random value depending on your requirements.
        return 120;
    }
}
