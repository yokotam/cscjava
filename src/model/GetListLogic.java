package model;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class GetListLogic {

	private Date dateFrom;
	private Date dateTo;
	private String name;
	private int num = 50;
	private boolean sortDesc = true;
	private String comment;

	public Date getDateFrom() {
		return dateFrom;
	}


	public String getName() {
		return name;
	}


	public int getNum() {

		return num;

	}


	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setNum(int num) throws Exception {

		if( num >= 0 ){
			this.num = num;
		}else{
			throw new Exception("げったーろぼ");
		}

	}






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


	public boolean isSortDesc() {
		return sortDesc;
	}


	public void setSortDesc(boolean sortDesc) {
		this.sortDesc = sortDesc;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}


	public Date getDateTo() {
		return dateTo;
	}


	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
}
