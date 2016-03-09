package model;


public class WriteLogic {

	public void Post(Hatsugen hatsugen)throws Exception{

		dao.HatsugenDao dao = new dao.HatsugenDao();
		dao.posts(hatsugen);


	}

}
