class Node {
    String type;  // "operator" or "operand"
    Node left;    // Left child for operators
    Node right;   // Right child for operators
    String value; // Condition for operands, or operator type (AND/OR)

    public Node(String type, String value) {
        this.type = type;
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public Node(String type, Node left, Node right, String value) {
        this.type = type;
        this.left = left;
        this.right = right;
        this.value = value;
    }
}