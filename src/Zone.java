abstract class Zone{
    private int population;
    private String name;
    private CrimeData crimeData;

    public Zone(String name, int populationSize){
        this.name = name;
        setPopulation(populationSize);
    }

    public Zone(String name){
        this(name,0);
    }

    public void  setCrimeData(CrimeData cd){
        this.crimeData = cd;
    }

    public int getPopulation(){
        return population;
    }

    public void setPopulation(int population){
        this.population = population;
    }

    public String getName(){
        return name;
    }
}
