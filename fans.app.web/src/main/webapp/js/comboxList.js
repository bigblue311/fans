jQuery(function(){
	jQuery(".item").click(function(){
		if(jQuery(this).hasClass("selected")){
			jQuery(this).removeClass("selected");
		} else {
			jQuery(this).addClass("selected");
		}
	});
	
	jQuery(".filter").keyup(function(){
		var keyword = jQuery(this).val();
		jQuery(this).parent().parent().find(".item").each(function(){
			if(jQuery(this).hasClass("selected")){
				jQuery(this).removeClass("selected");
			}
			jQuery(this).hide();
			jQuery(this).find("label").each(function(){
				var text = jQuery(this).text();
				if(text.contains(keyword)){
					jQuery(this).parent().show();
				}
			});
		});
	});

	jQuery(".selectAll").click(function(){
		jQuery(this).parent().parent().find(".item").each(function(){
			if(!jQuery(this).is(":hidden")){
				if(!jQuery(this).parent().hasClass("selected")){
	    			jQuery(this).addClass("selected");
	    		}   
			}
		});
	});
});
