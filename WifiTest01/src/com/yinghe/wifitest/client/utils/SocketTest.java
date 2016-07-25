//package com.yinghe.wifitest.client.utils;
//
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.net.Socket;
//
//public class SocketTest {
//	protected void connectServerWithTCPSocket() {  
//		   Socket socket; 
//		   try {// 创建一个Socket对象，并指定服务端的IP及端口号 
//		     socket = new Socket("255.255.255.255", 9000);
//		      // 创建一个InputStream用户读取要发送的文件。 
//		      InputStream inputStream = new FileInputStream("e://a.txt"); 
//		      // 获取Socket的OutputStream对象用于发送数据。 
//		      OutputStream outputStream = socket.getOutputStream(); 
//		      // 创建一个byte类型的buffer字节数组，用于存放读取的本地文件 
//		      byte buffer[] = new byte[ 1024]; 
//		      int temp = ""; 
//		      // 循环读取文件 
//		      while ((temp = inputStream.read(buffer)) != -) { 
//		        // 把数据写入到OuputStream对象中 
//		        outputStream.write(buffer, , temp); 
//		      } 
//		      // 发送读取的数据到服务端 
//		      outputStream.flush(); 
//		  
//		      /** 或创建一个报文，使用BufferedWriter写入,看你的需求 **/
////		     String socketData = "[;fjks;]"; 
////		     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter( 
////		         socket.getOutputStream())); 
////		     writer.write(socketData.replace("\n", " ") + "\n"); 
////		     writer.flush(); 
//		      /************************************************/
//		    } catch (UnknownHostException e) { 
//		      e.printStackTrace(); 
//		    } catch (IOException e) { 
//		      e.printStackTrace(); 
//		    } 
//		  
//		  }
//
//}
