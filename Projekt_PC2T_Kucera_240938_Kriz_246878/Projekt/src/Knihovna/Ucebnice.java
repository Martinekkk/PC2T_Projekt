package Knihovna;

public class Ucebnice extends Kniha
{
	    
	private int Rocnik;
	    
	public Ucebnice(String nazev, String[] autor, int rokVydani, boolean dostupnost, int rocnik) 
	{        
		super(nazev, autor, rokVydani, dostupnost, TypyKnih.ucebnice);
	    Rocnik = rocnik;        
	}
	    
	public int getRocnik() 
	{
	    return Rocnik;
	}
	   
	@Override
	public String toString() 
	{
	    return super.toString() + ", Typ: ucebnice, Rocnik: " + Rocnik;
	}
	    
	@Override
	public String ulozeniDoSouboru()
	{
	    return super.ulozeniDoSouboru() + ";" + Rocnik;
	}
}

