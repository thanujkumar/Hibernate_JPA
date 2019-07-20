package playground.main.concepts;

import playground.main.Logging;

/**
 * https://thoughts-on-java.org/jpa-21-entity-graph-part-1-named-entity/
 * https://thoughts-on-java.org/jpa-21-entity-graph-part-2-define/
 * <p>
 * The definition of an entity graph is independent of the query and defines which attributes to fetch from the database.
 * An entity graph can be used as a fetch or a load graph. If a fetch graph is used, only the attributes specified by the
 * entity graph will be treated as FetchType.EAGER. All other attributes will be lazy. If a load graph is used, all
 * attributes that are not specified by the entity graph will keep their default fetch type.
 */
public class JPAEntityGraph extends Logging {

//TODO
}
