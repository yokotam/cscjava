package test;

import model.GetListLogic;
import model.Hatsugen;

public class main {

	public static void main(final String[] args)  {
		// TODO 自動生成されたメソッド・スタブ

		try {

		GetListLogic hatsugenList = new GetListLogic();


		hatsugenList.setName("石井");
		hatsugenList.setNum(-1);
	//	hatsugenList.setDateFrom();

		for(Hatsugen hhh : hatsugenList.execute()){


			System.out.println(hhh.getComment());

		}

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			System.out.println(e.getMessage());
		}


	}



		/* この処理は、ログイン時に実施。ログインメンバーはセッションで保持 */
//		Member loginUser = new Member();
//		loginUser.setId(1);		//←ログイン後に情報がセットされる想定だが、とりあえずＩＤをセット


		/* 発言クラスを生成 */
//		Hatsugen hatsugen = new Hatsugen(loginUser);
//		Hatsugen hatsugen = new Hatsugen(loginUser,"わーい");

		/* 発言内容を投稿（新規） */
//		hatsugen.setComment("いしだてくんはがんばった、と、思う、と、言えと言われた。");


		/* 発言内容を投稿（変更） */


		/* 発言内容を投稿（新規：コメント） */


		/* 発言内容を投稿（変更：コメント） */


//		WriteLogic write = new WriteLogic();
//		write.Post(hatsugen);



}
