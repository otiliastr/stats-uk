
public class DefaultSearchStrategy implements SearchStrategy {
	private GUIDirector mediator;
	private String[] regions;
	
	public DefaultSearchStrategy(GUIDirector mediator){
		this.mediator = mediator;
		regions = mediator.getRegions();
	}
	
	/*format the word */
	public String formatWord(String word){		
		//only lower letters
		word = word.toLowerCase();
				
	    //remove unnecessary whitespaces
		word = word.replaceAll("\\s+", " "); //remove repeated whitespaces
		if (word.length() > 0 && Character.isWhitespace(word.charAt(0)) ) //remove white spaces at the beginning of the string 
		  	word = word.substring(1);
		if (word.length() > 0 && Character.isWhitespace(word.charAt(word.length()-1)) ) //remove white spaces at the end of the string 
			word = word.substring(0, word.length()-1); 		   
		
		return word;
	}
	
	@Override
	public void performSearch(String word) {
		word = formatWord(word); 
		
		if (word.length() == 0) 
			return;
		
		//search in region array
		for (int i = 0; i < regions.length; ++i)
			if ((regions[i].toLowerCase()).equals(word)){ //the entry is a valid region name
				mediator.paintRegion(word);
				break;
			}
		
		//if not found , print suggestions in main frame
		String [] suggestions = {"England", "West Midlands", "East Midlands"};
		mediator.searchPerformed(suggestions);
	}

}
