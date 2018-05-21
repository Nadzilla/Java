package app;

import java.io.*;
import java.util.*;
import java.util.regex.*;

import structures.Stack;

public class Expression {

	public static String delims = " \t*+-/()[]"; 
	/* Assignment is to work with expression that is a string
	 * 
	 * makevariablelist:
	 * has three arguments:
	 * 	string 
	 * 	arraylist of variables (variables is a class that holds a simple variable, such as A (which has a number))
	 *  arraylist of arrays 
	 * example: "A+Boo[A*C]"
	 * what we gotta do:
	 * traverse the string and figure out which symbol is an array and which is a variable 
	 * 	three ways:
	 * 				if one variable is already there, then you do not insert it
	 * 					we insert one entry for each variable 
	 * 
	 *		we can use tockenizer, it breaks it down into +-()* and / and [] 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
			
    /**
     * Populates the vars list with simple variables, and arrays lists with arrays
     * in the expression. For every variable (simple or array), a SINGLE instance is created 
     * and stored, even if it appears more than once in the expression.
     * At this time, values for all variables and all array items are set to
     * zero - they will be loaded from a file in the loadVariableValues method.
     * 
     * @param expr The expression
     * @param vars The variables array list - already created by the caller
     * @param arrays The arrays array list - already created by the caller
     */
    public static void 
    makeVariableLists(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	/** DO NOT create new vars and arrays - they are already created before being sent in
    	 ** to this method - you just need to fill them in.
    	 **/
    	System.out.println(vars.size());
    	String operators = "+-*/"; 
    	String variableName = ""; 
    	Variable variable = new Variable("");
    	String arrayName = ""; 
    	Array array = new Array(""); 
    	int n = 0; 
    	int m = 0; 
    	expr = expr.replace(" ", ""); 
    	
    	
	    	for (int i = 0; i < expr.length(); i++) { // traverses the expression
	    		variableName = ""; 
	    		arrayName = ""; 
	    		System.out.println("the traverse expression loop: " + expr.charAt(i)); 
	    		for (int j = 0; j < operators.length(); j++) { // traverses the operators 
	    			if (expr.charAt(i) == operators.charAt(j)) { // checks if the traversed charAt is an operator 
	    				n = i; 
	    					if (i == expr.length() -1) {
	    						n++; 
	    					}
	    				System.out.println("Found an operator: " + expr.charAt(i)); 
	  
	        				while (n > 0 && Character.isLetter(expr.charAt(n-1))){ // traverses the variable before the operator 
	        					variableName += expr.charAt(n-1); // records the variable backwards
	        					System.out.println("the while loop: " + expr.charAt(n-1)); 
	        					n--; 
	        				}
	    					
	    			} 
	    		}
	    		if (i == expr.length() -1 || expr.charAt(i) == ')' || expr.charAt(i) == ']') {
    				n = i; 
    				while(expr.charAt(n) == ')' || expr.charAt(n) == ']') {
    					n--; 
    				}
    				while (n > 0 && Character.isLetter(expr.charAt(n))){ // traverses the variable before the operator 
    					variableName += expr.charAt(n); // records the variable backwards
    					System.out.println("the last letter while loop: " + expr.charAt(n)); 
    					n--; 
    				}
    			}
	    		if (variableName != "") {
		        	variableName = reverseString(variableName); 
		        	variableName = variableName.replaceAll(" ", ""); 
		        	variable = new Variable(variableName); 
		        	if (vars.contains(variable)) {
		        		System.out.println("Variable: '" + variable.name + "' is already in the array list.");
		        	} else {
		        	System.out.println("Added to Vars arraylist: " + variable.name); 
		        	vars.add(variable); 
		        	}
		    	}
	    		
	 
	    	if (expr.charAt(i) == '[') { // finds any arrays
	    		m = i; 
	    		while (m > 0 && Character.isLetter(expr.charAt(m-1))){ // traverses the variable before the operator 
					arrayName += expr.charAt(m-1); // records the variable backwards
					System.out.println("the while [array] loop: " + expr.charAt(m-1)); 
					m--; 
				}
	    		
	    	}
	    	if (arrayName != "") {
	        	arrayName = reverseString(arrayName); 
	        	array = new Array(arrayName); 
	        	if (arrays.contains(array)) {
	        		System.out.println("Array: '" + array.name + "' is already in the array list.");
	        	} else {
	        	System.out.println("Added to Arrays arraylist: " + array.name); 
	        	arrays.add(array); 
	        	}
	    	}
	    		
	    	}
	    	
	    	System.out.println(vars); 
	    	System.out.println(arrays); 

	    	
    	
    	
    }
    
    private static String reverseString (String string) {
    	String reversed = ""; 
    	for (int i = string.length()-1; i >= 0; i--) {
    		reversed += string.charAt(i); 
    	}
    	return reversed; 
    }
    
 
    
    /**
     * Loads values for variables and arrays in the expression
     * 
     * @param sc Scanner for values input
     * @throws IOException If there is a problem with the input 
     * @param vars The variables array list, previously populated by makeVariableLists
     * @param arrays The arrays array list - previously populated by makeVariableLists
     */
    public static void 
    loadVariableValues(Scanner sc, ArrayList<Variable> vars, ArrayList<Array> arrays) 
    throws IOException {
        while (sc.hasNextLine()) {
            StringTokenizer st = new StringTokenizer(sc.nextLine().trim());
            int numTokens = st.countTokens();
            String tok = st.nextToken();
            Variable var = new Variable(tok);
            Array arr = new Array(tok);
            int vari = vars.indexOf(var);
            int arri = arrays.indexOf(arr);
            if (vari == -1 && arri == -1) {
            	continue;
            }
            int num = Integer.parseInt(st.nextToken());
            if (numTokens == 2) { // scalar symbol
                vars.get(vari).value = num;
            } else { // array symbol
            	arr = arrays.get(arri);
            	arr.values = new int[num];
                // following are (index,val) pairs
                while (st.hasMoreTokens()) {
                    tok = st.nextToken();
                    StringTokenizer stt = new StringTokenizer(tok," (,)");
                    int index = Integer.parseInt(stt.nextToken());
                    int val = Integer.parseInt(stt.nextToken());
                    arr.values[index] = val;              
                }
            }
        }
    }
    
    // prints the stack and clears it
    private static void stackPrint(Stack stack) {
    	while (stack.isEmpty() == false) {
    		System.out.println(stack.pop()); 
    	}
    }
    
      
    
    private static String arrayCalc(String expr, int x, ArrayList<Variable> vars, ArrayList<Array> arrs) {
    	// varx[varx*vary*varx*vary*varx]
    	int ln = x; 
    	Stack<Variable> varsInArray = new Stack<Variable>(); 
    	Stack<Character> operators = new Stack<Character>(); 
    	Stack<Character> simplifiedOperators = new Stack<Character>(); 
    	Stack<Integer> simplifiedNumbers = new Stack<Integer>(); 
    	
    	String variableString = ""; 
    	Variable variable = new Variable(""); 
    	
    	int value = 0; // value of the array
    	System.out.println("Char at X: " + expr.substring(x,expr.length()));
    	String nameOfArrayBeingSimplified = ""; 
    	for (int xyz = x-2; Character.isLetter(expr.charAt(xyz)) == true; xyz--) {
    		nameOfArrayBeingSimplified+= expr.charAt(xyz); 
    	}
    	nameOfArrayBeingSimplified = reverseString(nameOfArrayBeingSimplified); 
    	
    	while (expr.charAt(x) != ']') {
    		if (expr.charAt(x) == '[') {
    			x++; 
    		}
    		variableString = ""; 
    		
    		
    		while (Character.isDigit(expr.charAt(x))) {
    			variableString +=expr.charAt(x); 
    			x++; 

    		}
    		
    		while (Character.isLetter(expr.charAt(x))) {
    			variableString +=expr.charAt(x); 
    			x++; 
    			/*
    			if (expr.charAt(x) == '[') {
    				for (int z = 0; z < arrs.size(); z++) {
    					if (variable.name.equals(arrs.get(z).name)) {
    						int[] intsInArray = arrs.get(z).values;
    						int ihatethis = x;
    	    				String numberInArray = ""; 
    	    				while (expr.charAt(ihatethis) != ']') {
    	    					numberInArray += expr.charAt(ihatethis); 
    	    					ihatethis++; 
    	    				}
    	    				value = intsInArray[Integer.parseInt(numberInArray)]; 
    					}
    	    		}
    				
    			}
    			*/ 
    		}
    		
    		
    		if (variableString != "") {
    		variable = new Variable(variableString); 
    		if (Character.isDigit(variableString.charAt(0))){
    			variable.value = Integer.parseInt(variableString); 
    		} else {
    			variable.value = value; 
    			for (int z = 0; z < vars.size(); z++) {
					if (variable.name.equals(vars.get(z).name)) {
	        			variable.value = vars.get(z).value; 
					}
	    		}
    		}
    		System.out.println("Adding in Variable: " + variable.name); 
    		varsInArray.push(variable);
    		
	    		
    		}
    		if (expr.charAt(x) == '*' ||expr.charAt(x) == '/' ||expr.charAt(x) == '+' ||expr.charAt(x) == '-' ) {
    			operators.push(expr.charAt(x));
        		x++; 
    		}

    	}
    	
    	int hn = x; 
    	int number1; 
    	int number2; 
    	int number3 = 0; 
    	while(operators.isEmpty() == false && varsInArray.isEmpty() == false) {
    		if (operators.peek() == '+' || operators.peek() == '-') {
    			simplifiedOperators.push((operators.pop()));
    			System.out.println("Pushed into add/sub stack: " + varsInArray.peek().value);
    			simplifiedNumbers.push((varsInArray.pop().value));
    			if (operators.isEmpty() == true) {
    				System.out.println("Pushed into add/sub stack: " + varsInArray.peek().value);
        			simplifiedNumbers.push((varsInArray.pop().value));
    			}
    			number1 = 0;
    			number2 = 0;
    			number3 = 0; 
    		}
    		if (operators.isEmpty() == false && operators.peek() == '*') {
    			number1 = varsInArray.pop().value; 
    			System.out.println("269: " + number1 + operators.peek()); 
    			number2 = varsInArray.pop().value;
    			System.out.println("271: " + number2 + operators.peek());
    			number3 = number1*number2; 
    			System.out.println("273: " + number3 + operators.peek()); 
    			variable = new Variable("x"); 
    			variable.value = number3; 
    			varsInArray.push(variable);
    			operators.pop();
    			if (operators.isEmpty() == true) {
    				simplifiedNumbers.push((variable.value));
    			}
    			
    
    		}
    		if (operators.isEmpty() == false && operators.peek() == '/') {
    			operators.pop();
    			number1 = varsInArray.pop().value; 
    			System.out.println("291: " + number1); 
    			number2 = varsInArray.pop().value;
    			System.out.println("293: " + number2); 
    			number3 = number2/number1; 
    			System.out.println("295: " + number3); 
    			variable = new Variable("x"); 
    			variable.value = number3; 
    			varsInArray.push(variable);
    			if (operators.isEmpty() == true) {
    				simplifiedNumbers.push((variable.value));
    			}
    		
    		}
    		

    	}
    	
    	// Now I have to do the addition and subtraction 
    	
    	while(simplifiedNumbers.isEmpty() == false && simplifiedOperators.isEmpty() == false) {
    		
    		if (simplifiedNumbers.isEmpty() == false && simplifiedOperators.isEmpty() == false && simplifiedOperators.peek() == '+') {
    			simplifiedOperators.pop(); 
    			number1 = simplifiedNumbers.pop(); 
    			System.out.println("add number1: " + number1); 
    			number2 = simplifiedNumbers.pop(); 
    			System.out.println("add number2: " + number2); 
    			number3 = number1 + number2; 
    			simplifiedNumbers.push(number3);
    			System.out.println("add number3: " + number3);
    		}
    		if (simplifiedNumbers.isEmpty() == false && simplifiedOperators.isEmpty() == false && simplifiedOperators.peek() == '-') {
    			simplifiedOperators.pop(); 
    			number1 = simplifiedNumbers.pop(); 
    			System.out.println("sub number1: " + number1); 		
    			number2 = simplifiedNumbers.pop(); 
    			System.out.println("sub number2: " + number1); 
    			number3 = number1 - number2; 
    			simplifiedNumbers.push(number3);
    			System.out.println("sub number3: " + number1); 

    		}
    	}
    	System.out.println("NUMBER 3: " + number3); 
    	
			for (int z = 0; z < arrs.size(); z++) {
				System.out.println("The loop is running. " + nameOfArrayBeingSimplified);
				if (nameOfArrayBeingSimplified.equals(arrs.get(z).name)) {
					int[] intsInArray = arrs.get(z).values;
    				System.out.println("The INT array: " + intsInArray);

    				value = intsInArray[number3]; 
    				break; 
				} // a+A[b*B[1+1]+A[b*b]-5]+b
    		}
			
		
	    	System.out.println("VALUE: " + value); 

    	
    	
    	expr = expr.substring(0, ln-nameOfArrayBeingSimplified.length()-1) + Integer.toString(value) + expr.substring(hn+1,expr.length()); 
    	
    	System.out.println("Expr: " + expr); 
    	
        	return expr; 

    	
    }
    
    /**
     * Evaluates the expression.
     * 
     * @param vars The variables array list, with values for all variables in the expression
     * @param arrays The arrays array list, with values for all array items
     * @return Result of evaluation
     */
    /* ex; A+B*C
     * two stacks: 
     * 		operator
     * 		operands 
     * push an operator to each stack (refer to phone)
     * after both stacks are full, you pop, calculate and then push
     * 
     * 
     */
    public static float evaluate(String expr, ArrayList<Variable> vars, ArrayList<Array> arrays) {
    	/** COMPLETE THIS METHOD **/
    	// following line just a placeholder for compilation
    	expr = expr.replaceAll(" ", ""); 
    	// Count the number of arrays in the d***ed expression 
    	int numOfArrays = 0; 
    	for(int z = 0; z < expr.length(); z++) {
    		
    		if (expr.charAt(z) == ']') {
    			numOfArrays++; 
    		}
    	}
    	
    	int inMethodNumOfArrays = numOfArrays; 
    	System.out.println("Number of arrays: " + numOfArrays);
    	int x = 0; 
    	while (inMethodNumOfArrays != 0 && numOfArrays != 0) {
    		if (expr.charAt(x) == '[') {
    			inMethodNumOfArrays--; 
    	    	System.out.println("in method num of arraus: " + inMethodNumOfArrays);

    		}
    		x++; 
    		if (inMethodNumOfArrays <= 0 && numOfArrays != 0) {
    			System.out.println("Array calc is running" );
    			expr = arrayCalc(expr,x,vars,arrays);
    			numOfArrays--; 
    			inMethodNumOfArrays = numOfArrays; 
    			x = 0; 
    		}
    		
    	}
    	
    	return 0;
    }
}