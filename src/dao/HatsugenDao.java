package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.DoHatsugen;
import model.GetListLogic.WRITE;
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



	public void insertSQL(model.GetListLogic jouken) {

		if(conn == null)
			try {
				throw new Exception("直接呼べないよ");
			} catch (Exception e1) {
				// TODO 自動生成された catch ブロック
				e1.printStackTrace();
			}

		try {
			pStmt = conn.prepareStatement("INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),?)");
			pStmt.setString(1, jouken.getComment());
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}


	private boolean isNew;
	private boolean isEdit;
	private boolean isDelete;
	public boolean isNew() {return isNew;}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
		if(isNew){
			setEdit(false);
			setDelete(false);
		}
	}
	public boolean isEdit() {return isEdit;}
	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
		if(isEdit){
			setNew(false);
			setDelete(false);
		}
	}
	public boolean isDelete() {return isDelete;}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
		if(isDelete){
			setNew(false);
			setEdit(false);
		}
	}



	public void toukou_1(model.GetListLogic jouken) throws SQLException{
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);
			String sql = "";

			if(jouken.getDousa() == WRITE.NEW){
				sql = "INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),?)";
			}else if (jouken.getDousa() == WRITE.EDIT){
				sql ="INSERT INTO t_keijiban(id,hatsugen,person) SELECT DISTINCT on(id) ?,?,person FROM t_keijiban WHERE id = ?";
			}else if (jouken.getDousa() == WRITE.DELETE){
				sql = "INSERT INTO t_keijiban_truncate(id) VALUES(?)";
			}

			//WRITE は３種類しか存在しないので、sqlが空白になることは無い。
			pStmt = conn.prepareStatement(sql);

			if(jouken.getDousa() == WRITE.NEW){
				pStmt.setString(1, jouken.getComment());
			}else if (jouken.getDousa() == WRITE.EDIT){
				pStmt.setInt(1, jouken.getId());
				pStmt.setString(2, jouken.getComment());
				pStmt.setInt(3, jouken.getId());
			}else if (jouken.getDousa() == WRITE.DELETE){
				pStmt.setInt(1, jouken.getId());
			}

			//SQL文の実行
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


	public void toukou_2(model.GetListLogic jouken) throws SQLException{
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);

			String sql = "";

			if(isNew()){
				sql = "INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),?)";
			}else if (isEdit()){
				sql ="INSERT INTO t_keijiban(id,hatsugen,person) SELECT DISTINCT on(id) ?,?,person FROM t_keijiban WHERE id = ?";
			}else if (isDelete()){
				sql = "INSERT INTO t_keijiban_truncate(id) VALUES(?)";
			}

			//フラグのセットし忘れの場合、sqlが空白になる可能性がある
			if(sql.equals(""))
				return;

			pStmt = conn.prepareStatement(sql);

			if(isNew()){
				pStmt.setString(1, jouken.getComment());
			}else if (isEdit()){
				pStmt.setInt(1, jouken.getId());
				pStmt.setString(2, jouken.getComment());
				pStmt.setInt(3, jouken.getId());
			}else if (isDelete()){
				pStmt.setInt(1, jouken.getId());
			}

			//SQL文の実行
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


	public void toukou_3(model.GetListLogic jouken, WRITE action) throws SQLException{
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);

			String sql = "";

			if(action.equals(WRITE.NEW)){
				sql = "INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),?)";
			}else if (action.equals(WRITE.EDIT)){
				sql ="INSERT INTO t_keijiban(id,hatsugen,person) SELECT DISTINCT on(id) ?,?,person FROM t_keijiban WHERE id = ?";
			}else if (action.equals(WRITE.DELETE)){
				sql = "INSERT INTO t_keijiban_truncate(id) VALUES(?)";
			}

			pStmt = conn.prepareStatement(sql);

			if(action.equals(WRITE.NEW)){
				pStmt.setString(1, jouken.getComment());
			}else if (action.equals(WRITE.EDIT)){
				pStmt.setInt(1, jouken.getId());
				pStmt.setString(2, jouken.getComment());
				pStmt.setInt(3, jouken.getId());
			}else if (action.equals(WRITE.DELETE)){
				pStmt.setInt(1, jouken.getId());
			}

			//SQL文の実行
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

	public void toukou_4_NEW(model.GetListLogic jouken) throws SQLException{
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);

			String sql = "INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),?)";

			pStmt = conn.prepareStatement(sql);

			pStmt.setString(1, jouken.getComment());

			//SQL文の実行
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


	public void toukou_6_NEW(model.GetListLogic jouken) throws SQLException{

			String sql = "INSERT INTO t_keijiban(id,hatsugen) VALUES(nextval('keijibanid_seq'),'" + jouken.getComment() + "')";

			toukou_6(jouken,sql);

	}

	public void toukou_6(model.GetListLogic jouken,String sql) throws SQLException{
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);


			pStmt = conn.prepareStatement(sql);

//			pStmt.setString(1, jouken.getComment());

			//SQL文の実行
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


	public void toukou_5(model.GetListLogic jouken, DoHatsugen sql) throws SQLException {
		try{
			// JDBCドライバを読み込む
			Class.forName(driver);
			// データベースに接続
			conn = DriverManager.getConnection(url,user,password);

			sql.DoSQL(jouken);
			//実行
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
