<%@page import="com.Payment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>

<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/PaymentJS.js"></script>

<meta charset="ISO-8859-1">
<title>Payment Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
</head>
<body>

<div class="container"> 
		<div class="row">  
		
			<div class="col-8">       
				<h1 class="m-3">Payment Management</h1>        
				
				<form id="formPayment" name="formPayment" method="post" action="PaymentAPI">  
					User Name:  
					<input id="Username" name="Username" type="text" class="form-control form-control-sm">  
					
					<br> 
					Amount to be Paid:  
					<input id="Amount" name="Amount" type="text" class="form-control form-control-sm">  
					
					<br>
					 Payment Type:  
					 <input id="Type" name="Type" type="text" class="form-control form-control-sm">  
					 
					 <br> 
					 Card Type:  
					 <input id="CrdType" name="CrdType" type="text" class="form-control form-control-sm">  
					 
					
					 
					 
					 <br>  
					 <input id="btnSave" name="btnSave" type="submit" value="Save" class="btn btn-primary">  
					 <input type="hidden" id="hidPaymentIDSave" name="hiPaymentIDSave" value="Payment.jsp"> 
					 
				</form> 
				
				<div id="alertSuccess" class="alert alert-success"></div>  
				<div id="alertError" class="alert alert-danger"></div> 
				
				<br>
				 <br>
                   <div id="divPaymentGrid">   
					<%
   					Payment paymentdbObj = new Payment();
   					out.print(paymentdbObj.readPayment());
   					%>  
					
					<br>

				</div> 
                   
                </div>
            </div>
 		</div>	
</body>
</html>