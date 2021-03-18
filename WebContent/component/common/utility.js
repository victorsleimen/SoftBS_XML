function UtilObj(owner)
{
	this.owner = owner;
	this.browsers = {isChrome :  (window.navigator.userAgent.toLowerCase().indexOf("chrome")>=0)?true:false,
				     isMoz    :  (window.navigator.userAgent.toLowerCase().indexOf("mozilla")>=0)?true:false,
				     isIE     :  (window.navigator.userAgent.toLowerCase().indexOf("msie")>=0)?true:false};	
}

UtilObj.prototype.parseJSON = function (data)
{
	return ((typeof JSON)!="undefined"? JSON.parse(data):eval("("+data+")"));
};

UtilObj.prototype.prepareParameters = function (pageWin,pageDoc,paramStr)
{
	var tempParam = "";
	if(paramStr)
	{
		var paramArr = paramStr.split(",");
		
		for(var i=0;i<paramArr.length;i++)
		{
			if(pageDoc.getElementById(paramArr[i]))
			{
				tempParam +="&"+paramArr[i]+"="+encodeURIComponent(pageDoc.getElementById(paramArr[i]).value);
			}
			else if(pageWin[paramArr[i]] )
			{
				tempParam +="&"+paramArr[i]+"="+encodeURIComponent(pageWin[paramArr[i]]);
			}
		}
	}
	return tempParam;
	
};

UtilObj.prototype.centerElement=function (pageWin,pageDoc,container)
{
	  // calculate the current window width //
	   var width  = pageWin.innerWidth != null ? pageWin.innerWidth : pageDoc.documentElement && pageDoc.documentElement.clientWidth ? pageDocdocumentElement.clientWidth : pageDoc.body != null ? pageDoc.body.clientWidth : null;
	   // calculate the current window height //
	   var height = pageWin.innerHeight != null? pageWin.innerHeight : pageDoc.documentElement && pageDoc.documentElement.clientHeight ? pageDoc.documentElement.clientHeight : pageDoc.body != null? pageDoc.body.clientHeight : null;
	   // calculate the current window vertical offset //
	   var top   = typeof pageWin.pageYOffset != 'undefined' ? pageWin.pageYOffset : pageDoc.documentElement && pageDoc.documentElement.scrollTop ? pageDoc.documentElement.scrollTop : pageDoc.body.scrollTop ? pageDoc.body.scrollTop : 0;
	   // calculate the position starting at the left of the window //
	   var left  = typeof pageWin.pageXOffset != 'undefined' ? pageWin.pageXOffset : pageDoc.documentElement && pageDoc.documentElement.scrollLeft ? pageDoc.documentElement.scrollLeft : pageDoc.body.scrollLeft ? pageDoc.body.scrollLeft : 0;

	   var positions = {};
	   positions.topposition  = top + (height / 2) - (container.offsetHeight / 2);
	   positions.leftposition = left + (width / 2) - (container.offsetWidth / 2);

	  return  positions;
};

UtilObj.prototype.getAjaxObj=function()
{
	var httpObj  = null;
    if(this.browsers.isIE)
    {
       var version = ["MSXML2.XMLHTTP.6.0","MSXML2.XMLHTTP.3.0","MSXML2.XMLHTTP"];
       for(var i=0;i<version.length;i++)
       {
        try
	    {
            httpObj = new ActiveXObject(version[i]);
            break;
	    }
	    catch(e)
	    {
           // skip
	    }
	  }
    }
    else
    {
        httpObj = new XMLHttpRequest();
    }
    return httpObj;
};

UtilObj.prototype.addEvent=function(element,eventName,callBackHandler)
{
	if(this.browsers.isIE)
	{
		element.attachEvent(eventName,callBackHandler);
	}
	else
	{
		var zEvt = eventName.replace("on","");
		element.addEventListener(zEvt,callBackHandler,true);
	}
	
};

UtilObj.prototype.removeEvent=function(element,eventName,callBackHandler)
{
	if(this.browsers.isIE)
	{
		element.detachEvent(eventName,callBackHandler);
	}
	else
	{
		var zEvt = eventName.replace("on","");
		element.addEventListener(zEvt,callBackHandler,true);
	}	
};

UtilObj.prototype.checkbrowser=function()
{
	if ((navigator.appName =="Opera")) 
	{
		// (navigator.appName =="Microsoft Internet Explorer")
        // document.getElementById("supported-browsers-box").setAttribute("class", "supported-browsers not-supported");
        alertString = "The browser you are using";
        alertString += " ("+navigator.appName+") ";
        alertString += "is not supported for its use with SoftBS. ";
        alertString += "Please use some of the compatible browsers: Chrome, Firefox, Safari or Epiphany.";
        window.alert(alertString);
    }
}