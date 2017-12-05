import java.util.Random;

public class randomRestartStochastic {
    int iterations;
    routine resultRoutine;

    public routine getResultRoutine() {
        return resultRoutine;
    }

    public randomRestartStochastic(int iterations , int beam,
                                int numberOfRooms,int numberOfClasses,int numberOfTeachers,
                                   int[][][] input,int numberOfDays,int numberOfPeriods,
                                   int teacherWeight,int roomWeight,int classWeight){
        this.iterations = iterations;
        int cost = Integer.MAX_VALUE;
        Random rand = new Random();
        for(int ii=0;ii<iterations;ii++){
            routine[] routine = new routine[beam]; //initializing beam number of initial states
            for(int i=0 ; i<beam ; i++) {
                routine[i] = new routine(numberOfDays , numberOfPeriods ,numberOfTeachers ,numberOfClasses ,numberOfRooms,teacherWeight,roomWeight,classWeight);
            }//
            for(int q=0 ; q<beam ; q++){
                for (int i = 0; i < numberOfRooms; i++) {
                    for (int j = 0; j < numberOfClasses; j++) {
                        for (int k = 0; k < numberOfTeachers; k++) {
                            for (int p = 0; p < input[i][j][k]; p++) {
                                int randomDay = rand.nextInt(numberOfDays);
                                int randomPeriod = rand.nextInt(numberOfPeriods);
                                node o = new node(i, j, k);
                                routine[q].blks[randomDay][randomPeriod].nodes.add(o);
                            }
                        }
                    }
                }
                routine[q].cost=routine[q].cost();
            }



            stochastic stochastic = new stochastic(routine , beam);
            routine result = stochastic.algo();
            if(result.cost<cost){
                cost = result.cost;
                resultRoutine = result;
            }
        }
    }
}
