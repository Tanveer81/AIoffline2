import java.util.ArrayList;
import java.util.Random;

public class stochastic {
    pq initStates = new pq();
    int beam;

    public stochastic(routine[] routines, int beam) {
        for (int q = 0; q < beam; q++) {
            initStates.states.add(routines[q]);
        }
        this.beam = beam;
    }

    routine algo(){

        Random rand = new Random();
        while(true){
            pq states = new pq();
            ArrayList<routine> rt = new ArrayList<routine>();//////////
            routine initialRoutine=null,best;
            for(int i=0 ; i<beam ; i++){
                routine routine = this.initStates.states.remove();
                if(i==0)initialRoutine = routine;
                else rt.add(routine);//////////
                ArrayList<routine> routines = routine.getChildren();
                for(int j=0 ; j<routines.size() ; j++){
                    states.states.add(routines.get(j));
                    rt.add(routines.get(j));//////////
                }
            }
            best = states.states.remove();
            if(initialRoutine.cost <= best.cost)return initialRoutine;
            initStates.states.add(best);

            //////////////////////////////////
            int maxCost = 0;
            ArrayList<Integer> tickets = new ArrayList<Integer>();

            for(int i=0;i<rt.size();i++) {
                if (rt.get(i).cost > maxCost) maxCost = rt.get(i).cost;
            }
            int totalOccur = 0;
            for(int i=0;i<rt.size();i++) {
                int occurrence = maxCost + 1 - rt.get(i).cost;
                totalOccur+=occurrence;
                for(int j=0;j<occurrence;j++)tickets.add(j);
            }
            for(int i=1 ; i<beam ; i++){
                int selected = rand.nextInt(totalOccur);
                initStates.states.add(rt.get(tickets.get(selected)));
            }
        }
    }
}

