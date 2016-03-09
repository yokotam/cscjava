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
	public void setComment(String comment) {
		this.comment = comment;
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

}
