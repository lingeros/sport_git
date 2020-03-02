package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class userdataOperate {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private	String sql;
	DatabaseInformation d=new DatabaseInformation();
	
	public void create()	
	{
		try {
			
			conn=d.getconn();
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			sql="CREATE TABLE  userdata(\r\n" + 
					"	user_id varchar(16) PRIMARY KEY ,\r\n" + 
					"	user_name varchar(16),\r\n" + 
					"	user_sex varchar(4),\r\n" + 
					"	user_phone varchar(25))";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			
		}catch(Exception e) {
			
		}finally {
	    	d.close(conn, ps, rs);
	    }
	}
	public boolean	jduge(String uid)
	{
		boolean jduge=false;
		try {
			conn=d.getconn();
			 sql="select * from userdata where user_id=?";
			 ps = conn.prepareStatement(sql);
			 ps.setString(1,uid);
			 rs= ps.executeQuery();	
			while(rs.next())
			{
				if(rs.getString(1).equals(uid))jduge=true;
			}
			
		}catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally {
	    	d.close(conn, ps, rs);
	    }
		return jduge;
	}
	public void add(String user_id,String user_name ,String user_sex,String user_phone)
	{
		try {
			conn=d.getconn();
			 sql="INSERT INTO userdata(user_id,user_name,user_sex,user_phone )VALUES (?,?,?,?)";
			 ps = conn.prepareStatement(sql);
			 ps.setString(1,user_id);
			 ps.setString(2,user_name);
			 ps.setString(3,user_sex);
			 ps.setString(4,user_phone);
			int i= ps.executeUpdate();			
			 if(i!=0){
					DebugPrint.DPrint("��ӳɹ�");
				}
			
		}catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally {
	    	d.close(conn, ps, rs);
	    }
		
	}
	public void update(String uid,String uname ,String usex,String uphone)//����user_id�޸�������Ϣ
	{
		try {
			conn=d.getconn();
			sql="UPDATE userdata set user_name="+"'"+uname+"'"+
					",user_sex="+"'"+usex+"'"+",user_phone="+"'"+uphone+"'"
					+ " where user_id="+"'"+uid+"'";
			ps = conn.prepareStatement(sql);
			int i =ps.executeUpdate();
			if(i!=0){
				DebugPrint.DPrint("�޸ĳɹ�");
			}
		
		
		}
		catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally {
	    	d.close(conn, ps, rs);
	    }
		}
	public void select(ArrayList<String>array) {//������񣬲������д������
		try {
		conn=d.getconn();
		sql="select*from userdata";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		
		while(rs.next())
		{
			
				array.add(rs.getString(1)+","+rs.getString(2)
				+","+rs.getString(3)+","+rs.getString(4));
			
			}
		
		}catch(Exception e)
	    {
    	e.printStackTrace();
    }finally {
    	d.close(conn, ps, rs);
    }
}
	public void delete(String uid) {//����idɾ���û���Ϣ
	try {	
		conn=d.getconn();
		sql="DELETE FROM userdata\r\n" + 
				"WHERE user_id="+"'"+uid+"'";			
		ps = conn.prepareStatement(sql);
		int i =ps.executeUpdate();
		if(i!=0){
			DebugPrint.DPrint("ɾ���ɹ�");
		}
		}catch(Exception e)
	    {
	    	e.printStackTrace();
	    }finally {
	    	d.close(conn, ps, rs);
	    }
	}
	public void deleteAll() {//����idɾ���û���Ϣ
		try {
			conn=d.getconn();
			sql="DELETE FROM userdata";
			ps = conn.prepareStatement(sql);
			int i =ps.executeUpdate();
			if(i!=0){
				DebugPrint.DPrint("ɾ���ɹ�");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			d.close(conn, ps, rs);
		}
	}
	public int getPgNum()
	{	int i=-1; 
		try {
		conn=d.getconn();
		sql="select*from userdata";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		while(rs.next())
		{
			i++;
		}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		i=i/20+1;
		return i;
	}
	public String select(String s)
	{	 String a="";
		try {
			conn=d.getconn();
			sql="select*from userdata where user_id =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, s);
			rs = ps.executeQuery();
		
			while(rs.next())
			{
				a=rs.getString(2);
				}
			
			}catch(Exception e)
		    {
	    	e.printStackTrace();
	    }finally {
	    	d.close(conn, ps, rs);
	    }
		return a;
	}
	public void selectID(ArrayList<String>array) {//������񣬲���ID�������
		try {
		conn=d.getconn();
		sql="select*from userdata";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		int i=0;
		while(rs.next())
		{	
				array.add(rs.getString(1));		
			}
		
		}catch(Exception e)
	    {
    	e.printStackTrace();
    }finally {
    	d.close(conn, ps, rs);
    }
	}
	public String selectName(String s) {//�����û�id��ѯ������������
		String a="";
		try {
		conn=d.getconn();
		sql="select user_name  from userdata where user_id =?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, s);
		rs = ps.executeQuery();
		
		while(rs.next())
		{			
			a=rs.getString(1);
			}
		
		}catch(Exception e)
	    {
    	e.printStackTrace();
    }finally {
    	d.close(conn, ps, rs);
    }
		return a;
	}
}
