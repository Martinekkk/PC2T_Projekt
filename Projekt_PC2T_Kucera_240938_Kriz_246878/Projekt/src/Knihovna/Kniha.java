package Knihovna;

public abstract class Kniha 
{
	
	private String Nazev;
	    private String[] Autor;
	    private int RokVydani;
	    private boolean Dostupnost;
	    private TypyKnih Typ;
	    
	public enum TypyKnih
	{
	    ucebnice,
	    roman
	}
	
	protected Kniha(String nazev, String[] autor, int rokVydani, boolean dostupnost, TypyKnih typ) 
	{
        Nazev = nazev;
        Autor = autor;
        RokVydani = rokVydani;
        Dostupnost = dostupnost;
        Typ = typ;
    }
	
	public String getNazev()
	{
		return Nazev;
	}

	public String[] getAutor ()
	{
		return Autor;
	}
	
	public int getRokVydani ()
	{
		return RokVydani;
	}
	
	public boolean getDostupnost()
	{
		return Dostupnost;
	}
	
	public TypyKnih getTypyKnih()
	{
		return Typ;
	}
	
	public int getTypyKnihInteger()
	{	
		if(Typ==TypyKnih.roman)
			return 1;
		else 
			return 0;
	}
	
	public String autori()
	{
		return String.join(",", Autor);
	}
	
	public void nastavDostupnost(boolean dostupnost) 
	{
		Dostupnost = dostupnost;
	}
	
	@Override
	public String toString()
	{
		String dostupnost;
		String vypis;
		
		
		if(Dostupnost)
			dostupnost = "Volna";
		else
			dostupnost = "Vypujcena";
		
		vypis = "\nNazev knihy: " + Nazev + ", Autor/i knihy: " + autori() + ", Rok vydani knihy: " + RokVydani + ", Dostupnost knihy: " + dostupnost ;
		
		return vypis;
	}
	
	public String ulozeniDoSouboru()
	{
		return String.format("%s;%s;%d;%s;%s", Nazev, autori(), RokVydani, Dostupnost, getTypyKnihInteger());
	}
	
	
	
}

