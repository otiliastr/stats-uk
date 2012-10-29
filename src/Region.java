import java.util.ArrayList;

class Region extends Zone{
    private ArrayList<Zone> zones;

    public Region(){
	zones = new ArrayList<Zone>();
	setPopulation(0);
    }

    public Region(int populationSize){
	zones = new ArrayList<Zone>();
	setPopulation(populationSize);   
    }
}