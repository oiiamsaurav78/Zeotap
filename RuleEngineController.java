import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RuleEngineController {

    private final RuleEngine ruleEngine = new RuleEngine();  // Your rule engine logic
    
    // Show the form to create a rule
    @GetMapping("/createrule")
    public String showCreateRuleForm() {
        return "createrule";
    }

    // Handle the creation of a rule
    @PostMapping("/createrule")
    public String createRule(@RequestParam String ruleString, Model model) {
        // You can add more logic here to save the rule, but for now, it just creates an AST
        Node rule = ruleEngine.createRule(ruleString);
        model.addAttribute("message", "Rule created successfully!");
        return "createrule"; // Renders the same form with success message
    }

    // Show the form to evaluate a rule
    @GetMapping("/evaluaterule")
    public String showEvaluateRuleForm() {
        return "evaluaterule";
    }

    // Handle the evaluation of a rule based on user input
    @PostMapping("/evaluaterule")
    public String evaluateRule(@RequestParam int age, @RequestParam String department, 
                               @RequestParam int salary, @RequestParam int experience, Model model) {
        
        // Example: Rule "age > 30 AND department = 'Sales'"
        Node rule = ruleEngine.createRule("age > 30 AND department = 'Sales'");

        // Create the data map to pass to the rule engine
        Map<String, Object> userData = new HashMap<>();
        userData.put("age", age);
        userData.put("department", department);
        userData.put("salary", salary);
        userData.put("experience", experience);

        // Evaluate rule
        boolean isEligible = ruleEngine.evaluateRule(rule, userData);

        // Pass result to the view
        model.addAttribute("result", isEligible ? "User eligible: True" : "User eligible: False");
        
        return "evaluaterule";
    }
}
