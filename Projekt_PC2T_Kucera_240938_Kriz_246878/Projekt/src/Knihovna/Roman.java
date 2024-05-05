package Knihovna;

public class Roman extends Kniha 
{
    
	private Zanry Zanr;

    public enum Zanry 
    { 
    	biograficky,
    	dobrodruzny,
    	historicky,
    	profesni,
    	valecny
    }
    
    public Roman(String nazev, String[] autor, int rokVydani, boolean dostupnost, Zanry zanr) 
    {    
        super(nazev,autor,rokVydani,dostupnost, TypyKnih.roman);
    	Zanr = zanr;
    }

    public Zanry getZanr() 
    {
        return Zanr;
    }
    
    @Override
    public String toString() 
    {
        return super.toString() + ", Typ: roman, Zanr: " + Zanr;
    }

    @Override
    public String ulozeniDoSouboru()
    {
        return super.ulozeniDoSouboru() + ";" + Zanr.ordinal();
    }
}
