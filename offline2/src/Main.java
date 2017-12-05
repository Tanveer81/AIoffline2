import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        int numberOfTeachers = 0,numberOfSubjects = 0,numberOfClasses = 0,numberOfRooms = 0,numberOfRequirements = 0,numberOfPeriods = 0,numberOfDays = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Input File No (4/5/6/8)");
        int inputFileNo = sc.nextInt();
        System.out.println("Enter Number of Days");
        numberOfDays = 5;//sc.nextInt();
        System.out.println("Enter Number of Periods");
        numberOfPeriods = 8;//sc.nextInt();
        System.out.println("Enter Number of Beams");
        int beam = 3;//sc.nextInt();
        System.out.println("Enter teacherWeight");
        int teacherWeight =1;//= sc.nextInt();
        System.out.println("Enter roomWeight");
        int roomWeight = 1;//sc.nextInt();
        System.out.println("Enter classWeight");
        int classWeight = 1;//sc.nextInt();
        sc.close();

        int iterationForRandomRestart = 10;

        File file = new File("E:\\L-4 T-2\\AI sessional\\Offline2\\offline2\\src\\hdtt"+ inputFileNo + "note.txt");
        try {
            sc = new Scanner(file);
            numberOfTeachers = sc.nextInt();
            numberOfSubjects = sc.nextInt();
            numberOfClasses = sc.nextInt();
            numberOfRooms = sc.nextInt();
            numberOfRequirements = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }



        File file2 = new File("E:\\L-4 T-2\\AI sessional\\Offline2\\offline2\\src\\hdtt"+ inputFileNo + "req.txt");

        int randomDay , randomPeriod;
        Random rand = new Random();
        int input[][][] = new int[numberOfRooms][numberOfClasses][numberOfTeachers];
        try {
            sc = new Scanner(file2);
            for(int i=0 ; i<numberOfRooms ; i++){
                for(int j=0 ; j<numberOfClasses ; j++){
                    for(int k=0 ; k<numberOfTeachers ; k++){
                        input[i][j][k] = sc.nextInt();
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) { e.printStackTrace(); }

        routine[] routine = new routine[beam]; //initializing beam number of initial states
        for(int i=0 ; i<beam ; i++) {
            routine[i] = new routine(numberOfDays , numberOfPeriods ,numberOfTeachers ,numberOfClasses ,numberOfRooms,teacherWeight,roomWeight,classWeight);
        }//

        for(int q=0 ; q<beam ; q++){
            for (int i = 0; i < numberOfRooms; i++) {
                for (int j = 0; j < numberOfClasses; j++) {
                    for (int k = 0; k < numberOfTeachers; k++) {
                        for (int p = 0; p < input[i][j][k]; p++) {
                            randomDay = rand.nextInt(numberOfDays);
                            randomPeriod = rand.nextInt(numberOfPeriods);
                            node o = new node(i, j, k);
                            routine[q].blks[randomDay][randomPeriod].nodes.add(o);
                        }
                    }
                }
            }
            routine[q].cost=routine[q].cost();
            /*/////////////////////only for checking initial states///////////////////////////////////
            System.out.println("Initial State No : " + q + "\n");
            for(int i=0 ; i<numberOfDays ; i++){
                for(int j=0 ; j<numberOfPeriods ; j++){
                    for( node a : routine[q].blks[i][j].nodes){
                        System.out.print(a.teacher+"."+a.classNo+"."+a.room+"|");
                    }
                    System.out.print("  ");
                }
                System.out.println("");System.out.println("");
            }
            System.out.println("\n\n");
            //////////////////////only for checking initial states//////////////////////////////////*/
        }

        localBeamSearch localBeamSearch = new localBeamSearch(routine , beam);
        routine result = localBeamSearch.algo();

        //randomRestartLocalBeam rlbs = new randomRestartLocalBeam(iterationForRandomRestart,beam,numberOfRooms, numberOfClasses,numberOfTeachers,input,numberOfDays,numberOfPeriods,teacherWeight,roomWeight, classWeight);
        //routine result = rlbs.getResultRoutine();

        //stochastic stochastic = new stochastic(routine , beam);
        //routine result = stochastic.algo();

        //randomRestartStochastic rls = new randomRestartStochastic(iterationForRandomRestart,beam,numberOfRooms, numberOfClasses,numberOfTeachers,input,numberOfDays,numberOfPeriods,teacherWeight,roomWeight, classWeight);
        //routine result = rls.getResultRoutine();



        System.out.println("printing the result tcr");
        System.out.println("cost :" + result.cost);
        for(int i=0 ; i<numberOfDays ; i++){
            for(int j=0 ; j<numberOfPeriods ; j++) {
                for( node a : result.blks[i][j].nodes) {
                    System.out.print(a.teacher + "." + a.classNo + "." + a.room + "|");
                }
                System.out.print("  ");
            }
            System.out.println("");System.out.println("");
        }
        System.out.println("\n\n");
    }
}

