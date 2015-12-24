if (typeof WeixinJSBridge == "undefined"){
   if(document.addEventListener){
       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
   }else if (document.attachEvent){
       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
   }
}

function onBridgeReady(){
	$('.pay').on('click', function() {
		var type = $(this).attr('type');
		var cash = $(this).attr('cash');
		var data1 = $(this).attr('data1');
		var reUrl = $(this).attr('reUrl');
		$.ajax({
			  url: '/api/getPrepay.json?type='+type+"&cash="+cash+"&data1="+data1,
			  type: 'POST',
			  success: function(res){
				  if(!res.success){
					  alert(res.message);
					  return;
				  }
				  var data = res.dataObject;
				  if(data.resultCode != "SUCCESS") {
					  alert(data.returnMsg);
					  return;
				  }
				  try{
					  WeixinJSBridge.invoke('getBrandWCPayRequest',{  
		                "appId" : data.appId,              //公众号名称，由商户传入  
		                "timeStamp": data.timestamp,       //时间戳，自 1970 年以来的秒数  
		                "nonceStr" : data.nonceStr,        //随机串  
		                "package" :  data.packageValue,    //prepay_id=xxxx
		                "signType" : "MD5",         	   //微信签名方式:  
		                "paySign" : data.paySign           //微信签名  
			          },function(res){      
		                if(res.err_msg == "get_brand_wcpay_request:ok" ) {  
		                    window.location.href = "/index.htm";
		                }else{  
		                    alert("支付失败");
		                }  
			          });
				  } catch(e){
					  alert("支付失败");
				  }
			  },
			  dataType: "json"
		});
	});
}