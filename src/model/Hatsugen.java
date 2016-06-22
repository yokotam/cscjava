package model;

import java.io.Serializable;
import java.util.Date;

public class Hatsugen implements Serializable{
	int id;
	String comment;
	Date date;	//発言日時
	Hatsugen oyahatsugen;	//親発言
	Member user;	//発言者

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Hatsugen getOyahatsugen() {
		return oyahatsugen;
	}

	public void setOyahatsugen(Hatsugen oyahatsugen) {
		this.oyahatsugen = oyahatsugen;
	}

	public Member getUser() {
		return user;
	}

	public void setUser(Member user) {
		this.user = user;
	}

	public int getId(){
		return id;
	}


	public Hatsugen(Member member){
		this.user = member;
	}


	public Hatsugen(int id,String comment){
		this.id = id;
		this.comment = comment;
	}

	public Hatsugen(Member member,String comment){
		this.user = member;
		this.comment = comment;
	}

}

/*
insert into hatsugen(name_id,text) values(1,'わ－いわーい');	--　新規発言
insert into hatsugen(name_id,text) values(1,'うにゃ？');	--　新規発言
insert into hatsugen(hatsugen_id,text) values(2,'んあ？');-- 発言の更新
insert into hatsugen(hatsugen_id) values(2);-- 発言の削除
insert into hatsugen(name_id,text) values(1,'をーをー');	--　新規発言
insert into hatsugen(hatsugen_id,text) values(5,'んあ？');-- 発言の更新
insert into hatsugen(hatsugen_id,text) values(5,'んああああ？');
*/

/*
select a.id,a.name_id,coalesce(b.text,a.text),coalesce(b.updatetime,a.updatetime) from hatsugen a
left join
(
SELECT hatsugen_id AS id,text,updatetime FROM hatsugen WHERE id IN(
select max(id) from hatsugen where hatsugen_id > 0
group by hatsugen_id
)
) b USING(id)
where
a.hatsugen_id = 0 AND
a.hatsugen_id not in (select hatsugen_id from hatsugen where text is null)

order by coalesce(b.updatetime,a.updatetime) desc,a.id desc

*/
