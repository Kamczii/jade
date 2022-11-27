package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

public class MigratinBehaviour extends SimpleBehaviour {

    protected final MigratingAgent myAgent;

    public MigratinBehaviour(MigratingAgent myAgent) {
        this.myAgent = myAgent;
    }

    @Override
    public void action() {
        try {
            Location location = this.myAgent.pop();
            Thread.sleep(2000);
            myAgent.doMove(location);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean done() {
        return myAgent.locationsEmpty();
    }
}
