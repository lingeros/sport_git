package ling.originalSources;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

//记录所有手环传过来的数据
public class HistorybdOper {
	private Connection conn = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private	String sql;
	DatabaseInformation d=new DatabaseInformation();
	public void create()	
	{
		try {
			
			conn=d.getconn();
			sql="CREATE TABLE historybd(	\r\n" + 
					"	num integer not null auto_increment primary key,\r\n" +
					"	id varchar(25)not null ,\r\n" + //对应currentbdOper的id属性
					"	user_id varchar(16)NOT NULL,\r\n" + 
					"	user_name varchar(16),\r\n" + 
					"	equipment_id varchar(16)NOT NULL,\r\n" + 
					"	`condition` varchar(16),\r\n" +
					"	cycle_num varchar(4),\r\n" +
					"	hearbeat varchar(16),\r\n" + 
					"	`power` double(4),\r\n" +
					"	`long` varchar(16),\r\n" +
					"	lat varchar(16),\r\n" +
					"	set_time timestamp DEFAULT CURRENT_TIMESTAMP\r\n" + 
					"	)";
				ps = conn.prepareStatement(sql);
				ps.executeUpdate();
			
		}catch(Exception e) {
			
		}finally {
	    	d.close(conn, ps, rs);
	    }
	}	
	public void add(String id,String user_id,String user_name,
			String equipment_id,String condition,
			String cycle_num,String hearbeat,
			String power,String lon,String lat ,
			Timestamp set_time )
	{
		try {
			conn=d.getconn();
			 sql="INSERT INTO historybd(id,user_id,user_name,"
			 		+ "equipment_id,`condition`,cycle_num,hearbeat,"
			 		+ "`power`,`long`,lat,set_time)"
			 		+ " VALUES(?,?,?,?,?,?,?,?,?,?,?)";
			 ps = conn.prepareStatement(sql);
			 ps.setString(1,id);
			 ps.setString(2,user_id);
			 ps.setString(3,user_name);
			 ps.setString(4,equipment_id); 
			 ps.setString(5,condition);
			 ps.setString(6,cycle_num);
			 ps.setString(7,hearbeat);
			 ps.setString(8,power);
			 ps.setString(9,lon);
			 ps.setString(10,lat);
			 ps.setTimestamp(11,set_time);
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
	//查询某个同学的所有手环数据，添加到array
	public void select(String id,ArrayList<String>array )//�������к�id����,������ؽ�����array������
	{
	try {
		conn=d.getconn();
		sql="select * from historybd where id="+"'"+id+"'";
		ps = conn.prepareStatement(sql);
		rs=ps.executeQuery();	
		while(rs.next())
		{
			array.add(rs.getString(2)+","+rs.getString(3)+","+rs.getString(4)+","+
					rs.getString(5)+","+rs.getString(6)+","+rs.getString(7)+","+
					rs.getString(8)+","+rs.getString(9)+","+rs.getString(10)+","+
					rs.getString(11)+","+rs.getString(12));
		}		
	}catch(Exception e)
	{
		e.printStackTrace();
	}finally {
		d.close(conn, ps, rs);
		
	}
	}
	//查询某同学的所有轨迹点的数据(以数组形式存储东经数据和北纬数据)
	public void select(ArrayList<String> Aarray,ArrayList<String> Barray,String id)
	{
		try {
			int i=0;int j=0;
			conn=d.getconn();
			sql="select * from historybd where id ="+"'"+id+"'";
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				if(!rs.getString("long").equals("")&&!rs.getString("lat").equals("")) {
				Aarray.add(rs.getString("long"));
				Barray.add(rs.getString("lat"));
			}
					i++;j++;
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
		sql="SELECT COUNT(*) FROM historybd";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		i=rs.getInt(1);
		i=i/21+1;
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		i=i/20+1;
		return i;
	}
	public void delete(String id) {
		try {
			String sql ="delete from historybd where id="+"'"+id+"'";
			conn=d.getconn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally {
	    	d.close(conn, ps, rs);
	    }
	}
	public void deleteAll() {
		try {
			String sql ="delete from historybd";
			conn=d.getconn();
			ps = conn.prepareStatement(sql);
			ps.executeUpdate();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally {
			d.close(conn, ps, rs);
		}
	}
	public void command(String sql,ArrayList<String>array) {
		try {
			conn=d.getconn();
			ps = conn.prepareStatement(sql);
			rs=ps.executeQuery();
			while(rs.next())
			{
				array.add(rs.getString(2)+", "+rs.getString(3)+", "+
						rs.getString(4)+", "+rs.getString(5)+", "+rs.getString(6)+", "+
						rs.getString(7)+", "+rs.getString(8)+", "+rs.getString(9)+",("+
						rs.getString(10)+"/ "+rs.getString(11)+")"+", "+rs.getString(12));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}finally {
	    	d.close(conn, ps, rs);
	    }
	}
	public void Update_power( String power,String e_id )
	{
		try {
			conn=d.getconn();
			sql="update currentbd set `power`  =? where equipment_id =? and run='true'";
			ps = conn.prepareStatement(sql);
			ps.setString(1, power );
			ps.setString(2, e_id);
			ps.executeUpdate();	
			int t=ps.executeUpdate();
			DebugPrint.DPrint(t);
		}catch(Exception e)
	    {
    	e.printStackTrace();
    }finally {
    	d.close(conn, ps, rs);
    }
	}
	
	public void Update_lat_lng(String lng,String lat,String id,String uid,String eid)
	{	
		
		try {
			conn=d.getconn();
			sql="INSERT INTO  historybd (id,user_id,equipment_id,`long`,lat)VALUES(?,?,?,?,?) ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ps.setString(2, uid);
			ps.setString(3, eid);
			ps.setString(4, lng);
			ps.setString(5, lat);
			
			ps.executeUpdate();	
			int t=ps.executeUpdate();
			DebugPrint.DPrint("y");
		}catch(Exception e)
	    {
    	e.printStackTrace();
    }finally {
    	d.close(conn, ps, rs);
    }


	}
}
