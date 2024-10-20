import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class RuleEngine {
    public Node createRule(String ruleString) {
        // For simplicity, let's create a hardcoded AST for the rule: 
        // ((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)
    
        Node root = new Node("operator", "AND");
        
        Node orNode1 = new Node("operator", "OR");
        Node andNode1 = new Node("operator", "AND");
        andNode1.left = new Node("operand", "age > 30");
        andNode1.right = new Node("operand", "department = 'Sales'");
        
        Node andNode2 = new Node("operator", "AND");
        andNode2.left = new Node("operand", "age < 25");
        andNode2.right = new Node("operand", "department = 'Marketing'");
        
        orNode1.left = andNode1;
        orNode1.right = andNode2;
        
        Node orNode2 = new Node("operator", "OR");
        orNode2.left = new Node("operand", "salary > 50000");
        orNode2.right = new Node("operand", "experience > 5");
        
        root.left = orNode1;
        root.right = orNode2;
        
        return root;
    }
    public boolean evaluateRule(Node node, Map<String, Object> data) {
    if (node.type.equals("operand")) {
        return evaluateCondition(node.value, data);
    }

    boolean leftResult = evaluateRule(node.left, data);
    boolean rightResult = evaluateRule(node.right, data);

    if (node.value.equals("AND")) {
        return leftResult && rightResult;
    } else if (node.value.equals("OR")) {
        return leftResult || rightResult;
    }

    return false;
    }

    public boolean evaluateCondition(String condition, Map<String, Object> data) {
        // For simplicity, parse simple conditions like "age > 30"
        if (condition.contains("age")) {
            int age = (int) data.get("age");
            if (condition.contains(">")) {
                return age > Integer.parseInt(condition.split(">")[1].trim());
            } else if (condition.contains("<")) {
                return age < Integer.parseInt(condition.split("<")[1].trim());
            }
        } else if (condition.contains("department")) {
            String department = (String) data.get("department");
            return condition.contains(department);
        } else if (condition.contains("salary")) {
            int salary = (int) data.get("salary");
            return salary > Integer.parseInt(condition.split(">")[1].trim());
        } else if (condition.contains("experience")) {
            int experience = (int) data.get("experience");
            return experience > Integer.parseInt(condition.split(">")[1].trim());
        }
        return false;
    }
    public Node combineRules(List<Node> rules) {
    if (rules.size() == 1) {
        return rules.get(0);  // Only one rule, no need to combine
    }

    Node root = new Node("operator", "AND");  // Combine rules with AND for this example
    for (Node rule : rules) {
        root = new Node("operator", root, rule, "AND");
    }
    
    return root;
    }
     public static void main(String[] args) {
        // Create rules
        RuleEngine engine = new RuleEngine();
        Node rule1 = engine.createRule("((age > 30 AND department = 'Sales') OR (age < 25 AND department = 'Marketing')) AND (salary > 50000 OR experience > 5)");
        
        // Sample user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", 35);
        userData.put("department", "Sales");
        userData.put("salary", 60000);
        userData.put("experience", 3);
        
        // Evaluate the rule
        boolean isEligible = engine.evaluateRule(rule1, userData);
        // Node rule2 = engine.createRule("(age > 30 AND salary > 50000)");
        // userData.put("age", 31);
        // userData.put("salary", 45000);
        // boolean isEligible2 = engine.evaluateRule(rule2, userData);
        // System.out.println(isEligible2);
        System.out.println("User eligible: " + isEligible);
    }

    
}
