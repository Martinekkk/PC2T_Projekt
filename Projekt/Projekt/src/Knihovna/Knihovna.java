package Knihovna;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import Knihovna.Kniha.TypyKnih;
import Knihovna.Roman.Zanry;

public class Knihovna 
{
    private SortedMap<String, Kniha> SeznamKnih;
    private Soubor NaseDatabaze;
	private static Scanner sc = new Scanner(System.in);

    public static boolean AnoNe() 
    {
        boolean vysledek = false;
        boolean opakovat = true;

        while (opakovat) 
        {
            String vstup = sc.nextLine();
            if (vstup.equals("ano") || vstup.equals("a")) 
            {
                vysledek = true;
                opakovat = false;
            } 
            else if (vstup.equals("ne") || vstup.equals("n")) 
            {
                vysledek = false;
                opakovat = false;
            } 
            else
                System.out.println("\nNapsal jste chybne slovo, napis ano / ne !");
        }
        return vysledek;
    }
    
    public static int pouzeCelaCisla() 
	{
    	int cislo = 0;
        boolean uspesne = false;

        while (!uspesne) 
        {
            if (sc.hasNextInt()) 
            {
                cislo = sc.nextInt();
                uspesne = true;
            } 
            else 
            {
                System.out.println("\nNenapsal jste cele cislo, opakujte zadanim celeho cisla!");
                sc.next();
            }
        }
        sc.nextLine();
        return cislo;
    }
    
    public static int pouzeCelaCislaRokVydani() 
 	{
     	int cislo = 0;
         boolean uspesne = false;

         while (!uspesne) 
         {
             if (sc.hasNextInt()) 
             {
            	 do
            	 {
            		 cislo = sc.nextInt();
            		 uspesne = true;
            		 if (cislo>2024)
            		 		System.out.println("\nNapiste platne cele cislo vydani knihy, rok vydani knihy musi byt mensi nebo rovno 2024!");
            	 }
                 while((cislo >2024));
             } 
             else 
             {
                 System.out.println("\nNenapsal jste cele cislo, opakujte zadanim celeho cisla!.");
                 sc.next();
             }
         }
         sc.nextLine();
         return cislo;
     }
    
    void pridaniUpravaKnihy(int uprava, String novynazev) 
    {
        TypyKnih typ;
        boolean opakovat= true;
        String autori;
        try 
        {
            if (uprava == 2) 
                System.out.println("Napiste nove udaje o knize:");
                String nazev = neExistujiciKniha(novynazev);
                if ("".equals(nazev)) 
                    throw new ProjektException("Navrat do systemu!");
                do
                {
                	System.out.println("\nNapiste autora knihy, v pripade vice autoru oddelujte carkou , ");
                	autori = sc.nextLine();
                }  
                while (autori =="");
                
                System.out.println("\nNapiste rok vydani knihy: ");
                int rokVydani = pouzeCelaCislaRokVydani();
                System.out.println("\nNastavte dostupnost knihy, je dostupna? Napiste ano / ne");
                boolean dostupnost = AnoNe();
            

            if (uprava == 1) 
            {
                typ = typKnihy();
            } 
            else 
            {
                typ = this.getTypyKnih(novynazev);
            }

			if (typ == TypyKnih.ucebnice) 
			{
                System.out.println("\nNapiste, pro jaky rocnik ZS je ucebnice urcena, prijatelne rocniky 1 az 9");
                while (opakovat) 
                {
                    try 
                    {
                        int rocnik = pouzeCelaCisla();
                        if (rocnik < 1 || rocnik > 9)
                            throw new ProjektException("\nNeplatny rocnik ucebnice. Napiste prosim rocnik ucebnice znovu.");

                        opakovat = false;
                        Ucebnice novaKniha = new Ucebnice(nazev, autori.split(","), rokVydani, dostupnost, rocnik);
                        if (uprava == 1)
                            this.pridaniKnihy1(novaKniha);
                        else
                            this.upraveniKnihy(novynazev, novaKniha);
                    } 
                    catch (ProjektException e) 
                    {
                        System.out.println(e.getMessage());
                    }
                }
            } 
			else if (typ == TypyKnih.roman) 
			{
                Zanry zanr = ZanryRomanu();
                Roman novaKniha = new Roman(nazev, autori.split(","), rokVydani, dostupnost, zanr);
                if (uprava == 1)
                    this.pridaniKnihy1(novaKniha);
                else
                    this.upraveniKnihy(novynazev, novaKniha);
            } 
			else 
            {
                System.out.println("\nTypy knih jsou pouze ucebnice a roman! Zadejte typ knihy znovu!");
            }
        } 
        catch (ProjektException e) 
        {
            System.out.println(e.getMessage());
        }

    } 

    public static Zanry ZanryRomanu() 
    {
        Zanry zanr = null;
        boolean opakovat = false;
        System.out.println("\nNapiste zanr knihy: biograficky / dobrodruzny / historicky / profesni / valecny");

        while (!opakovat) 
        {
            String zanrString = sc.nextLine();
            try 
            {
                zanr = Zanry.valueOf(zanrString);
                opakovat = true;
            } 
            catch (IllegalArgumentException e) 
            {
                System.out.println("\nNapsal jste spatny nazev zanru. Zadejte prosim zanr znovu: biograficky / dobrodruzny / historicky / profesni / valecny.");
            }
        }

        return zanr;
    }

    public static TypyKnih typKnihy() 
    {
        TypyKnih typKnihy = null;
        boolean uspesne = false;
        
        while (!uspesne) 
        {
            System.out.println("\nNapiste typ knihy: ucebnice / roman");
            String typString = sc.nextLine().toLowerCase();
            try 
            {
                typKnihy = TypyKnih.valueOf(typString);
                uspesne = true;
            } 
            catch (IllegalArgumentException e) 
            {
                System.out.println("\nTypy knih jsou pouze ucebnice a roman! Zadejte typ knihy znovu!");
            }
        }

        return typKnihy;
    }

    public String existujiciKniha(String vstup) 
    {
        String nazev = null;
        boolean opakovat = true;
        
        System.out.println(vstup);
        while (opakovat) 
        {
            try 
            {
                nazev = sc.nextLine();
                if ("navrat".equals(nazev))
                    opakovat = false;
                else
                {
                	if (!this.overeniKnihy(nazev))
                
                        throw new ProjektException("\nVami napsana kniha jeste neni v systemu. Napiste jiny nazev knihy nebo navrat pro navraceni do systemu!");
                    opakovat = false;
                } 
            }
            catch (ProjektException e) 
            {
                System.out.println(e.getMessage());
            }
        }
        return nazev;
    }

    public  String neExistujiciKniha(String novynazev)
    {
        String nazev = null;
        boolean opakovat = true;
        System.out.println("\nZadejte nazev knihy:");
        
        while (opakovat) 
        {
            try 
            {
                nazev = sc.nextLine();
                if ("navrat".equals(nazev))
                    opakovat = false;
                else 
                {
                    if (this.overeniKnihy(nazev) && !novynazev.equals(nazev))
                        throw new ProjektException("\nKniha jiz v knihovne existuje. Zadejte jiny nazev knihy nebo navrat pro navraceni");
                    opakovat = false;
                }
            } 
            catch (ProjektException e) {
                System.out.println(e.getMessage());
            }
        }
        return nazev;
    }
	   	    
    public Knihovna() 
	{
    	SeznamKnih = new TreeMap<>();
	    NaseDatabaze = new Soubor();
	}
	    
	public boolean pridaniKnihy1(Kniha kniha) 
	{
	    if(SeznamKnih.put(kniha.getNazev(), kniha) == null)
	        return true;
	    else
	        return false;          
	}
	    
	public boolean upraveniKnihy(String nazev, Kniha kniha) 
	{
	    if(SeznamKnih.containsKey(nazev)) 
	    {
	        SeznamKnih.remove(nazev);
	        SeznamKnih.put(kniha.getNazev(), kniha);
	        return true;
	    }
	    else
	        return false;
	}
	    
	public boolean smazaniKnihy(String nazev) 
	{
	    if(SeznamKnih.remove(nazev) == null)
	        return false;
	    else
	        return true;
	}  
	    
	public boolean nastaveniDostupnostiKnihy(String nazev, boolean stavDostupnosti) 
	{
	    Kniha hledanaKniha;
	    if(SeznamKnih.containsKey(nazev)) 
	    {
	        hledanaKniha = SeznamKnih.get(nazev);
	        hledanaKniha.nastavDostupnost(stavDostupnosti);
	        return true;
	    }
	    else
	        return false;
	}
	
	public boolean vypsaniKnih() 
	{
	    try 
	    {
	        
	    	for (Kniha kniha : SeznamKnih.values()) 
	    	{
	    		System.out.println(kniha);
	        }
	    return true;
	    }	
	    catch(Exception e) 
	    {
	    	System.out.println("Knihy neni mozne vypsat");
	        return false;
	    }
	}
	
	public boolean vyhledaniKnihy(String nazev) 
	{
	    Kniha hledanaKniha;
	    if(SeznamKnih.containsKey(nazev)) 
	    {
	        hledanaKniha = SeznamKnih.get(nazev);
	        System.out.println("Vami hledana kniha je:" + hledanaKniha);
	        return true;
	    }
	    else
	        return false;
	}
	    
	
	    
	public void vypsaniKnihAutora(String jmeno) 
	{
		SortedMap<Integer, List<Kniha>> KnihyAutora = new TreeMap<>();
	    
	    for (Kniha kniha : SeznamKnih.values()) 
	    {
	    	String[] autori = kniha.getAutor();
	        for (String autor : autori) 
	        {
	            if (autor.equals(jmeno)) 
	            {
	                int rokVydani = kniha.getRokVydani();
	                KnihyAutora.putIfAbsent(rokVydani, new ArrayList<>());
	                KnihyAutora.get(rokVydani).add(kniha);
	            break;
	            }
	        }
	    }
	    System.out.println("\nAbecedne serazene knihy autora " + jmeno + ":");
	    
	    for (List<Kniha> knihy : KnihyAutora.values()) 
	    {
	    	for (Kniha kniha : knihy)
	    	{
	    		System.out.println(kniha);
	        }
	    }
	}
	    
	public void vypsaniZanruKnihy(TypyKnih typ) 
	{
	        
		System.out.println("\nVypis knih podle zanru: " + typ);
	    for (Kniha kniha : SeznamKnih.values()) 
	    {
	        if(kniha.getTypyKnih() == typ)
	        	System.out.println(kniha);
	    }
	}

	public void vypsaniVypujcenychKnih() 
	{
	        
		System.out.println("\nVypujcene knihy: ");
	    for (Kniha kniha : SeznamKnih.values()) 
	    {
	    	if(!kniha.getDostupnost())
	    		System.out.println(kniha);
	    }
	}
	
	public boolean ulozeniKnihy(String nazev) 
	{
		return NaseDatabaze.ulozitKnihu(SeznamKnih.get(nazev));
	}

	public boolean nacteniKnih(String nazev) 
	{
	        
		if (NaseDatabaze.nacistKnihu((TreeMap<String,Kniha>)SeznamKnih, nazev)) 
	    {
			System.out.println(SeznamKnih.get(nazev));
	        return true; 
	    } 
	    else 
	    {
	        return false;
	    }
	}

	public boolean nacteniKnihZDatabaze() 
	{
		SeznamKnih = NaseDatabaze.nacteniDatabaze();
	    return !SeznamKnih.isEmpty();
	                        
	} 
	
	public boolean ulozeniKnihDoDatabaze() 
	{
		if(NaseDatabaze.ulozeniDatabaze(new TreeMap<String,Kniha>(SeznamKnih)))
			return true;
	    else
	        return false;
	}

	public TypyKnih getTypyKnih(String nazev) 
	{
		Kniha vyhledanaKniha;
	    vyhledanaKniha = SeznamKnih.get(nazev);
	    return vyhledanaKniha.getTypyKnih();
	}

	public boolean overeniKnihy(String nazev) 
	{
	    return SeznamKnih.containsKey(nazev);
	}   
}


