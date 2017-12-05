import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class node {
        int teacher;
        int room;
        int classNo;

        public node(int room, int classNo ,int teacher) {
            this.teacher = teacher;
            this.room = room;
            this.classNo = classNo;
        }
    }

    class block{
        ArrayList<node> nodes;
        block(){
            nodes = new ArrayList<node>();
        }
    }

    class pq {
        PriorityQueue<routine> states = new PriorityQueue<routine>(10, new Comparator<routine>() {
            public int compare(routine node1, routine node2) {
                if (node1.cost < node2.cost) return -1;
                if (node1.cost > node2.cost) return 1;
                return 0;
            }
        });
    }

    class routine{
        block blks[][];
        int cost,days,periods,teachers,classes,rooms,w1,w2,w3;

        public routine(int days , int periods ,int teachers,int classes,int rooms , int w1, int w2, int w3){
            this.days = days;this.periods = periods;this.teachers = teachers;this.classes = classes;
            this.rooms = rooms;this.w1 = w1;this.w2 = w2;this.w3 = w3;

            blks = new block[days][periods];
            for(int i=0 ; i<days ; i++) {
                for (int p = 0; p < periods; p++) {
                    blks[i][p] = new block();
                }
            }
        }

        public int cost(){
            int costOfRoutine = 0 , number = 0;
            for(int i=0 ; i<days ; i++){
                for(int j=0 ; j<periods ; j++){

                    for(int k=0 ; k<teachers ; k++){
                        for(node a : blks[i][j].nodes){
                            if(a.teacher == k)number++;
                        }
                        if(number != 0)costOfRoutine+=(number-1)*w1;
                        number = 0;
                    }

                    for(int k=0 ; k<rooms ; k++){
                        for(node a : blks[i][j].nodes){
                            if(a.room == k)number++;
                        }
                        if(number != 0)costOfRoutine+=(number-1)*w2;
                        number = 0;
                    }

                    for(int k=0 ; k<classes ; k++){
                        for(node a : blks[i][j].nodes){
                            if(a.classNo == k)number++;
                        }
                        if(number != 0)costOfRoutine+=(number-1)*w3;
                        number = 0;
                    }

                }
            }
            return costOfRoutine;
        }

        ArrayList<routine> getChildren(){ //all children are inserted into routines and returned
            ArrayList<routine> routines = new ArrayList<routine>();
            for(int i=0 ; i<days ; i++){
                for(int j=0 ; j<periods ; j++) {
                    for(int g=0 ; g<blks[i][j].nodes.size() ; g++){
                        for(int k=0 ; k<days ; k++){
                            for(int l=0 ; l<periods ; l++) {
                                if(i!=k && j!=l){ //creating new children
                                    routine temp = new routine(days , periods , teachers , classes , rooms , w1,w2,w3);
                                    makeCopy(temp);
                                    temp.blks[k][l].nodes.add(temp.blks[i][j].nodes.remove(g));
                                    temp.cost = temp.cost();
                                    routines.add(temp);
                                }
                            }
                        }
                    }
                }
            }
            return routines;
        }

        void makeCopy(routine temp){ //copy routine into temp unchanged
            for(int i=0 ; i<days ; i++){
                for(int j=0 ; j<periods ; j++) {
                    for(node a : blks[i][j].nodes){
                        temp.blks[i][j].nodes.add(a);
                    }
                }
            }
        }

    }



