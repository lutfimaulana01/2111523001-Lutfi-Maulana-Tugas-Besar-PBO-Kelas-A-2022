import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Pemasukan implements TokoLukisanLupi{ //class Pemasukan mengimplementasikan interface TOKO LUKISAN LUPI
    //static Scanner scanner;
    static Connection conn;
	String url = "jdbc:mysql://localhost:3306/uaspbo";

    //deklarasi variabel
    public Integer nomor, jmlPesanan, jmlVoucher, pilihan, harga, totalHarga, potongan, totalBayar;
    public String nama, variasi;

    //constructor
    public Pemasukan()
    {

    }

    public Pemasukan(Integer a)
    {
        System.out.println("Pilihan ditemukan.\n");
    }

    Scanner temp = new Scanner(System.in);

    public void show() throws SQLException{ //menampilkan data yang ada pada database
        String judul = "Informasi penjualan lukisan";
        String judulBaru = judul.replace("Informasi penjualan lukisan", "\nINFORMASI PENJUALAN"); //method replace
        System.out.println(judulBaru); 
        System.out.println("-------------------");

						
		String sql ="SELECT * FROM penjualan_lukisan"; //menghubungkan database untuk menampilkan data dari tabel penjualan_lukisan
		conn = DriverManager.getConnection(url,"root","");
		Statement statement = conn.createStatement();
		ResultSet result = statement.executeQuery(sql);
		
        //perulangan while
		while(result.next()){
			System.out.print("\nNomor Transaksi\t\t: ");
            System.out.print(result.getString("nomor"));
            System.out.print("\nNama Pembeli\t\t: ");
            System.out.print(result.getString("nama"));
            System.out.print("\nHarga Lukisan\t\t: ");
            System.out.print(result.getString("harga_lukisan"));
            System.out.print("\nJumlah Pesanan\t\t: ");
            System.out.print(result.getInt("jumlah_pesanan"));
            System.out.print("\nPotongan \t\t: ");
            System.out.print(result.getInt("potongan"));
            System.out.print("\nTotal Bayar\t\t: ");
            System.out.print(result.getInt("total_bayar"));
            System.out.print("\n");
		}
	}

    public void insert() throws SQLException{ //menginput data baru ke dalam database 
        System.out.println("\nInputkan Data Penjualan.");
		
        try {
        // nomor transaksi
        System.out.println("Masukkan Nomor Transaksi: ");
        nomor = temp.nextInt();

        // nama pembeli
        System.out.println("Masukkan Nama Pembeli: ");
        nama = temp.next();

        // pilih variasi
        System.out.println("\nDaftar Lukisan ");
        System.out.println("1 >> Raden Saleh \n2 >> Gerbang Alam \n3 >> Potret Diri \n4 >> Kapal Karam \n5 >> Pukul 12 Siang Hari");
        System.out.print("Masukkan Nomor Lukisan: ");
        pilihan = temp.nextInt();
        
        //percabangan if else
        // variasi
        if (pilihan == 1)
        {
            variasi = "Raden Saleh";
        } 
        else if (pilihan == 2)
        {
            variasi = "Gerbang Alam";
        } 
        else if (pilihan == 3)
        {
            variasi = "Potret Diri";
        } 
        else if (pilihan == 4)
        {
            variasi = "Kapal Karam";
        }
        else if (pilihan == 5)
        {
            variasi = "Pukul 12 Siang Hari";
        }
        else
        {
            System.out.println("Pilihan tidak tersedia.");
        }
        
        //percabangan if else
        // harga satuan
        if(pilihan == 1)
        {
            harga = 75000;
        }
        else if(pilihan == 2)
        {
            harga = 85000;
        } 
        else if (pilihan == 3)
        {
            harga = 80000;
        } 
        else if (pilihan == 4)
        {
            harga = 60000;
        } 
        else if (pilihan == 5)
        {
            harga = 70000;
        }
        else
        {
            System.out.println("\nHarga lukisan tidak tersedia.");
        }
        System.out.println("\nHarga: Rp" +harga);

        // jumlah pesanan
        System.out.print("Inputkan Jumlah Pesanan: ");
        jmlPesanan = temp.nextInt();
        
        // total harga
        totalHarga = jmlPesanan * harga; //operasi matematika

        // potongan
        System.out.print("Inputkan Jumlah Voucher: ");
        jmlVoucher = temp.nextInt();
        potongan = jmlVoucher * 5000; //operasi matematika
        System.out.println("\nPotongan: Rp" +potongan);

        // total bayar
        totalBayar = totalHarga - potongan; //operasi matematika
        System.out.println("Total Bayar: Rp" +totalBayar);

        //menghubungkan database untuk menginputkan data baru ke dalam tabel penjualan_lukisan 
        String sql = "INSERT INTO penjualan_lukisan (nomor, nama, variasi, harga_lukisan, jumlah_pesanan, potongan, total_bayar) VALUES ('"+nomor+"','"+nama+"','"+variasi+"','"+harga+"','"+jmlPesanan+"','"+potongan+"','"+totalBayar+"')";
	    conn = DriverManager.getConnection(url,"root","");
	    Statement statement = conn.createStatement();
	    statement.execute(sql);
	    System.out.println("\nInput Data Berhasil!!");
    }

        catch (SQLException e){ //exception pada database
            System.err.println("Terjadi kesalahan saat menginput data.");
        } 

        catch (InputMismatchException e){ //exception saat tipe data inputannya tidak sesuai
            System.err.println("Inputan harus berupa angka.");
        }
    }

    public void edit() throws SQLException{ //mengedit data pada database
        System.out.println("\nEdit Data Penjualan.");

        try{
            show();
            System.out.print("\nMasukkan Nomor Transaksi yang ingin diubah : "); //mengubah data menggunakan nomor transaksi
            Integer nomor = Integer.parseInt(temp.nextLine());
            
            String sql = "SELECT * FROM penjualan_lukisan WHERE Nomor = " +nomor; //menghubungkan database untuk mengubah data pada tabel penjualan_lukisan
            conn = DriverManager.getConnection(url,"root","");
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            
            //percabangan if
            if(result.next()){
                
                System.out.print("Nama baru ["+result.getString("Nama")+"]\t: "); //mengubah nama pembeli pada database 
                String nama = temp.nextLine();
                   
                sql = "UPDATE penjualan_lukisan SET nama='"+nama+"' WHERE nomor='"+nomor+"'";
                //System.out.println(sql);

                if(statement.executeUpdate(sql) > 0){
                    System.out.println("Data telah diperbarui (Nomor "+nomor+")");
                }
            }
            statement.close();        
        } 

		catch (SQLException e){ //exception pada database
        	System.err.println("Terjadi kesalahan saat mengubah data.");
            System.err.println(e.getMessage());
        }
	}

    public void delete(){ //memangil method delete

    }

    public void search() throws SQLException{ //memanggil method search

    }
}