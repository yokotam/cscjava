package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
//	private String server = "localhost";
	private String dbname = "cscboard";
	private String url = "jdbc:postgresql://" + server + "/" + dbname;
	private String user = "postgres";
	private String password = "";

	public List<model.Hatsugen> findAll(model.GetListLogic jouken) throws SQLException{
		List<model.Hatsugen> hatsugenList = new ArrayList<model.Hatsugen>();

		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);
			//System.out.println(jouken.getNum());
			//Select文の準備
			String sql = "SELECT id,hatsugen,name,updatetime FROM v_keijiban WHERE TRUE " +
			//コメント内容
			((jouken.getComment() == null || jouken.getComment().length() == 0) ? "" : " AND hatsugen LIKE '%" + jouken.getComment() + "%' ") +
			//日付
			((jouken.getDateFrom() == null) ? "" : " AND updatetime >= '" + sdf.format(jouken.getDateFrom()) + "'") +
			((jouken.getDateTo() == null) ? "" : " AND updatetime <= '" + sdf.format(jouken.getDateTo()) + "'") +
			//並び順
			" ORDER BY sid " +
			(jouken.isSortDesc() ? "DESC" : "")
			+ " limit "+Integer.toString(jouken.getNum());

			pStmt = conn.prepareStatement(sql);


			System.out.println(sql);

			//SELECT文を実行
			rs = pStmt.executeQuery();

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

	public void toukou(model.GetListLogic jouken) throws SQLException{
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);
			System.out.println(jouken.getId());
			String sql;
//			if(jouken.getId() < 0){
			if(jouken.getType().equals("")){

				sql = "INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),?)";

			}else{


				if(jouken.getType().equals("DELETE")){
					sql = "INSERT INTO t_keijiban_truncate(id) VALUES(?)";
				}else{
					sql ="INSERT INTO t_keijiban(id,hatsugen,person) SELECT DISTINCT on(id) ?,?,person FROM t_keijiban WHERE id = ?";
				}
			}

/*
			--新規発言（コメントのみ）
			INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),'発言内容');

			--発言を削除（IDのみ）
			INSERT INTO t_keijiban_truncate(id) VALUES(6);

			--発言を修正（IDとコメント）
			INSERT INTO t_keijiban(id,hatsugen,person) SELECT DISTINCT on(id) 38,'発言の訂正に発言者情報は不要?',person FROM t_keijiban WHERE id = 38;


			--親発言に対するコメント（IDとコメント）
*/
			pStmt = conn.prepareStatement(sql);

			if(jouken.getId() < 0){
				pStmt.setString(1, jouken.getComment());
			}else{
				if(jouken.getComment() == null){
					pStmt.setInt(1, jouken.getId());
				}else{
					pStmt.setInt(1, jouken.getId());
					//pStmt.setInt(1, hatsugen.getUser().getId());
					pStmt.setString(2, jouken.getComment());
		//		pStmt.setString(1, "ちっくしょー");
					pStmt.setInt(3, jouken.getId());
				}
			}

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
