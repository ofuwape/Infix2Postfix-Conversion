
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import java.util.EmptyStackException;

public class infix2Postfix {

 public static class mismatchedParenthesesException extends Exception {
  public mismatchedParenthesesException(String s) {
   super(s);
  }
 }
 public static class missingOperandException extends Exception {
   public missingOperandException(String s) {
   super(s);
  }
 }
 public static class missingOperatorException extends Exception {
   public missingOperatorException(String s) {
   super(s);
  }
 }

 public static void main(String[] args) {
  boolean stillTesting = true;
  LABEL: while (stillTesting) {
   Scanner get = new Scanner(System.in);
   System.out.println("Enter the infix expression: ");
   String infix = get.nextLine();
   infix = infix.trim().replaceAll("\\s+", "");
   int openParaCounter = 0;
   int closedParaCounter = 0;
   int operandCounter = 0;
   int operatorCounter = 0;

   Queue<Character> postfix = new LinkedList<Character>();
   Stack<Character> theStack = new Stack<Character>();

   for (Character eachCharacter : infix.toCharArray()) {
    // operands--letters
    try {
     if ((eachCharacter >= 'a' && eachCharacter <= 'z')
       || (eachCharacter >= 'A' && eachCharacter <= 'Z')) {
      postfix.add(eachCharacter);
      operandCounter++;
     }
     // open brackets
     if (eachCharacter == '(') {
      openParaCounter++;
      theStack.push(eachCharacter);
     }
     // operators
      if (eachCharacter == '/' || eachCharacter == '*'
       || eachCharacter == '+' || eachCharacter == '-') {
         operatorCounter++;
      while ((!theStack.isEmpty())
        && (theStack.peek() != '(')
        && (precedence(eachCharacter, theStack.peek()))) {
       postfix.add(theStack.pop());
      }
      theStack.push(eachCharacter);
     }
     // closed brackets
     if (eachCharacter == ')') {
      closedParaCounter++;
      while (theStack.peek() != '(') {
       postfix.add(theStack.pop());
      }
      theStack.pop();
     }
    } catch (EmptyStackException e) {
     System.out.println("You have Mismatched Parentheses");
     continue LABEL;
    }
   }
    try {
     if ((operandCounter - operatorCounter) >= 2)
      throw new missingOperatorException(
        "You are Missing an operator");
     else if (operatorCounter >= operandCounter)
      throw new missingOperandException(
        "You are Missing  an operand");
     else if (closedParaCounter != openParaCounter) {
      throw new mismatchedParenthesesException(
        "You have Mismatched Parentheses");
     }

    } catch (mismatchedParenthesesException ex) {
     System.out.println(ex.getMessage());
     continue LABEL;
    } catch (missingOperandException ex) {
     System.out.println(ex.getMessage());
     continue LABEL;
    } catch (missingOperatorException ex) {
     System.out.println(ex.getMessage());
     continue LABEL;
    }
   
   while (!theStack.isEmpty()) {
     postfix.add(theStack.pop());
    }
   System.out.print("This is the posfix expression: ");
   while (!postfix.isEmpty()) {
    System.out.print(postfix.remove());
   }
   System.out.println();
   System.out.println("Enter yes to enter another infix expression");
   if (get.next().equals("yes"))
    stillTesting = true;
   else
    stillTesting = false;
  }
 }

 public static boolean precedence(char first, char second) {
  int firstValue, secondValue;
  if (first == '/' || first == '*')
   firstValue = 1;
  else
   firstValue = 0;
  if (second == '/' || second == '*')
   secondValue = 1;
  else
   secondValue = 0;
  return (firstValue <= secondValue);
 }

}
