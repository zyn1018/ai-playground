package com.yinan.play.demo.meta;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String operationStr = in.nextLine();
        String paramStr = in.nextLine();

        if (operationStr == null || operationStr.isEmpty()) {
            return;
        }
        String[] operations = operationStr.split(",");
        String[] params = paramStr.split(",");
        if (operations.length != params.length) {
            return;
        }

        MyQueue queue = new MyQueue();
        boolean hasPrinted = false;
        int paramIndex = 0;
        for (String operation: operations) {
            if (operation == null || operation.isEmpty()) {
                paramIndex += 1;
                continue;
            }
            String result = "";
            String param = params[paramIndex];
            if (operation.equals("push")) {
                queue.push(Integer.valueOf(param));

            } else if (operation.equals("peek")) {
                int peekValue = queue.peek();
                result = String.valueOf(peekValue);
            } else if (operation.equals("pop")) {
                int popValue = queue.pop();
                result = String.valueOf(popValue);
            } else if (operation.equals("empty")) {
                boolean emptyValue = queue.empty();
                result = emptyValue ? "false" : "true";
            }

            if (hasPrinted) {
                System.out.print("," + result);
            } else {
                System.out.print(result);
                hasPrinted = true;

            }

        }


    }
    static class MyQueue {
        private Stack<Integer> s1;

        private Stack<Integer> s2;

        public MyQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        public void push(int x) {
            s1.add(x);
        }

        public int pop() {
            if (!s1.isEmpty()) {
                // 如果s1不为空，将其中元素都先加到s2中再pop
                while (!s1.isEmpty()) {
                    int element = s1.pop();
                    s2.add(element);
                }
            }
            return s2.pop();
        }

        public int peek() {
            if (!s1.isEmpty()) {
                // 如果s1不为空，将其中元素都先加到s2中再pop
                while (!s1.isEmpty()) {
                    int element = s1.pop();
                    s2.add(element);
                }
            }
            return s2.peek();
        }

        public boolean empty() {
            return s1.isEmpty() && s2.isEmpty();
        }
    }
}
