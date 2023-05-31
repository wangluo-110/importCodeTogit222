import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class testgit {

        /*System.out.println("hello git! ---add content33333");
        TestThread a=new TestThread();jum89
        TestThread2 b=new TestThread2();
        a.start();
        b.start();*/
        int[] nums=new int[10];
        private ReentrantLock lock = new ReentrantLock();
        private Condition conditionA = lock.newCondition();
        private Condition conditionB = lock.newCondition();
        private boolean flag = true;


        public static void main(String[] args) {
            String a = "1234";
            String b = "abcd";

            testgit test = new testgit();
            Thread t1 = new Thread(() -> {
                test.outA(a);
            });
            Thread t2 = new Thread(() -> {
                test.outB(b);
            });
            t1.start();
            t2.start();
        }

        private void outA(String a) {
            for (int i = 0; i < a.length(); i ++) {
                lock.lock();
                try {
                    if (!flag) {
                        conditionA.await();
                    }
                    System.out.print(a.charAt(i));
                    /*if (i  < a.length()) {
                        System.out.print(a.charAt(i));
                    }*/
                    flag = false;
                    conditionB.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }

        private void outB(String b) {
            for (int i = 0; i < b.length(); i ++) {
                lock.lock();
                try {
                    if (flag) {
                        conditionB.await();
                    }
                    System.out.print(b.charAt(i));
                   /* if (i  < b.length()) {
                        System.out.print(b.charAt(i));
                    }*/
                    flag = true;
                    conditionA.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }



}
