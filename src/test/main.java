package test;

import java.text.SimpleDateFormat;

import model.GetListLogic;
import model.Hatsugen;
import model.Member;

public class main {

	public static void main(String[] args)  {

		//書き込み
		//オーバーライドは二つ
		//
		//WriteMessage();

		//表示
		ShowMessage();
	}

	private static void WriteMessage(){


		/* この処理は、ログイン時に実施。ログインメンバーはセッションで保持 */
		Member loginUser = new Member();
		loginUser.setId(1);		//←ログイン後に情報がセットされる想定だが、とりあえずＩＤをセット


		/* 発言クラスを生成 */
		Hatsugen hatsugen = new Hatsugen(loginUser);
		//Hatsugen hatsugen = new Hatsugen(loginUser,"わーい");

		GetListLogic getListLogic = new GetListLogic();


		/* 発言内容を投稿（新規） */
		try {
//			getListLogic.setComment("石井さんだってがんばってる。");
			//getListLogic.createComment("新規コメント");


			//getListLogic.createComment(100027, "コメント変更");

			getListLogic.editComment(100028, "28変更");
			//getListLogic.deleteComment(100026);


		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}



		/* 発言内容を投稿（変更） */


		/* 発言内容を投稿（新規：コメント） */




		/* 発言内容を投稿（変更：コメント） */


//		WriteLogic write = new WriteLogic();
//		write.Post(hatsugen);




	}

	private static void ShowMessage(){
		try {

			GetListLogic hatsugenList = new GetListLogic();


			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			//検索条件をセット
			hatsugenList.setNum(10);
			hatsugenList.setDateFrom(sdf.parse("20141210"));
//			hatsugenList.setComment("大吉");
//			hatsugenList.setSortDesc(false);

			for(Hatsugen hhh : hatsugenList.execute()){

				System.out.println(hhh.getId()+hhh.getComment());
				//System.out.println(hhh.getId());

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}




}
