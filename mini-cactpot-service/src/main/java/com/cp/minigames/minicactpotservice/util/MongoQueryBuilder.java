package com.cp.minigames.minicactpotservice.util;

import lombok.NonNull;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.lang.Nullable;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import static com.cp.minigames.minicactpot.domain.model.util.AggregateConstants.UPDATED_DATE;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * Utility class for building Mongo-specific {@link Query} instances.
 */
public class MongoQueryBuilder {

    private final Query query;

    private MongoQueryBuilder() {
        this.query = new Query();
    }

    private MongoQueryBuilder(Query query) {
        this.query = query;
    }

    /**
     * @return Initialized instance of the builder.
     */
    public static MongoQueryBuilder init() {
        return new MongoQueryBuilder();
    }

    public static MongoQueryBuilder init(Query query) {
        Query copy = Query.of(query);
        return new MongoQueryBuilder(copy);
    }

    // ---

    /**
     * Adds an equals criteria on field 'key', matching documents where the field's value is equal to 'value'.
     * This method is null-safe for 'value'.
     */
    public <T> MongoQueryBuilder withEqualsMatch(@NonNull String key, @Nullable T value) {
        if (value == null) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).is(value));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds a date criteria on field 'key', matching documents where the field's value is after the date of 'value'.
     * This method is null-safe for 'value'.
     */
    public MongoQueryBuilder withDateFrom(@NonNull String key, @Nullable String value) {
        if (value == null) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).gte(value));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds a date criteria on field 'key', matching documents where the field's value is after the date of 'value'.
     * This method is null-safe for 'value'.
     */
    public MongoQueryBuilder withDateFrom(@NonNull String key, @Nullable OffsetDateTime value) {
        if (value == null) return this;

        return withDateFrom(key, value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId()))));
    }

    /**
     * Adds a date criteria on field 'key', matching documents where the field's value is before the date of 'value'.
     * This method is null-safe for 'value'.
     */
    public MongoQueryBuilder withDateTo(@NonNull String key, @Nullable String value) {
        if (value == null) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).lte(value));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds a date criteria on field 'key', matching documents where the field's value is before the date of 'value'.
     * This method is null-safe for 'value'.
     */
    public MongoQueryBuilder withDateTo(@NonNull String key, @Nullable OffsetDateTime value) {
        if (value == null) return this;

        return withDateTo(key, value.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME.withZone(ZoneId.of(ZoneOffset.UTC.getId()))));
    }

    /**
     * Adds a regex-based like criteria on field 'key', matching documents where the given 'value' is a substring of the
     * actual value of field 'key'. This method is null-safe for 'value'.
     */
    public MongoQueryBuilder withLikeMatch(@NonNull String key, @Nullable String value) {
        if (value == null) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).regex("^.*" + value + ".*$", "si"));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds an includes criteria on the field 'key', matching documents where the given 'values' are all included in the
     * target collection. This method is null-safe for 'values'.
     */
    public <T> MongoQueryBuilder withFieldIncludes(@NonNull String key, @Nullable Collection<T> values) {
        if (values == null || values.isEmpty()) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).all(values));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds a criteria on field 'key', matching documents where the value of 'key' matches one or more values in the
     * given 'values' collection.
     * This method is null-safe for 'values'.
     */
    public <T> MongoQueryBuilder withInMatch(@NonNull String key, @Nullable Collection<T> values) {
        if (values == null || values.isEmpty()) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).in(values));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds a criteria on field 'key', matching documents where the value of 'key' does not match any values in the
     * given 'values' collection.
     * This method is null-safe for 'values'.
     */
    public <T> MongoQueryBuilder withNotInMatch(@NonNull String key, @Nullable Collection<T> values) {
        if (values == null || values.isEmpty()) return this;

        var temp = Query.of(query);
        temp.addCriteria(where(key).nin(values));
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds an exclusion projection for field 'key'.
     */
    public MongoQueryBuilder withExcludedField(@NonNull String key) {
        var temp = Query.of(query);
        temp.fields().exclude(key);
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds skip and limit to the query based on the supplied 'page' and 'limit' parameters.
     * This method is null-safe.
     */
    public MongoQueryBuilder withPagination(@Nullable Long page, @Nullable Long limit) {
        if (page == null || limit == null) return this;

        var temp = Query.of(query);
        long skip = (page - 1) * limit;
        temp.skip(skip);
        temp.limit(limit.intValue());
        return new MongoQueryBuilder(temp);
    }

    /**
     * Adds sorting to the query such that documents will be ordered in DESC order by the 'updatedDate' field.
     */
    public MongoQueryBuilder withLatestFirst() {
        var temp = Query.of(query);
        temp.with(Sort.by(Sort.Direction.DESC, UPDATED_DATE));
        return new MongoQueryBuilder(temp);
    }

    // ---

    /**
     * Returns the {@link Query} instance wrapped within the builder.
     */
    public Query build() {
        return query;
    }
}
