package org.kinotic.grind.api.model;

import java.util.function.BiFunction;

/**
 * A view a {@link Task} declares, as {@link Task#startView(Class, java.util.function.Function)} and
 * {@link Task#resultView(java.util.function.Function)} build it: the producer is given the task's
 * result, null for a view shown from the task's start, and the scope value of the source type,
 * null when the view names none.
 *
 * @param source   the type of the scope value the view is built from, or null for none
 * @param producer builds the view from the result and the scope value
 */
public record ViewProducer(Class<?> source, BiFunction<Object, Object, TaskView<?>> producer) {
}
