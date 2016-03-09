package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Hatsugen;

public class HatsugenDao {

	private Connection conn = null;
	private PreparedStatement pStmt = null;
	private ResultSet rs = null;

	// JDBCドライバの登録
	private String driver = "org.postgresql.Driver";
	// データベースの指定
	private String server = "210.129.133.232";
	private String dbname = "cscboard";
	private String url = "jdbc:postgresql://" + server + "/" + dbname;
	private String user = "postgres";
	private String password = "";

	public List<model.Hatsugen> findAll(model.GetListLogic jouken) throws SQLException{
		List<model.Hatsugen> hatsugenList = new ArrayList<model.Hatsugen>();

		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);
			//System.out.println(jouken.getNum());
			//Select文の準備
			String sql = "SELECT id,hatsugen,name,updatetime FROM v_keijiban ORDER BY sid DESC limit "+Integer.toString(jouken.getNum());


			pStmt = conn.prepareStatement(sql);

			//SELECT文を実行
			rs = pStmt.executeQuery();
			System.out.println("実行");

			//SELECT文の結果をArrayListに格納
			while(rs.next()){
				int id = rs.getInt("id");
				String comment = rs.getString("hatsugen");
				Hatsugen hatsugen = new Hatsugen(id,comment);
				hatsugenList.add(hatsugen);
			}

		}catch (SQLException e){
			System.err.println("SQL failed.");
			e.printStackTrace();
		} catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}finally{
			//データベースを切断
			if(rs != null) rs.close();
			if(pStmt != null) pStmt.close();
			if(conn != null) {
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return hatsugenList;

	}

	public void posts(model.Hatsugen hatsugen) throws Exception{

		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);
			String sql = "INSERT INTO t_keijiban(id,person,hatsugen) VALUES(nextval('keijibanid_seq'),?,?)";

			pStmt = conn.prepareStatement(sql);

			pStmt.setInt(1, hatsugen.getUser().getId());
			pStmt.setString(2, hatsugen.getComment());

			//INSERT文の実行
			pStmt.executeUpdate();

		}catch (SQLException e){
			System.err.println("SQL failed.");
			e.printStackTrace();
		} catch (ClassNotFoundException ex){
			ex.printStackTrace();
		}finally{
			//データベースを切断
			if(rs != null) rs.close();
			if(pStmt != null) pStmt.close();
			if(conn != null) {
				try{
					conn.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
}
