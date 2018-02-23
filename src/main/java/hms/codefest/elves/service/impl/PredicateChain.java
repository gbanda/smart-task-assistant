package hms.codefest.elves.service.impl;

import hms.codefest.elves.domain.Task;
import hms.codefest.elves.service.TaskUpdateEligibilityPredicate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by gayan on 2/23/18.
 */
public class PredicateChain implements TaskUpdateEligibilityPredicate {
    private Queue<TaskUpdateEligibilityPredicate> predicates;

    public PredicateChain() {
        predicates = new ConcurrentLinkedQueue<>();
    }

    public void addToChain(TaskUpdateEligibilityPredicate predicate) {
        predicates.add(predicate);
    }

    @Override
    public boolean test(Task task) {
        return predicates.size() == 0 || predicates.stream().allMatch(predicate -> predicate.test(task));
    }
}
