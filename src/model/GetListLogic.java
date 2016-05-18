package model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class GetListLogic {

	private Date dateFrom;
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	private Date dateTo;
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	private int num = 50;
	public int getNum() {
		return num;
	}
	public void setNum(int num) throws Exception {
		if( num >= 0 ){
			this.num = num;
		}else{
			throw new Exception("げったーろぼ");
		}
	}

	private String type = "";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	private boolean sortDesc = true;
	public boolean isSortDesc() {
		return sortDesc;
	}
	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}

	private String comment;
	public String getComment() {
		return comment;
	}
	private void setComment(String comment) {
		this.comment = comment;
	}


	private int id = -1;


	public int getId() {
		return id;
	}
	private void setId(int id) {
		this.id = id;
	}
	public GetListLogic(){ }


	public List<Hatsugen> execute() {


		dao.HatsugenDao dao = new dao.HatsugenDao();
		List<Hatsugen> hatsugenList = null;
		try {
			hatsugenList = dao.findAll(this);
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return hatsugenList;
	}

	//親発言に対するコメント
	public void createComment(int id, String comment) throws SQLException{

		setId(id);
		createComment(comment);
	}

	//新規
	public void createComment(String comment) throws SQLException{

		setComment(comment);

		dao.HatsugenDao dao = new dao.HatsugenDao();
		dao.toukou(this);

	}

	//削除
	private void editComment(int id) throws SQLException{

		setId(id);
		dao.HatsugenDao dao = new dao.HatsugenDao();
		setType("delete");
		dao.toukou(this);
	}
	public void deleteComment(int id) throws SQLException{
		this.editComment(id);
	}


	//修正
	public void editComment(int id, String comment){

	}

}
