import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTable;




public class ProducerConsumer {
    
    int numProducers = 0;
    int numConsumers = 0;
    int bufferSize = 1;
    int minValueRange = 0;
    int maxValueRange = 0;
    byte schemeOperators = 0;
    int producerWaitTime = 0;
    int consumerWaitTime = 0;
    Producer[] producerList;
    Consumer[] consumerList;
    
    ProducerConsumer(int numProducers, int numConsumers, int bufferSize, int minValueRange, int maxValueRange, byte schemeOperators, int producerWaitTime, int consumerWaitTime){
        this.numProducers = numProducers;
        this.numConsumers = numConsumers;
        this.bufferSize = bufferSize;
        this.minValueRange = minValueRange;
        this.maxValueRange = maxValueRange;
        this.schemeOperators = schemeOperators;
        this.producerWaitTime = producerWaitTime;
        this.consumerWaitTime = consumerWaitTime;
    }
    
    public void runPC(JTable producerTable, JTable consumerTable, JProgressBar jProgress, JSpinner jSpinner) {
        
        Buffer buffer = new Buffer(this.bufferSize, jProgress, jSpinner);
        producerList = new Producer[numProducers];
        consumerList = new Consumer[numConsumers];
        for(int i=0; i<numProducers; i++)
        {
            producerList[i] = new Producer(buffer, i, minValueRange, maxValueRange, schemeOperators, producerWaitTime, producerTable);
            producerList[i].start();
        }
        for(int i=0; i<numConsumers; i++)
        {
            consumerList[i] = new Consumer(buffer, i, consumerWaitTime, consumerTable);
            consumerList[i].start();
        }
    }
    
    public void stopPC(){
        for(int i=0; i<numProducers; i++)
        {
            producerList[i].StopRunning();
        }
        for(int i=0; i<numConsumers; i++)
        {
            consumerList[i].StopRunning();
        }
    }
    
}