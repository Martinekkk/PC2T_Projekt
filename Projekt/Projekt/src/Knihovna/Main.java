package Knihovna;


import java.util.Scanner;

public class Main 
{
	
	 private static Knihovna knihovna = new Knihovna();

	public static void main(String args []) 
	{
	String nazev;
	
		Connect con = new Connect();
		 if (con.connect())
			 System.out.println("Pripojeni k databazi se podarilo! ");
		 else 
			 System.out.println("Pripojeni k databazi se nepodarilo! ");
		 
		 if (con.createTable())
			 System.out.println("Vytvoreni tabulky se podarilo! ");
		 else 
			 System.out.println("Vytvoreni tabulky se nepodarilo! ");
		Scanner sc=new Scanner(System.in);
		knihovna.nacteniKnihZDatabaze();
		System.out.println("\n\nVitejte v systemu knihovny");
		int volba;	 
		int ukoncit;
		boolean run=true;
		while(run)
		{
			System.out.println("\n-----------------------------------------------");
			System.out.println("\nVyberte pozadovanou cinnost:");
			System.out.println("1 .. Pridani nove knihy");
			System.out.println("2 .. Uprava knihy");
			System.out.println("3 .. Smazani knihy");
			System.out.println("4 .. Oznacit vypujena/vracena");
			System.out.println("5 .. Vypis knih");
			System.out.println("6 .. Vyhledani knihy");
			System.out.println("7 .. Vypis knih podle autora");
			System.out.println("8 .. Vypis knih podle zanru");
			System.out.println("9 .. Vypis vypujcenych knih (ucebnice/roman)");
			System.out.println("10 .. Ulozeni knihy do souboru");
			System.out.println("11 .. Nacteni knihy ze souboru");
			System.out.println("12 .. Konec programu");
			volba=Knihovna.pouzeCelaCisla();
			switch(volba)
			{
				case 1:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Pridani nove knihy");
					knihovna.pridaniUpravaKnihy(1,"");
					break;
					
				case 2:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Uprava stavajici knihy");
					 nazev = knihovna.existujiciKniha("\nZadejte jméno knihy pro upravení:");
	                    if(!"navrat".equals(nazev)); 
	                    {
	                        knihovna.vyhledaniKnihy(nazev);
	                        System.out.println();
	                        knihovna.pridaniUpravaKnihy(2, nazev);
	                    }
					break;
					
				case 3:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Smazani knihy");
					nazev = knihovna.existujiciKniha("Zadejte nazev knihy, jenz chcete smazat");
					knihovna.smazaniKnihy(nazev);
					break;
					
				case 4:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Oznaceni knihy, zda je vypujena/vracena");
					nazev = knihovna.existujiciKniha("Zadejte nazev knihy pro oznaceni dostupna/vypujcena");
					System.out.println("Nastavte, zda je kniha dostupna (ano/ne)");
					boolean dostupnost = Knihovna.AnoNe();
					knihovna.nastaveniDostupnostiKnihy(nazev, dostupnost);
					break;
					
				case 5:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Vypis knih");
					knihovna.vypsaniKnih();				
					break;
					
				case 6:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Vyhledani knihy");
					nazev = knihovna.existujiciKniha("Zadejte nazev knihy, jenz chcete vyhledat");
					knihovna.vyhledaniKnihy(nazev);
					break;
					
				case 7:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Vypis knih podle autora");
					System.out.println("Napiste jmeno autora, jehoz knihy chcete vypsat");
					String autor = sc.nextLine();
					knihovna.vypsaniKnihAutora(autor);
					break;
					
				case 8:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Vypis knih podle zanru");
					knihovna.vypsaniZanruKnihy(Knihovna.typKnihy());
					break;
					
				case 9:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Vypis vypujcenych knih (ucebnice/roman)");
					knihovna.vypsaniVypujcenychKnih();
					break;
					
				case 10:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Ulozeni knihy do souboru");
					nazev = knihovna.existujiciKniha("Zadejte nazev knihy, jenz chcete ulozit");
					knihovna.ulozeniKnihy(nazev);
					break;
					
				case 11:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Nacteni knihy ze souboru");
					System.out.println("Napiste nazev knihy, jenz chcete nacist");
					nazev = sc.nextLine();
					knihovna.nacteniKnih(nazev);
					break;
					
				case 12:
					System.out.println("\n-----------------------------------------------");
					System.out.println("Opravdu ukoncit program? Pro konec napis 1");
					ukoncit = Knihovna.pouzeCelaCisla();
					if (ukoncit==1)
					{
					if (con == null) 
						System.out.println("Databaze neni pripojena");
					knihovna.ulozeniKnihDoDatabaze();
					System.out.println("Program byl ukoncen!");
					sc.close();
					run=false;
					con.disconnect();
					}
					break;
					
				default:
					System.out.println("\nNapsal jste spatne cislo, zadavejte cisla od 1 do 12!");
					System.out.println("-----------------------------------------------");
			}
		}	
		
		
	}
	 public static Knihovna getKnihovna()
		{
			return knihovna;
		}
}


