abstract class Zone{
    private int population;

    public Zone(int populationSize)
    {
	setPopulation(populationSize);
    }

    public Zone()
    {
	setPopulation(0);
    }

    public int getPopulation(){
	return population;
    }
    
    public void setPopulation(int population){
	this.population = population;
    }
}