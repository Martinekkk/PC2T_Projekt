package Knihovna;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.SortedMap;
import java.util.TreeMap;

import Knihovna.Roman.Zanry;

public class Connect 
{
		private static Connection conn; 
		public  boolean connect () 
		{ 
		       conn= null; 
		       try 
		       {
		    	   	  Class.forName("org.sqlite.JDBC");
		              conn = DriverManager.getConnection("jdbc:sqlite:Databaze.db");                       
		       } 
		      catch (SQLException e) 
		       { 
		            System.out.println(e.getMessage());
			    return false;
		      }
		      catch (Exception e) 
		      { 
		    	  	e.printStackTrace();
			    return false;
		      }
		      return true;
		}
				
		public boolean createTable()
		{
		     if (conn==null)
		           return false;
		    String sql = "CREATE TABLE IF NOT EXISTS databaze (" + "id integer PRIMARY KEY," + "nazev varchar(255) NOT NULL," + "autor varchar(255) NOT NULL, " + "rokVydani int, " + "dostupnost boolean, " + " TypyKnih int, " + "ZanrRocnik int " + ");";
		    try
		    {
		    	Statement stmt = conn.createStatement(); 
		        stmt.execute(sql);
		        return true;
		    } 
		    catch (SQLException e) 
		    {
		    System.out.println(e.getMessage());
		    }
		    return false;
		}
		
		public static SortedMap <String,Kniha> nacteniKnihzDatabaze(Connection connection)
		{	
			SortedMap<String, Kniha> knihy = new TreeMap<>();
		    Zanry[] zanr = Zanry.values();
		    String[] autori;
	        String sql = "SELECT id, nazev, autor, rokVydani, dostupnost, TypyKnih, ZanrRocnik FROM databaze";
	        
	        try 
	        {
	        	Statement stmt  = conn.createStatement();
	        	ResultSet rs    = stmt.executeQuery(sql);
	             while (rs.next()) 
	             {
	                String nazev = rs.getString("Nazev");
	                String autor = rs.getString("Autor");
	                int rokVydani = rs.getInt("RokVydani");
	                boolean dostupnost = rs.getBoolean("Dostupnost");
	                int typ = rs.getInt("TypyKnih");
	                int zanrRocnik = rs.getInt("ZanrRocnik");

	                autori = autor.split(", ");

	                if(typ==1)
	                {
	                	Roman roman = new Roman(nazev, autori, rokVydani, dostupnost, zanr[zanrRocnik]);
	                    knihy.put(nazev, roman);
	                }
	                else
	                {
	                	Ucebnice ucebnice = new Ucebnice(nazev, autori, rokVydani, dostupnost, zanrRocnik);
	                    knihy.put(nazev, ucebnice);
	                }
	             }
	        }
	        catch(SQLException e) 
	        {
	            System.out.println("Nastala chyba pri pristupu k databazi: " + e.getMessage());    
	        } 
	        catch(NullPointerException e) 
	        {
	            System.out.println("Nastala chyba pri nacitani knih, databaze neni pripojena!");
	        }
	        return knihy;
		}
	        
	    public static boolean ulozeniKnihDoDatabaze(TreeMap<String,Kniha> knihy, Connection naseDatabaze) 
	    {
	    	int ZanrRocnik;
	        	
	    	String sqlDelete = "DELETE FROM databaze";
		    String sqlInsert = "INSERT INTO databaze(nazev,autor,rokVydani,dostupnost,TypyKnih,ZanrRocnik) VALUES(?,?,?,?,?,?)";
		        
		    try 
		    {
		    	PreparedStatement pstmtd = conn.prepareStatement(sqlDelete);
		        PreparedStatement pstmti = conn.prepareStatement(sqlInsert); 
		            
		        pstmtd.executeUpdate();
		            
		        for(Kniha kniha : knihy.values())
		        {	                
		        	if(kniha.getTypyKnihInteger()==1)
		        		ZanrRocnik = ((Roman)kniha).getZanr().ordinal();
		            else
		                ZanrRocnik = ((Ucebnice)kniha).getRocnik();
		            
		        	pstmti.setString(1, kniha.getNazev());
		            pstmti.setString(2, kniha.autori());
		            pstmti.setInt(3, kniha.getRokVydani());
		            pstmti.setBoolean(4, kniha.getDostupnost());
		            pstmti.setInt(5, kniha.getTypyKnihInteger());
		            pstmti.setInt(6, ZanrRocnik);
		            pstmti.executeUpdate();
		            } 
		            return true;
		        } 
		        catch (SQLException e) 
		        {
		            System.out.println(e.getMessage());
		            System.out.println("Nastala chyba pri ukladani knih do databaze!");
		            return false;
		            
				} 
		        catch (NullPointerException e) 
		        {
					e.printStackTrace();					
					System.out.println("Nastala vhyba pri ukladani knih, databaze neni pripojena!");
			        return false;
				}
	    }
			
		public void disconnect() 
		{ 
			if (conn != null) 
			{
				try 
				{     
					conn.close();  
				} 
				catch (SQLException ex) 
				{ 
					System.out.println(ex.getMessage()); 
				}
			}
		}
}
