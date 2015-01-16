package in.twizmwaz.cardinal.filter.type;

import in.twizmwaz.cardinal.filter.Filter;
import in.twizmwaz.cardinal.filter.FilterState;
import in.twizmwaz.cardinal.filter.parsers.CauseFilterParser;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.player.PlayerEvent;

import static in.twizmwaz.cardinal.filter.FilterState.*;

public class CauseFilter extends Filter {

    private final EventCause cause;

    public CauseFilter(final EventCause cause) {
        this.cause = cause;
    }

    public CauseFilter(final CauseFilterParser parser) {
        this.cause = parser.getCause();
    }

    @Override
    public FilterState evaluate(final Event event) {
        EventCause eventCause = null;
        if (event instanceof PlayerEvent) {
            eventCause = EventCause.PLAYER;
        } else if (event instanceof EntityEvent) {
            if (((EntityEvent) event).getEntityType().equals(EntityType.PRIMED_TNT)) {
                eventCause = EventCause.TNT;
            }
        }
        if (cause.equals(eventCause)) return ALLOW;
        else if (!eventCause.equals(null)) return DENY;
        else return ABSTAIN;
    }

    public enum EventCause {

        /**
         * The event was generated be a player action.
         */
        PLAYER(),
        /**
         * The event was generated by TNT.
         */
        TNT();

        public static EventCause getEventCause(String string) {
            switch (string.toLowerCase().replaceAll(" ", "")) {
                case "player":
                    return PLAYER;
                case "tnt":
                    return TNT;
                default:
                    return null;
            }
        }

    }

}