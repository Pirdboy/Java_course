package cqu.shy.arithmetic;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by SHY on 2014/12/16/0016.
 */
public class Arithmetic {
    public static int mode=1;   //1表示弧度制,2表示角度制
    public static String getResult(ArrayList<Element> infix){

        Stack<Element> myRPN = infixToRPN(infix);
        if(myRPN==null)
            return "表达式输入有误";
        String result = computeRPN(myRPN);
        return result;
    }
    private static Stack<Element> infixToRPN(ArrayList<Element> infix){
        Stack<Element> RPN = new Stack<Element>();
        Stack<Element> opeStack = new Stack<Element>();
        int i;
        for(i=0;i<infix.size();i++){
            Element e = infix.get(i);
            if(e.type==ElementType.OPERATION && e.ope=='('){
                //左括号
                opeStack.push(e);
            }
            else if(e.type==ElementType.OPERATION && e.ope==')'){
                //右括号,弹出opeStack元素,直到遇到左括号
                boolean haveLeft=false;
                while(!opeStack.empty()){
                    Element temp = opeStack.pop();
                    if(temp.type==ElementType.OPERATION && temp.ope=='('){
                        haveLeft=true;
                        break;
                    }
                    else{
                        RPN.push(temp);
                    }
                }
                if(!haveLeft){ //表达式语法有错
                    return null;
                }
            }
            else if(e.type==ElementType.OPERATION){
                //如果是运算符
                while(!opeStack.empty() && priority(e.ope)<priority(opeStack.peek().ope)){
                    RPN.push(opeStack.pop());
                }
                opeStack.push(e);
            }
            else if(e.type==ElementType.NUM || e.type==ElementType.FUNCTION){
                //如果是操作数
                RPN.push(e);
            }
        }
        //表达式结束,剩余元素全部放进RPN
        while(!opeStack.empty()){
            RPN.push(opeStack.pop());
        }
        //需要将RPN反转一次
        Stack<Element> finalRPN = new Stack<Element>();
        while(!RPN.empty()){
            finalRPN.push(RPN.pop());
        }
        return finalRPN;
    }
    private static String computeRPN(Stack<Element> RPN){
        Stack<Element> tempStack = new Stack<Element>();
        while(!RPN.empty()){
            Element ele = RPN.pop();
            if(ele.type==ElementType.NUM || ele.type==ElementType.FUNCTION){
                tempStack.push(ele);
            }
            else if(ele.type==ElementType.OPERATION){
                if(tempStack.size()<2){
                    return "表达式输入有误";
                }
                Element e1=tempStack.pop();
                Element e2=tempStack.pop();
                if(e1.type==ElementType.OPERATION || invalid(e1.value.toString()))
                    return "value invalid";
                if(e2.type==ElementType.OPERATION || invalid(e2.value.toString()))
                    return "value invalid";
                Element re = new Element();
                re.type=ElementType.NUM;
                if(ele.ope=='+'){
                    re.value = e2.value+e1.value;
                }
                else if(ele.ope=='-'){
                    re.value = e2.value-e1.value;
                }
                else if(ele.ope=='×'){
                    re.value = e2.value*e1.value;
                }
                else if(ele.ope=='/'){
                    if(Math.abs(e1.value-0.0)<0.000000000000001){
                        //除数为0
                        return "value invalid";
                    }
                    else{
                        re.value = e2.value/e1.value;

                    }
                }
                else{
                    return "表达式输入有误";
                }
                tempStack.push(re);
            }
        }
        if(tempStack.size()==1){
            return String.valueOf(tempStack.pop().value);
        }
        else
            return "表达式输入有误";
    }
    private static int priority(char ope){
        switch (ope){
            case '(':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '×':
                return 4;
            case '/':
                return 5;
            default:
                return 0;
        }
    }
    private static boolean invalid(String s){
        if(s.equals("")||s.equals("NaN")||s.equals("Infinity"))
            return true;
        else
            return false;
    }
}
