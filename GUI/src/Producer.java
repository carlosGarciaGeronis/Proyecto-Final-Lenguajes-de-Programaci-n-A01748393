import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
        
public class Producer extends Thread {
    
    //OPERATOR CONSTANTS
    final int PLUS = 1;
    final int MINUS = 1 << 1;
    final int MULT = 1 << 2;
    final int DIV = 1 << 3;
    
    Buffer buffer;
    int id;
    boolean running = false;
    int minValueRange = 0;
    int maxValueRange = 0;
    byte schemeOperators = 0;
    int waitTime = 0;
    JTable jTable;
    
    Producer(Buffer buffer, int id, int minValueRange, int maxValueRange, byte schemeOperators, int waitTime, JTable jTable) {
        this.buffer = buffer;
        this.id = id;
        this.minValueRange = minValueRange;
        this.maxValueRange = maxValueRange;
        this.schemeOperators = schemeOperators;
        this.waitTime = waitTime;
        this.jTable = jTable;
    }
    
    public void StopRunning(){
        running = false;
        System.out.println("Stopping Producer " + id + "...");
    }
    
    @Override
    public void run() {
        System.out.println("Running Producer " + id + "..."); 
        Random r = new Random(System.currentTimeMillis());
                
        int a;
        int b;
        String product;
        char operator;
        String operatorsOpt = "";
        
        running = true;
        
        if((schemeOperators & PLUS) != 0){
            operatorsOpt += "+";
        }
        if((schemeOperators & MINUS) != 0){
            operatorsOpt += "-";
        }
        if((schemeOperators & MULT) != 0){
            operatorsOpt += "*";
        }
        if((schemeOperators & DIV) != 0){
            operatorsOpt += "/";
        }
        
        while(running) {
            r.setSeed(System.nanoTime());
            
            a = r.nextInt(this.maxValueRange - this.minValueRange + 1) + this.minValueRange;
            b = r.nextInt(this.maxValueRange - this.minValueRange + 1) + this.minValueRange;            
            
            operator = operatorsOpt.charAt(r.nextInt(operatorsOpt.length()));
            
            if(operator == '/' && b == 0) continue;
            
            product = "(" + operator + " " + a + " " + b + ")";
            
            //Buffer deberia recibir un string y agregarlo a una lista
            if(this.buffer.produce(product)){
                //System.out.println("Producer produced: " + product);
                Buffer.print("Producer "+ id +" produced: " + product);
                DefaultTableModel model = (DefaultTableModel) jTable.getModel();
                model.addRow(new Object[]{product, id});
            }
            
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Producer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}