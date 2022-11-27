package pl.gda.pg.eti.kask.sa.migration.agents;

import jade.content.ContentManager;
import jade.content.lang.sl.SLCodec;
import jade.core.Agent;
import jade.core.Location;
import jade.domain.mobility.MobilityOntology;
import pl.gda.pg.eti.kask.sa.migration.behaviours.RequestContainersListBehaviour;

import java.util.ArrayList;
import java.util.List;

public class MigratingAgent extends Agent {
    private List<Location> locations;

    @Override
    protected void setup() {
        super.setup();
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
        this.addBehaviour(new RequestContainersListBehaviour(this));
    }
    @Override
    protected void afterMove() {
        super.afterMove();
        ContentManager cm = getContentManager();
        cm.registerLanguage(new SLCodec());
        cm.registerOntology(MobilityOntology.getInstance());
    }
    @Override
    protected void beforeMove() {
        //stop threads
        //save state
        super.beforeMove();
    }

    public void setLocations(List<Location> locations) {
        this.locations = new ArrayList<>();
        this.locations.add(this.here());
        this.locations.addAll(locations);
    }

    public Location pop() {
        if (!this.locations.isEmpty()) {
            return locations.remove(this.locations.size()-1);
        } else {
            return null;
        }
    }

    public boolean locationsEmpty() {
        return locations.isEmpty();
    }
}
