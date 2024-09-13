package Rules;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class for creating DeductionRule instances.
 */
public class DeductionRuleFactory {

    private static final Map<RuleType, DeductionRule> ruleInstances = new HashMap<>();

    static {
        // Initialize and cache singleton instances
        ruleInstances.put(RuleType.NAKED_SINGLE, DR1.getInstance());
        ruleInstances.put(RuleType.HIDDEN_SINGLE, DR2.getInstance());
        ruleInstances.put(RuleType.POINTING_PAIR, DR3.getInstance());
    }

    /**
     * Gets the DeductionRule instance based on the given rule type.
     *
     * @param ruleType The type of rule to retrieve.
     * @return The corresponding DeductionRule instance.
     */
    public static DeductionRule getDeductionRule(RuleType ruleType) {
        return ruleInstances.get(ruleType);
    }

    /**
     * Enum representing the types of DeductionRules available.
     */
    public enum RuleType {
        NAKED_SINGLE,
        HIDDEN_SINGLE,
        POINTING_PAIR
    }
}
