package com.m2rs.core.model;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkNotNull;

public class Id<R, V> {

    private final Class<R> clazz;
    private final V value;

    private Id(Class<R> clazz, V value) {
        this.clazz = clazz;
        this.value = value;
    }

    public static <R, V> Id<R, V> of(Class<R> reference, V value) {
        checkNotNull(reference, "reference must be provided.");
        checkNotNull(value, "value must be provided.");

        return new Id<>(reference, value);
    }

    public V value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Id<?, ?> id = (Id<?, ?>) o;
        return Objects.equals(clazz, id.clazz) && Objects.equals(value, id.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clazz, value);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("clazz", clazz.getSimpleName())
            .append("value", value)
            .toString();
    }

}
