package org.nalby.zomato.stats;

import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

import org.nalby.zomato.util.QueryName;

@SqlResultSetMapping(
	name = "statsMapping",
	entities = @EntityResult(
		entityClass = CategoryStats.class,
		fields = {
			@FieldResult(name = "id", column = "id"),
			@FieldResult(name = "name", column = "name"),
			@FieldResult(name = "count", column = "count")
		}
	)
)
@NamedNativeQuery(name = QueryName.GET_CATEGORY_STATS, query = "SELECT categories.id as id, categories.name, count(*) as count FROM restaurant_categories INNER "
		+ "JOIN categories ON restaurant_categories.category_id = categories.id GROUP BY categories.id", resultSetMapping ="statsMapping")
@Entity
public class CategoryStats extends AbstractStats {
}
