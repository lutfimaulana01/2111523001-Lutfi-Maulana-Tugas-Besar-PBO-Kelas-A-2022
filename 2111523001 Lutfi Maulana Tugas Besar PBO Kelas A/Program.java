import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.sql.*;

public class Program{  
    //static Scanner scanner;
	static Connection conn;
    static String url = "jdbc:mysql://localhost:3306/uaspbo";
    public static void main(String[] args) throws Exception{
        try (Scanner inputan = new Scanner (System.in)){
            String pilihanUser;
            boolean isLanjutkan = true;
            
            System.out.println("\nTOKO LUKISAN LUPI");
            System.out.println("TELP. 081371136137");

            try (Scanner input = new Scanner(System.in)){
                System.out.println("\n(Harap login sebagai admin terlebih dahulu)");
                System.out.println("\nMasukkan Kata Sandi: ");
                String password1 = input.nextLine();
                String password2="admin"; 
                System.out.println("Password " +password1.equals(password2) +"."); //method equals()

                //percabangan if 
                if(password1.equals(password2))
                {
                    System.out.println();
                    String salamTemu = "     --- Selamat datang!! ---";
                    String temu = salamTemu.trim(); //method trim()
                    System.out.println(temu); 
                }

                else
                {
                    System.out.println("\nMasukkan password dengan benar!");
                }

                    try{
                        CatatPemasukan step = new CatatPemasukan();
                        
                        Class.forName("com.mysql.cj.jdbc.Driver");
            	        conn = DriverManager.getConnection(url,"root","");
                        
                        
                        //perulangan while
                        while (isLanjutkan){
                            System.out.println("\n=========================================");
                            System.out.println("=\t    TOKO LUKISAN LUPI\t\t=");
                            System.out.println("=========================================");
            		        System.out.println("Database Penjualan.");
            		        System.out.println("1. Lihat Data Penjualan");
            		        System.out.println("2. Tambah Data Penjualan");
            		        System.out.println("3. Ubah Data Penjualan");
            		        System.out.println("4. Hapus Data Penjualan");
            		        System.out.println("5. Cari Data Penjualan");
            		
            		        System.out.print("\nPilihan Anda (1-5): ");
            		        pilihanUser = inputan.next();
                            Pemasukan pemasukan = new Pemasukan(1);
                            
            		
                            //percabangan switch case
            		        switch (pilihanUser)
                            {
                                case "1":
            			        step.show();
            			        break;

            		            case "2":
            			        step.insert();
            			        break;

            		            case "3":
            			        //admin();
                                step.edit();
            			        break;

            		            case "4":
            			        //admin();
                                step.delete();
            			        break;

            		            case "5":
            			        step.search();
            			        break;

            		            default:
            			        System.err.println("\nInputan Anda tidak ditemukan.\nSilakan pilih (1-5).");
                            }
                        
                        //memberikan pilihan kepada user apakah ingin kembali ke menu utama
                        System.out.print("\nApakah Anda ingin melanjutkan? [y/n] \n");
            		    pilihanUser = inputan.next();
            		    isLanjutkan = pilihanUser.equalsIgnoreCase("y");
                        System.out.print("\n");
                        }
                    
            	    System.out.println("Program selesai...");
                    }

                catch(ClassNotFoundException ex) //exception pada database
                {
                    System.err.println("Driver Error.");
                    System.exit(0);
                }
                catch(SQLException e) //exception pada database
                {
                    System.err.println("Koneksi tidak berhasil.");
                }
                finally
                {
                    //time and date
                    LocalDate tanggalSkrg = LocalDate.now(); 
                    LocalTime waktuSkrg = LocalTime.now();
                    System.out.println("--------------------------");               
                    System.out.println("Accessed on :");
                    System.out.println("Date\t: "+tanggalSkrg.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")));
                    System.out.println("Time\t: "+waktuSkrg.format(DateTimeFormatter.ofPattern("hh:mm:ss")) +" WIB");
                    System.out.println("--------------------------");

                    String salamPisah = "Program selesai. See you later! :)";
                    String pisah = salamPisah.replace("Program selesai.", "Thanks for using this program."); //method replace()
                    System.out.println(pisah);
                }
                
            }
        }             
    }

    private static void admin() throws SQLException 
    {
        //membuat objek HashMap baru
        Map<String, String> Login = new HashMap<String, String>();
        Scanner input = new Scanner(System.in);

        //membaca data di database penjualan_lukisan tabel admin
        String inputUsername, inputPassword;
        
        String sql = "SELECT * FROM admin"; //menghubungkan database tabel admin
        boolean relogin = true;
        conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
	    ResultSet result = statement.executeQuery(sql);

        //perulangan while
        while (result.next())
        {
            //mengambil nilai di database dan menyimpannya ke dalam variabel
            String username = result.getString("username");
            String password = result.getString("password");

            //input key dan value 
            Login.put(username, password);
        }
        System.out.println("===Silakan login terlebih dahulu===");

        //perulangan while
        while (relogin)
        {
            System.out.println("Masukkan username dan password yang benar!");
            System.out.print("Username : ");
            inputUsername = input.nextLine();
            System.out.print("Password : ");
            inputPassword = input.nextLine();

            //percabangan if 
            if (Login.containsValue(inputUsername)==true) //method bawaan HashMap
            {
                //percabangan if 
                if (Login.get(inputUsername).equals(inputPassword)) //method bawaan HashMap n method string
                {
                    System.out.println("Berhasil login");
                    relogin = false;
                }
                else
                {
                    relogin = true;
                }
            }
            else
            {
                relogin = true;
            }
        }
        statement.close();
    }
}