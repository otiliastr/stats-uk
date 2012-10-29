public class CrimeData implements StatisticData {
    // Stores the tags associated with crime rates
    private static ArrayList<String> tags;
    // Stores one data corresponding to one region
    private ArrayList<String> data;

    public CrimeData() {
        tags = new ArrayList<String>();
        data = new ArrayList<String>();
    }

    public CrimeData(ArrayList<String> tags, ArrayList<String> data) {
        this.tags = tags;
        this.data = data;
    }

    public ArrayList<String> getData() {
        return data;
    }

    public ArrayList<String> getTags() {
        return tags;
    }
}
