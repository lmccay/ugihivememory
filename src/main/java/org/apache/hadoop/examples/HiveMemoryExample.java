/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
 package org.apache.hadoop.examples;

import java.security.PrivilegedExceptionAction;
import java.sql.*;

import org.apache.hadoop.security.UserGroupInformation;


public class HiveMemoryExample {

	//  JDBC credentials
	static final String JDBC_DRIVER = "org.apache.hive.jdbc.HiveDriver";
	static final String KEYTABDIR = "/etc/security/keytabs/hive.service.keytab";
	static final String HIVE_PRINCIPAL = "hive/example.com@EXAMPLE.COM";
	static final String JDBC_DB_URL = "jdbc:hive2://127.0.0.1:10000/default;principal=" + HIVE_PRINCIPAL;
	static final String USER = null; 
	static final String PASS = null; 
	
	
	static Connection getConnection() throws Exception{
		final UserGroupInformation ugi = UserGroupInformation.
		        loginUserFromKeytabAndReturnUGI(HIVE_PRINCIPAL, KEYTABDIR);
		
				Connection conn = (Connection) ugi.doAs(new PrivilegedExceptionAction<Object>() {
					public Object run() {    	        	  
						Connection con = null;
						try {
							Class.forName(JDBC_DRIVER);
							con =  DriverManager.getConnection(JDBC_DB_URL,USER,PASS);
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
						return con;
					}
				});

		return conn;
	}
	
	public static void main(String[] args) {

		UserGroupInformation.setConfiguration(new org.apache.hadoop.conf.Configuration());
		
		System.out.println("-- Test started ---");
		Runtime rtime = Runtime.getRuntime();

		
		for(int i=0; i<200000; i++) {
			Connection conn = null;
			try {
				conn = getConnection();		
			} catch (Exception e){
				e.printStackTrace();
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {}
			}
			
			//Print used memory
			System.out.println("Iteration = " + i + " Used Memory:" 
				+ (rtime.totalMemory() - rtime.freeMemory())  + " Bytes " );

			
		}

		System.out.println("Test ended  ");
	}
}
