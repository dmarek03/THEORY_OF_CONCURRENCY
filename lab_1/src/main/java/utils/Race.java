package utils;

import java.util.HashMap;
import java.util.Map;


public class Race {

    private final int numberOfRun;
    private final boolean isPredictable;
    Map<Integer, Integer> counterValueMap =  new HashMap<>();

    public Race(int raceNumber, boolean isPredictable) {

        this.numberOfRun = raceNumber;
        this.isPredictable = isPredictable;
    }



    public void beginRace(){
        for(int i = 0; i < numberOfRun; i ++){
            Counter cnt;
            if(isPredictable){
                cnt = new PredictableCounter(0);
            }else {
                cnt = new Counter(0);
            }

            IThread iThread = new IThread(cnt);
            DThread dThread = new DThread(cnt);

            iThread.start();
            dThread.start();


            try {
                iThread.join();
                dThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            counterValueMap.put(cnt.value(), counterValueMap.getOrDefault(cnt.value(), 0) + 1);


        }
        for (Map.Entry<Integer, Integer> entry : counterValueMap.entrySet()) {
            System.out.println("Wartość " + entry.getKey() + ": " + entry.getValue() + " razy");
        }

        for (Integer key : counterValueMap.keySet()) {
            System.out.print("Wartość:" + " " + key);
        }
        for (Integer value : counterValueMap.values()) {
            System.out.println(value);
        }

    }


}
