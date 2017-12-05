import java.util.ArrayList;

public class localBeamSearch {
    pq initStates = new pq();
    int beam;

    public localBeamSearch(routine[] routines , int beam){
            for(int q=0 ; q<beam ; q++){
                initStates.states.add(routines[q]);
            }
            this.beam = beam;

    }

    routine algo(){

        while(true){
            pq states = new pq();
            routine initialRoutine=null,best;
            for(int i=0 ; i<beam ; i++){
                routine routine = this.initStates.states.remove();
                if(i==0)initialRoutine = routine;
                ArrayList<routine> routines = routine.getChildren();
                for(int j=0 ; j<routines.size() ; j++){
                    states.states.add(routines.get(j));
                }
            }
            best = states.states.remove();
            if(initialRoutine.cost <= best.cost)return initialRoutine;
            initStates.states.add(best);
            for(int i=1 ; i<beam ; i++)initStates.states.add(states.states.remove());
        }
    }
}
