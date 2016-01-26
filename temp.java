import java.io.*;
import java.sql.*;
import org.apache.derby.jdbc.ClientDriver;


public class temp {

	public static void main(String[] args) throws IOException {
		
		Connection conn = null;
		try {
			
			// Step 1: connect to database server
			Driver d = new ClientDriver();
			String url = "jdbc:derby://localhost/tmp;create=false;";
			conn = d.connect(url, null);
		
		
		int usertype = 0;
		
		while (usertype != 4) {
			
		System.out.println("1 - Citizen");
		System.out.println("2 - Worker");
		System.out.println("3 - Supervisor");
		System.out.println("4 - Exit");
		System.out.println("Enter your choice : ");
		BufferedReader br = new BufferedReader (new InputStreamReader (System.in));
		String temp = br.readLine();
		usertype = Integer.parseInt(temp);
		
		switch (usertype) {
		
		case 1:
			System.out.println("Welcome Citizen");
			//citizen c1 = new citizen();
			System.out.println("1 - Create new request");
			System.out.println("2 - View my requests");
			System.out.println("Enter your choice : ");
			br = new BufferedReader (new InputStreamReader (System.in));
			String temp3 = br.readLine();
			int cho = Integer.parseInt(temp3);
			
			switch (cho) {
			case 1:
				//create new request
				
				// query to show all problem type and codes
				Statement stmt = conn.createStatement();
				String qry = "select ProblemCode, ProblemName "
				+ "from PROBLEM_TYPE ";
				
				ResultSet rs = stmt.executeQuery(qry);
				
				System.out.println("Problem Code	Problem Type");
				System.out.println("-----------------------------");
				
				while (rs.next()) {
					String code = rs.getString("ProblemCode");
					String type = rs.getString("ProblemName");
					System.out.println(code + "\t \t" + type);
				}
				rs.close();
				System.out.println("Enter problem code : ");
				
				String temp1 = br.readLine();
				int problemcode = Integer.parseInt(temp1);
				System.out.println("Enter Location : ");
				String location = br.readLine();
				System.out.println("Enter Description : ");
				String description = br.readLine();
				System.out.println("Enter reporter name : ");
				String reportername = br.readLine();
				System.out.println("Enter reporter phone : ");
				String phone = br.readLine();
				System.out.println("Enter reporter email : ");
				String email = br.readLine();
				
				//get count on number of records and put requestID=count +1
				
				Statement stmt1 = conn.createStatement();
				String qry1 = "select * "
				+ "from REQUEST "
				+ "order by RequestId";
				
				ResultSet rs1 = stmt1.executeQuery(qry1);
				int reccount = 0;
				 
				while (rs1.next()) {
					
					String strcount = rs1.getString("RequestId");
					reccount = Integer.parseInt(strcount);
				}
				rs1.close();
				
				reccount++;
				
							
				//check if phone no exists in reporter table, if no enter phone entry under that table
				
				Statement stmt2 = conn.createStatement();
				String qry2 = "select * "
				+ "from REPORTER "
				+ "where PHONE =" + phone;
				
				ResultSet rs2 = stmt2.executeQuery(qry2);
				int reccount2 = 0;
				while (rs2.next()) {
					
					reccount2++;
				}
				rs2.close();
				
				if (reccount2 == 0) { // does not exist
					
					Statement stmt3 = conn.createStatement();
					String qry3 = "insert "
					+ "into REPORTER values"
					+ "('" + reportername + "'," + phone + ",'" + email + "')";
					
					stmt3.executeUpdate(qry3);
									
					
				}
				//insert new record in request table
				
				Statement stmt4 = conn.createStatement();
				String qry4 = "insert "
				+ "into REQUEST values"
				+ "(" + reccount + "," + problemcode + ",'" + location + "','" + description + "','" + reportername + "'," + phone + ",'" + email + "')";
				
				stmt4.executeUpdate(qry4);
				
				Statement stmt4b = conn.createStatement();
				String qry4b = "insert "
				+ "into SCHEDULE values"
				+ "(" + reccount + ", 'pending' , 0 , 0 ,'Created')";
				
				stmt4b.executeUpdate(qry4b);
				
				System.out.println("New request created!");
				
				//query to show all requests matching "phone"
				
				Statement stmt5 = conn.createStatement();
				String qry5 = "select * "
						+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
						+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
						+ "where r.PHONE =" + phone;
				
				ResultSet rs5 = stmt5.executeQuery(qry5);
				System.out.println("RequestID	Problem Type		Location	Description	   Status");
				while (rs5.next()) {
				
					String reqid = rs5.getString("RequestId");
					String probtype = rs5.getString("ProblemName");
					String loc = rs5.getString("Location");
					String desc = rs5.getString("Description");
					String stat = rs5.getString("Status");
					
					System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + stat);
				
				}
				rs5.close();
				
				
				break;
			case 2:
				System.out.println("Please enter your phone number : ");
				phone = br.readLine();
				//query to show all requests matching "phone"
				Statement stmt6 = conn.createStatement();
				String qry6 = "select * "
				+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
				+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
				+ "where r.PHONE =" + phone;
				
				ResultSet rs6 = stmt6.executeQuery(qry6);
				System.out.println("RequestID	Problem Type		Location	Description	   Status");
				while (rs6.next()) {
				
					String reqid = rs6.getString("RequestId");
					String probtype = rs6.getString("ProblemName");
					String loc = rs6.getString("Location");
					String desc = rs6.getString("Description");
					String stat = rs6.getString("Status");
					
					System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + stat);
				
				}
				rs6.close();
				break;
			default:
				System.out.println("Invalid Entry!");
							
			}

			
			break;
		case 2:
			
			//worker w1 = new worker();
			System.out.println("Please enter your worker ID : ");
			br = new BufferedReader (new InputStreamReader (System.in));
			String workerID = br.readLine();
			
			//if workerid exists, proceed else  System.out.println("Invalid workerID");
			Statement stmt2 = conn.createStatement();
			String qry2 = "select * "
			+ "from WORKER "
			+ "where WorkerId =" + workerID;
			
			ResultSet rs2 = stmt2.executeQuery(qry2);
			int reccount2 = 0;
			while (rs2.next()) {
				
				reccount2++;
			}
			rs2.close();
			
			if (reccount2 == 0) {
				
				System.out.println("Invalid workerID");
			}
			else {
				System.out.println("Welcome Worker");
			
			
			System.out.println("1 - View assigned requests");
			System.out.println("2 - Update a request status");
			System.out.println("Enter your choice : ");
			String temp4 = br.readLine();
			int cho1 = Integer.parseInt(temp4);	
			
			switch (cho1) {
			
			case 1:			
				//query to show all requests with matching "workerID"
				
				Statement stmt6 = conn.createStatement();
				String qry6 = "select * "
				+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
				+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
				+ "where s.WrokerId =" + workerID;
				
				ResultSet rs6 = stmt6.executeQuery(qry6);
				System.out.println("RequestID	Problem Type		Location	Description	   VehicleID		Time		Status");
				while (rs6.next()) {
				
					String reqid = rs6.getString("RequestId");
					String probtype = rs6.getString("ProblemName");
					String loc = rs6.getString("Location");
					String desc = rs6.getString("Description");
					String veh = rs6.getString("VehicleId");
					String tim = rs6.getString("Time");
					String stat = rs6.getString("Status");
					System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t \t" + veh + "\t \t" + tim + "\t \t" + stat);
				
				}
				rs6.close();
				
				break;
				
			case 2:
				//query to show all requests with matching "workerID"
				
				Statement stmt5 = conn.createStatement();
				String qry5 = "select * "
				+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
				+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
				+ "where s.WrokerId =" + workerID;
				
				ResultSet rs5 = stmt5.executeQuery(qry5);
				System.out.println("RequestID	Problem Type		Location	Description	   VehicleID	Time	Status");
				while (rs5.next()) {
				
					String reqid = rs5.getString("RequestId");
					String probtype = rs5.getString("ProblemName");
					String loc = rs5.getString("Location");
					String desc = rs5.getString("Description");
					String veh = rs5.getString("VehicleId");
					String tim = rs5.getString("Time");
					String stat = rs5.getString("Status");
					System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + veh + "\t \t" + tim + "\t \t" + stat);
				
				}
				rs5.close();
				
				System.out.println("Enter the request ID you want to update : ");
				String reqID = br.readLine();
				System.out.println("1 - In progress");	
				System.out.println("2 - Finished");
				System.out.println("0 - Invalid Request");
				String status = br.readLine();
				int statusnum = Integer.parseInt(status); 
				switch (statusnum) {
				
				case 0:
					//query to update record with requestid = reqid and change status to Invalid Req
					
					Statement stmt4b = conn.createStatement();
					String qry4b = "update SCHEDULE set Status = 'Invalid Req' "
					+ "where ReqId = " + reqID;
					
					stmt4b.executeUpdate(qry4b);
					
					//query to show all requests with matching "workerID"
					
					Statement stmt4 = conn.createStatement();
					String qry4 = "select * "
					+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
					+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
					+ "where s.WrokerId =" + workerID;
					
					ResultSet rs4 = stmt4.executeQuery(qry4);
					System.out.println("RequestID	Problem Type		Location	Description	   VehicleID	Time	Status");
					while (rs4.next()) {
					
						String reqid = rs4.getString("RequestId");
						String probtype = rs4.getString("ProblemName");
						String loc = rs4.getString("Location");
						String desc = rs4.getString("Description");
						String veh = rs4.getString("VehicleId");
						String tim = rs4.getString("Time");
						String stat = rs4.getString("Status");
						System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + veh + "\t \t" + tim + "\t \t" + "\t \t" + stat);
					
					}
					rs4.close();
					break;
					
				case 1:
					//query to update record with requestid = reqid and change status to In progress
					
					Statement stmt3b = conn.createStatement();
					String qry3b = "update SCHEDULE set Status = 'In progress' "
					+ "where ReqId = " + reqID;
					
					stmt3b.executeUpdate(qry3b);
					
					//query to show all requests with matching "workerID"
					Statement stmt3 = conn.createStatement();
					String qry3 = "select * "
					+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
					+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
					+ "where s.WrokerId =" + workerID;
					
					ResultSet rs3 = stmt3.executeQuery(qry3);
					System.out.println("RequestID	Problem Type		Location	Description	   VehicleID	Time	Status");
					while (rs3.next()) {
					
						String reqid = rs3.getString("RequestId");
						String probtype = rs3.getString("ProblemName");
						String loc = rs3.getString("Location");
						String desc = rs3.getString("Description");
						String veh = rs3.getString("VehicleId");
						String tim = rs3.getString("Time");
						String stat = rs3.getString("Status");
						System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + veh + "\t \t" + tim + "\t \t" + stat);
					
					}
					rs3.close();
					break;
					
					
				case 2:
					//query to update record with requestid = reqid and change status to Finished
					
					Statement stmt2b = conn.createStatement();
					String qry2b = "update SCHEDULE set Status = 'Finished' "
					+ "where ReqId = " + reqID;
					
					stmt2b.executeUpdate(qry2b);
					
					//query to show all requests with matching "workerID"
					Statement stmt2a = conn.createStatement();
					String qry2a = "select * "
					+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
					+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
					+ "where s.WrokerId =" + workerID;
					
					ResultSet rs2a = stmt2a.executeQuery(qry2a);
					System.out.println("RequestID	Problem Type		Location	Description	   VehicleID	Time	Status");
					while (rs2a.next()) {
					
						String reqid = rs2a.getString("RequestId");
						String probtype = rs2a.getString("ProblemName");
						String loc = rs2a.getString("Location");
						String desc = rs2a.getString("Description");
						String veh = rs2a.getString("VehicleId");
						String tim = rs2a.getString("Time");
						String stat = rs2a.getString("Status");
						System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + veh + "\t \t" + tim + "\t \t" + stat);
					
					}
					rs2a.close();
					break;
					
				default:
					System.out.println("Invalid Input");	
				}
			}
			}
			break;
		case 3:
			System.out.println("Welcome Supervisor");
			//supervisor s1 = new supervisor();
			System.out.println("Please enter password : ");
			br = new BufferedReader (new InputStreamReader (System.in));
			String passwd = br.readLine();
					
			if (passwd.equals("123")) {
				
				System.out.println("Login Successful");
				int cho4 = 0;
				
				while (cho4 != 4) {
					
					System.out.println("1 - View all requests");
					System.out.println("2 - Delete a request");
					System.out.println("3 - Update a request");
					System.out.println("4 - Logout");
					System.out.println("Enter your choice : ");
					
					String temp5 = br.readLine();
					cho4 = Integer.parseInt(temp5);
					
					switch (cho4) {
					
					case 1:
						
						System.out.println("Sort by : ");
						System.out.println("1 - Date");
						System.out.println("2 - Priority");
						System.out.println("3 - Status");
						System.out.println("Enter your choice : ");
						
						String temp1 = br.readLine();
						int cho12 = Integer.parseInt(temp1);
						
						switch (cho12) {
						
						case 1:
							//query to show all requests with sorted time created
							
							Statement stmt1a = conn.createStatement();
							String qry1a = "select * "
							+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
							+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
							+ "order by r.RequestId";
							
							ResultSet rs1a = stmt1a.executeQuery(qry1a);
							System.out.println("RequestID	Problem Type		Location	Description	   WrokerID		VehicleID	Time	Priority	Status");
							while (rs1a.next()) {
							
								String reqid = rs1a.getString("RequestId");
								String probtype = rs1a.getString("ProblemName");
								String loc = rs1a.getString("Location");
								String desc = rs1a.getString("Description");
								String work = rs1a.getString("WrokerId");
								String veh = rs1a.getString("VehicleId");
								String tim = rs1a.getString("Time");
								String prio = rs1a.getString("PriorityLevel");
								String stat = rs1a.getString("Status");
								System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + work + "\t \t" + veh + "\t \t" + tim + "\t \t" + prio + "\t \t" + stat);
							
							}
							rs1a.close();
			
							break;
							
						case 2:
							//query to show all requests with sorted priority
							
							Statement stmt2a = conn.createStatement();
							String qry2a = "select * "
							+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
							+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
							+ "order by p.PriorityLevel";
							
							ResultSet rs2a = stmt2a.executeQuery(qry2a);
							System.out.println("RequestID	Problem Type		Location	Description	   WrokerID		VehicleID	Time	Priority	Status");
							while (rs2a.next()) {
							
								String reqid = rs2a.getString("RequestId");
								String probtype = rs2a.getString("ProblemName");
								String loc = rs2a.getString("Location");
								String desc = rs2a.getString("Description");
								String work = rs2a.getString("WrokerId");
								String veh = rs2a.getString("VehicleId");
								String tim = rs2a.getString("Time");
								String prio = rs2a.getString("PriorityLevel");
								String stat = rs2a.getString("Status");
								System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + work + "\t \t" + veh + "\t \t" + tim + "\t \t" + prio + "\t \t" + stat);
							
							}
							rs2a.close();
							break;
							
						case 3:
							//query to show all requests with sorted Status
							
							Statement stmt3a = conn.createStatement();
							String qry3a = "select * "
							+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
							+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)"
							+ "order by s.Status";
							
							ResultSet rs3a = stmt3a.executeQuery(qry3a);
							System.out.println("RequestID	Problem Type		Location	Description	   WrokerID		VehicleID	Time	Priority	Status");
							while (rs3a.next()) {
							
								String reqid = rs3a.getString("RequestId");
								String probtype = rs3a.getString("ProblemName");
								String loc = rs3a.getString("Location");
								String desc = rs3a.getString("Description");
								String work = rs3a.getString("WrokerId");
								String veh = rs3a.getString("VehicleId");
								String tim = rs3a.getString("Time");
								String prio = rs3a.getString("PriorityLevel");
								String stat = rs3a.getString("Status");
								System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + work + "\t \t" + veh + "\t \t" + tim + "\t \t" + prio + "\t \t" + stat);
							
							}
							rs3a.close();
							break;
							
						default:
							System.out.println("Invalid Entry");
							break;
						}
						
						break;
						
					case 2:
						//query to show all records with all details
						Statement stmt2a = conn.createStatement();
						String qry2a = "select * "
						+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
						+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)";
						
						ResultSet rs2a = stmt2a.executeQuery(qry2a);
						System.out.println("RequestID	Problem Type		Location	Description	   WrokerID		VehicleID	Time	Priority	Status");
						while (rs2a.next()) {
						
							String reqid = rs2a.getString("RequestId");
							String probtype = rs2a.getString("ProblemName");
							String loc = rs2a.getString("Location");
							String desc = rs2a.getString("Description");
							String work = rs2a.getString("WrokerId");
							String veh = rs2a.getString("VehicleId");
							String tim = rs2a.getString("Time");
							String prio = rs2a.getString("PriorityLevel");
							String stat = rs2a.getString("Status");
							System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + work + "\t \t" + veh + "\t \t" + tim + "\t \t" + prio + "\t \t" + stat);
						
						}
						rs2a.close();
						
						System.out.println("Enter the request ID : ");
						String reqID = br.readLine();
						//query to delete the record of requestid = reqID
						
						Statement stmt1b = conn.createStatement();
						
						String qry1a = "delete from REQUEST where RequestId = " + reqID;
						stmt1b.executeUpdate(qry1a);
					
						String qry1b = "delete from SCHEDULE where ReqId = " + reqID;
						stmt1b.executeUpdate(qry1b);
						
						break;
						
					case 3:
						//query to show all records with all details
						
						Statement stmt3a = conn.createStatement();
						String qry3a = "select * "
						+ "from ((REQUEST r join SCHEDULE s on r.RequestId = s.ReqId) "
						+ "join PROBLEM_TYPE p on r.ProbCode = p.ProblemCode)";
						
						ResultSet rs3a = stmt3a.executeQuery(qry3a);
						System.out.println("RequestID	Problem Type		Location	Description	   WrokerID		VehicleID	Time	Priority	Status");
						while (rs3a.next()) {
						
							String reqid = rs3a.getString("RequestId");
							String probtype = rs3a.getString("ProblemName");
							String loc = rs3a.getString("Location");
							String desc = rs3a.getString("Description");
							String work = rs3a.getString("WrokerId");
							String veh = rs3a.getString("VehicleId");
							String tim = rs3a.getString("Time");
							String prio = rs3a.getString("PriorityLevel");
							String stat = rs3a.getString("Status");
							System.out.println(reqid + "\t \t" + probtype + "\t \t" + loc + "\t \t" + desc + "\t \t" + work + "\t \t" + veh + "\t \t" + tim + "\t \t" + prio + "\t \t" + stat);
						
						}
						rs3a.close();
						
						System.out.println("Enter the request number : ");
						String reqID1 = br.readLine();
						System.out.println("1 - Update status");
						System.out.println("2 - Update workerID");
						System.out.println("3 - Update vehicleID");
						System.out.println("4 - Update scheduled time");
						System.out.println("Enter your choice : ");
						String temp2 = br.readLine();
						int temp2num = Integer.parseInt(temp2); 
						
						switch (temp2num) {
						
						case 1:
							
							System.out.println("1 - In progress");	
							System.out.println("2 - Finished");
							System.out.println("3 - Assigned");
							System.out.println("0 - Invalid Request");
							String status = br.readLine();
							int statusnum = Integer.parseInt(status); 
							switch (statusnum) {
							
							case 0:
								//query to update record with requestid = reqid and change status to Invalid Request
								
								Statement stmt1c = conn.createStatement();
								String qry1c = "update SCHEDULE set Status = 'Invalid Request' "
								+ "where ReqId = " + reqID1;
								
								stmt1c.executeUpdate(qry1c);
								
								break;
								
							case 1:
								//query to update record with requestid = reqid and change status to In progress
								
								Statement stmt2b = conn.createStatement();
								String qry2b = "update SCHEDULE set Status = 'In progress' "
								+ "where ReqId = " + reqID1;
								
								stmt2b.executeUpdate(qry2b);
								
								break;
								
							case 2:
								//query to update record with requestid = reqid and change status to Finished
								
								Statement stmt3b = conn.createStatement();
								String qry3b = "update SCHEDULE set Status = 'Finished' "
								+ "where ReqId = " + reqID1;
								
								stmt3b.executeUpdate(qry3b);
								break;
								
							case 3:
								//query to update record with requestid = reqid and change status to Assigned
								
								Statement stmt4b = conn.createStatement();
								String qry4b = "update SCHEDULE set Status = 'Assigned' "
								+ "where ReqId = " + reqID1;
								
								stmt4b.executeUpdate(qry4b);
								break;
								
							default:
								System.out.println("Invalid Input");
								break;
							}
							
							break;
							
						case 2:
							System.out.println("Enter the worker ID : ");
							String workerID1 = br.readLine();
							//query to update the record matching reqID and change workerID1
							
							Statement stmt4b = conn.createStatement();
							String qry4b = "update SCHEDULE set WrokerId = " + workerID1
							+ "where ReqId = " + reqID1;
							
							stmt4b.executeUpdate(qry4b);
							break;
							
						case 3:
							System.out.println("Enter the vehicle ID : ");
							String vehicleID = br.readLine();
							//query to update the record matching reqID and change vehicleID
							
							Statement stmt3b = conn.createStatement();
							String qry3b = "update SCHEDULE set VehicleId = " + vehicleID
							+ "where ReqId = " + reqID1;
							
							stmt3b.executeUpdate(qry3b);
							break;
							
						case 4:
							System.out.println("Enter the scheduled date & time (mm/dd hh:mm) : ");
							String time = br.readLine();
							//query to update the record matching reqID and change time
							
							Statement stmt2b = conn.createStatement();
							String qry2b = "update SCHEDULE set Time = " + time
							+ "where ReqId = " + reqID1;
							
							stmt2b.executeUpdate(qry2b);
							break;
							
						default:
							System.out.println("Invalid Input");
							break;
							
						}
						
						
						break;
						
					case 4:
						System.out.println("Logout Successful");
						break;
						
					default:
						System.out.println("Invalid Entry");
						break;		
					
					}
					
				}
			} else {
				
				System.out.println("Invalid Password");
			}
			break;
		case 4:
			System.out.println("Good Bye!");
			break;
		default:
			System.out.println("Invalid Entry");
				
		}
		
		}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			// Step 4: Disconnect from the server
			try {
			if (conn != null)
			conn.close();
			}
			catch (SQLException e) {
			e.printStackTrace();
			}
		}
	}
}

