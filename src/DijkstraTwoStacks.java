import java.util.Stack;


public class DijkstraTwoStacks {
    
    // single digit only TODO multiple digits and floating point
    // TODO no operations precedence implemented
    public static double evaluate(String expression) {
        Stack<Double> values = new Stack<Double>();
        Stack<Character> operations = new Stack<Character>();
        
        for (char c : expression.toCharArray()){
            if (c == '(' || c == ' '){}  // ignore
            else if (Character.getNumericValue(c) != -1){
                values.push((double)Character.getNumericValue(c));
            }else if (c == '+' || c == '-' || c == '*' || c == '/'){
                operations.push(c);
            }else if (c == ')'){
                evalTopofStack(values, operations.pop());
            }else{}  // ignore, why throw exception? Maybe warning ...
        }     
        
        // deal without brackets
        while (!operations.isEmpty()){
            evalTopofStack(values, operations.pop());
        }
        return values.pop();
    }
    
    private static Stack<Double> evalTopofStack(Stack<Double> values, char op){
        double a = values.pop();
        double b = values.pop();
             if (op == '+') values.push(a + b);
        else if (op == '-') values.push(a - b);
        else if (op == '*') values.push(a * b);
        else if (op == '/') values.push(a / b);
        return values;
    }
    

    public static void main(String[] args) {
        System.out.println(String.valueOf(evaluate("((4 * 5) * (3 + 2)) + 8 + 9")));
    }

}
