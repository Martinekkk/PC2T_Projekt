package Knihovna;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.util.SortedMap;
import java.util.TreeMap;

import Knihovna.Roman.Zanry;

public class Soubor 
{
	
 private Connection NaseDatabaze;  
	 	    
	public boolean ulozitKnihu(Kniha kniha) 
	{
		
        try 
        {
            File soubor = new File("Knihy\\" + kniha.getNazev() + ".txt");
            soubor.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(soubor);
            fw.write(kniha.ulozeniDoSouboru());
            fw.close();
            System.out.println("\nUlozeni knihy " + kniha.getNazev() +" probehlo uspesne!");
            return true;
        }
        catch(IOException e) 
        {
            System.out.println("\nNastala chyba pri ukladani knihy: " + e.getMessage());
            return false;
        }
    }
	
	public boolean nacistKnihu(TreeMap<String,Kniha> SeznamKnih, String nazevSouboru) 
	{
        try 
        {
            Zanry[] zanr = Zanry.values();
            File soubor = new File("Knihy\\" + nazevSouboru + ".txt");
            BufferedReader in = new BufferedReader(new FileReader(soubor));

            String radek = in.readLine();

            String[] hodnoty = radek.split(";");

            String nazev = hodnoty[0];
            String[] autori = hodnoty[1].split(", ");
            int rokVydani = Integer.parseInt(hodnoty[2]);
            boolean dostupnost = Boolean.parseBoolean(hodnoty[3]);
            int typKnihy = Integer.parseInt(hodnoty[4]);
            int zanrRocnik = Integer.parseInt(hodnoty[5]);

            
            if(typKnihy==0) 
            {
            	Ucebnice ucebnice = new Ucebnice(nazev,autori,rokVydani,dostupnost,zanrRocnik);
                SeznamKnih.put(nazev, ucebnice);
            }
            else 
            {
            	Roman roman = new Roman(nazev, autori, rokVydani, dostupnost, zanr[zanrRocnik]);
                SeznamKnih.put(nazev, roman);
            }

            in.close();
            return true;

        } 
        catch (IOException e) 
        {
            System.out.println("\nNastala chyba pri nacteni knihy: " + e.getMessage());
            return false;
        }
    }
	public boolean ulozeniDatabaze(TreeMap<String, Kniha> SeznamKnih) 
	{
		return Connect.ulozeniKnihDoDatabaze(SeznamKnih, NaseDatabaze);
	}
	public SortedMap<String, Kniha> nacteniDatabaze() 
	{
		return Connect.nacteniKnihzDatabaze(NaseDatabaze);
	}
	
}
