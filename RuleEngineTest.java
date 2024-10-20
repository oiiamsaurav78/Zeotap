import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

public class RuleEngineTest {

    @Test
    public void testEvaluateRule() {
        RuleEngine engine = new RuleEngine();
        Node rule = engine.createRule("age > 30 AND department = 'Sales'");
        
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", 35);
        userData.put("department", "Sales");

        boolean result = engine.evaluateRule(rule, userData);
        assert result == true;
    }
}
