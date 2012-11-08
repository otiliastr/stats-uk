abstract class Zone{
    private int population;
    private double normalisedRating;
    private String name;
    private CrimeData crimeData;

    public Zone(String name, int populationSize){
        this.name = name;
        setPopulation(populationSize);
    }

    public Zone(String name){
        this(name,0);
    }

    public void setCrimeData(CrimeData crimeData){
        this.crimeData = crimeData;
    }

    public int getPopulation(){
        return population;
    }

    public void setPopulation(int population){
        this.population = population;
    }

    public double getRating() {
        return crimeData.getRating();
    }

    public void setNormalisedRating(double rating) {
        this.normalisedRating = rating; 
    }

    public String getName(){
        return name;
    }

    // override toString to help with error messages
    // prints out all the information about this particular Zone
    public String toString() {
        String resultString = name + '\n';
        resultString += String.valueOf(population) + '\n';
        resultString += crimeData.toString();

        return resultString;
    }
}
