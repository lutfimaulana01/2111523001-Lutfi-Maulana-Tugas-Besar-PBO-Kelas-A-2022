import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CatatPemasukan extends Pemasukan{ //class catatPemasukan ini mewarisi seluruh method dan atribut dari class Pemasukan
    static Connection conn;
	String url = "jdbc:mysql://localhost:3306/uaspbo";

    public void delete(){ //menghapus data pada database
        System.out.println("\nHapus Data Penjualan.");
		
		try{
	        show();
            System.out.print("\nInput Nomor Transaksi: "); //menggunakan nomor transaksi untuk penghapusan data
            String keyword = temp.nextLine();
	        
	        String sql = "DELETE FROM penjualan_lukisan WHERE nomor LIKE '%"+keyword+"%'"; //menghubungkan database untuk menghapus data dari tabel penjualan_lukisan
	        conn = DriverManager.getConnection(url,"root","");
	        Statement statement = conn.createStatement();
	        //ResultSet result = statement.executeQuery(sql);
	        
	        if(statement.executeUpdate(sql) > 0){
	            System.out.println("Data penjualan berhasil dihapus. (Nomor "+nomor+")");
	        }
	    }
		catch(SQLException e){ // merupakan exception pada database
	        System.out.println("Terjadi kesalahan saat menghapus data.");
	    }
        catch(Exception e){ //merupakan exception saat salah input
            System.out.println("Input data yang benar!");
        }
	}

    public void search() throws SQLException{ //berguna untuk mencari data pada database
        System.out.println("\nCari Data Penjualan.");
				
		System.out.print("Input Nama Pembeli: "); //berguna untuk menggunakan nama pembeli untuk mencari data
		String keyword = temp.nextLine();
		
		String sql = "SELECT * FROM penjualan_lukisan WHERE nama LIKE '%"+keyword+"%'"; //berguna untuk menghubungkan database untuk menampilkan data dari tabel penjualan_lukisan
		conn = DriverManager.getConnection(url,"root","");
        Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sql); 
         
        //merupakan perulangan while
        while(result.next()){ //berguna untuk menemukan data yang dicari
            System.out.print("\nNomor Transaksi\t\t: ");
            System.out.print(result.getInt("nomor"));
            System.out.print("\nNama Pembeli\t\t: ");
            System.out.print(result.getString("nama"));
            System.out.print("\nVariasi\t\t\t: ");
            System.out.print(result.getString("variasi"));
            System.out.print("\nHarga Lukisan\t\t: ");
            System.out.print(result.getInt("harga_lukisan"));
            System.out.print("\nJumlah Pesanan\t\t: ");
            System.out.print(result.getInt("jumlah_pesanan"));
            System.out.print("\nPotongan\t\t: ");
            System.out.print(result.getInt("potongan"));
            System.out.print("\nTotal Bayar\t\t: ");
            System.out.print(result.getInt("total_bayar"));
            System.out.print("\n");
	    }   
    }
}