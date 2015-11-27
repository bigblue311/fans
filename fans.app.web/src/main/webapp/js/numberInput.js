jQuery(function(){
	jQuery(".numbericLeft").click(function(){
		if(jQuery(this).next().attr("disabled") == "disabled"){
			return;
		}
		var current = jQuery(this).next().val();
		if(current.isNumber()){
			current++;
    		jQuery(this).next().val(current);
		} else {
			jQuery(this).next().val("1");
		}
	});
	
	jQuery(".numbericRight").click(function(){
		if(jQuery(this).next().attr("disabled") == "disabled"){
			return;
		}
		var current = jQuery(this).prev().val();
		if(current.isNumber()){
			current--;
			if(current<=1) {
				current = 1;
			}
    		jQuery(this).prev().val(current);
		} else {
			jQuery(this).prev().val("1");
		}
	});
});