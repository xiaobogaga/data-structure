package com.tomzhu.example.list;

import com.tomzhu.list.MyNosuchElementException;
import com.tomzhu.list.MyNotSupportException;
import com.tomzhu.list.Stack;

/**
 * a simple postfix expression implementation using stack
 * is to transfer a standard expression like :
 * a + b to postfix expression like :
 * a b +.
 */
public class PostfixExpression {

    /**
     *
     */
    public enum Operators{
        ADD('+' , 0) ,
        MINUS('-' , 0) ,
        PRODUCT('*' , 1) ,
        DIV('/' , 1) ,
        LEFTP('(' , 2) ,
        RIGHTP(')' , 2);

        private char s;
        private int priority;

        private Operators(char s , int priority) {
                this.s = s;
                this.priority = priority;
        }

        public char getSign() {
            return s;
        }

        public int getPriority() {
            return this.priority;
        }

        public static Operators fetchOperator(char s) {
            if (s == '+')
                return ADD;
            if (s == '-')
                return MINUS;
            if (s == '/')
                return DIV;
            if (s == '*')
                return PRODUCT;
            return null;
        }

    }


    /**
     * a static method for transfer standard expression to
     * postfix expression.
     *
     * We use a pending stack to saving signs . and each time:
     * 1. if the read sign is an operand, like a variable or constant
     *    we can simply put it in the result sequence.
     * 2. if the read sign is an operator but (opening parenthesis or closing
     *    parenthesis) (which must be a member of
     *    @see{com.tomzhu.list.example.PostfixExpression.Operators}) ,
     *    then we push into the pending stack. However before pending
     *    it to the pending stack, we first pop the operators in the stack
     *    which are not less prioritized than the adding operator, remember, if
     *    there exits a (, then it and in front of it , the operators will not poped.
     * 3. if the read sign is ( , just push it to the stack.
     * 4. if the read sign is ) , we pop the enties between ( and ) , the put it
     *    to sequence not include ( and ).
     * just giving an example to illustrate:
     *   a + b * c + ( d * e + f) / g
     * 1sh turn:
     *  stack :
     *  sequence : a
     * 2nd turn:
     *  stack : +
     *  sequence : a
     * 3rd turn:
     *  stack : +
     *  sequence : a b
     * 4th turn:
     *  stack : + *
     *  sequence : a b c
     * 5th turn:
     *  stack : + * +
     *  sequence : a b c
     *  because of the priority, before pushing + , we must pop the more or equal prioritized.
     *  stack : +
     *  sequence : a b c * +
     * 6th turn:
     *  stack : + (
     *  sequence : a b c * +
     * 7th turn:
     *  stack : + (
     *  sequence : a b c * + d
     * 8th turn:
     *  stack : + ( *
     *  sequence : a b c * + d
     * 9th turn:
     *  stack : + ( *
     *  sequence : a b c * + d e
     * 10th turn:
     *  stack : + ( +
     *  sequence : a b c * + d e *
     * 11th turn:
     *  stack : + ( +
     *  sequence a b c * + d e * f
     * 12th turn:
     *  stack : +
     *  sequence : a b c * + d e * f +
     * 13th turn:
     *  stack : + /
     *  sequence : a b c * + d e * f +
     * 14th turn:
     *  stack : + /
     *  sequence : a b c * + d e * f + g
     * ended :
     *  stack :
     *  sequence : a b c * + d e * f + g / +
     *
     *
     *
     * @param expressoin
     * @return
     */
    public static String transfer(String expressoin) throws MyNosuchElementException, MyNotSupportException {
        StringBuilder sequence = new StringBuilder();
        char[] array = expressoin.toCharArray();
        Stack<Operators> pendingStack = new Stack<Operators>();
        for (char c : array) {

            // if it is a variable or digit.
            if (Character.isLetter(c) || Character.isDigit(c))
                sequence.append(c);

            // if it is a (
            else if (c == Operators.LEFTP.getSign())
                pendingStack.push(Operators.LEFTP);

            // if it is a )
            else if (c == Operators.RIGHTP.getSign()) {
                Operators temp;
                while (!pendingStack.isEmpty()) {
                    temp = pendingStack.pop();
                    if ( temp.getSign() == '(' )
                        break;
                    else
                        sequence.append(temp.getSign());
                }
            }

            // just whitespace
            else if (c == ' ')
                continue;

            // otherwise.
            else {
                Operators ope = Operators.fetchOperator(c);
                Operators temp;
                if (ope == null)
                    throw new MyNotSupportException("current not support this operator " + c);
                while (!pendingStack.isEmpty()) {
                    temp = pendingStack.getHead();
                    if (temp.getSign() == '(') {
                        break;
                    }
                    else if (temp.getPriority() >= ope.getPriority())
                        sequence.append(pendingStack.pop().getSign());
                    else
                        break;
                }
                pendingStack.push(ope);
            }

        }

        // pop the stack and add to sequence
        while (!pendingStack.isEmpty())
            sequence.append(pendingStack.pop().getSign());
        return sequence.toString();
    }

    /**
     * this method is simply used to calculate the value of
     * a polynomial using postfix expression.
     * @param sequence
     * @return
     */
    public int calcValue(String sequence) {
        return 0;
    }

    public static void main(String args[]) throws MyNotSupportException, MyNosuchElementException {
        String s = "a + b * c + ( d * e + f) / g";
        System.out.println(PostfixExpression.transfer(s));
    }
}
