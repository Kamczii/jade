package pl.gda.pg.eti.kask.sa.migration.behaviours;

import jade.content.ContentElement;
import jade.content.lang.Codec;
import jade.content.onto.OntologyException;
import jade.content.onto.basic.Result;
import jade.core.Location;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import pl.gda.pg.eti.kask.sa.migration.agents.MigratingAgent;

import java.util.ArrayList;
import java.util.List;

public class ReceiveContainerListBehaviour extends SimpleBehaviour  {
    private boolean done = false;
    protected final MigratingAgent myAgent;
    private String conversationId;
    private MessageTemplate messageTemplate;

    public ReceiveContainerListBehaviour(MigratingAgent agent, String conversationId) {
        super(agent);
        this.myAgent = agent;
        this.conversationId = conversationId;
    }

    @Override
    public void onStart() {
        super.onStart();
        messageTemplate = MessageTemplate.MatchConversationId(conversationId);
    }

    @Override
    public void action() {
        ACLMessage msg = myAgent.receive(messageTemplate);
        if (msg != null) {
            done = true;
            try {
                ContentElement ce =
                        myAgent.getContentManager().extractContent(msg);
                jade.util.leap.List items = ((Result) ce).getItems();
                List<Location> locations = new ArrayList<>();
                items.iterator().forEachRemaining(i -> {
                        locations.add((Location) i);
            });
                locations.remove(myAgent.here());
                myAgent.setLocations(locations);
                myAgent.addBehaviour(new MigratinBehaviour(myAgent));
            } catch (Codec.CodecException | OntologyException ex) {
                System.out.println(ex.getMessage() + ex);
            }
        }

    }

    @Override
    public boolean done() {
        return done;
    }

}
