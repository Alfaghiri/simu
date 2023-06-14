import desmoj.core.simulator.*;
import java.util.ArrayList;
import java.util.List;

public class Makerspace extends Model {
    private List<Machine> machines;
    private List<Member> members;

    public Makerspace(Model owner, String name, boolean showInTrace) {
        super(owner, name, true, showInTrace);
        machines = new ArrayList<>();
        members = new ArrayList<>();
    }

    public String description() {
        return "Makerspace Model";
    }

    public void doInitialSchedules() {
        double arrivalTime = 0.0;
        for (Member member : members) {
            member.activate(new TimeSpan(arrivalTime));
            System.out.println(member+" "+arrivalTime);
            arrivalTime += 10.0; // Adjust this value
        }
    }


    public void init() {
        // Initialize the machine
        Machine laserCutter = new Machine(this, "Laser Cutter", true);
        machines.add(laserCutter);
        Machine cnc = new Machine(this, "CNC", true);
        machines.add(cnc);
        Machine threed = new Machine(this, "3D", true);
        machines.add(threed);
        Machine tpresse = new Machine(this, "Transferpresse", true);
        machines.add(tpresse);

        // Initialize the members
        for (int i = 1; i <= 50; i++) {
            Member member = new Member(this, "Member " + i, true, laserCutter, 30,10);
            Member member1 = new Member(this, "Member " + i, true, threed, 30,10);
            Member member2 = new Member(this, "Member " + i, true, cnc, 30,10);
            Member member3 = new Member(this, "Member " + i, true, tpresse, 30,10);
            members.add(member);
        }
    }


    public List<Machine> getMachines() {
        return machines;
    }

    public List<Member> getMembers() {
        return members;
    }
}
