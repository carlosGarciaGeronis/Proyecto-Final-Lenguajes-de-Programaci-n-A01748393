import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Queue;
import java.util.LinkedList;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;

public class Buffer {
    
    Queue<String> buffer = new LinkedList<String>();
    static int bufferSize = 1;
    int consumedCount = 0;
    JSpinner jSpinner;
    JProgressBar jProgress;
    
    Buffer(int bufferSize, JProgressBar jProgress, JSpinner jSpinner) {
        this.bufferSize = bufferSize;
        this.jProgress = jProgress;
        this.jSpinner = jSpinner;
        count = 0;
        jProgress.setMaximum(this.bufferSize);
    }
    
    
    synchronized String consume() {
        String prod;
        
        while(buffer.size() < 1) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        prod = this.buffer.poll();
        notifyAll();
        jProgress.setValue(this.buffer.size());
        consumedCount++;
        jSpinner.setValue(consumedCount);
        return prod;
    }
    
    synchronized boolean produce(String product) {
        while(this.buffer.size() >= this.bufferSize) {
            System.out.println("El buffer esta lleno, no se puede producir mas");
            try {
                wait();
                return false;
            } catch (InterruptedException ex) {
                Logger.getLogger(Buffer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        this.buffer.add(product);
        jProgress.setValue(this.buffer.size());
        notifyAll();
        return true;
    }
    
    static int count = 1;
    synchronized static void print(String string) {
        System.out.print(count++ + " ");
        System.out.println(string);
    }
    
}