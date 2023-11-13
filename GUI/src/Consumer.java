import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Consumer extends Thread {
    Buffer buffer;
    int id;
    int waitTime = 0;
    JTable jTable;
    
    boolean running = false;
    
    Consumer(Buffer buffer, int id, int waitTime, JTable jTable) {
        this.buffer = buffer;
        this.id = id;
        this.waitTime = waitTime;
        this.jTable = jTable;
    }
    
    public void StopRunning(){
        running = false;
        System.out.println("Stopping Consumer " + id + "...");
    }
    
    @Override
    public void run() {
        System.out.println("Running Consumer " + id + "...");
        String product;
        
        running = true;
        
        int a;
        int b;
        char operator;
        int answer;
        
        while(running) {
            //Consume deberia regresar un String
            product = this.buffer.consume();
            
            if(!product.equalsIgnoreCase("nulo"))
            {
                operator = product.charAt(1);
                a = Integer.parseInt(""+product.charAt(3));
                b = Integer.parseInt(""+product.charAt(5));

                switch(operator){
                        case'+':
                            answer = a+b;
                            break;
                        case '-':
                            answer = a-b;
                            break;
                        case '*':
                            answer = a*b;
                            break;
                        case '/':
                            answer = a/b;
                            break;
                        default:
                            answer = 0;
                }
                //System.out.println("Consumer consumed: " + product);
                Buffer.print("Consumer " + id + " consumed: " + product + " = " + answer);
                DefaultTableModel model = (DefaultTableModel) jTable.getModel();
                model.addRow(new Object[]{product, answer, id});
            }
            
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}